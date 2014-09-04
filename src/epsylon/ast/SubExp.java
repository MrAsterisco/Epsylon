package epsylon.ast;

import epsylon.interfaces.*;

/**
 * Represents a mathematical subtraction in the Abstract Syntax Tree.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class SubExp extends AbsOpExp {
	
	/**
	 * Instantiates a new Sub expression with its terms.
	 * 
	 * @param leftExp The left value.
	 * @param rightExp The right value.
	 */
	public SubExp(Exp leftExp, Exp rightExp) {
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
	 * Represents a Subtraction expression as a string.
	 * 
	 * @return The string representation of the Subtraction expression.
	 */
	@Override
	public String toString() {
		return "SUBTRACTION";
	}
	
}
