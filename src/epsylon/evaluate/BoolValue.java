package epsylon.evaluate;

import epsylon.interfaces.Value;

/**
 * Wraps a boolean value into a Evaluation-compatible class.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class BoolValue implements Value {
	
	private Boolean value;
	
	/**
	 * Instantiates a new BoolValue with its intrinsic value.
	 * 
	 * @param value A boolean value.
	 */
	public BoolValue(Boolean value) {
		this.value = value;
	}

	/**
	 * Gets the intrinsic value.
	 * 
	 * @return A boolean value.
	 */
	@Override
	public Boolean getValue() {
		return this.value;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
	    int result = 1;
	    result = prime * result + (int) (this.value.toString().hashCode());
	    return result;
	}
	
	@Override
	public boolean equals(Object obj2) {
		if (obj2 instanceof BoolValue) {
			return this.value.equals((((BoolValue) obj2).value));
		}
		return false;
	}
	
	@Override
	public String toString() {
		return this.value.toString();
	}
}
