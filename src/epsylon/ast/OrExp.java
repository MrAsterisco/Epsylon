package epsylon.ast;

import epsylon.interfaces.*;

/**
 * Represents an Or operation in the Abstract Syntax Tree.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class OrExp extends AbsOpExp {

	/**
	 * Instantiates a new Or expression with its propositions.
	 * 
	 * @param leftExp The left proposition.
	 * @param rightExp The right proposition.
	 */
	public OrExp(Exp leftExp, Exp rightExp) {
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
	 * Represents an Or expression as a string.
	 * 
	 * @return The string representation of the Or expression.
	 */
	@Override
	public String toString() {
		return "OR";
	}

}
