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

import java.util.Set;

import org.ungtb10d.graf.IgrafElement;
import org.ungtb10d.graf.grafcss.StyleSet;
import org.ungtb10d.graf.style.IStyle;
import org.ungtb10d.graf.style.Span;

import com.google.common.base.Function;

/**
 * Data for label format.
 */
public class LabelCell {
	public static class Separator extends LabelCell {
		public Separator(Function<IgrafElement, Set<String>> styleClass, Function<IgrafElement, ILabelTemplate> value) {
			super(styleClass, value);
		}

		@Override
		public boolean isSeparator() {
			return true;
		}
	}

	private Function<IgrafElement, ILabelTemplate> valueFunc;

	private Function<IgrafElement, Set<String>> styleClassFunc;

	private final Span span;

	private StyleSet instanceStyles;

	/**
	 * @param styleClass2
	 * @param f
	 */
	public LabelCell(Function<IgrafElement, Set<String>> styleClass, Function<IgrafElement, ILabelTemplate> value) {
		this.valueFunc = value;
		this.styleClassFunc = styleClass;
		this.span = Span.SPAN_1x1;
	}

	public LabelCell(Function<IgrafElement, Set<String>> styleClass, Function<IgrafElement, ILabelTemplate> value,
			Span span) {
		this.valueFunc = value;
		this.styleClassFunc = styleClass;
		this.span = span;
	}

	public Span getSpan() {
		return span;
	}

	public Set<String> getStyleClass(IgrafElement ge) {
		return styleClassFunc.apply(ge);
	}

	public StyleSet getStyles() {
		return instanceStyles;
	}

	public ILabelTemplate getValue(IgrafElement ge) {
		return valueFunc.apply(ge);
	}

	public boolean isSeparator() {
		return false;
	}

	public LabelCell withStyle(IStyle<?> style) {
		return withStyles(style);
	}

	/**
	 * Convenience method to set instance styles on a LabelCell.
	 * It is illegal to call this method more than once.
	 * 
	 * @param styles
	 * @return the LabelCell
	 */
	public LabelCell withStyles(IStyle<?>... styles) {
		if(instanceStyles != null)
			throw new IllegalArgumentException("LabelCell's instance style is immutable once set");
		StyleSet styleMap = new StyleSet();
		for(IStyle<?> s : styles)
			styleMap.put(s);
		instanceStyles = styleMap;

		return this;
	}

}
