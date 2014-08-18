package epsylon.ast;

import epsylon.interfaces.*;

public class StrictAndExp extends AbsOpExp {

	public StrictAndExp(Exp leftExp, Exp rightExp) {
		super(leftExp, rightExp);
	}
	
	public <T>T accept(Visitor<T> v) {
		return v.visit(this);
	}

}
