package epsylon.ast;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import epsylon.interfaces.Exp;
import epsylon.interfaces.Visitor;

/**
 * Represents a Set in the Abstract Syntax Tree.
 * 
 * @author Alessio Moiso
 * @version 1.0
 */
public class SetExp extends AbsOpExp {
	
	/**
	 * The expressions that are contained by this Set.
	 */
	private Set<Exp> children;
	
	/**
	 * Instantiates a new empty Set.
	 */
	public SetExp() {
		children = new HashSet<Exp>();
	}
	
	/**
	 * Instantiates a new Set with one item.
	 * 
	 * @param oneItem The item to be added to the Set.
	 */
	public SetExp(Exp oneItem)  {
		this();
		this.add(oneItem);
	}
	
	/**
	 * Adds an item to the Set
	 * 
	 * @param e The expression to be added to the Set.
	 */
	public void add(Exp e) {
		children.add(e);
	}
	
	/**
	 * Gets an iterator on the children of this Set.
	 * 
	 * @return An iterator on the children of this Set.
	 */
	@Override
	public Iterator<Exp> getChildren() {
		return children.iterator();
	}

	/**
	 * Calls the visit method of a Visitor.
	 * 
	 * @param v A visitor on which the visit method should be called.
	 * @return The object returned by the Visitor.
	 */
	@Override
	public <T> T accept(Visitor<T> v) {
		return v.visit(this);
	}

}
