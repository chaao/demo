package kafka;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author lichao
 *
 */
public class TopicMeta {
	private String topic;

	private List<PartitionMeta> partitionMetas = Lists.newArrayList();

	public TopicMeta(String topic) {
		this.topic = topic;
	}

	public void sortPartitionMetas() {
		Collections.sort(partitionMetas);
	}

	public void addPartitionMeta(int id, long logsize) {
		PartitionMeta meta = new PartitionMeta();
		meta.setId(id);
		meta.setLogsize(logsize);
		partitionMetas.add(meta);
	}

	public String getTopic() {
		return topic;
	}

	public List<PartitionMeta> getPartitionMetas() {
		return partitionMetas;
	}
}
