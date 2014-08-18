package epsylon.ast;

import epsylon.interfaces.*;

public class AddExp extends AbsOpExp {

	public AddExp(Exp leftExp, Exp rightExp) {
		super(leftExp, rightExp);
	}
	
	public <T>T accept(Visitor<T> v) {
		return v.visit(this);
	}

}
