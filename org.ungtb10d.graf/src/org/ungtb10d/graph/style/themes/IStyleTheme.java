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
package org.ungtb10d.graf.style.themes;

import java.util.Collection;

import org.ungtb10d.graf.grafcss.grafCSS;
import org.ungtb10d.graf.grafcss.Rule;

import com.google.inject.ImplementedBy;

/**
 * Interface for a style theme.
 * 
 */
@ImplementedBy(DefaultStyleTheme.class)
public interface IStyleTheme {

	/**
	 * Produce the default font family name.
	 * 
	 * @return a font family name
	 */
	public String defaultFontFamily();

	/**
	 * Produce the default point size.
	 * 
	 * @return a point size
	 */
	public int defaultPointSize();

	/**
	 * Returns a the static rules output as defaults.
	 * Do not include this set in the grafCSS that is passed as an instance css as it would lead
	 * to output of matching style per instance.
	 * 
	 * @return
	 */
	public grafCSS getDefaultRules();

	/**
	 * A derived implementation may want to override these defaults.
	 * Note that a collection of rules are returned as opposed to a grafCSS as this is more
	 * efficient (avoids cloning rules) since they can only be part of one set.
	 * 
	 * @return a collection of suitable rules for the theme
	 */
	public Collection<Rule> getInstanceRules();

}
