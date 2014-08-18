package epsylon.ast;

import epsylon.interfaces.*;

public class Node implements Exp {
	private final Exp exp;

	public Node(Exp exp) {
		if (exp == null)
			throw new IllegalArgumentException();
		this.exp = exp;
	}

	public Exp getExp() {
		return exp;
	}
	
	@Override
	public String toString() {
		StringBuilder root = new StringBuilder(getClass().getSimpleName());
		root.append("( ").append(exp.toString()).append(" )");
		return root.toString();
	}
	
	public <T>T accept(Visitor<T> v) {
		return v.visit(this);
	}
	
}
