package kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kafka.api.PartitionOffsetRequestInfo;
import kafka.common.TopicAndPartition;
import kafka.javaapi.OffsetResponse;
import kafka.javaapi.PartitionMetadata;
import kafka.javaapi.TopicMetadata;
import kafka.javaapi.TopicMetadataRequest;
import kafka.javaapi.TopicMetadataResponse;
import kafka.javaapi.consumer.SimpleConsumer;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

/**
 * @author lichao
 *
 */
public class KafkaMonitor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		KafkaMonitor test = new KafkaMonitor();

		List<String> brokers = new ArrayList<String>();
		brokers.add("10.5.20.100");
		brokers.add("10.5.20.18");
		brokers.add("10.9.20.31");
		int port = 9092;

		List<TopicMeta> topicMetas = test.getLogSize(Lists.newArrayList("__consumer_offsets"), brokers, port);

		for (TopicMeta topicMeta : topicMetas) {
			System.out.println(topicMeta.getTopic());

			for (PartitionMeta item : topicMeta.getPartitionMetas()) {
				System.out.println(item);
			}
		}
	}

	public List<TopicMeta> getLogSize(List<String> topics, List<String> brokers, int port) {
		List<TopicMeta> topicMetas = Lists.newArrayList();

		for (String broker : brokers) {
			SimpleConsumer consumer = null;
			try {
				consumer = new SimpleConsumer(broker, port, 5000, 1024, "leaderLookup");
				TopicMetadataRequest req = new TopicMetadataRequest(topics);
				TopicMetadataResponse resp = consumer.send(req);
				List<TopicMetadata> metaData = resp.topicsMetadata();
				for (TopicMetadata item : metaData) {
					String topic = item.topic();
					TopicMeta topicMeta = new TopicMeta(topic);

					Multimap<String, Integer> partInfo = HashMultimap.create();
					for (PartitionMetadata part : item.partitionsMetadata()) {
						String host = part.leader().host();
						partInfo.put(host, part.partitionId());
					}

					for (String host : partInfo.keySet()) {

						SimpleConsumer consumer1 = null;
						try {
							Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfo = Maps.newHashMap();
							for (Integer partition : partInfo.get(host)) {
								requestInfo.put(new TopicAndPartition(topic, partition), new PartitionOffsetRequestInfo(-1, 1));
							}
							kafka.javaapi.OffsetRequest request = new kafka.javaapi.OffsetRequest(requestInfo, (short) 0, "");

							consumer1 = new SimpleConsumer(host, port, 3000, 1024, topic);
							OffsetResponse response = consumer1.getOffsetsBefore(request);
							for (Integer partition : partInfo.get(host)) {
								long[] offsets = response.offsets(topic, partition);
								topicMeta.addPartitionMeta(partition, offsets[0]);
							}
						} catch (Exception e) {
							throw new RuntimeException(e);
						} finally {
							if (consumer1 != null)
								consumer1.close();
						}
					}
					topicMeta.sortPartitionMetas();
					topicMetas.add(topicMeta);
				}

				break;
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				if (consumer != null)
					consumer.close();
			}
		}
		return topicMetas;
	}
}
