package epsylon.exception;

public class IllegalTokenException extends ParseException {

	public IllegalTokenException(String tokenString) {
		super("Illegal token found! " + tokenString + " cannot be parsed.");
	}

}
