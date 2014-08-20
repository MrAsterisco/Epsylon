package epsylon.ast;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import epsylon.interfaces.Exp;

/**
 * Represents a generic expression in the Abstract Syntax Tree.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public abstract class AbsOpExp implements Exp {

	/**
	 * Contains the expression contained in this expression.
	 */
	private final List<Exp> children;
	
	/**
	 * Instantiates a new generic expression using
	 * an array of children.
	 * 
	 * @param children An array of expressions
	 * @throws IllegalArgumentException If the children array is null.
	 */
	protected AbsOpExp(Exp... children) {
		for (Exp exp : children)
			if (exp == null)
				throw new IllegalArgumentException();
		this.children = Collections.unmodifiableList(Arrays.asList(children));
	}
	
	/**
	 * Gets an iterator on its children.
	 * 
	 * @return A valid iterator in the children of this expression.
	 */
	public Iterator<Exp> getChildren() {
		return children.iterator();
	}

	/**
	 * Represents an Abstract Syntax Tree in the usual usual prefix notation.
	 * 
	 * @return The string representation of this expression.
	 */
	@Override
	public String toString() {
		StringBuilder root = new StringBuilder(getClass().getSimpleName());
		if (children.size() == 0)
			return root.toString();
		root.append("( ");
		for (Exp exp : children)
			root.append(exp.toString()).append(" ");
		root.append(")");
		return root.toString();
	}
}
