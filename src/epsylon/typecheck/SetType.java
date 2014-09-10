package epsylon.typecheck;

import epsylon.interfaces.Type;

/**
 * Represents a Set type.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class SetType implements Type {
	private Type innerType;
	
	/**
	 * Instantiates a new Set without an inner type.
	 */
	public SetType() {
		this.innerType = null;
	}
	
	/**
	 * Instantiates a new Set with its inner type.
	 * 
	 * @param innerType A type.
	 */
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
	
	/**
	 * Represents this Set as a String.
	 * 
	 * @return GENERIC SET if the inner type of this Set is not defined, otherwise SET OF innerType.
	 */
	@Override
	public String toString() {
		if (this.innerType == null) {
			return "GENERIC SET";
		}
		
		return "SET OF " + this.innerType.toString();
	}
	
	/**
	 * Gets the inner type of this Set.
	 * 
	 * @return The inner type.
	 */
	public Type getInnerType() {
		return this.innerType;
	}
	
	/**
	 * Compares two Sets to see if they're the same.
	 * 
	 * @return true if the second object is a Set has the same innerType as this Set.
	 */
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
