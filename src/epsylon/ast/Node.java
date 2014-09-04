package epsylon.ast;

import epsylon.interfaces.*;

/**
 * Represents a generic Node in the Abstract Syntax Tree. This is also the source of the AST.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class Node implements Exp {
	private final Exp exp;
	
	/**
	 * Instantiates a new Node with its expression.
	 * 
	 * @param exp An expression.
	 */
	public Node(Exp exp) {
		if (exp == null)
			throw new IllegalArgumentException();
		this.exp = exp;
	}

	/**
	 * Gets the contained expression.
	 * 
	 * @return The expression stored inside this Node.
	 */
	public Exp getExp() {
		return exp;
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
	 * Represents a Node as a string.
	 * 
	 * @return The string representation of the Node.
	 */
	@Override
	public String toString() {
		StringBuilder root = new StringBuilder(getClass().getSimpleName());
		root.append("( ").append(exp.toString()).append(" )");
		return root.toString();
	}
	
}
