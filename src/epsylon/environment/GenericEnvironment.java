package epsylon.environment;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import epsylon.ast.Ident;

/**
 * Represents an abstract environment, implementing all the common methods from the Environment interface.
 * 
 * @author Alessio Moiso
 * @version 1.0
 * @see epsylon.environment.Environment
 * @param <T> A type to which all the items contained into the Environment should belong.
 */
public abstract class GenericEnvironment<T> implements Environment<T> {

	private LinkedList<Map<Ident, T>> localEnvs = new LinkedList<>();

	protected Map<Ident, T> getCurrentLocalEnv() {
		return localEnvs.get(0);
	}

	@Override
	public void pushNewLevel() {
		localEnvs.add(0, new HashMap<Ident, T>());
	}

	@Override
	public void popCurrentLevel() {
		localEnvs.remove(0);
	}

	@Override
	public T put(Ident id, T value) {
		return getCurrentLocalEnv().put(id, value);
	}

	protected void checkAndPut(Ident id, T value, RuntimeException exc) {
		Map<Ident, T> locEnv = getCurrentLocalEnv();
		if (locEnv.containsKey(id))
			throw exc;
		locEnv.put(id, value);
	}

	@Override
	public T lookup(Ident id) {
		for (Map<Ident, T> locEnv : localEnvs) {
			T value = locEnv.get(id);
			
			if (value != null)
				return value;
		}
		return null;
	}

	protected T lookupAndCheck(Ident id, RuntimeException exc) {
		T value = lookup(id);
		if (value == null)
			throw exc;
		return value;
	}

}
