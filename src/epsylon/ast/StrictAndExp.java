package epsylon.ast;

import epsylon.interfaces.*;

/**
 * Represents an Intersection between two Sets in the Abstract Syntax Tree.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class StrictAndExp extends AbsOpExp {
	
	/**
	 * Instantiates a new Intersection with its sets.
	 * 
	 * @param leftExp The left set.
	 * @param rightExp The right set.
	 */
	public StrictAndExp(Exp leftExp, Exp rightExp) {
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
	 * Represents the Intersection expression as a string.
	 * 
	 * @return The string representation of the Intersection expression.
	 */
	@Override
	public String toString() {
		return "INTERSECTION";
	}

}
