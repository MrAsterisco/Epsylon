package epsylon.interfaces;

import epsylon.ast.*;

/**
 * Defines the interface that all Visitors must comply with.
 * 
 * @author Alessio Moiso
 * @version 1.0
 * @param <T> A generic type, defined by the Visitor itself.
 */
public interface Visitor<T> {
	
	T visit(AddExp exp);
	T visit(AllSetExp exp);
	T visit(AndExp exp);
	T visit(BackslashExp exp);
	T visit(BoolLit exp);
	T visit(ContainsExp exp);
	T visit(DivExp exp);
	T visit(EqExp exp);
	T visit(ExistsExp exp);
	T visit(ForallExp exp);
	T visit(ForallSetExp exp);
	T visit(GeqExp exp);
	T visit(GthExp exp);
	T visit(HashExp exp);
	T visit(Ident exp);
	T visit(ImpliesExp exp);
	T visit(IntervalExp exp);
	T visit(LeqExp exp);
	T visit(LthExp exp);
	T visit(ModExp exp);
	T visit(MulExp exp);
	T visit(NegExp exp);
	T visit(NeqExp exp);
	T visit(NotExp exp);
	T visit(NumLit exp);
	T visit(OrExp exp);
	T visit(SetExp exp);
	T visit(StrictAndExp exp);
	T visit(StrictOrExp exp);
	T visit(SubExp exp);
	
	T visit(Node exp);
	
}
