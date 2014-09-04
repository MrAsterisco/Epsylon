package epsylon.evaluate;

import java.util.Iterator;
import java.util.Set;

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
import epsylon.environment.DynamicEnvironment;
import epsylon.exception.EvaluationException;
import epsylon.interfaces.Exp;
import epsylon.interfaces.Value;
import epsylon.interfaces.Visitor;

public class Evaluator implements Visitor<Value> {

	DynamicEnvironment environment;
	
	public Evaluator() {
		environment = new DynamicEnvironment();
	}
	
	public Value evaluate(Node tree) {
		return tree.accept(this);
	}
	
	private Boolean compare(Value e1, Value e2) {
		if (e1 instanceof IntValue && e2 instanceof IntValue) {
			return ((IntValue) e1).getValue().intValue() <= ((IntValue) e2).getValue().intValue();
		}
		else if (e1 instanceof SetValue && e2 instanceof SetValue) {
			Set<Value> hashSet1 = (((SetValue) e1).getValue());
			Set<Value> hashSet2 = (((SetValue) e2).getValue());
			for (Value item : hashSet1) {
				if (!hashSet2.contains(item)) {
					return false;
				}
			}
		}
		
		throw new EvaluationException("Compare operation undefined between " + e1.toString() + " and " + e2.toString());
	}

	@Override
	public Value visit(AddExp exp) {
		Iterator<Exp> children = exp.getChildren();
		
		Value val = children.next().accept(this);
		Value val2 = children.next().accept(this);
		
		if (!(val instanceof IntValue) || !(val2 instanceof IntValue)) {
			throw new EvaluationException(exp, val, val2);
		}
		
		return new IntValue(((IntValue) val).getValue().intValue() + ((IntValue) val2).getValue().intValue());
	}

	@Override
	public Value visit(AllSetExp exp) {
		environment.pushNewLevel();
		
		Iterator<Exp> children = exp.getChildren();
		Ident ident = (Ident) children.next();
		environment.checkAndPut(ident, null);
		
		Value set = children.next().accept(this);
		AbsOpExp formula = (AbsOpExp) children.next();
		
		if (!(set instanceof SetValue)) {
			throw new EvaluationException(exp, set);
		}
		
		Set<Value> hashSet = ((SetValue) set).getValue();
		
		SetValue builtSet = new SetValue();
		
		for (Value value : hashSet) {
			environment.put(ident, value);
			if (((BoolValue)formula.accept(this)).getValue()) {
				builtSet.add(value);
			}
		}
		
		environment.popCurrentLevel();
		
		return builtSet;
	}

	@Override
	public Value visit(AndExp exp) {
		Iterator<Exp> children = exp.getChildren();
		
		Value val = children.next().accept(this);
		Value val2 = children.next().accept(this);
		
		if (!(val instanceof BoolValue) || !(val2 instanceof BoolValue)) {
			throw new EvaluationException(exp, val, val2);
		}
		
		return new BoolValue(((BoolValue) val).getValue().booleanValue() && ((BoolValue) val2).getValue().booleanValue());
	}

	@Override
	public Value visit(BackslashExp exp) {
		Iterator<Exp> children = exp.getChildren();
		
		Value e1 = children.next().accept(this);
		Value e2 = children.next().accept(this);
		
		if (!(e1 instanceof SetValue) || !(e2 instanceof SetValue)) {
			throw new EvaluationException(exp, e1, e2);
		}
		
		Set<Value> hashSet = ((SetValue) e1).getValue();
		Set<Value> hashSet2 = ((SetValue) e2).getValue();
		
		hashSet.removeAll(hashSet2);
		
		SetValue builtSet = new SetValue();
		builtSet.addAll(hashSet);
		return builtSet;
	}

	@Override
	public Value visit(BoolLit exp) {
		return new BoolValue(exp.getValue());
	}

	@Override
	public Value visit(ContainsExp exp) {
		Iterator<Exp> children = exp.getChildren();
		
		Value val = children.next().accept(this);
		Value val2 = children.next().accept(this);
		
		if (!(val instanceof SetValue)) {
			throw new EvaluationException(exp, val, val2);
		}
		
		Set<Value> items = ((SetValue) val).getValue();
		for (Value item : items) {
			if (item.equals(val2)) {
				return new BoolValue(true);
			}
		}
		return new BoolValue(false);
	}

	@Override
	public Value visit(DivExp exp) {
		Iterator<Exp> children = exp.getChildren();
		
		Value val = children.next().accept(this);
		Value val2 = children.next().accept(this);
		
		if (!(val instanceof IntValue) || !(val2 instanceof IntValue)) {
			throw new EvaluationException(exp, val, val2);
		}
		
		if (((IntValue)val2).getValue() == 0) {
			throw new EvaluationException("Math division by zero is undefined.");
		}
		
		return new IntValue(((IntValue) val).getValue().intValue() / ((IntValue) val2).getValue().intValue());
	}

