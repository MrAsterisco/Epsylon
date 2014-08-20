package epsylon.ast;

import epsylon.interfaces.*;

/**
 * Represents a mathematical addition in the Abstract Syntax Tree.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class AddExp extends AbsOpExp {
	
	/**
	 * Instantiates a new Add expression with its addends.
	 * 
	 * @param leftExp The left addend.
	 * @param rightExp The right addend.
	 */
	public AddExp(Exp leftExp, Exp rightExp) {
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
	 * Represents an Add expression as a string.
	 * 
	 * @return The string representation of the Add expression.
	 */
	@Override
	public String toString() {
		return "ADD";
	}

}
