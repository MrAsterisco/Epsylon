package epsylon.typecheck;

import epsylon.interfaces.Type;

public class SetType implements Type {
	private Type innerType;
	
	public SetType(Type innerType) {
		this.innerType = innerType;
	}
	
	@Override
	public String toString() {
		return "SET OF " + this.innerType.toString();
	}
	
	public Type getInnerType() {
		return this.innerType;
	}
	
}
