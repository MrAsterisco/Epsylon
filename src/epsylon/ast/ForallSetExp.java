package epsylon.ast;

import epsylon.interfaces.Exp;
import epsylon.interfaces.Visitor;

public class ForallSetExp extends AbsOpExp {

	public ForallSetExp(Exp exp1, Ident ident, Exp exp2) {
		super(ident, exp2, exp1);
	}

	public <T>T accept(Visitor<T> v) {
		return v.visit(this);
	}
	
	@Override
	public String toString() {
		return "{FORALL}";
	}

}
