package epsylon.environment;

import epsylon.ast.Ident;

/**
 * Defines an interface for all the objects that want to represent an Environment.
 * 
 * @author Alessio Moiso
 * @version 1.0
 * @param <T> A type to which all the items contained into the Environment should belong.
 */
public interface Environment<T> {
	
	void pushNewLevel();
	void popCurrentLevel();
	
	T put(Ident id, T value);
	
	void checkAndPut(Ident id, T value);
	
	T lookup(Ident id);
	
	T lookupAndCheck(Ident id);
	
}
