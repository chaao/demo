package zookeeper.exception;

/**
 * @author lichao
 *
 */
public class ZkException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ZkException() {
		super();
	}

	public ZkException(String message, Throwable cause) {
		super(message, cause);
	}

	public ZkException(String message) {
		super(message);
	}

	public ZkException(Throwable cause) {
		super(cause);
	}
}
