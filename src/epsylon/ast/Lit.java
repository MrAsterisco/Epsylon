package epsylon.ast;

/**
 * An abstract implementation of a value in the Abstract Syntax Tree.
 * 
 * @author Alessio Moiso
 * @version 1.0
 * @param <T> The type that this value should encapsulate
 */
public abstract class Lit<T> extends AbsOpExp {
	
	/**
	 * The encapsulated value.
	 */
	protected final T value;

	/**
	 * Instantiates a new value.
	 * 
	 * @param value The value.
	 */
	public Lit(T value) {
		this.value = value;
	}

	/**
	 * Gets the encapsulated value.
	 * 
	 * @return The value.
	 */
	public T getValue() {
		return value;
	}
	
	/**
	 * Represents a Value expression as a string.
	 * 
	 * @return The string representation of the Value expression.
	 */
	@Override
	public String toString() {
		StringBuilder root = new StringBuilder(super.toString());
		root.append("(").append(value).append(")");
		return root.toString();
	}

}
