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

import java.util.Collection;
import java.util.Set;

import org.ungtb10d.graf.IgrafElement;
import org.ungtb10d.graf.grafcss.IFunctionFactory;
import org.ungtb10d.graf.style.IStyleFactory;

import com.google.common.base.Function;

/**
 * A helper class that makes it easier/more readable to build a label table.
 * Simply implement the {@link #build()} method as described in this method's documentation.
 * This class also implements {@link IFunctionFactory} to make it convenient to use
 * functions as values in the calls to {@link #row(Function, LabelCell...)}, {@link #cell(Function, Function)},
 * (and related methods).
 * 
 * TODO: Add support for tables in cells.
 * 
 */
public abstract class LabelTableBuilder implements IFunctionFactory {

	private IStyleFactory styleFactory;

	private IFunctionFactory functions;

	public LabelTableBuilder(IStyleFactory styleFactory, IFunctionFactory functions) {
		this.styleFactory = styleFactory;
		this.functions = functions;
	}

	/**
	 * <p>
	 * This method should perform something like:
	 * </p>
	 * <code>
	 * return table(
	 *     "MyTableClass", //
	 *     row("MyRowClass", cell("LabelCell", "theLabel"), cell("ValueCell", "theValue")),
	 *     row("MyRowClass", cell("LabelCell", "theLabel"), cell("ValueCell", "theValue")));
	 * </code>
	 * 
	 * @return
	 */
	public abstract LabelTable build();

	final protected LabelCell cell(Function<IgrafElement, Set<String>> styleClass,
			Function<IgrafElement, ILabelTemplate> f) {
		return styleFactory.labelCell(styleClass, f, null);
	}

	final protected LabelCell cell(String styleClass, Function<IgrafElement, ILabelTemplate> f) {
		return styleFactory.labelCell(styleClass, f, null);
	}

	final protected LabelCell cell(String styleClass, String s) {
		return styleFactory.labelCell(styleClass, s);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.grafcss.IFunctionFactory#emptyLabel()
	 */
	@Override
	public Function<IgrafElement, Boolean> emptyLabel() {
		return functions.emptyLabel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.grafcss.IFunctionFactory#emptyLabelData(java.lang.Object)
	 */
	@Override
	public Function<IgrafElement, Boolean> emptyLabelData(Object key) {
		return functions.emptyLabelData(key);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.grafcss.IFunctionFactory#idClassReplacer()
	 */
	@Override
	public Function<IgrafElement, String> idClassReplacer() {
		// TODO Auto-generated method stub
		return functions.idClassReplacer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.grafcss.IFunctionFactory#label()
	 */
	@Override
	public Function<IgrafElement, ILabelTemplate> label() {
		return functions.label();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.grafcss.IFunctionFactory#labelData(java.lang.Object)
	 */
	@Override
	public Function<IgrafElement, String> labelData(Object key) {
		return functions.labelData(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.grafcss.IFunctionFactory#labelTemplate(com.google.common.base.Function)
	 */
	@Override
	public Function<IgrafElement, ILabelTemplate> labelTemplate(Function<IgrafElement, String> stringFunc) {
		return functions.labelTemplate(stringFunc);
	}

	@Override
	public Function<IgrafElement, ILabelTemplate> literalLabelTemplate(LabelTable t) {
		return functions.literalLabelTemplate(t);
	}

	@Override
	public Function<IgrafElement, ILabelTemplate> literalLabelTemplate(String s) {
		return functions.literalLabelTemplate(s);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.grafcss.IFunctionFactory#literalString(java.lang.String)
	 */
	@Override
	public Function<IgrafElement, String> literalString(String s) {
		return functions.literalString(s);
	}

	@Override
	public Function<IgrafElement, Set<String>> literalStringSet(Collection<String> s) {
		return functions.literalStringSet(s);
	}

	@Override
	public Function<IgrafElement, Set<String>> literalStringSet(String s) {
		return functions.literalStringSet(s);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.grafcss.IFunctionFactory#notEmptyLabel()
	 */
	@Override
	public Function<IgrafElement, Boolean> notEmptyLabel() {
		return functions.notEmptyLabel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.grafcss.IFunctionFactory#notEmptyLabelData(java.lang.Object)
	 */
	@Override
	public Function<IgrafElement, Boolean> notEmptyLabelData(Object key) {
		return functions.notEmptyLabelData(key);
	}

	final protected LabelRow row(Function<IgrafElement, Set<String>> styleClass, LabelCell... cells) {
		return new LabelRow(styleClass, cells);
	}

	final protected LabelRow row(String styleClass, LabelCell... cells) {
		return new LabelRow(styleClass, cells);
	}

	final protected LabelTable table(Function<IgrafElement, Set<String>> styleClass, LabelRow... rows) {
		return new LabelTable(styleClass, rows);
	}

	final protected LabelTable table(String styleClass, LabelRow... rows) {
		return new LabelTable(styleClass, rows);
	}
}
