package epsylon.ast;

import epsylon.interfaces.*;

/**
 * Represents a Union operation between two sets in the Abstract Syntax Tree.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class StrictOrExp extends AbsOpExp {
	
	/**
	 * Instantiates a new Union operation with its two sets.
	 * 
	 * @param leftExp The left set.
	 * @param rightExp The right set.
	 */
	public StrictOrExp(Exp leftExp, Exp rightExp) {
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
	 * Represents a Union expression as a string.
	 * 
	 * @return The string representation of the Union expression.
	 */
	@Override
	public String toString() {
		return "UNION";
	}

}
