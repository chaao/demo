package zookeeper.exception;

import org.apache.zookeeper.KeeperException;

/**
 * @author lichao
 *
 */
public class ZkNoNodeException extends ZkException {

    private static final long serialVersionUID = 1L;

    public ZkNoNodeException() {
        super();
    }

    public ZkNoNodeException(KeeperException cause) {
        super(cause);
    }

    public ZkNoNodeException(String message, KeeperException cause) {
        super(message, cause);
    }

    public ZkNoNodeException(String message) {
        super(message);
    }
}
