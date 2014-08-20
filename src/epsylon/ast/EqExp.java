package epsylon.ast;

import epsylon.interfaces.*;

public class EqExp extends AbsOpExp {

	public EqExp(Exp leftExp, Exp rightExp) {
		super(leftExp, rightExp);
	}
	
	public <T>T accept(Visitor<T> v) {
		return v.visit(this);
	}
	
	@Override
	public String toString() {
		return "EQUAL";
	}
	
}
