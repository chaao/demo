package kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

/**
 * @author lichao
 *
 */
public class TestConsumer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		test();
	}

	public static void test() {
		Properties props = new Properties();
		props.put("zookeeper.connect", "106.75.9.5:2181,106.75.9.6:2181,106.75.9.7:2181/kafka");
		props.put("group.id", "evan");
		props.put("zookeeper.session.timeout.ms", "6000");//超时时间，临时节点
		props.put("zookeeper.sync.time.ms", "2000");
		props.put("auto.commit.interval.ms", "1000");
		props.put("auto.offset.reset", "smallest");
		ConsumerConnector consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
		String topic = "request-uid-through-primary";
		

		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, new Integer(1));
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
		
		KafkaStream<byte[], byte[]> stream = consumerMap.get(topic).get(0);
		ConsumerIterator<byte[], byte[]> it = stream.iterator();
		while (it.hasNext()) {

			try {
				MessageAndMetadata<byte[], byte[]> item = it.next();
				System.out.println(new String(item.message()));
				Thread.sleep(500);
			} catch (InterruptedException e) {	
				e.printStackTrace();
			}
		}
		
		System.exit(0);
	}
}
