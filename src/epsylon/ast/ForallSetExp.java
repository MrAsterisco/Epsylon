package epsylon.ast;

import epsylon.interfaces.Exp;
import epsylon.interfaces.Visitor;

public class ForallSetExp extends AbsOpExp {

	public ForallSetExp(Exp exp1, Ident ident, Exp exp2) {
		super(exp1, ident, exp2);
	}

	public <T>T accept(Visitor<T> v) {
		return v.visit(this);
	}

}
