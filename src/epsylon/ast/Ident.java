package epsylon.ast;

import epsylon.interfaces.*;

/**
 * Represents a variable (or identifier) in the Abstract Syntax Tree.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class Ident extends AbsOpExp {
	protected final String name;

	/**
	 * Instantiates a new Identifier with its name.
	 * 
	 * @param name The name that should be associated with this identifier.
	 * @throws IllegalArgumentException If the name is null.
	 */
	public Ident(String name) {
		if (name == null)
			throw new IllegalArgumentException();
		this.name = name;
	}

	/**
	 * Gets the name of this Identifier.
	 * 
	 * @return The name associated with this Identifier.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the hash code that represents this Identifier.
	 * 
	 * @return An int value representing this Identifier.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + name.hashCode();
		return result;
	}
	
	/**
	 * Compares two objects to know if they're the same.
	 * 
	 * @param obj2 An object against which this Identifier should be compared.
	 * @return true if obj2 is the same Identifier as this one.
	 */
	@Override
	public boolean equals(Object obj2) {
		if (obj2 instanceof Ident) {
			return ((Ident)obj2).toString().equalsIgnoreCase(this.toString());
		}
		return false;
	}
	
	/**
	 * Calls the visit method of a Visitor.
	 * 
	 * @param v A visitor on which the visit method should be called.
	 * @return The object returned by the Visitor.
	 */
	public <T>T accept(Visitor<T> v) {
		return v.visit(this);
	}
	
	/**
	 * Represents an Identifier as a string.
	 * 
	 * @return The string representation of the Identifier.
	 */
	@Override
	public String toString() {
		return super.toString() + "(" + name + ")";
	}
	
}