	@Override
	public Value visit(EqExp exp) {
		Iterator<Exp> children = exp.getChildren();
		
		Value val = children.next().accept(this);
		Value val2 = children.next().accept(this);
		
		return new BoolValue(val.equals(val2));
	}

	@Override
	public Value visit(ExistsExp exp) {
		environment.pushNewLevel();
		
		Iterator<Exp> children = exp.getChildren();
		
		Ident ident = (Ident) children.next();
		environment.checkAndPut(ident, null);
		
		Value set = children.next().accept(this);
		Set<Value> hashSet = ((SetValue) set).getValue();
		
		AbsOpExp condition = (AbsOpExp)children.next();
		
		for (Value value : hashSet) {
			environment.put(ident, value);
			Value bool = condition.accept(this);
			if (((BoolValue) bool).getValue()) {
				environment.popCurrentLevel();
				
				return new BoolValue(new Boolean(true));
			}
		}
		
		environment.popCurrentLevel();
		
		return new BoolValue(new Boolean(false));
	}

	@Override
	public Value visit(ForallExp exp) {
		environment.pushNewLevel();
		
		Iterator<Exp> children = exp.getChildren();
		
		Ident ident = (Ident) children.next();
		environment.checkAndPut(ident, null);
		
		Value set = children.next().accept(this);
		Set<Value> hashSet = ((SetValue) set).getValue();
		
		AbsOpExp formula = (AbsOpExp)children.next();
		
		for (Value value : hashSet) {
			environment.put(ident, value);
			Value bool = formula.accept(this);
			if (!((BoolValue) bool).getValue()) {
				environment.popCurrentLevel();
				
				return new BoolValue(new Boolean(false));
			}
		}
		
		environment.popCurrentLevel();
		
		return new BoolValue(new Boolean(true));
	}

	@Override
	public Value visit(ForallSetExp exp) {
		environment.pushNewLevel();
		
		Iterator<Exp> children = exp.getChildren();
		
		Ident ident = (Ident) children.next();
		environment.checkAndPut(ident, null);
		
		Value set = children.next().accept(this);
		AbsOpExp formula = (AbsOpExp) children.next();
		
		if (!(set instanceof SetValue)) {
			throw new EvaluationException(exp, set);
		}
		
		Set<Value> hashSet = ((SetValue) set).getValue();
		SetValue builtSet = new SetValue();
		
		for (Value value : hashSet) {
			environment.put(ident, value);
			builtSet.add(formula.accept(this));
		}
		
		environment.popCurrentLevel();
		
		return builtSet;
	}

	@Override
	public Value visit(GeqExp exp) {
		Iterator<Exp> children = exp.getChildren();
		
		Value e1 = children.next().accept(this);
		Value e2 = children.next().accept(this);
		
		if (!e1.equals(e2)) {
			return new BoolValue(compare(e2, e1));
		}
		return new BoolValue(new Boolean(false));
	}

	@Override
	public Value visit(GthExp exp) {
		Iterator<Exp> children = exp.getChildren();
		
		Value e1 = children.next().accept(this);
		Value e2 = children.next().accept(this);
		
		return new BoolValue(compare(e2, e1));
	}

	@Override
	public Value visit(HashExp exp) {
		Iterator<Exp> children = exp.getChildren();
		
		Value child = children.next().accept(this);
		
		if (!(child instanceof SetValue)) {
			throw new EvaluationException(exp, child);
		}
		
		return new IntValue(((SetValue) child).getValue().size());
	}

	@Override
	public Value visit(Ident exp) {
		return environment.lookupAndCheck(exp);
	}

	@Override
	public Value visit(ImpliesExp exp) {
		Iterator<Exp> children = exp.getChildren();
		
		Value e1 = children.next().accept(this);
		Value e2 = children.next().accept(this);
		
		if (!(e1 instanceof BoolValue) && !(e2 instanceof BoolValue)) {
			throw new EvaluationException(exp, e1, e2);
		}
		
		return new BoolValue(!((BoolValue) e1).getValue().booleanValue() || ((BoolValue) e1).getValue().booleanValue());
	}

	@Override
	public Value visit(IntervalExp exp) {
		Iterator<Exp> children = exp.getChildren();
		
		Value start = children.next().accept(this);
		Value end = children.next().accept(this);
		
		if (!(start instanceof IntValue) && !(end instanceof IntValue)) {
			throw new EvaluationException(exp, start, end);
		}
		
		int startInt = ((IntValue) start).getValue();
		int endInt = ((IntValue) end).getValue();
		
		SetValue set = new SetValue();
		for (; startInt <= endInt; startInt++) {
			set.add(new IntValue(startInt));
		}
		
		return set;
	}

	@Override
	public Value visit(LeqExp exp) {
		Iterator<Exp> children = exp.getChildren();
		
		Value e1 = children.next().accept(this);
		Value e2 = children.next().accept(this);
		
		return new BoolValue(compare(e1, e2));
	}

