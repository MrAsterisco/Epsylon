package epsylon.ast;

import epsylon.interfaces.*;

public class DivExp extends AbsOpExp {

	public DivExp(Exp leftExp, Exp rightExp) {
		super(leftExp, rightExp);
	}
	
	public <T>T accept(Visitor<T> v) {
		return v.visit(this);
	}
	
	@Override
	public String toString() {
		return "DIVISION";
	}

}
