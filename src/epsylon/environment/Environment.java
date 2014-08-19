package epsylon.environment;

import epsylon.ast.Ident;

public interface Environment<T> {
	
	void pushNewLevel();
	void popCurrentLevel();
	
	T put(Ident id, T value);
	
	void checkAndPut(Ident id, T value);
	
	T lookup(Ident id);
	
	T lookupAndCheck(Ident id);
	
}
