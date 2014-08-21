package epsylon.ast;

import epsylon.interfaces.*;

/**
 * Represents a mathematical comparison (Less Than or Equal To) in the Abstract Syntax Tree.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class LeqExp extends AbsOpExp {

	/**
	 * Instantiates a new Less Than or Equal To operation.
	 * 
	 * @param leftExp The left value.
	 * @param rightExp The right value.
	 */
	public LeqExp(Exp leftExp, Exp rightExp) {
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
	 * Represents a Less Than or Equal To expression as a string.
	 * 
	 * @return The string representation of the Less Than or Equal To expression.
	 */
	@Override
	public String toString() {
		return "LESS OR EQUAL";
	}
	
}
