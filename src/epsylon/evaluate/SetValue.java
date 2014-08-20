package epsylon.evaluate;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import epsylon.interfaces.Value;

public class SetValue implements Value {
	
	private HashSet<Value> items;
	
	public SetValue() {
		items = new HashSet<Value>();
	}
	
	public void add(Value item) {
		items.add(item);
	}
	
	public void addAll(Set<Value> collection) {
		for (Value value : collection) {
			this.add(value);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
	    int result = 1;
	    result = prime * result + (int) (this.items.hashCode());
	    return result;
	}
	
	@Override
	public HashSet<Value> getValue() {
		return this.items;
	}
	
	@Override
	public boolean equals(Object obj2) {
		if (obj2 instanceof SetValue && ((SetValue) obj2).getValue() instanceof HashSet<?>) {
			HashSet<Value> hashSet = (((SetValue) obj2).getValue());
			if (hashSet.size() == items.size()) {
				for (Value item : items) {
					if (!hashSet.contains(item)) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("{");
		
		if (this.items.size() > 0) {
			if (this.items.iterator().next() instanceof IntValue) {
				Set<Integer> treeSet = new TreeSet<Integer>();
				for (Value value : this.items) {
					treeSet.add(((IntValue) value).getValue());
				}
				
				for (Integer value : treeSet) {
					builder.append(value.toString() + ", ");
				}
			}
			else {
				for (Value value : this.items) {
					builder.append(value.toString() + ", ");
				}
			}
			
			builder.delete(builder.length()-2, builder.length());
		}
		
		builder.append("}");
		
		return builder.toString();
	}
}
