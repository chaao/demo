package zookeeper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import baseFramework.jackson.JsonUtils;
import zookeeper.annotation.ZkConfigProperty;
import zookeeper.annotation.ZkConfigType;
import zookeeper.exception.ServerInitException;
import zookeeper.model.DefaultJsonType;
import zookeeper.model.ZkPropertyInfo;
import zookeeper.model.ZkResult;

/**
 * @author lichao
 *
 */
public class ZkConfigServer {
	Logger log = LoggerFactory.getLogger(ZkConfigServer.class);
	private ZkClient client;

	public ZkConfigServer(String zkAddress, String projectName) {

		client = ZkClientFactory.createZkClient(zkAddress, projectName);

	}

	public void initStaticClass(Class<?> clazz) {
		ZkConfigType configType = clazz.getDeclaredAnnotation(ZkConfigType.class);

		if (configType == null)
			return;

		String rootPath = configType.path();

		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			ZkConfigProperty zkConfigProperty = field.getDeclaredAnnotation(ZkConfigProperty.class);
			if (zkConfigProperty == null) {
				continue;
			}
			if (!Modifier.isStatic(field.getModifiers())) {
				throw new ServerInitException(String.format("%s.%s is not static field!", clazz.getName(), field.getName()));
			}

			ZkPropertyInfo propertyInfo = new ZkPropertyInfo(zkConfigProperty.path(), field, true, zkConfigProperty.json(),
					zkConfigProperty.jsonType(), null);
			initProperty(rootPath, propertyInfo);
		}

	}

	public void initObject(Object obj) throws Exception {
		ZkConfigType configType = obj.getClass().getDeclaredAnnotation(ZkConfigType.class);
		if (configType == null)
			return;

		String rootPath = configType.path();

		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			ZkConfigProperty zkConfigProperty = field.getDeclaredAnnotation(ZkConfigProperty.class);
			if (zkConfigProperty == null) {
				continue;
			}
			ZkPropertyInfo propertyInfo = new ZkPropertyInfo(zkConfigProperty.path(), field, Modifier.isStatic(field.getModifiers()),
					zkConfigProperty.json(), zkConfigProperty.jsonType(), obj);
			initProperty(rootPath, propertyInfo);

		}
		if (obj instanceof ReloadInstance) {
			((ReloadInstance) obj).init();
		}

	}

	private void initProperty(String rootPath, ZkPropertyInfo propertyInfo) {
		String path = null;
		try {
			path = rootPath + "/" + propertyInfo.getPath();
			String value = client.getZnode(path);
			if (value != null) {
				ZkResult zkResult = JsonUtils.fromJson(value, ZkResult.class);
				setValue(zkResult.getResult(), propertyInfo);
				log.info("init property,path:{},result:{}", path, zkResult.getResult());
			} else {
				setValue("", propertyInfo);
				log.info("init property,path:{},result:", path);
			}
		} catch (Exception e) {
			log.error("init property error,path:" + path, e);
			throw new ServerInitException("property init error path:" + path, e);
		}
	}

	private boolean setValue(String valueStr, ZkPropertyInfo propertyInfo) throws Exception {
		Object value = null;
		if (StringUtils.isNotBlank(valueStr)) {
			if (propertyInfo.isJson()) {
				if (propertyInfo.getJsonType() == DefaultJsonType.class) {
					value = JsonUtils.fromJson(valueStr, propertyInfo.getField().getType());
				} else {
					value = JsonUtils.fromJson(valueStr, propertyInfo.getJsonType());
				}
			} else {
				Class<?> fieldType = propertyInfo.getField().getType();
				if (fieldType == Integer.class || fieldType == int.class) {
					value = Integer.parseInt(valueStr);
				} else if (fieldType == String.class) {
					value = valueStr;
				} else if (fieldType == Long.class || fieldType == long.class) {
					value = Long.parseLong(valueStr);
				} else if (fieldType == Double.class || fieldType == double.class) {
					value = Double.parseDouble(valueStr);
				} else if (fieldType == Boolean.class || fieldType == boolean.class) {
					value = Boolean.parseBoolean(valueStr);
				} else if (fieldType == Float.class || fieldType == float.class) {
					value = Float.parseFloat(valueStr);
				} else {
					throw new ServerInitException("not supper:" + fieldType);
				}
			}
		}
		propertyInfo.getField().setAccessible(true);
		Object oldValue = null;
		if (propertyInfo.isIsstatic()) {
			oldValue = propertyInfo.getField().get(null);
			propertyInfo.getField().set(null, value);
		} else {
			oldValue = propertyInfo.getField().get(propertyInfo.getObj());
			propertyInfo.getField().set(propertyInfo.getObj(), value);
		}

		if (value == null && oldValue == null) {
			return false;
		} else if (value != null && oldValue == null) {
			return true;
		} else if (value == null && oldValue != null) {
			return true;
		} else if (value.equals(oldValue)) {
			return false;
		} else {
			return true;
		}

	}

}
