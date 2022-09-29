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
package org.ungtb10d.graf.graphviz;

/**
 * An exception indicating that the external (graphviz) executable reported problems indicating
 * failure.
 */
public class graphvizException extends IllegalStateException {

	private static final long serialVersionUID = 1L;

	public graphvizException(String message) {
		super(message);
	}

	public graphvizException(String message, Throwable cause) {
		super(message, cause);
	}
}
