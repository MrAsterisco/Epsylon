package epsylon.evaluate;

import epsylon.interfaces.Value;

public class BoolValue implements Value {
	
	private Boolean value;
	
	public BoolValue(Boolean value) {
		this.value = value;
	}

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
