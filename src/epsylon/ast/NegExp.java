package epsylon.ast;

import epsylon.interfaces.*;

public class NegExp extends AbsOpExp {

	public NegExp(Exp exp) {
		super(exp);
	}

	public <T>T accept(Visitor<T> v) {
		return v.visit(this);
	}
	
	@Override
	public String toString() {
		return "NEGATIVE";
	}

}
