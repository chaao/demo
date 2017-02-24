package shard;

/**
 * @author lichao
 * @date 2014年8月7日
 */
public class Client {
	private String conn;
	public Client(NodeShardInfo shardInfo) {
		conn="host:"+shardInfo.getHost()+"	port:"+shardInfo.getPort();
	}
	@Override
	public String toString() {
		return "Client [conn=" + conn + "]";
	}
	
}
