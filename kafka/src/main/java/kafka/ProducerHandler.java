package kafka;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.google.common.base.Preconditions;

/**
 * @author chao.li
 *
 */
public class ProducerHandler {

	private Logger logger = LogManager.getLogger("producer");

	@Value("${kafka.server}")
	private String server;

	// The producer is thread safe
	private Producer<String, String> producer;

	@PostConstruct
	private void init() {
		Preconditions.checkNotNull(this.server, "kafka.server is required");

		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.server);
		props.put(ProducerConfig.ACKS_CONFIG, "1");// 1：只保证发送到leader成功，all：保证leader,follower都成功
		props.put(ProducerConfig.RETRIES_CONFIG, 1);// 重试次数
		// props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
		props.put(ProducerConfig.LINGER_MS_CONFIG, 5);// 延迟发送，以聚合更多消息
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 32 * 1024 * 1024L);// 缓存
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringSerializer");

		this.producer = new KafkaProducer<>(props);
		logger.info("@@@@@@@@@@@@@@@@@@@@@@  init producer finished!!!");
	}

	@PreDestroy
	public void destroy() {
		try {
			producer.close();
		} catch (Exception e) {
			logger.error("destroy kafkaProducer error!", e);
		}
		logger.info("@@@@@@@@@@@@@@@@@@@@@@  destroy kafkaProducer finished!!!");
	}

	/**
	 * @param topic
	 * @param partition
	 *            指定分区
	 * @param key
	 *            murmur2(key)
	 * @param value
	 */
	public Future<RecordMetadata> send(String topic, String value) {
		return producer.send(new ProducerRecord<String, String>(topic, value));
	}

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	public static void main(String[] args) throws Exception {
		String topic = "test1";

		ProducerHandler handler = new ProducerHandler();
		handler.server = "106.75.9.8:9092,106.75.9.9:9092,123.59.77.55:9092";
		handler.init();

//		List<PartitionInfo> list = handler.producer.partitionsFor(topic);
//		System.out.println(list);
		
		System.out.println("start");
		for (int i = 1; i <= 5; i++) {
			handler.send(topic, String.valueOf(i));
			System.out.println(i);
		}
//		handler.producer.flush();
//		System.out.println("flush");
		System.out.println("finish");
		Thread.sleep(100000);
	}
}
