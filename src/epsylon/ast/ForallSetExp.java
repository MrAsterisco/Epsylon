package epsylon.ast;

import epsylon.interfaces.Exp;
import epsylon.interfaces.Visitor;

/**
 * Represents a Forall operation that returns a Set in the Abstract Syntax Tree.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class ForallSetExp extends AbsOpExp {

	/**
	 * Instantiates a new Forall operation.
	 * 
	 * @param exp1 An expression to be applied.
	 * @param ident An identifier with which the Forall should work.
	 * @param exp2 A set on which the Forall should work.
	 */
	public ForallSetExp(Exp exp1, Ident ident, Exp exp2) {
		super(ident, exp2, exp1);
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
		return "{FORALL}";
	}

}
