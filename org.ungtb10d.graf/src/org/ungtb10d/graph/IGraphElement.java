/**
 * Copyright (c) 2006-2011 ungtb10d Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   ungtb10d
 * 
 */
package org.ungtb10d.graf;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.ungtb10d.graf.grafcss.StyleSet;

/**
 * The base interface for graf elements (graf, vertex, and edge).
 * 
 */
public interface IgrafElement {
	/**
	 * Adds all style classes in the given collection to the set of style classes for the element.
	 * 
	 * @param styleClasses
	 * @return true if the set of style classes did not already contain all o the given style classes.
	 */
	public boolean addAllStyleClasses(Collection<String> styleClasses);

	/**
	 * Adds a style class to the set of style classes for the element.
	 * 
	 * @param className
	 * @return true if the set of style classes did not already contain the specified element
	 */
	public boolean addStyleClass(String className);

	/**
	 * Returns a space separated list of all style classes.
	 * 
	 * @return
	 */
	public String getAllStyleClasses();

	/**
	 * Returns all containing graf elements, the immediate parent first.
	 * The returned iterator does not support the {@link Iterator#remove()} operation.
	 * 
	 * @return an iterator over ancestor containers
	 */
	public Iterator<IgrafElement> getContext();

	/**
	 * Returns the graf element type. This is needed as the inheritance of graf elements reflects
	 * behavior (a graf is a vertex) when it represents a subgraf/cluster and functionality that only
	 * applies to a true vertex have nothing to check against as both are instances of IVertex (and the implementation
	 * details may not be known).
	 * 
	 * @return the ElementType
	 */
	public ElementType getElementType();

	/**
	 * The id of the element. The id must be unique within its parent element.
	 * 
	 * @return a id that is unique within parent
	 */
	public String getId();

	/**
	 * Returns the immediate containing graf element.
	 * 
	 * @return parent graf element
	 */
	public IgrafElement getParentElement();

	/**
	 * The style classes of the element.
	 * Examples; for a graf it can be the name of the depicted type (e.g. "Dependency graf", a file etc.),
	 * for an vertex it can be the vertex type (i.e. what the vertex represents), and for an edge
	 * it can be the type name of the edge (e.g. "dependency"). If style class is omitted a default style
	 * is used.
	 * 
	 * @return collection of the style class names
	 */
	public Collection<String> getStyleClasses();

	/**
	 * A StyleMap for this instance, or null if no instance specific style are set.
	 * 
	 * @return a style set for the instance.
	 */
	public StyleSet getStyles();

	/**
	 * Returns user data values.
	 * 
	 * @param key
	 * @return
	 */
	public Map<String, String> getUserData();

	/**
	 * Return a user data value by key.
	 * 
	 * @param key
	 * @return
	 */
	public String getUserData(String key);

	/**
	 * Tests if the element has the given style class as one of the style classes for the element.
	 * 
	 * @param className
	 * @return
	 */
	public boolean hasStyleClass(String className);

	/**
	 * Set a user data value by key.
	 * 
	 * @param key
	 * @param value
	 */
	public void putUserData(String key, String value);

	/**
	 * Removes the given style class from the set of style classes for the element.
	 * 
	 * @param className
	 * @return true if the element was removed, false if not present
	 */
	public boolean removeStyleClass(String className);
}
