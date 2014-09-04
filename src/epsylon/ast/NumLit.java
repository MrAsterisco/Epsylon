package epsylon.ast;

import epsylon.interfaces.*;

/**
 * Represents a numeric value in the Abstract Syntax Tree.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class NumLit extends Lit<Integer> {

	/**
	 * Instantiates a new NumLit expression with its value
	 * 
	 * @param value A numeric value.
	 */
	public NumLit(Integer value) {
		super(value);
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

}
