package shard;

/**
 * @author lichao
 * @date 2014年8月7日
 */
public class NodeShardInfo extends ShardInfo<Client> {

	private int timeout;
	private String host;
	private int port;
	private String name = null;

	public NodeShardInfo(String name, String host, int port) {
		this(name, host, port, 2000);
	}

	public NodeShardInfo(String name, String host, int port, int timeout) {
		this(name, host, port, timeout, DEFAULT_WEIGHT);
	}

	public NodeShardInfo(String name, String host, int port, int timeout, int weight) {
		this(host, port, timeout, weight);
		this.name = name;
	}

	public NodeShardInfo(String host, int port, int timeout, int weight) {
		super(weight);
		this.host = host;
		this.port = port;
		this.timeout = timeout;
	}

	@Override
	public Client createResource() {
		return new Client(this);
	}

	@Override
	public String getName() {
		return name;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setName(String name) {
		this.name = name;
	}

}
