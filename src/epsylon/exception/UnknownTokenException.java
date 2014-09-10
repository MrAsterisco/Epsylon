package epsylon.exception;

/**
 * Represents an exception thrown by the Tokenizer.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class UnknownTokenException extends RuntimeException {

	private static final long serialVersionUID = 3998501812568191212L;
	private static final String MSG = "Unknown token: ";
	private final String unknownToken;

	public UnknownTokenException(String unknownToken) {
		super(MSG + unknownToken);
		this.unknownToken = unknownToken;
	}

	public UnknownTokenException(String unknownToken, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(MSG + unknownToken, cause, enableSuppression, writableStackTrace);
		this.unknownToken = unknownToken;
	}

	public UnknownTokenException(String unknownToken, Throwable cause) {
		super(MSG + unknownToken, cause);
		this.unknownToken = unknownToken;
	}

	public String getUnknownToken() {
		return unknownToken;
	}

}
