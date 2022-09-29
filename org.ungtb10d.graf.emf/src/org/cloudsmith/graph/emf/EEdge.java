/**
 * Copyright (c) 2011 ungtb10d Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   ungtb10d
 * 
 */
package org.ungtb10d.graf.emf;

import org.ungtb10d.graf.IVertex;
import org.ungtb10d.graf.elements.Edge;
import org.ungtb10d.graf.style.themes.DefaultStyleTheme;
import org.eclipse.emf.ecore.EReference;

/**
 * An implementation of graf Edge that sets information from an EMF EReference and remembers the
 * EReference. The styleClass is by default set to "Containment" or "Reference" depending on the type of reference.
 * The label is by default set to the reference name.
 * 
 */
public class EEdge extends Edge {

	private static String labelFor(EReference e) {
		return e.getName();
	}

	private static String styleClassFor(EReference e) {
		return e.isContainment()
				? DefaultStyleTheme.THEME_EDGE_CONTAINMENT
				: DefaultStyleTheme.THEME_EDGE_REFERENCE;
	}

	private EReference referenceObj;

	public EEdge(EReference e, IVertex from, IVertex to) {
		super(labelFor(e), styleClassFor(e), from, to);
		referenceObj = e;
	}

	public EEdge(EReference e, String label, String styleClass, IVertex from, IVertex to) {
		super(label, styleClass, from, to);
		referenceObj = e;
	}

	public EEdge(EReference e, String label, String styleClass, IVertex from, IVertex to, String id) {
		super(label, styleClass, from, to, id);
		referenceObj = e;
	}

	public EReference getReference() {
		return referenceObj;
	}

}
