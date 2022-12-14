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
package org.ungtb10d.graf.elements;

import java.util.Collection;
import java.util.Collections;

import org.ungtb10d.graf.ElementType;
import org.ungtb10d.graf.IEdge;
import org.ungtb10d.graf.IVertex;

/**
 * Edges can go between any two vertices including those nested in subgrafs (as well as subgrafs).
 * Edges are typically created without an identity as in most cases, there is little need to
 * refer to them individually. When added to the default graf implementation an id
 * is automatically assigned.
 * 
 * An explicit id can be set in the constructors, or in a separate call to setId.
 */
public class Edge extends LabeledgrafElement implements IEdge {
	private final IVertex to;

	private final IVertex from;

	public Edge(IVertex from, IVertex to) {
		this("", "", from, to, null);
	}

	public Edge(IVertex from, IVertex to, String id) {
		this("", "", from, to, id);
	}

	public Edge(String label, Collection<String> styleClasses, IVertex from, IVertex to) {
		this(label, styleClasses, from, to, null);
	}

	public Edge(String label, Collection<String> styleClass, IVertex from, IVertex to, String id) {
		super(label, styleClass, id);
		if(from == null)
			throw new IllegalArgumentException("from can not be null");
		if(to == null)
			throw new IllegalArgumentException("to can not be null");
		this.from = from;
		this.to = to;
	}

	public Edge(String label, IVertex from, IVertex to) {
		this(label, "", from, to, null);
	}

	public Edge(String label, IVertex from, IVertex to, String id) {
		this(label, "", from, to, id);
	}

	public Edge(String label, String styleClass, IVertex from, IVertex to) {
		this(label, styleClass, from, to, null);
	}

	public Edge(String label, String styleClass, IVertex from, IVertex to, String id) {
		this(label, Collections.singleton(styleClass), from, to, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.impl.grafElement#getElementType()
	 */
	@Override
	public ElementType getElementType() {
		return ElementType.edge;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.impl.grafElement#getFrom()
	 */
	@Override
	public IVertex getFrom() {
		return from;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.impl.grafElement#getTo()
	 */
	@Override
	public IVertex getTo() {
		return to;
	}

}
