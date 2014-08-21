package epsylon.ast;

import epsylon.interfaces.Exp;
import epsylon.interfaces.Visitor;

/**
 * Represents the module operation in the Abstract Syntax Tree.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class ModExp extends AbsOpExp {

	/**
	 * Instantiates a new Module expression.
	 * 
	 * @param exp1 The left value.
	 * @param exp2 The right value.
	 */
	public ModExp(Exp exp1, Exp exp2) {
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
	 * Represents a Module expression as a string.
	 * 
	 * @return The string representation of the Module expression.
	 */
	@Override
	public String toString() {
		return "MODULE";
	}

}
