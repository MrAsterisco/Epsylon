package epsylon.ast;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import epsylon.interfaces.Exp;
import epsylon.interfaces.Visitor;

public class SetExp extends AbsOpExp {
	
	private Set<Exp> children;
	
	public SetExp() {
		children = new LinkedHashSet<Exp>();
	}
	
	public SetExp(Exp oneItem) {
		children = new LinkedHashSet<Exp>();
		this.add(oneItem);
	}
	
	public void add(Exp e) {
		children.add(e);
	}
	
	@Override
	public Iterator<Exp> getChildren() {
		return children.iterator();
	}

	@Override
	public <T> T accept(Visitor<T> v) {
		return v.visit(this);
	}

}
