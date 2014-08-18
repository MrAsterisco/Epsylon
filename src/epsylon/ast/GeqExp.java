package epsylon.ast;

import epsylon.interfaces.*;

public class GeqExp extends AbsOpExp {

	public GeqExp(Exp leftExp, Exp rightExp) {
		super(leftExp, rightExp);
	}
	
	public <T>T accept(Visitor<T> v) {
		return v.visit(this);
	}

}
