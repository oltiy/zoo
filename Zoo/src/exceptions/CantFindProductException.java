package exceptions;

public class CantFindProductException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CantFindProductException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CantFindProductException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public CantFindProductException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public CantFindProductException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public CantFindProductException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
