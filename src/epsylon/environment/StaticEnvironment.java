package epsylon.environment;

import epsylon.ast.Ident;
import epsylon.exception.TypecheckException;
import epsylon.interfaces.Type;

/**
 * Represents a Static Environment of variables associated with their type.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class StaticEnvironment extends GenericEnvironment<Type> {
	
	/**
	 * If not already present, puts an Identifier with its type into the Environment.
	 *
	 * @param id The identifier.
	 * @param type The associated type.
	 * @throws TypecheckException if the Identifier is already present in the Environment.
	 */
	@Override
	public void checkAndPut(Ident id, Type type) {
		super.checkAndPut(id, type, new TypecheckException("Variable " + id.getName() + " has been already declared."));
	}
	
	/**
	 * Gets the Type associated to an Identifier.
	 * 
	 * @param id The identifier
	 * @return The associated type.
	 * @throws TypecheckException if the Identifier cannot be found in the Environment.
	 */
	@Override
	public Type lookupAndCheck(Ident id) {
		return super.lookupAndCheck(id, new TypecheckException("Variable " + id.getName() + " is undefined."));
	}
}