package epsylon.ast;

import epsylon.interfaces.*;

public class GthExp extends AbsOpExp {

	public GthExp(Exp leftExp, Exp rightExp) {
		super(leftExp, rightExp);
	}
	
	public <T>T accept(Visitor<T> v) {
		return v.visit(this);
	}
	
}
