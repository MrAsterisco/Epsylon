package epsylon.ast;

import epsylon.interfaces.*;

public class MulExp extends AbsOpExp {

	public MulExp(Exp leftExp, Exp rightExp) {
		super(leftExp, rightExp);
	}
	
	public <T>T accept(Visitor<T> v) {
		return v.visit(this);
	}
	
}
