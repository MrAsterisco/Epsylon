package epsylon.ast;

import epsylon.interfaces.Exp;
import epsylon.interfaces.Visitor;

public class ForallExp extends AbsOpExp {

	public ForallExp(Ident ident, Exp exp1, Exp exp2) {
		super(ident, exp1, exp2);
	}

	public <T>T accept(Visitor<T> v) {
		return v.visit(this);
	}
	
	@Override
	public String toString() {
		return "FORALL";
	}

}
