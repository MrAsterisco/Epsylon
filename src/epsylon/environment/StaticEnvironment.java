package epsylon.environment;

import epsylon.ast.Ident;
import epsylon.exception.TypecheckException;
import epsylon.interfaces.Type;

public class StaticEnvironment extends GenericEnvironment<Type> {
	
	@Override
	public void checkAndPut(Ident id, Type type) {
		super.checkAndPut(id, type, new TypecheckException(id.getName() + " has been already declared."));
	}
	
	@Override
	public Type lookupAndCheck(Ident id) {
		return super.lookupAndCheck(id, new TypecheckException(id.getName() + " is undefined."));
	}
}