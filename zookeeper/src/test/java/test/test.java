package test;

import java.lang.reflect.Field;

import zookeeper.Constant;
import zookeeper.ZkClient;
import zookeeper.ZkClientFactory;
import zookeeper.callback.NodeChildrenMonitorCallback;

/**
 * @author lichao
 *
 */
public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		ZkClient client = ZkClientFactory.createZkClient("10.5.20.18:2181,10.5.20.100:2181,10.9.20.31:2181", "test");

		// client.createZnode("znode", "znode");
		// client.setZnode("znode", "test");
		// client.setZnode("znode1", "test");

		client.nodeChildrenMonitor("/a", new NodeChildrenMonitorCallback() {

			@Override
			public void updated(String path, String data) {
				System.out.println("update:" + path + ":" + data);
			}

			@Override
			public void removed(String path, String data) {
				System.out.println("remove:" + path + ":" + data);
			}

			@Override
			public void added(String path, String data) {
				System.out.println("add:" + path + ":" + data);
			}
		});
		Thread.sleep(1000 * 60 * 60);
		
		
	}
	

}
