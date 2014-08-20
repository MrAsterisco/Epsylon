package epsylon.ast;

import epsylon.interfaces.Exp;
import epsylon.interfaces.Visitor;

/**
 * Represents a Forall operation in the Abstract Syntax Tree.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class ForallExp extends AbsOpExp {

	/**
	 * Instantiates a new Forall operation.
	 * 
	 * @param ident An identifier with which the Forall should work.
	 * @param exp1 A set on which the Forall should work.
	 * @param exp2 A boolean expression to be applied.
	 */
	public ForallExp(Ident ident, Exp exp1, Exp exp2) {
		super(ident, exp1, exp2);
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
	 * Represents a Forall expression as a string.
	 * 
	 * @return The string representation of the Forall expression.
	 */
	@Override
	public String toString() {
		return "FORALL";
	}

}
