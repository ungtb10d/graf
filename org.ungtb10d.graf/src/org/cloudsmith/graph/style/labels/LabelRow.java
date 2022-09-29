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
public class LabelRow {
	public static class Separator extends LabelRow {
		public Separator(Function<IgrafElement, Set<String>> styleClass) {
			super(styleClass);
		}

		@Override
		public boolean isSeparator() {
			return true;
		}
	}

	LabelCell[] cells;

	Function<IgrafElement, Set<String>> styleClass;

	public LabelRow(final Function<IgrafElement, Set<String>> styleClass, LabelCell... cells) {
		this.styleClass = styleClass;
		this.cells = cells;
	}

	public LabelRow(LabelCell... cells) {
		this("", cells);
	}

	public LabelRow(final String styleClass, LabelCell... cells) {
		this.styleClass = new Function<IgrafElement, Set<String>>() {
			public Set<String> apply(IgrafElement ge) {
				return Collections.singleton(styleClass);
			}
		};
		this.cells = cells;
	}

	public LabelCell[] getCells() {
		return cells;
	}

	public Set<String> getStyleClasses(IgrafElement ge) {
		return styleClass.apply(ge);
	}

	public boolean isSeparator() {
		return false;
	}
}
