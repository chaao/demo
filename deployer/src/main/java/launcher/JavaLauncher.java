package launcher;

import org.springframework.context.ApplicationContext;

import baseFramework.launcher.ContextLauncher;

/**
 * @author chao.li
 * @date 2016年11月11日
 */
public class JavaLauncher extends ContextLauncher {

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		JavaLauncher launcher = new JavaLauncher();
		launcher.run();
		launcher.join();
	}

	@Override
	protected void init() {
		super.init();
	}

	@Override
	protected void release(ApplicationContext context) {
		super.release(context);
	}

}
