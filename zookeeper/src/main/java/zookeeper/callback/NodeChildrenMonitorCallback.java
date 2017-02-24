package zookeeper.callback;

/**
 * @author lichao
 *
 */
public interface NodeChildrenMonitorCallback {
	void added(String path, String data);

	void updated(String path, String data);

	void removed(String path, String data);
}
