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
package org.ungtb10d.graf;

/**
 * Marker interface for a graf having a root role.
 * 
 */
public interface IRootgraf extends Igraf, ILabeledgrafElement {

	/**
	 * Create a new Clustergraf with the same content as this root graf.
	 * 
	 * @return an IClustergraf being a copy of this Rootgraf's content.
	 */
	public abstract IClustergraf asCluster();

	/**
	 * Create a new subgraf with the same content as this root graf (looses label and label data
	 * information).
	 * 
	 * @return an ISubgraf being a copy of this Rootgraf's content.
	 */
	public abstract ISubgraf asSubgraf();

}
