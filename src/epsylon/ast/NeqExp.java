package epsylon.ast;

import epsylon.interfaces.*;

/**
 * Represents the negation of the Equal operation in the Abstract Syntax Tree.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class NeqExp extends AbsOpExp {

	/**
	 * Instantiates a new Not Equal To expression.
	 * 
	 * @param leftExp The left object.
	 * @param rightExp The right object.
	 */
	public NeqExp(Exp leftExp, Exp rightExp) {
		super(leftExp, rightExp);
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
	 * Represents a Not Equal To expression as a string.
	 * 
	 * @return The string representation of the Not Equal To expression.
	 */
	@Override
	public String toString() {
		return "NOT EQUAL";
	}

}
