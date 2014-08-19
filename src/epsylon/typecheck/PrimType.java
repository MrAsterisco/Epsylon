package epsylon.typecheck;

import epsylon.interfaces.Type;

public enum PrimType implements Type {

	INT, BOOL;
	
	public String toString() {
		return name();
	}
	
	public boolean equals(Type type) {
		return this == type;
	}

}
