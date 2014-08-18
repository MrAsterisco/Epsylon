package epsylon.ast;

import epsylon.interfaces.*;

public class AndExp extends AbsOpExp {

	public AndExp(Exp leftExp, Exp rightExp) {
		super(leftExp, rightExp);
	}
	
	public <T>T accept(Visitor<T> v) {
		return v.visit(this);
	}
}
