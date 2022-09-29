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

import org.ungtb10d.graf.ElementType;
import org.ungtb10d.graf.IVertex;

/**
 * An implementation of IVertex.
 * 
 */
public class Vertex extends LabeledgrafElement implements IVertex {
	public Vertex(String label, Collection<String> styleClasses) {
		super(label, styleClasses, null);
	}

	public Vertex(String label, Collection<String> styleClasses, String id) {
		super(label, styleClasses, id);
	}

	public Vertex(String label, String styleClass) {
		super(label, styleClass, null);
	}

	public Vertex(String label, String styleClass, String id) {
		super(label, styleClass, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.impl.grafElement#getElementType()
	 */
	@Override
	public ElementType getElementType() {
		return ElementType.vertex;
	}
}
