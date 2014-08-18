package epsylon.ast;

public abstract class Lit<T> extends AbsOpExp {
	protected final T value;

	public Lit(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	@Override
	public String toString() {
		StringBuilder root = new StringBuilder(super.toString());
		root.append("(").append(value).append(")");
		return root.toString();
	}

}
