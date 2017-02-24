package zookeeper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import zookeeper.model.DefaultJsonType;
import zookeeper.model.JsonType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ZkConfigProperty {

	/**
	 * zk path
	 * 
	 * @return
	 */
	String path();

	/**
	 * 是否是json
	 * 
	 * @return
	 */
	boolean json() default false;

	Class<? extends JsonType<?>> jsonType() default DefaultJsonType.class;

}
