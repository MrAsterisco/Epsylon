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

public class Typechecker implements Visitor<Type> {

	private StaticEnvironment environment;
	
	public Typechecker() {
		environment = new StaticEnvironment();
	}
	
	public Type typecheck(Node tree) {
		return tree.accept(this);
	}
	
	private void checkAllChildrenConformToType(AbsOpExp exp, Type requestedType) {
		for (Iterator<Exp> iterator = exp.getChildren(); iterator.hasNext();) {
			AbsOpExp child = (AbsOpExp)iterator.next();
			
			Type type = child.accept(this);
			
			if (!type.equals(requestedType)) {
				throw new TypecheckException(requestedType, type);
			}
		}
	}
	
	private Type checkAllChildrenConformToType(AbsOpExp exp) {
		Type requestedType = exp.getChildren().next().accept(this);
		
		checkAllChildrenConformToType(exp, requestedType);
		
		return requestedType;
	}
	
	private Type checkOperationSet(AbsOpExp exp) {
		environment.pushNewLevel();
		
		Iterator<Exp> children = exp.getChildren();
		Ident ident = (Ident)children.next();
		
		Exp set = children.next();
		Type type = set.accept(this);
		if (!type.equals(new SetType())) {
			throw new TypecheckException(new SetType(), type);
		}
		
		Type innerType = ((SetType) type).getInnerType();
		environment.checkAndPut(ident, innerType);
		
		Exp bool = children.next();
		
		Type type2 = bool.accept(this);
		if (!type2.equals(PrimType.BOOL)) {
			throw new TypecheckException(PrimType.BOOL, type2);
		}
		
		environment.popCurrentLevel();
		
		return innerType;
	}
	
	private Type checkBinaryOperationSet(AbsOpExp exp) {
		Iterator<Exp> children = exp.getChildren();
		
		Type set1 = children.next().accept(this);
		if (!set1.equals(new SetType())) {
			throw new TypecheckException(new SetType(), set1);
		}
		
		Type set2 = children.next().accept(this);
		if (!set2.equals(new SetType())) {
			throw new TypecheckException(new SetType(), set2);
		}
		
		if (!set1.equals(set2)) {
			throw new TypecheckException(set1, set2);
		}
		
		return set1;
	}
	
	@Override
	public Type visit(AddExp exp) {
		checkAllChildrenConformToType(exp, PrimType.INT);
		return PrimType.INT;
	}

	@Override
	public Type visit(AllSetExp exp) {
		return new SetType(checkOperationSet(exp));
	}

	@Override
	public Type visit(AndExp exp) {
		checkAllChildrenConformToType(exp, PrimType.BOOL);
		return PrimType.BOOL;
	}

	@Override
	public Type visit(BackslashExp exp) {
		return checkBinaryOperationSet(exp); 
	}

	@Override
	public Type visit(BoolLit exp) {
		return PrimType.BOOL;
	}

	@Override
	public Type visit(ContainsExp exp) {
		Iterator<Exp> children = exp.getChildren();
		
		Exp set = children.next();
		Type type = set.accept(this);
		if (!type.equals(new SetType())) {
			throw new TypecheckException(new SetType(), type);
		}
		
		Exp item = children.next();
		Type type2 = item.accept(this);
		Type innerType = ((SetType) type).getInnerType();
		if (!type2.equals(innerType)) {
			throw new TypecheckException(innerType, type2);
		}
		
		return PrimType.BOOL;
	}

	@Override
	public Type visit(DivExp exp) {
		checkAllChildrenConformToType(exp, PrimType.INT);
		return PrimType.INT;
	}

	@Override
	public Type visit(EqExp exp) {
		checkAllChildrenConformToType(exp);
		return PrimType.BOOL;
	}

	@Override
	public Type visit(ExistsExp exp) {
		checkOperationSet(exp);
		return PrimType.BOOL;
	}

	@Override
	public Type visit(ForallExp exp) {
		checkOperationSet(exp);
		return PrimType.BOOL;
	}

