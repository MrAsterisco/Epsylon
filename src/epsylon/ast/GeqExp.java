package epsylon.ast;

import epsylon.interfaces.*;

/**
 * Represents a mathematical comparison (Greater Than or Equal To) in the Abstract Syntax Tree.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class GeqExp extends AbsOpExp {

	/**
	 * Instantiates a new Greater Than or Equal To operation.
	 * 
	 * @param leftExp The left value.
	 * @param rightExp The right value.
	 */
	public GeqExp(Exp leftExp, Exp rightExp) {
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
	 * Represents a Greater Than expression as a string.
	 * 
	 * @return The string representation of the Greater Than expression.
	 */
	@Override
	public String toString() {
		return "GREATER OR EQUAL";
	}

}
