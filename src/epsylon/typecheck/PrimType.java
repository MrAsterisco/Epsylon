package epsylon.typecheck;

import epsylon.interfaces.Type;

/**
 * Represents a primitive type (boolean or integer).
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public enum PrimType implements Type {

	INT, BOOL;
	
	public String toString() {
		return name();
	}

}
