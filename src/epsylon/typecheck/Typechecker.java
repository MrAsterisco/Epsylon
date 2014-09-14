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

/**
 * The typechecker checks that all the types given to each function in the AST are correct.
 * The AST must have been parsed by a Parser object.
 * 
 * If a type does not conform to the language syntax rules, a TypecheckException is thrown,
 * 
 * The typechecker uses the default constructor. Call typecheck(Node) to start typechecking.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class Typechecker implements Visitor<Type> {

	private StaticEnvironment environment;
	
	public Typechecker() {
		environment = new StaticEnvironment();
	}
	
	/**
	 * Starts typechecking the given tree.
	 * 
	 * @param tree A valid AST.
	 * @return Return the final redult type of the AST.
	 */
	public Type typecheck(Node tree) {
		return tree.accept(this);
	}
	
	// Checks that all the children of a given type conform to a specified type.
	private void checkAllChildrenConformToType(AbsOpExp exp, Type requestedType) {
		for (Iterator<Exp> iterator = exp.getChildren(); iterator.hasNext();) {
			AbsOpExp child = (AbsOpExp)iterator.next();
			
			Type type = child.accept(this);
			
			if (!type.equals(requestedType)) {
				throw new TypecheckException(requestedType, type);
			}
		}
	}
	
	// Checks that all the children of a given expression belongs to the same type.
	private Type checkAllChildrenConformToType(AbsOpExp exp) {
		Type requestedType = exp.getChildren().next().accept(this);
		
		checkAllChildrenConformToType(exp, requestedType);
		
		return requestedType;
	}
	
	// Validates a AllSet/Exists/Forall expression.
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
	
	// Validates a binary operation.
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
	
	// Validates a mathematical addition.
	@Override
	public Type visit(AddExp exp) {
		checkAllChildrenConformToType(exp, PrimType.INT);
		return PrimType.INT;
	}

	// Validates an AllSet expression.
	@Override
	public Type visit(AllSetExp exp) {
		return new SetType(checkOperationSet(exp));
	}

	// Validates an And expression.
	@Override
	public Type visit(AndExp exp) {
		checkAllChildrenConformToType(exp, PrimType.BOOL);
		return PrimType.BOOL;
	}

	// Validates a subtraction between sets.
	@Override
	public Type visit(BackslashExp exp) {
		return checkBinaryOperationSet(exp); 
	}

	// Returns the type of a bool.
	@Override
	public Type visit(BoolLit exp) {
		return PrimType.BOOL;
	}

	// Validates a Contains expression.
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

	// Validates a mathematical division.
	@Override
	public Type visit(DivExp exp) {
		checkAllChildrenConformToType(exp, PrimType.INT);
		return PrimType.INT;
	}

	// Validates an equality operation.
	@Override
	public Type visit(EqExp exp) {
		checkAllChildrenConformToType(exp);
		return PrimType.BOOL;
	}

	// Validates an Exists expression.
	@Override
	public Type visit(ExistsExp exp) {
		checkOperationSet(exp);
		return PrimType.BOOL;
	}

	// Validates a Forall expression
	@Override
	public Type visit(ForallExp exp) {
		checkOperationSet(exp);
		return PrimType.BOOL;
	}

	// Validates a ForallSet expression.
	@Override
	public Type visit(ForallSetExp exp) {
		environment.pushNewLevel();
		
		Iterator<Exp> children = exp.getChildren();
		
		Ident ident = (Ident)children.next();
		
		Exp set = children.next();
		Exp e1 = children.next();
		
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

	// Validates a Greater Than expression.
	@Override
	public Type visit(GeqExp exp) {
		if (checkAllChildrenConformToType(exp).equals(PrimType.BOOL)) {
			throw new TypecheckException("Expected INT or SET but found BOOL.");
		}
		return PrimType.BOOL;
	}

	// Validates a Greater Than or Equal to expression.
	@Override
	public Type visit(GthExp exp) {
		if (checkAllChildrenConformToType(exp).equals(PrimType.BOOL)) {
			throw new TypecheckException("Expected INT or SET but found BOOL.");
		}
		return PrimType.BOOL;
	}

	// Validates a cardinality operation.
	@Override
	public Type visit(HashExp exp) {
		Iterator<Exp> children = exp.getChildren();
		
		Type set = children.next().accept(this);
		if (!set.equals(new SetType())) {
			throw new TypecheckException(new SetType(), set);
		}
		
		return PrimType.INT;
	}

	// Returns the associated type of an Identifier.
	@Override
	public Type visit(Ident exp) {
		return environment.lookupAndCheck(exp);
	}

	// Validates an Implies expression.
	@Override
	public Type visit(ImpliesExp exp) {
		checkAllChildrenConformToType(exp, PrimType.BOOL);
		return PrimType.BOOL;
	}

	// Validates an Interval.
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

	// Validates a Less Than expression.
	@Override
	public Type visit(LeqExp exp) {
		if (checkAllChildrenConformToType(exp).equals(PrimType.BOOL)) {
			throw new TypecheckException("Expected INT or SET but found BOOL.");
		}
		return PrimType.BOOL;
	}

	// Validates a Less Than or Equal to expression.
	@Override
	public Type visit(LthExp exp) {
		if (checkAllChildrenConformToType(exp).equals(PrimType.BOOL)) {
			throw new TypecheckException("Expected INT or SET but found BOOL.");
		}
		return PrimType.BOOL;
	}

	// Validates a mathematical module.
	@Override
	public Type visit(ModExp exp) {
		checkAllChildrenConformToType(exp, PrimType.INT);
		return PrimType.INT;
	}

	// Validates a mathematical multiplication.
	@Override
	public Type visit(MulExp exp) {
		checkAllChildrenConformToType(exp, PrimType.INT);
		return PrimType.INT;
	}

	// Validates a negative operation.
	@Override
	public Type visit(NegExp exp) {
		checkAllChildrenConformToType(exp, PrimType.INT);
		return PrimType.INT;
	}

	// Validates a Not Equal To expression.
	@Override
	public Type visit(NeqExp exp) {
		checkAllChildrenConformToType(exp);
		return PrimType.BOOL;
	}

	// Validates a Not expression.
	@Override
	public Type visit(NotExp exp) {
		checkAllChildrenConformToType(exp, PrimType.BOOL);
		return PrimType.BOOL;
	}

	// Returns the type of an Int.
	@Override
	public Type visit(NumLit exp) {
		return PrimType.INT;
	}

	// Validates an Or expression.
	@Override
	public Type visit(OrExp exp) {
		checkAllChildrenConformToType(exp, PrimType.BOOL);
		return PrimType.BOOL;
	}

	// Returns the intrinsic type of a Set.
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

	// Validates an intersection operation.
	@Override
	public Type visit(StrictAndExp exp) {
		return checkBinaryOperationSet(exp);
	}

	// Validates a union operation.
	@Override
	public Type visit(StrictOrExp exp) {
		return checkBinaryOperationSet(exp);
	}

	// Validates a mathematical subtraction.
	@Override
	public Type visit(SubExp exp) {
		checkAllChildrenConformToType(exp, PrimType.INT);
		return PrimType.INT;
	}

	// Starts visiting the source node.
	@Override
	public Type visit(Node node) {
		return node.getExp().accept(this);
	}
	
}