	@Override
	public Type visit(ForallSetExp exp) {
		environment.pushNewLevel();
		
		Iterator<Exp> children = exp.getChildren();
		
		Ident ident = (Ident)children.next();
		
		Exp e1 = children.next();
		Exp set = children.next();
		
		Type type = set.accept(this);
		if (!type.equals(new SetType())) {
			throw new TypecheckException(new SetType(), type);
		}
		
		Type innerType = ((SetType) type).getInnerType();
		environment.checkAndPut(ident, innerType);
		
		Type type1 = e1.accept(this);
		if (!type1.equals(innerType)) {
			throw new TypecheckException(type1, innerType);
		}
		
		environment.popCurrentLevel();
		
		return new SetType(innerType);
	}

	@Override
	public Type visit(GeqExp exp) {
		checkAllChildrenConformToType(exp);
		return PrimType.BOOL;
	}

	@Override
	public Type visit(GthExp exp) {
		checkAllChildrenConformToType(exp);
		return PrimType.BOOL;
	}

	@Override
	public Type visit(HashExp exp) {
		Iterator<Exp> children = exp.getChildren();
		
		Type set = children.next().accept(this);
		if (!set.equals(new SetType())) {
			throw new TypecheckException(new SetType(), set);
		}
		
		return PrimType.INT;
	}

	@Override
	public Type visit(Ident exp) {
		return environment.lookupAndCheck(exp);
	}

	@Override
	public Type visit(ImpliesExp exp) {
		checkAllChildrenConformToType(exp, PrimType.BOOL);
		return PrimType.BOOL;
	}

	@Override
	public Type visit(IntervalExp exp) {
		Iterator<Exp> children = exp.getChildren();
		
		Type e1 = children.next().accept(this);
		if (!e1.equals(PrimType.INT)) {
			throw new TypecheckException(PrimType.INT, e1);
		}
		
		Type e2 = children.next().accept(this);
		if (!e2.equals(PrimType.INT)) {
			throw new TypecheckException(PrimType.INT, e2);
		}
		
		return new SetType(PrimType.INT);
	}

	@Override
	public Type visit(LeqExp exp) {
		checkAllChildrenConformToType(exp);
		return PrimType.BOOL;
	}

	@Override
	public Type visit(LthExp exp) {
		if (checkAllChildrenConformToType(exp).equals(PrimType.BOOL)) {
			throw new TypecheckException("Compare functions support " + PrimType.BOOL.toString() + " or " + PrimType.INT.toString());
		}
		
		return PrimType.BOOL;
	}

	@Override
	public Type visit(ModExp exp) {
		checkAllChildrenConformToType(exp, PrimType.INT);
		return PrimType.INT;
	}

	@Override
	public Type visit(MulExp exp) {
		checkAllChildrenConformToType(exp, PrimType.INT);
		return PrimType.INT;
	}

	@Override
	public Type visit(NegExp exp) {
		checkAllChildrenConformToType(exp, PrimType.INT);
		return PrimType.INT;
	}

	@Override
	public Type visit(NeqExp exp) {
		checkAllChildrenConformToType(exp);
		return PrimType.BOOL;
	}

	@Override
	public Type visit(NotExp exp) {
		checkAllChildrenConformToType(exp, PrimType.BOOL);
		return PrimType.BOOL;
	}

	@Override
	public Type visit(NumLit exp) {
		return PrimType.INT;
	}

	@Override
	public Type visit(OrExp exp) {
		checkAllChildrenConformToType(exp, PrimType.BOOL);
		return PrimType.BOOL;
	}

	@Override
	public Type visit(SetExp exp) {
		Iterator<Exp> children = exp.getChildren();
		
		if (!children.hasNext()) {
			return new SetType();
		}
		
		Type firstChildrenType = children.next().accept(this);
		Type childrenType = null;
		while (children.hasNext()) {
			childrenType = children.next().accept(this);
			
			if (!childrenType.equals(firstChildrenType)) {
				throw new TypecheckException("SET has children with different types (supposed type is " + firstChildrenType.toString() + " but at least another item is " + childrenType.toString() + ")");
			}
		}
		
		return new SetType(firstChildrenType);
	}

	@Override
	public Type visit(StrictAndExp exp) {
		return checkBinaryOperationSet(exp);
	}

	@Override
	public Type visit(StrictOrExp exp) {
		return checkBinaryOperationSet(exp);
	}

	@Override
	public Type visit(SubExp exp) {
		checkAllChildrenConformToType(exp, PrimType.INT);
		return PrimType.INT;
	}

	@Override
	public Type visit(Node node) {
		return node.getExp().accept(this);
	}
	
}
