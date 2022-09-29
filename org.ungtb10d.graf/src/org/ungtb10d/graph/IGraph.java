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

/**
 * The interface for a graf that is either the root graf or a subgraf.
 * 
 */
public interface Igraf extends IgrafElement {
	/**
	 * Returns a list of all edges in the graf.
	 * 
	 * @return empty list if there are no edges.
	 */
	public Iterable<IEdge> getEdges();

	/**
	 * Returns a list of all subgrafs in the graf.
	 * 
	 * @return
	 */
	public Iterable<ISubgraf> getSubgrafs();

	/**
	 * Returns a list of all vertices in the graf.
	 * 
	 * @return empty list if there are no edges.
	 */
	public Iterable<IVertex> getVertices();

	// /**
	// * Subgrafs that should be handled as separate "clustered" grafs (in a separate
	// * space) should return true on this method. The root graf should not be clustered.
	// * The content of unclustered grafs are seen as additions to the parent graf, and the
	// * content is laid out intermixed with all other content in the parent graf.
	// *
	// * @return true if this graf is a subgraf
	// */
	// public boolean isCluster();
}
