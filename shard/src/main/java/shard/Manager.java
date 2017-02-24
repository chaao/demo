package shard;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author lichao
 * @date 2014年8月7日
 */
public class Manager {

	public static void main(String[] args) {
		NodeShardInfo info1 = new NodeShardInfo("1", "192.168.1.1", 8080);
		NodeShardInfo info2 = new NodeShardInfo("2", "192.168.1.2", 8080);
		NodeShardInfo info3 = new NodeShardInfo("3", "192.168.1.3", 8080);
		List<NodeShardInfo> shards = Lists.newArrayList(info1, info2, info3);

		Sharded<Client, NodeShardInfo> shard = new Sharded<Client, NodeShardInfo>(shards);
		System.out.println(shard.getShard("a"));
		System.out.println(shard.getShard("a".getBytes()));
		System.out.println(shard.getShard("b"));
		System.out.println(shard.getAllShardInfo());
	}

}
