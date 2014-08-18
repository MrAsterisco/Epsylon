package epsylon.ast;

import epsylon.interfaces.*;

public class OrExp extends AbsOpExp {

	public OrExp(Exp leftExp, Exp rightExp) {
		super(leftExp, rightExp);
	}
	
	public <T>T accept(Visitor<T> v) {
		return v.visit(this);
	}

}
