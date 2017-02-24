package tools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lichao
 * @date 2014年11月27日
 */
public enum SpringUtils {

	instance;

	private Logger logger = LogManager.getLogger(SpringUtils.class);
	private ApplicationContext applicationContext;

	private SpringUtils() {
		try {
			applicationContext = new ClassPathXmlApplicationContext("classpath*:spring.xml");
			logger.info("load Spring sucess!");
		} catch (Exception e) {
			logger.error("load spring error!" + e);
		}
	}

	public String getDisplayName() {
		return applicationContext.getDisplayName();
	}

	public Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	public <T> T getBean(Class<T> requiredType) {
		return applicationContext.getBean(requiredType);
	}

	public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return applicationContext.getBean(name, requiredType);
	}

}
