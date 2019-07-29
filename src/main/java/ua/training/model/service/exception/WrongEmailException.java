package ua.training.model.service.exception;

public class WrongEmailException extends LoginException {

	public WrongEmailException() {
		super();
	}

	public WrongEmailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WrongEmailException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongEmailException(String string) {
		super(string);
	}

	public WrongEmailException(Throwable cause) {
		super(cause);
	}
}
