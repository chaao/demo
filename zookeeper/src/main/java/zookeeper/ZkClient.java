package zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;

import zookeeper.callback.NodeChildrenMonitorCallback;
import zookeeper.callback.NodeMonitorCallback;
import zookeeper.exception.ZkException;

import com.google.common.base.Charsets;

/**
 * @author lichao
 *
 */
public class ZkClient {

	private String zkAddress;

	private String namespace;

	private String projectName;

	private CuratorFramework client;

	ZkClient(String zkAddress, String namespace, String projectName, CuratorFramework client) {
		super();
		this.zkAddress = zkAddress;
		this.namespace = namespace;
		this.projectName = projectName;
		this.client = client;
	}

	/**
	 * @return the zkAddress
	 */
	public String getZkAddress() {
		return zkAddress;
	}

	/**
	 * @return the namespace
	 */
	public String getNamespace() {
		return namespace;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * 临时节点
	 */
	public void createEphemeralZnode(String path, String data) {
		try {
			this.client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, data.getBytes(Charsets.UTF_8));
		} catch (Exception e) {
			throw new ZkException(e);
		}
	}

	public void createZnode(String path, String data) {
		try {
			this.client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, data.getBytes(Charsets.UTF_8));
		} catch (Exception e) {
			throw new ZkException(e);
		}
	}

	public void setZnode(String path, String data) {
		try {
			Stat stat = this.client.checkExists().forPath(path);
			if (stat == null) {
				this.client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, data.getBytes(Charsets.UTF_8));
			} else {
				this.client.setData().forPath(path, data.getBytes(Charsets.UTF_8));
			}
		} catch (Exception e) {
			throw new ZkException(e);
		}
	}

	public String getZnode(String path) {
		try {
			String result = null;
			byte[] data = this.client.getData().forPath(path);
			if (data != null && data.length != 0) {
				result = new String(data, Charsets.UTF_8);
			}
			return result;
		} catch (Exception e) {
			throw new ZkException(e);
		}
	}

	public void deleteZnode(String path) {
		try {
			this.client.delete().deletingChildrenIfNeeded().forPath(path);
		} catch (KeeperException.NoNodeException e) {
		} catch (Exception e) {
			throw new ZkException(e);
		}
	}

	/**
	 * 监听Znode的子节点
	 */
	public void nodeChildrenMonitor(String path, final NodeChildrenMonitorCallback callback) {
		try {
			@SuppressWarnings("resource")
			PathChildrenCache pathChildrenCache = new PathChildrenCache(this.client, path, true);
			pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
				@Override
				public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
					PathChildrenCacheEvent.Type eventType = event.getType();
					switch (eventType) {
					case CHILD_ADDED: {
						callback.added(ZKPaths.getNodeFromPath(event.getData().getPath()), event.getData().getData() == null ? "" : new String(event
								.getData().getData(), Charsets.UTF_8));
						break;
					}
					case CHILD_UPDATED: {
						callback.updated(ZKPaths.getNodeFromPath(event.getData().getPath()), event.getData().getData() == null ? "" : new String(
								event.getData().getData(), Charsets.UTF_8));
						break;
					}
					case CHILD_REMOVED: {
						callback.removed(ZKPaths.getNodeFromPath(event.getData().getPath()), event.getData().getData() == null ? "" : new String(
								event.getData().getData(), Charsets.UTF_8));
						break;
					}
					default:
						break;
					}
				}
			});
			pathChildrenCache.start(StartMode.BUILD_INITIAL_CACHE);
		} catch (Exception e) {
			throw new ZkException(e);
		}
	}

	/**
	 * 监听Znode
	 */
	public void nodeMonitor(String path, final NodeMonitorCallback callback) {
		try {
			@SuppressWarnings("resource")
			final NodeCache nodeCache = new NodeCache(this.client, path);
			nodeCache.getListenable().addListener(new NodeCacheListener() {
				@Override
				public void nodeChanged() throws Exception {
					if (nodeCache.getCurrentData() != null) {
						callback.changed(nodeCache.getCurrentData().getPath(), new String(nodeCache.getCurrentData().getData(), Charsets.UTF_8));
					}
				}
			});
			nodeCache.start(true);
		} catch (Exception e) {
			throw new ZkException(e);
		}
	}

	public void test(String path) {
		try {
			client.setData().forPath(path);
		} catch (Exception e) {
			throw new ZkException(e);
		}
	}

	@Override
	public String toString() {
		return String.format("ZkClient [zkAddress=%s, namespace=%s, projectName=%s]", zkAddress, namespace, projectName);
	}

}
