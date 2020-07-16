package exception;

public class NoGoodsNumException extends Exception {

	private static final long serialVersionUID = 2165174692195059200L;

	public NoGoodsNumException() {
		super();
	}

	public NoGoodsNumException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoGoodsNumException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoGoodsNumException(String message) {
		super(message);
	}

	public NoGoodsNumException(Throwable cause) {
		super(cause);
	}
	
}
