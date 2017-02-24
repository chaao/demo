package kafka;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import baseFramework.utils.ArithUtil;

/**
 * @author chao.li
 * @date 2016年10月31日
 */
public class ConsumerHandler {
	private static Logger logger = LogManager.getLogger("consumer");

	@Value("${kafka.server}")
	private String server;
	@Value("${kafka.group}")
	private String group;
	@Value("${kafka.topic}")
	private String topic;

	KafkaConsumer<String, String> consumer;
	private volatile long lastTime;

	private BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);
	private ExecutorService singleExecutor;

	@PostConstruct
	public void init() throws Exception {
		Preconditions.checkNotNull(this.server, "kafka.server is required");
		Preconditions.checkNotNull(this.group, "kafka.group is required");
		Preconditions.checkNotNull(this.topic, "kafka.topic is required");

		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.server);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, this.group);// group
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "3000");// commit时间间隔
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");// 心跳
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");// 从最新开始读取
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringDeserializer");
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringDeserializer");
		KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(props);
		kafkaConsumer.subscribe(Lists.newArrayList(this.topic));

		this.consumer = kafkaConsumer;
		this.singleExecutor = Executors.newSingleThreadExecutor();

		singleExecutor.submit(new Runnable() {

			@Override
			public void run() {

				while (true) {
					try {
						ConsumerRecords<String, String> records = consumer.poll(100);
						if (records.count() > 0) {
							lastTime = System.currentTimeMillis();
							logger.info("Fetch {} data from the topic({})", records.count(), topic);
						}

						for (ConsumerRecord<String, String> record : records) {
							if (!queue.offer(record.value())) {
								logger.error("add queue error, queue filling:{}%",
										ArithUtil.div(queue.size(), 10000) * 100);
							}
						}
					} catch (Exception e) {
						logger.error("", e);
					}

				}
			}
		});
		logger.info("@@@@@@@@@@@@@@@@@@@@@@  init consumer finished!!!");
	}

	@PreDestroy
	public void destroy() {
		try {
			singleExecutor.shutdown();
			consumer.close();
		} catch (Exception e) {
			logger.error("destroy kafkaConsumer error!", e);
		}
		logger.info("@@@@@@@@@@@@@@@@@@@@@@  destroy kafkaConsumer finished!!!");
	}

	public String poll() {
		try {
			return this.queue.poll(3, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			logger.error("poll error!!!", e);
			return null;
		}
	}

	public long getLastTime() {
		return this.lastTime;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public static void main(String[] args) throws Exception {
		ConsumerHandler handler = new ConsumerHandler();
		handler.server = "127.0.0.1:9092";
		handler.group = "test";
		handler.topic = "request-uid-through-primary";

		handler.init();
		while(true){
			System.out.println(handler.poll());
		}

	}

}
