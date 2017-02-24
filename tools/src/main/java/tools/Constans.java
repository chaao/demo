package tools;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lichao
 * @date 2015年9月15日
 *
 */
public class Constans {

	/** 逗号 */
	public static final String SEPARATOR_COMMA = ",";
	/** 空格 */
	public static final String SEPARATOR_SPACE = " ";
	/** 空字符串 */
	public static final String SEPARATOR_EMPTY = "";
	/** 点号 */
	public static final String SEPARATOR_DOT = ".";
	/** 冒号 */
	public static final String SEPARATOR_COLON = ":";
	/** 分号 */
	public static final String SEPARATOR_SEMICOLON = ";";
	/** 下划线 */
	public static final String SEPARATOR_UNDERLINE = "_";

	public static final int SORT_METHOD_ASC = 1;
	public static final String SORT_METHOD_ASC_STRING = "asc";
	public static final int SORT_METHOD_DESC = 2;
	public static final String SORT_METHOD_DESC_STRING = "desc";
	public static final Map<String, Object> successResult = new HashMap<String, Object>();
	public static final Map<String, Object> erroResult = new HashMap<String, Object>();

	static {
		erroResult.put("success", false);
		successResult.put("success", true);
	}

}
