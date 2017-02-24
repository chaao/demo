package shard;

/**
 * @author lichao
 * @date 2014年8月7日
 */
public abstract class ShardInfo<T> {
	private int weight;
	protected static final int DEFAULT_WEIGHT = 1;
	
	public ShardInfo(int weight) {
		this.weight = weight;
	}

	public int getWeight() {
		return this.weight;
	}

	protected abstract T createResource();

	public abstract String getName();
}
