package launcher;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import webFramework.AbstractServer;
import webFramework.JettyConfig;

/**
 * @author chao.li
 * @date 2017年1月20日
 */
public class WebLauncher extends AbstractServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new WebLauncher().run();
	}

	@Override
	protected void config(JettyConfig config) throws Exception {
		Properties properties = new Properties();
		properties.load(this.getClass().getClassLoader().getResourceAsStream("jetty.properties"));

		String host = properties.getProperty("jetty.host");
		if (StringUtils.isNotBlank(host)) {
			config.setHost(host);
		}

		String port = properties.getProperty("jetty.port");
		if (StringUtils.isNotBlank(port)) {
			config.setPort(NumberUtils.toInt(port));
		}

		String maxThreads = properties.getProperty("jetty.maxThreads");
		if (StringUtils.isNotBlank(maxThreads)) {
			config.setMaxThreads(NumberUtils.toInt(maxThreads));
		}

		String minThreads = properties.getProperty("jetty.minThreads");
		if (StringUtils.isNotBlank(minThreads)) {
			config.setMinThreads(NumberUtils.toInt(minThreads));
		}

		config.setCheckTime(2000);
		config.openThreadMonitor();
	}

}
