package epsylon.ast;

import epsylon.interfaces.*;

/**
 * Represents an And expression in the Abstract Syntax Tree.
 * 
 * @author Alessio Moiso
 * @version 1.0
 *
 */
public class AndExp extends AbsOpExp {

	/**
	 * Instantiates a new And expression with its propositions.
	 * 
	 * @param leftExp The left proposition.
	 * @param rightExp The right proposition.
	 */
	public AndExp(Exp leftExp, Exp rightExp) {
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
	 * Represents an And expression as a string.
	 * 
	 * @return The string representation of the And expression.
	 */
	@Override
	public String toString() {
		return "AND";
	}
}
