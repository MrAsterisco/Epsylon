package epsylon.ast;

import epsylon.interfaces.Exp;
import epsylon.interfaces.Visitor;

public class BackslashExp extends AbsOpExp {

	public BackslashExp(Exp exp1, Exp exp2) {
		super(exp1, exp2);
	}

	public <T>T accept(Visitor<T> v) {
		return v.visit(this);
	}

}
