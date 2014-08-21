package epsylon.ast;

import epsylon.interfaces.*;

/**
 * Represents a mathematical comparison (Strictly Greater Than) in the Abstract Syntax Tree.
 * 
 * @author Alessio MOiso
 * @version 1.0
 */
public class GthExp extends AbsOpExp {

	/**
	 * Instantiates a new Strictly Greater Than operation.
	 * 
	 * @param leftExp The left value.
	 * @param rightExp The right value.
	 */
	public GthExp(Exp leftExp, Exp rightExp) {
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
	 * Represents a Strictly Greater Than expression as a string.
	 * 
	 * @return The string representation of the Strictly Greater Than expression.
	 */
	@Override
	public String toString() {
		return "GREATER";
	}
	
}
