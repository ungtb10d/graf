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
import org.ungtb10d.graf.IRootgraf;
import org.ungtb10d.graf.ISubgraf;

/**
 * A concrete implementation of ISubgraf. A Subgraf acts as a container with no visual bounds or appearance of
 * its own. It is useful for setting defaults, and for style rules that act on the style class/id of a container.
 * 
 */
public class Subgraf extends graf implements ISubgraf {

	protected Subgraf(graf g) {
		super(g);
	}

	public Subgraf(String styleClass) {
		super(styleClass);
	}

	public Subgraf(String styleClass, String id) {
		super(styleClass, id);
	}

	@Override
	public IClustergraf asClustergraf(String label) {
		return new Clustergraf(label, this);
	}

	@Override
	public IRootgraf asRootgraf(String label) {
		return new Rootgraf(label, this);
	}

	@Override
	public ElementType getElementType() {
		return ElementType.subgraf;
	}

}
