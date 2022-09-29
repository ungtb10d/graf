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
import org.ungtb10d.graf.IClustergraf;
import org.ungtb10d.graf.Igraf;
import org.ungtb10d.graf.IRootgraf;
import org.ungtb10d.graf.ISubgraf;

/**
 * A graf is a container of other graf elements (which include other grafs as subgrafs, vertexes and edges).
 * This implementation of {@link Igraf} works with instances of other graf elements in the same package
 * as it manages their containment and identity (if not set).
 * 
 */
public class Rootgraf extends Labeledgraf implements IRootgraf {

	/**
	 * @param clustergraf
	 */
	public Rootgraf(IClustergraf that) {
		super(that);
	}

	public Rootgraf(IRootgraf that) {
		super(that);
	}

	public Rootgraf(String label, Collection<String> styleClasses) {
		this(label, styleClasses, null);
	}

	public Rootgraf(String label, Collection<String> styleClasses, String id) {
		super(label, styleClasses, id);

	}

	public Rootgraf(String label, Igraf that) {
		super(label, that);
	}

	public Rootgraf(String label, String styleClass) {
		this(label, styleClass, null);
	}

	public Rootgraf(String label, String styleClass, String id) {
		super(label, styleClass, id);

	}

	@Override
	public IClustergraf asCluster() {
		return new Clustergraf(this);
	}

	@Override
	public ISubgraf asSubgraf() {
		return new Subgraf(this);
	}

	@Override
	public ElementType getElementType() {
		return ElementType.graf;
	}

}
