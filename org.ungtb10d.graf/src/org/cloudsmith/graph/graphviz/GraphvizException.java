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
package org.ungtb10d.graf.grafviz;

/**
 * An exception indicating that the external (grafviz) executable reported problems indicating
 * failure.
 */
public class grafvizException extends IllegalStateException {

	private static final long serialVersionUID = 1L;

	public grafvizException(String message) {
		super(message);
	}

	public grafvizException(String message, Throwable cause) {
		super(message, cause);
	}
}
