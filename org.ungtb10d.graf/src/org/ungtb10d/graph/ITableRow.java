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

import java.util.List;

/**
 * Interface for a TableRow
 * 
 */
public interface ITableRow extends IgrafElement {
	List<ITableCell> getCells();

	boolean isSeparator();
}
