package epsylon.environment;

import epsylon.ast.Ident;
import epsylon.exception.EvaluationException;
import epsylon.interfaces.Value;

public class DynamicEnvironment extends GenericEnvironment<Value> {

	@Override
	public void checkAndPut(Ident id, Value value) {
		super.checkAndPut(id, value, new EvaluationException("Variable " + id.getName() + " has been already declared."));
	}

	@Override
	public Value lookupAndCheck(Ident id) {
		return super.lookupAndCheck(id, new EvaluationException("Variable " + id.getName() + " is undefined."));
	}

}
