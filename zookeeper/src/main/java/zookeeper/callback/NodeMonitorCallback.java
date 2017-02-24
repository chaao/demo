package zookeeper.callback;

/**
 * @author lichao
 *
 */
public interface NodeMonitorCallback {
	void changed(String path, String data);
}
