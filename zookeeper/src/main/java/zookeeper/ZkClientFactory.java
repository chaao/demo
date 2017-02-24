package zookeeper;

import java.util.Map;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.google.common.collect.Maps;

/**
 * @author lichao
 *
 */
public class ZkClientFactory {

	private static Map<String, ZkClient> zkClients = Maps.newHashMap();

	/**
	 * default namespace = app
	 */
	public static ZkClient createZkClient(String zkAddress, String projectName) {
		return createZkClient(zkAddress, "app", projectName);
	}

	public static ZkClient createZkClient(String zkAddress, String namespace, String projectName) {
		String key = zkAddress + namespace + projectName;
		ZkClient zkClient = zkClients.get(key);
		if (zkClient == null) {
			synchronized (zkClients) {
				zkClient = zkClients.get(key);
				if (zkClient == null) {
					CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
					builder.connectString(zkAddress);
					builder.sessionTimeoutMs(10000);
					builder.connectionTimeoutMs(5000);
					builder.canBeReadOnly(false);
					builder.retryPolicy(new ExponentialBackoffRetry(1000, 29));
					builder.namespace(namespace + "/" + projectName);
					builder.defaultData(null);
					CuratorFramework curator = builder.build();
					curator.start();
					zkClient = new ZkClient(zkAddress, namespace, projectName, curator);
					zkClients.put(key, zkClient);
				}
			}
		}
		return zkClient;
	}
}
