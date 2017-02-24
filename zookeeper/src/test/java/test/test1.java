package test;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

/**
 * @author lichao
 *
 */
public class test1 {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
		builder.connectString("10.5.20.18:2181,10.5.20.100:2181,10.9.20.31:2181");
		builder.sessionTimeoutMs(10000);
		builder.connectionTimeoutMs(5000);
		builder.canBeReadOnly(false);
		builder.retryPolicy(new ExponentialBackoffRetry(1000, 29));
//		builder.namespace("");
		builder.defaultData(null);
		CuratorFramework client = builder.build();
		client.start();
		
		client.delete().deletingChildrenIfNeeded().forPath("/opt/kafka/consumers/leech/owners/track_pc");
		
		System.exit(0);
	}

}
