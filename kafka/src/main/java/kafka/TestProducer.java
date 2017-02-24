package kafka;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.management.StringValueExp;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;

/**
 * @author lichao
 *
 */
public class TestProducer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Properties props = new Properties();
			props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
					"106.75.9.8:9092,106.75.9.9:9092,123.59.77.55:9092");
			props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
					"org.apache.kafka.common.serialization.StringSerializer");
			props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
					"org.apache.kafka.common.serialization.StringSerializer");
			props.setProperty(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, "1000");

			String topic = "test1";

			KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
			List<PartitionInfo> list = producer.partitionsFor(topic);
			System.out.println(list);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String time = sdf.format(new Date());
			for (int i = 0; i < 3; i++) {
				ProducerRecord<String, String> data = new ProducerRecord<String, String>(topic, "a"+i);
				producer.send(data);
			}
			producer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void client() {
	}
}
