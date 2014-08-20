package epsylon.ast;

import epsylon.interfaces.Exp;
import epsylon.interfaces.Visitor;

/**
 * Represents an All operation that returns a Set in the Abstract Syntax Tree.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class AllSetExp extends AbsOpExp {

	/**
	 * Instantiates a new All expression.
	 * 
	 * @param ident An identifier to be used in the All operation.
	 * @param exp1 A set on which the All should work.
	 * @param exp2 A boolean condition to be applied on the set.
	 */
	public AllSetExp(Ident ident, Exp exp1, Exp exp2) {
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
	 * Represents an All expression as a string.
	 * 
	 * @return The string representation of the All expression.
	 */
	@Override
	public String toString() {
		return "{ALL}";
	}

}
