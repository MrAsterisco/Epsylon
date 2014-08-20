package epsylon.ast;

import epsylon.interfaces.Exp;
import epsylon.interfaces.Visitor;

/**
 * Represents an asymmetric Difference of Sets in the Abstract Syntax Tree.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class BackslashExp extends AbsOpExp {

	/**
	 * Instantiates a new Difference of Sets expression with its children.
	 * 
	 * @param exp1 The left set.
	 * @param exp2 The right set.
	 */
	public BackslashExp(Exp exp1, Exp exp2) {
		super(exp1, exp2);
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
	 * Represents a Difference of Sets expression as a string.
	 * 
	 * @return The string representation of the Difference of Set expression.
	 */
	@Override
	public String toString() {
		return "ASYMMETRIC DIFFERENCE OF SETS";
	}

}
