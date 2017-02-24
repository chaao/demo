package tools;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author lichao
 * @date 2014年11月27日
 */
public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils {

	public static List<Long> toLong(String... str) {

		List<Long> list = Lists.newArrayList();
		if (str != null)
			for (String s : str) {
				list.add(toLong(s));
			}

		return list;
	}

}
