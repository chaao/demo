package tools;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @author lichao
 *
 */
public class Algorithm {
	/**
	 * 获取list中所有元素的所有组合(包括全空组合)
	 */
	public static <T> List<Set<T>> allCombination(List<T> list) {

		List<Set<T>> result = Lists.newArrayList();

		int nCnt = list.size();

		int nBit = (0xFFFFFFFF >>> (32 - nCnt));

		for (int i = 1; i <= nBit; i++) {
			Set<T> group = Sets.newHashSet();
			for (int j = 0; j < nCnt; j++) {
				if ((i << (31 - j)) >> 31 == -1) {
					group.add(list.get(j));
				}
			}
			result.add(group);
		}
		result.add(new HashSet<T>());
		return result;
	}
}
