package epsylon.ast;

import epsylon.interfaces.*;

/**
 * Represents a mathematical division in the Abstract Syntax Tree.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class DivExp extends AbsOpExp {

	/**
	 * Instantiates a new Div expression with its children.
	 * 
	 * @param leftExp The dividend
	 * @param rightExp The divisor
	 */
	public DivExp(Exp leftExp, Exp rightExp) {
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
	 * Represents a Div expression as a string.
	 * 
	 * @return The string representation of the Div expression.
	 */
	@Override
	public String toString() {
		return "DIVISION";
	}

}
