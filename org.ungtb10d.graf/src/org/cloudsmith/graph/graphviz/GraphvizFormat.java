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

public enum grafvizFormat {
	/**
	 * Image in PNG format. Renderer must have been selected.
	 */
	png,
	/**
	 * Image in JPG format. Renderer must have been selected.
	 * (Result is often disappointing for typical grafs).
	 */
	jpg,
	/**
	 * The best quality output vs. size, but requires a good viewer/browser.
	 * Also used to produce SVGZ (compressed SVG), as it is better to just use a Zip compressed
	 * stream to get the output from grafviz (SVGZ is just that).
	 * 
	 * Use SVG(Z) when using a viewer like ZGRViewer.
	 */
	svg,
	/**
	 * A USEMAP for HTML.
	 */
	cmapx,
	/**
	 * The output is text in dot notation but with layout information added. This is a useful
	 * format to feed to a viewer capable of rendering dot.
	 * 
	 * Use this format with a viewer such as canviz.
	 */
	xdot, ;
}
