package epsylon.ast;

import epsylon.interfaces.*;

/**
 * Represents the negative operation in the Abstract Syntax Tree. 
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class NegExp extends AbsOpExp {

	/**
	 * Instantiates a new Negative expression.
	 * 
	 * @param exp The value.
	 */
	public NegExp(Exp exp) {
		super(exp);
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
	 * Represents a Negative expression as a string.
	 * 
	 * @return The string representation of the Negative expression.
	 */
	@Override
	public String toString() {
		return "NEGATIVE";
	}

}
