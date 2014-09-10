package epsylon.interfaces;

/**
 * Defines any expression as visitable.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public interface Exp {
	
	<T>T accept(Visitor<T> v);
	
}
