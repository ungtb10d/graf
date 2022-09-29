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
 * Marker interface for a graf having a non root role and being clustered.
 * 
 */
public interface IClustergraf extends ISubgraf, ILabeledgrafElement {

	/**
	 * Create a new Rootgraf with the same content as this subgraf.
	 * 
	 * @return an IRootgraf being a copy of this cluster graf's content.
	 */

	public abstract IRootgraf asRootgraf();

}
