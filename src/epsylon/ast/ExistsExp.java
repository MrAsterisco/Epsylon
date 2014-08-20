package epsylon.ast;

import epsylon.interfaces.Exp;
import epsylon.interfaces.Visitor;

/**
 * Represents an Exists operation in the Abstract Syntax Tree.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class ExistsExp extends AbsOpExp {

	/**
	 * Instantiates a new Exists operation.
	 * 
	 * @param ident An identifier with which the Exists should work.
	 * @param exp1 A set on which the Exists should work.
	 * @param exp2 A boolean expression to be applied.
	 */
	public ExistsExp(Ident ident, Exp exp1, Exp exp2) {
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
	 * Represents an Exists expression as a string.
	 * 
	 * @return The string representation of the Exists expression.
	 */
	@Override
	public String toString() {
		return "EXISTS";
	}

}
