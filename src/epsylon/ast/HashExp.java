package epsylon.ast;

import epsylon.interfaces.Exp;
import epsylon.interfaces.Visitor;

public class HashExp extends AbsOpExp {

	public HashExp(Exp exp) {
		super(exp);
	}

	public <T>T accept(Visitor<T> v) {
		return v.visit(this);
	}
	
	@Override
	public String toString() {
		return "CARDINALITY";
	}

}
