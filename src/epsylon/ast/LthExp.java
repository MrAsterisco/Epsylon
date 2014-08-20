package epsylon.ast;

import epsylon.interfaces.*;

public class LthExp extends AbsOpExp {

	public LthExp(Exp leftExp, Exp rightExp) {
		super(leftExp, rightExp);
	}
	
	public <T>T accept(Visitor<T> v) {
		return v.visit(this);
	}
	
	@Override
	public String toString() {
		return "LESS";
	}
	
}
