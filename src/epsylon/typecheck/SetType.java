package epsylon.typecheck;

import epsylon.interfaces.Type;

public class SetType implements Type {
	private Type innerType;
	
	public SetType() {
		this.innerType = null;
	}
	
	public SetType(Type innerType) {
		this.innerType = innerType;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
	    int result = 1;
	    result = prime * result + (int) (this.innerType.toString().hashCode());
	    return result;
	}
	
	@Override
	public String toString() {
		if (this.innerType == null) {
			return "GENERIC SET";
		}
		
		return "SET OF " + this.innerType.toString();
	}
	
	public Type getInnerType() {
		return this.innerType;
	}
	
	@Override
	public boolean equals(Object obj2) {
		if (obj2 instanceof SetType) {
			if (((SetType) obj2).getInnerType() == null) {
				return true;
			}
			
			return getInnerType().equals(((SetType) obj2).getInnerType());
		}
		return false;
	}
	
}
