package epsylon.ast;

import epsylon.interfaces.*;

public class StrictOrExp extends AbsOpExp {

	public StrictOrExp(Exp leftExp, Exp rightExp) {
		super(leftExp, rightExp);
	}
	
	public <T>T accept(Visitor<T> v) {
		return v.visit(this);
	}

}
