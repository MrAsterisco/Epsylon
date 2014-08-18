package epsylon.ast;

import epsylon.interfaces.*;

public class NeqExp extends AbsOpExp {

	public NeqExp(Exp leftExp, Exp rightExp) {
		super(leftExp, rightExp);
	}
	
	public <T>T accept(Visitor<T> v) {
		return v.visit(this);
	}

}
