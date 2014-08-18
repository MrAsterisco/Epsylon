package epsylon.ast;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import epsylon.interfaces.Exp;

public abstract class AbsOpExp implements Exp {

	private final List<Exp> children;

	protected AbsOpExp(Exp... children) {
		for (Exp exp : children)
			if (exp == null)
				throw new IllegalArgumentException();
		this.children = Collections.unmodifiableList(Arrays.asList(children));
	}

	public Iterator<Exp> getChildren() {
		return children.iterator();
	}

	/**
	 * returns the AST in the usual prefix notation
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
