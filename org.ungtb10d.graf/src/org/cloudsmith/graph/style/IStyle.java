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
package org.ungtb10d.graf.style;

import java.util.Set;

import org.ungtb10d.graf.ElementType;
import org.ungtb10d.graf.IgrafElement;

/**
 * Interface for a style.
 * A style has a {@link StyleType} and a value of type <T>.
 * 
 * @param <T>
 */
public interface IStyle<T> {

	public StyleType getStyleType();

	/**
	 * Gets the value of the style. A graf element must be provided as the style may be dynamic
	 * and produce its value as a function of the given graf element. If a style returns false
	 * from {@link #isFunction()}, the parameter ge may be null. At all other times must ge be a valid
	 * graf element.
	 * 
	 * @param ge
	 * @return
	 */
	public T getValue(IgrafElement ge);

	/**
	 * A style with a value determined as a function of a grafic element returns true. A style that
	 * returns false allows null being passed as the graf element in a call to {@link #getValue(IgrafElement)}.
	 * 
	 * @return
	 */
	public boolean isFunction();

	/**
	 * Returns true if the style is supported for a particular type of element.
	 * 
	 * @param type
	 * @return
	 */
	public boolean supports(ElementType type);

	/**
	 * Returns true if the style supports all elements in the given set.
	 * 
	 * @param types
	 * @return
	 */
	public boolean supports(Set<ElementType> types);

	/**
	 * Visiting this IStyle means it will call back to the given visitor method named after the style type.
	 * 
	 * @param ge
	 *            The visited graf element
	 * @param visitor
	 *            The visitor that will be called
	 */
	public void visit(IgrafElement ge, IStyleVisitor visitor);

}
