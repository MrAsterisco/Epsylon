package epsylon.ast;

import epsylon.interfaces.Exp;
import epsylon.interfaces.Visitor;

/**
 * Represents a Contains operation in the Abstract Syntax Tree.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class ContainsExp extends AbsOpExp {

	/**
	 * Instantiates a new Contains operation with two expressions.
	 * 
	 * @param exp1 A set on which the Contains should work.
	 * @param exp2 An expression of the same type as the inner type of the set.
	 */
	public ContainsExp(Exp exp1, Exp exp2) {
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
	 * Represents a Contains expression as a string.
	 * 
	 * @return The string representation of the Contains expression.
	 */
	@Override
	public String toString() {
		return "CONTAINS";
	}

}
