package epsylon.typecheck;

import java.util.Iterator;

import epsylon.ast.AbsOpExp;
import epsylon.ast.AddExp;
import epsylon.ast.AllSetExp;
import epsylon.ast.AndExp;
import epsylon.ast.BackslashExp;
import epsylon.ast.BoolLit;
import epsylon.ast.ContainsExp;
import epsylon.ast.DivExp;
import epsylon.ast.EqExp;
import epsylon.ast.ExistsExp;
import epsylon.ast.ForallExp;
import epsylon.ast.ForallSetExp;
import epsylon.ast.GeqExp;
import epsylon.ast.GthExp;
import epsylon.ast.HashExp;
import epsylon.ast.Ident;
import epsylon.ast.ImpliesExp;
import epsylon.ast.IntervalExp;
import epsylon.ast.LeqExp;
import epsylon.ast.LthExp;
import epsylon.ast.ModExp;
import epsylon.ast.MulExp;
import epsylon.ast.NegExp;
import epsylon.ast.NeqExp;
import epsylon.ast.Node;
import epsylon.ast.NotExp;
import epsylon.ast.NumLit;
import epsylon.ast.OrExp;
import epsylon.ast.SetExp;
import epsylon.ast.StrictAndExp;
import epsylon.ast.StrictOrExp;
import epsylon.ast.SubExp;
import epsylon.environment.StaticEnvironment;
import epsylon.exception.TypecheckException;
import epsylon.interfaces.Exp;
import epsylon.interfaces.Type;
import epsylon.interfaces.Visitor;
import epsylon.parser.Tokenizer;

public class Typechecker implements Visitor<Type> {

	private StaticEnvironment environment;
	
	public Typechecker() {
		environment = new StaticEnvironment();
	}
	
	private void checkChildrenType(AbsOpExp exp, Type requestedType) {
		for (Iterator<Exp> iterator = exp.getChildren(); iterator.hasNext();) {
			AbsOpExp child = (AbsOpExp)iterator.next();
			
			Type type = child.accept(this);
			
			if (!type.equals(requestedType)) {
				throw new TypecheckException("Type of " + child.toString() + " should be " + requestedType.toString() + " insted of " + type.toString());
			}
		}
	}
	
	@Override
	public Type visit(AddExp exp) {
		
		
		return null;
	}

	@Override
	public Type visit(AllSetExp exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(AndExp exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(BackslashExp exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(BoolLit exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(ContainsExp exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(DivExp exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(EqExp exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(ExistsExp exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(ForallExp exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(ForallSetExp exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(GeqExp exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(GthExp exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(HashExp exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(Ident exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(ImpliesExp exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(IntervalExp exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(LeqExp exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(LthExp exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(ModExp exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(MulExp exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(NegExp exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(NeqExp exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(NotExp exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(NumLit exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(OrExp exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(SetExp exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(StrictAndExp exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(StrictOrExp exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(SubExp exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type visit(Node exp) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
