package epsylon.exception;

import epsylon.interfaces.Type;

public class TypecheckException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TypecheckException(String message) {
		super(message);
	}

	public TypecheckException(Throwable cause) {
		super(cause);
	}

	public TypecheckException(String message, Throwable cause) {
		super(message, cause);
	}

	public TypecheckException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	public TypecheckException(Type expectedType, Type foundType) {
		this("Expected " + expectedType.toString() + " but found " + foundType.toString());
	}
	
}
