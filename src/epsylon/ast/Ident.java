package epsylon.ast;

import epsylon.interfaces.*;

public class Ident extends AbsOpExp {
	protected final String name;

	public Ident(String name) {
		if (name == null)
			throw new IllegalArgumentException();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + name.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return super.toString() + "(" + name + ")";
	}
	
	public <T>T accept(Visitor<T> v) {
		return v.visit(this);
	}
	
}
