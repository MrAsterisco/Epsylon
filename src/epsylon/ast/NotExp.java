package epsylon.ast;

import epsylon.interfaces.*;

/**
 * Represents a Not expression in the Abstract Syntax Tree.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class NotExp extends AbsOpExp {

	/**
	 * Instantiates a new Not operation with its expression.
	 * 
	 * @param exp An expression.
	 */
	public NotExp(Exp exp) {
		super(exp);
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
	 * Represents a Not expression as a string.
	 * 
	 * @return The string representation of the Not expression.
	*/
	@Override
	public String toString() {
		return "NOT";
	}

}
