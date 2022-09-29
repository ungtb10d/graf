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
package org.ungtb10d.graf.elements;

import org.ungtb10d.graf.ElementType;
import org.ungtb10d.graf.IClustergraf;
import org.ungtb10d.graf.ILabeledgrafElement;
import org.ungtb10d.graf.IRootgraf;

/**
 * A concrete implementation of ISubgraf. A Subgraf acts as a container with no visual bounds or appearance of
 * its own. It is useful for setting defaults, and for style rules that act on the style class/id of a container.
 * 
 */
public class Clustergraf extends Labeledgraf implements IClustergraf, ILabeledgrafElement {

	public Clustergraf(Labeledgraf g) {
		super(g);
	}

	public Clustergraf(String styleClass) {
		super(styleClass);
	}

	public Clustergraf(String label, graf g) {
		super(label, g);
	}

	public Clustergraf(String styleClass, String id) {
		super(styleClass, id);
	}

	public Clustergraf(String label, String styleClass, String id) {
		super(label, styleClass, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.ISubgraf#asClustergraf(java.lang.String)
	 */
	@Override
	public IClustergraf asClustergraf(String label) {
		return this;
	}

	@Override
	public IRootgraf asRootgraf() {
		return new Rootgraf(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.ISubgraf#asRootgraf(java.lang.String)
	 */
	@Override
	public IRootgraf asRootgraf(String label) {
		return new Rootgraf(label, this);
	}

	@Override
	public ElementType getElementType() {
		return ElementType.cluster;
	}
}
