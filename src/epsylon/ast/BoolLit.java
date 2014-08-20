package epsylon.ast;

import epsylon.interfaces.*;

/**
 * Represents a boolean in the Abstract Syntax Tree.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class BoolLit extends Lit<Boolean> {

	/**
	 * Instantiates a new Boolean with its value.
	 * 
	 * @param value A Boolean representing the value.
	 */
	public BoolLit(Boolean value) {
		super(value);
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
	 * Represents a Boolean expression as a string.
	 * 
	 * @return The string representation of the Boolean expression.
	 */
	@Override
	public String toString() {
		return "BOOL";
	}
}
