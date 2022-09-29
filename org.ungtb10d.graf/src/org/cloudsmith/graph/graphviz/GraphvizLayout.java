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
package org.ungtb10d.graf.grafviz;

/**
 * Layout algorithms supported by the {@link grafviz} runner
 * If you have a directed graf - you probably want {@link #dot} For undirected grafs you probably want {@link #neato}.
 * 
 */
public enum grafvizLayout {
	dot, neato, circo, fdp, twopi, ;
}
