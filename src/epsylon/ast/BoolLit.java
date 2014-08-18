package epsylon.ast;

import epsylon.interfaces.*;

public class BoolLit extends Lit<Boolean> {

	public BoolLit(Boolean value) {
		super(value);
	}
	
	public <T>T accept(Visitor<T> v) {
		return v.visit(this);
	}
}
