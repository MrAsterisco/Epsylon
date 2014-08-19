package epsylon.exception;

public class IllegalTokenException extends ParseException {
	
	private static final long serialVersionUID = 1L;

	public IllegalTokenException(String tokenString) {
		super("Illegal token found! " + tokenString + " cannot be parsed.");
	}

}
