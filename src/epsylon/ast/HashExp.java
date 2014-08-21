package epsylon.ast;

import epsylon.interfaces.Exp;
import epsylon.interfaces.Visitor;

/**
 * Represents the cardinality operation on a Set in the Abstract Syntax Tree.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class HashExp extends AbsOpExp {

	/**
	 * Instantiates a new Cardinality expression with its set.
	 * 
	 * @param exp A set of which you want to calculate the cardinality.
	 */
	public HashExp(Exp exp) {
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
	 * Represents a Cardinality expression as a string.
	 * 
	 * @return The string representation of the Cardinality expression.
	 */
	@Override
	public String toString() {
		return "CARDINALITY";
	}

}
