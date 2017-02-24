package beanutils;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.google.common.collect.Maps;

/**
 * @author lichao
 *
 */
public class Test {

	/**
	 * @param args
	 * @throws @throws
	 *             Exception
	 */
	public static void main(String[] args) throws Exception {

		Map<String, Object> map = Maps.newHashMap();
		
		
		
		map.put("id", new String[]{"1"});
		map.put("name",new Integer[]{5,6});
		map.put("age", new String[]{""});

		User user = new User();
		BeanUtils.populate(user, map);

		System.out.println(user);
	}

}