	@Override
	public Value visit(LthExp exp) {
		Iterator<Exp> children = exp.getChildren();
		
		Value e1 = children.next().accept(this);
		Value e2 = children.next().accept(this);
		
		if (!e1.equals(e2)) {
			return new BoolValue(compare(e1, e2));
		}
		return new BoolValue(new Boolean(false));
	}

	@Override
	public Value visit(ModExp exp) {
		Iterator<Exp> children = exp.getChildren();
		
		Value val = children.next().accept(this);
		Value val2 = children.next().accept(this);
		
		if (!(val instanceof IntValue) || !(val2 instanceof IntValue)) {
			throw new EvaluationException(exp, val, val2);
		}
		
		if (((IntValue)val2).getValue() == 0) {
			throw new EvaluationException("Math division by zero is undefined.");
		}
		
		return new IntValue(((IntValue) val).getValue().intValue() % ((IntValue) val2).getValue().intValue());
	}

	@Override
	public Value visit(MulExp exp) {
		Iterator<Exp> children = exp.getChildren();
		
		Value val = children.next().accept(this);
		Value val2 = children.next().accept(this);
		
		if (!(val instanceof IntValue) || !(val2 instanceof IntValue)) {
			throw new EvaluationException(exp, val, val2);
		}
		
		return new IntValue(((IntValue) val).getValue().intValue() * ((IntValue) val2).getValue().intValue());
	}

	@Override
	public Value visit(NegExp exp) {
		Iterator<Exp> children = exp.getChildren();
		
		Value val = children.next().accept(this);
		
		if (!(val instanceof IntValue)) {
			throw new EvaluationException(exp, val);
		}
		
		return new IntValue(-((IntValue) val).getValue().intValue());
	}

	@Override
	public Value visit(NeqExp exp) {
		Iterator<Exp> children = exp.getChildren();
		
		Value val = children.next().accept(this);
		Value val2 = children.next().accept(this);
		
		boolean equals = (val.equals(val2));
		return new BoolValue(!equals);
	}

	@Override
	public Value visit(NotExp exp) {
		Iterator<Exp> children = exp.getChildren();
		
		Value val = children.next().accept(this);
		
		if (!(val instanceof BoolValue)) {
			throw new EvaluationException(exp, val);
		}
		
		return new BoolValue(!((BoolValue) val).getValue().booleanValue());
	}

	@Override
	public Value visit(NumLit exp) {
		return new IntValue(exp.getValue());
	}

	@Override
	public Value visit(OrExp exp) {
		Iterator<Exp> children = exp.getChildren();
		
		Value val = children.next().accept(this);
		Value val2 = children.next().accept(this);
		
		if (!(val instanceof BoolValue) || !(val2 instanceof BoolValue)) {
			throw new EvaluationException(exp, val, val2);
		}
		
		return new BoolValue(((BoolValue) val).getValue().booleanValue() || ((BoolValue) val2).getValue().booleanValue());
	}

	@Override
	public Value visit(SetExp exp) {
		Iterator<Exp> children = exp.getChildren();
		SetValue set = new SetValue();
		
		while (children.hasNext()) {
			Value value = children.next().accept(this);
			set.add(value);
		}
		
		return set;
	}

	@Override
	public Value visit(StrictAndExp exp) {
		Iterator<Exp> children = exp.getChildren();
		
		Value e1 = children.next().accept(this);
		Value e2 = children.next().accept(this);
		
		if (!(e1 instanceof SetValue) || !(e2 instanceof SetValue)) {
			throw new EvaluationException(exp, e1, e2);
		}
		
		Set<Value> hashSet = ((SetValue) e1).getValue();
		Set<Value> hashSet2 = ((SetValue) e2).getValue();
		
		hashSet.retainAll(hashSet2);
		
		SetValue builtSet = new SetValue();
		builtSet.addAll(hashSet);
		
		return builtSet;
	}

	@Override
	public Value visit(StrictOrExp exp) {
		Iterator<Exp> children = exp.getChildren();
		
		Value e1 = children.next().accept(this);
		Value e2 = children.next().accept(this);
		
		if (!(e1 instanceof SetValue) || !(e2 instanceof SetValue)) {
			throw new EvaluationException(exp, e1, e2);
		}
		
		Set<Value> hashSet = ((SetValue) e1).getValue();
		Set<Value> hashSet2 = ((SetValue) e2).getValue();
		
		hashSet.addAll(hashSet2);
		
		SetValue builtSet = new SetValue();
		builtSet.addAll(hashSet);
		return builtSet;
	}

	@Override
	public Value visit(SubExp exp) {
		Iterator<Exp> children = exp.getChildren();
		
		Value val = children.next().accept(this);
		Value val2 = children.next().accept(this);
		
		if (!(val instanceof IntValue) && !(val2 instanceof IntValue)) {
			throw new EvaluationException(exp, val, val2);
		}
		
		return new IntValue(((IntValue) val).getValue().intValue() - ((IntValue) val2).getValue().intValue());
	}

	@Override
	public Value visit(Node node) {
		return node.getExp().accept(this);
	}

}
