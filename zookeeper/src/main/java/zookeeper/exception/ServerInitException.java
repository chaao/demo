package zookeeper.exception;

/**
 * @author lichao
 *
 */
public class ServerInitException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ServerInitException() {
		super();
	}

	public ServerInitException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServerInitException(String message) {
		super(message);
	}

	public ServerInitException(Throwable cause) {
		super(cause);
	}
}
