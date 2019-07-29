package ua.training.model.service.exception;

public class WrongPasswordException extends LoginException {

	public WrongPasswordException() {
		super();
	}

	public WrongPasswordException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WrongPasswordException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongPasswordException(String string) {
		super(string);
	}

	public WrongPasswordException(Throwable cause) {
		super(cause);
	}

}
