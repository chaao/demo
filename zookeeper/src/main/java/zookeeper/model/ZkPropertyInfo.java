package zookeeper.model;

import java.lang.reflect.Field;

public class ZkPropertyInfo {

	private String path;
	private Field field;
	private boolean isstatic;
	private boolean isJson;
	private Class<? extends JsonType<?>> jsonType;
	private Object obj;

	/**
	 * @param path
	 * @param field
	 * @param isstatic
	 * @param isJson
	 * @param jsonType
	 * @param obj
	 */
	public ZkPropertyInfo(String path, Field field, boolean isstatic, boolean isJson, Class<? extends JsonType<?>> jsonType, Object obj) {
		super();
		this.path = path;
		this.field = field;
		this.isstatic = isstatic;
		this.isJson = isJson;
		this.jsonType = jsonType;
		this.obj = obj;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the field
	 */
	public Field getField() {
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(Field field) {
		this.field = field;
	}

	/**
	 * @return the isstatic
	 */
	public boolean isIsstatic() {
		return isstatic;
	}

	/**
	 * @param isstatic the isstatic to set
	 */
	public void setIsstatic(boolean isstatic) {
		this.isstatic = isstatic;
	}

	/**
	 * @return the isJson
	 */
	public boolean isJson() {
		return isJson;
	}

	/**
	 * @param isJson the isJson to set
	 */
	public void setJson(boolean isJson) {
		this.isJson = isJson;
	}

	/**
	 * @return the jsonType
	 */
	public Class<? extends JsonType<?>> getJsonType() {
		return jsonType;
	}

	/**
	 * @param jsonType the jsonType to set
	 */
	public void setJsonType(Class<? extends JsonType<?>> jsonType) {
		this.jsonType = jsonType;
	}

	/**
	 * @return the obj
	 */
	public Object getObj() {
		return obj;
	}

	/**
	 * @param obj the obj to set
	 */
	public void setObj(Object obj) {
		this.obj = obj;
	}

}
