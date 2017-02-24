package tools;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import com.google.common.collect.Sets;

/**
 * @author lichao
 * @date 2015年8月29日
 *
 */
public class ModelUtils extends org.springframework.beans.BeanUtils {

	public static void copyPropertiesSkipNullAndId(Object source, Object target) throws BeansException {
		copyPropertiesSkipNullAndTime(source, target, "id");
	}

	public static void copyPropertiesSkipNullAndId(Object source, Object target, String... ignoreProperties)
			throws BeansException {

		Set<String> set = Sets.newHashSet(ignoreProperties);
		set.add("id");
		copyPropertiesSkipNullAndTime(source, target, set.toArray(new String[] {}));
	}

	public static void copyPropertiesSkipNull(Object source, Object target) throws BeansException {
		copyPropertiesSkipNullAndTime(source, target, (String[]) null);
	}

	public static void copyPropertiesSkipNull(Object source, Object target, String... ignoreProperties)
			throws BeansException {
		copyPropertiesSkipNullAndTime(source, target, ignoreProperties);
	}

	private static void copyPropertiesSkipNullAndTime(Object source, Object target, String... ignoreProperties)
			throws BeansException {
		Set<String> set = null;
		if (ignoreProperties != null && ignoreProperties.length > 0) {
			set = Sets.newHashSet(ignoreProperties);
		} else {
			set = Sets.newHashSet();
		}

		set.add("ctime");
		set.add("utime");

		copyProperties(source, target, true, set.toArray(new String[] {}));
	}

	private static void copyProperties(Object source, Object target, boolean skipNull, String... ignoreProperties)
			throws BeansException {

		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class<?> actualEditable = target.getClass();

		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;

		for (PropertyDescriptor targetPd : targetPds) {
			Method writeMethod = targetPd.getWriteMethod();
			if (writeMethod != null && (ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null) {
					Method readMethod = sourcePd.getReadMethod();
					if (readMethod != null && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0],
							readMethod.getReturnType())) {
						try {
							if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
								readMethod.setAccessible(true);
							}
							Object value = readMethod.invoke(source);
							if (!skipNull || value != null) {
								if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
									writeMethod.setAccessible(true);
								}
								writeMethod.invoke(target, value);
							}
						} catch (Throwable ex) {
							throw new FatalBeanException(
									"Could not copy property '" + targetPd.getName() + "' from source to target", ex);
						}
					}
				}
			}
		}
	}

}
