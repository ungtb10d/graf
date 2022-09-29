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
package org.ungtb10d.graf.style.labels;

import java.util.Collections;
import java.util.Set;

import org.ungtb10d.graf.IgrafElement;

import com.google.common.base.Function;

/**
 * Data for label format
 * 
 */
public class LabelTable implements ILabelTemplate {
	private LabelRow[] rows;

	private Function<IgrafElement, Set<String>> styleClass;

	public LabelTable(final Function<IgrafElement, Set<String>> styleClass, LabelRow... rows) {
		this.rows = rows;
		this.styleClass = styleClass;
	}

	public LabelTable(final String styleClass, LabelRow... rows) {
		this.rows = rows;
		this.styleClass = new Function<IgrafElement, Set<String>>() {
			public Set<String> apply(IgrafElement ge) {
				return Collections.singleton(styleClass);
			}
		};
	}

	public LabelRow[] getRows() {
		return rows;
	}

	public Set<String> getStyleClasses(IgrafElement ge) {
		return styleClass.apply(ge);
	}
}
