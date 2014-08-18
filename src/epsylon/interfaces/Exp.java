package epsylon.interfaces;

public interface Exp {
	
	<T>T accept(Visitor<T> v);
	
}
