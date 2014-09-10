package epsylon.evaluate;

import epsylon.interfaces.Value;

/**
 * Wraps an integer value into a Evaluation-compatible class.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class IntValue implements Value {
	
	private Integer value;
	
	/**
	 * Instantiates a new IntValue with its intrinsic value.
	 * 
	 * @param value An integer value.
	 */
	public IntValue(Integer value) {
		this.value = value;
	}

	/**
	 * Gets the intrinsic value.
	 * 
	 * @return An integer value.
	 */
	@Override
	public Integer getValue() {
		return this.value;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
	    int result = 1;
	    result = prime * result + (int) (this.value ^ (this.value >>> 32));
	    return result;
	}

	@Override
	public boolean equals(Object obj2) {
		if (obj2 instanceof IntValue) {
			return this.value.intValue() == ((IntValue) obj2).value.intValue();
		}
		return false;
	}
	
	@Override
	public String toString() {
		return this.value.toString();
	}
}
