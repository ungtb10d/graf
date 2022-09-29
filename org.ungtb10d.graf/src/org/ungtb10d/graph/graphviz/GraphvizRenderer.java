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
package org.ungtb10d.graf.graphviz;

/**
 * The renderer an Igraphviz should use. graphviz can use different rendering packages. The default windows installation of
 * graphviz supports only the gd library. The newer (and superior) "cairo" (the drawing package used by Gimp) is an add on
 * on linux systems. It is unclear if it is possible to get this library also working with graphviz on windows.
 * To check which renderers are supported run "dot -Tpng:" on a command line for a list of choices.
 * 
 * A Renderer is needed for output formats based on some sort of image (e.g.
 * {@link graphviz#toJPG(ICancel, graphvizLayout, Igraf, grafCSS, grafCSS...)},
 * and {@link graphviz#toPNG(ICancel, graphvizLayout, Igraf, grafCSS, grafCSS...)}
 */
public enum graphvizRenderer {
	/**
	 * Use default renderer bound in the runtime module. Should <b>NOT</b> be used as the default bound
	 * in the runtime module. This renderer should be specified when format does not require a renderer (such as
	 * the xdot format).
	 */
	standard,
	/**
	 * Use :gd renderer
	 */
	gd,
	/**
	 * Use :cairo renderer
	 */
	cairo,

	/**
	 * Use :quartz renderer on OSx
	 */
	quartz, ;
}
