package epsylon.ast;

import epsylon.interfaces.*;

/**
 * Represents a mathematical comparison (Strictly Less Than) in the Abstract Syntax Tree.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class LthExp extends AbsOpExp {

	/**
	 * Instantiates a new Strictly Less Than operation.
	 * 
	 * @param leftExp The left value.
	 * @param rightExp The right value.
	 */
	public LthExp(Exp leftExp, Exp rightExp) {
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
	 * Represents a Strictly Less Than expression as a string.
	 * 
	 * @return The string representation of the Strictly Less Than expression.
	 */
	@Override
	public String toString() {
		return "LESS";
	}
	
}
