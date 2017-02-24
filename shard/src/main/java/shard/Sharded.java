package shard;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

/**
 * @author lichao
 * @date 2014年8月7日
 */
public class Sharded<R, S extends ShardInfo<R>> {

	private TreeMap<Long, S> nodes;
	private final HashFunction algo;
	private final Map<ShardInfo<R>, R> resources = new LinkedHashMap<ShardInfo<R>, R>();
	private static final int VIRTUAL_NODE = 160;

	public Sharded(List<S> shards) {
		this(shards, Hashing.murmur3_128(0x1234ABCD));
	}

	public Sharded(List<S> shards, HashFunction algo) {
		this.algo = algo;
		initialize(shards);
	}

	private void initialize(List<S> shards) {
		nodes = new TreeMap<Long, S>();

		for (int i = 0; i != shards.size(); ++i) {
			final S shardInfo = shards.get(i);
			if (shardInfo.getName() == null)
				for (int n = 0; n < VIRTUAL_NODE * shardInfo.getWeight(); n++) {
					nodes.put(this.hash("SHARD-" + i + "-NODE-" + n), shardInfo);
				}
			else
				for (int n = 0; n < VIRTUAL_NODE * shardInfo.getWeight(); n++) {
					nodes.put(this.hash(shardInfo.getName() + "*" + shardInfo.getWeight() + "-" + n), shardInfo);
				}
			resources.put(shardInfo, shardInfo.createResource());
		}
	}

	private long hash(String key) {
		return algo.hashString(key, Charsets.UTF_8).asLong();
	}

	private long hash(byte[] key) {
		return algo.hashBytes(key).asLong();
	}

	public R getShard(byte[] key) {
		return resources.get(getShardInfo(key));
	}

	public R getShard(String key) {
		return resources.get(getShardInfo(key));
	}

	public S getShardInfo(byte[] key) {
		return getShardInfo(hash(key));
	}

	public S getShardInfo(String key) {
		return getShardInfo(hash(key));
	}

	private S getShardInfo(long hashcode) {
		SortedMap<Long, S> tail = nodes.tailMap(hashcode);
		if (tail.size() == 0) {
			return nodes.get(nodes.firstKey());
		}
		return tail.get(tail.firstKey());
	}

	public Collection<S> getAllShardInfo() {
		return Collections.unmodifiableCollection(nodes.values());
	}

	public Collection<R> getAllShards() {
		return Collections.unmodifiableCollection(resources.values());
	}
}
