package epsylon.environment;

import epsylon.ast.Ident;

/**
 * Defines an interface for all the objects that aims to represent an Environment.
 * 
 * @author Alessio Moiso
 * @version 1.0
 * @param <T> A type to which all the items contained into the Environment should belong.
 */
public interface Environment<T> {
	
	/**
	 * Pushes a new level (or scope) into the Environment.
	 */
	void pushNewLevel();
	
	/**
	 * Deletes the current level (or scope) of the Environment.
	 */
	void popCurrentLevel();
	
	/**
	 * Puts an Identifier with its T value into the Environment.
	 * 
	 * @param id An identifier
	 * @param value The associated value.
	 * @return The value type.
	 */
	T put(Ident id, T value);
	
	/**
	 * Checks if the Identifier has already been declared into the Environment and possibly sets its associated value.
	 * 
	 * @param id An identifier.
	 * @param value The associated value.
	 */
	void checkAndPut(Ident id, T value);
	
	/**
	 * Looks for the specified identifier into the Environment.
	 * 
	 * @param id An identifier.
	 * @return The associated value.
	 */
	T lookup(Ident id);
	
	/**
	 * Checks if the Identifier is declared into the Environment and possibly returns its associated value.
	 * 
	 * @param id An identifier.
	 * @return The associated value.
	 */
	T lookupAndCheck(Ident id);
	
}
