package tools;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Stopwatch;

import tools.DateTools;

/**
 * @author chao.li
 * @date 2016年11月10日
 */
public class HttpTest {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		Stopwatch watch  =Stopwatch.createStarted();
		int code = HttpClientUtil.getCode("http://www.baidu.com");
		System.out.println(String.format("code:%s time:%s", code,watch));
	}
	public static void test(){
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>(1000);

		for (int i = 0; i < 500; i++) {
			if (i % 2 == 0)
				// queue.offer("aaa:www.google.com/#q=" + i);
				queue.offer("aaa:www.google.com");
			else
				// queue.offer("aaa:www.baidu.com/s?wd=" + i);
				queue.offer("aaa:www.baidu.com");

		}

		ExecutorService processExecutor = Executors.newFixedThreadPool(50);
		for (int i = 0; i < 50; i++) {
			processExecutor.submit(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							String data = queue.poll(3, TimeUnit.SECONDS);
							if (StringUtils.isNotBlank(data)) {
								if (data.contains("www")) {
									Stopwatch watch = Stopwatch.createStarted();
									String url = "http://" + StringUtils.substringAfterLast(data, ":");
									int code = HttpClientUtil.getCode(url);
									if (code == 200) {
										System.out.println(DateTools.dateFormat(System.currentTimeMillis())+" "+watch.stop());
									} else {
										System.out.println("error");
									}
								}
							} else {
								break;
							}

						} catch (Exception e) {
						}
					}
				}
			});
		}
	}
}
