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
package org.ungtb10d.graph.elements;

import org.ungtb10d.graph.ElementType;
import org.ungtb10d.graph.IClusterGraph;
import org.ungtb10d.graph.ILabeledGraphElement;
import org.ungtb10d.graph.IRootGraph;

/**
 * A concrete implementation of ISubGraph. A SubGraph acts as a container with no visual bounds or appearance of
 * its own. It is useful for setting defaults, and for style rules that act on the style class/id of a container.
 * 
 */
public class ClusterGraph extends LabeledGraph implements IClusterGraph, ILabeledGraphElement {

	public ClusterGraph(LabeledGraph g) {
		super(g);
	}

	public ClusterGraph(String styleClass) {
		super(styleClass);
	}

	public ClusterGraph(String label, Graph g) {
		super(label, g);
	}

	public ClusterGraph(String styleClass, String id) {
		super(styleClass, id);
	}

	public ClusterGraph(String label, String styleClass, String id) {
		super(label, styleClass, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.ISubGraph#asClusterGraph(java.lang.String)
	 */
	@Override
	public IClusterGraph asClusterGraph(String label) {
		return this;
	}

	@Override
	public IRootGraph asRootGraph() {
		return new RootGraph(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.ISubGraph#asRootGraph(java.lang.String)
	 */
	@Override
	public IRootGraph asRootGraph(String label) {
		return new RootGraph(label, this);
	}

	@Override
	public ElementType getElementType() {
		return ElementType.cluster;
	}
}
