package epsylon.ast;

import epsylon.interfaces.*;

public class NotExp extends AbsOpExp {

	public NotExp(Exp exp) {
		super(exp);
	}
	
	public <T>T accept(Visitor<T> v) {
		return v.visit(this);
	}

}
