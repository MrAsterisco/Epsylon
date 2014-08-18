package epsylon.ast;

import epsylon.interfaces.*;

public class NumLit extends Lit<Integer> {

	public NumLit(Integer value) {
		super(value);
	}
	
	public <T>T accept(Visitor<T> v) {
		return v.visit(this);
	}

}
