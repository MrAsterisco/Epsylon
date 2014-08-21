package epsylon.ast;

import epsylon.interfaces.*;

/**
 * Represents mathematical multiplication in the Abstract Syntax Tree.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class MulExp extends AbsOpExp {

	/**
	 * Instantiates a new Multiply expression with its factors.
	 * 
	 * @param leftExp The first factor.
	 * @param rightExp The second factor.
	 */
	public MulExp(Exp leftExp, Exp rightExp) {
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
	 * Represents a Multiply expression as a string.
	 * 
	 * @return The string representation of the Multiply expression.
	 */
	@Override
	public String toString() {
		return "MULTIPLICATION";
	}
	
}
