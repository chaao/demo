package kafka;

/**
 * @author lichao
 *
 */
public class PartitionMeta implements Comparable<PartitionMeta> {
	private int id;
	private long logsize;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the logsize
	 */
	public long getLogsize() {
		return logsize;
	}

	/**
	 * @param logsize
	 *            the logsize to set
	 */
	public void setLogsize(long logsize) {
		this.logsize = logsize;
	}

	@Override
	public int compareTo(PartitionMeta o) {
		return (this.id < o.getId()) ? -1 : ((this.id == o.getId()) ? 0 : 1);
	}

	@Override
	public String toString() {
		return String.format("PartitionMeta [id=%s, logsize=%s]", id, logsize);
	}
}
