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
package org.ungtb10d.graf.grafcss;

import java.util.Collection;
import java.util.Set;

import org.ungtb10d.graf.IgrafElement;
import org.ungtb10d.graf.style.labels.ILabelTemplate;
import org.ungtb10d.graf.style.labels.LabelTable;

import com.google.common.base.Function;

/**
 * @author henrik
 * 
 */
public interface IFunctionFactory {

	public final static String ID_KEY = IFunctionFactory.class.getName() + "_Id";

	/**
	 * Returns a function returning true if the graf element has an empty label.
	 * 
	 * @return
	 */
	public Function<IgrafElement, Boolean> emptyLabel();

	/**
	 * Returns a function returning true if the graf element has an empty label data for the given key.
	 * 
	 * @return
	 */
	public Function<IgrafElement, Boolean> emptyLabelData(Object key);

	/**
	 * Returns a string with id="ID" class="CLASSES" based on an optional user data key (or the
	 * fully qualified id if missing), and the combination of graf element type and graf element style class.
	 * 
	 * @return
	 */
	Function<IgrafElement, String> idClassReplacer();

	/**
	 * Returns the label string of the graf element.
	 * 
	 * @return
	 */
	public Function<IgrafElement, ILabelTemplate> label();

	/**
	 * Returns the label data of the given key from the graf element.
	 * 
	 * @param key
	 * @return
	 */
	public Function<IgrafElement, String> labelData(Object key);

	public Function<IgrafElement, ILabelTemplate> labelTemplate(Function<IgrafElement, String> stringfunc);

	public Function<IgrafElement, ILabelTemplate> literalLabelTemplate(LabelTable t);

	public Function<IgrafElement, ILabelTemplate> literalLabelTemplate(String s);

	public Function<IgrafElement, String> literalString(String s);

	public Function<IgrafElement, Set<String>> literalStringSet(Collection<String> s);

	public Function<IgrafElement, Set<String>> literalStringSet(String s);

	/**
	 * Returns true if the graf element's label is not empty.
	 * 
	 * @return
	 */
	public Function<IgrafElement, Boolean> notEmptyLabel();

	/**
	 * Returns true if the graf element's label data with the given key is not empty.
	 * 
	 * @param key
	 * @return
	 */
	public Function<IgrafElement, Boolean> notEmptyLabelData(Object key);

}
