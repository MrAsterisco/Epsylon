package epsylon.environment;

import epsylon.ast.Ident;
import epsylon.exception.EvaluationException;
import epsylon.interfaces.Value;

/**
 * Represents a Dynamic Environment of variables associated with their values.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class DynamicEnvironment extends GenericEnvironment<Value> {

	/**
	 * If not already present, puts an Identifier with its value into the Environment.
	 *
	 * @param id The identifier.
	 * @param value The associated value.
	 * @throws EvaluationException if the Identifier is already present in the Environment.
	 */
	@Override
	public void checkAndPut(Ident id, Value value) {
		super.checkAndPut(id, value, new EvaluationException("Variable " + id.getName() + " has been already declared."));
	}

	/**
	 * Gets the Value associated to an Identifier.
	 * 
	 * @param id The identifier
	 * @return The associated value.
	 * @throws EvaluationException if the Identifier cannot be found in the Environment.
	 */
	@Override
	public Value lookupAndCheck(Ident id) {
		return super.lookupAndCheck(id, new EvaluationException("Variable " + id.getName() + " is undefined."));
	}

}
