package epsylon.exception;

/**
 * Represents an exception thrown by the Tokenizer.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class IllegalTokenException extends ParseException {
	
	private static final long serialVersionUID = 1L;

	public IllegalTokenException(String tokenString) {
		super("Illegal token found! " + tokenString + " cannot be parsed.");
	}

}
