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
package org.ungtb10d.graf.elements;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.ungtb10d.graf.IgrafElement;
import org.ungtb10d.graf.ILabeledgrafElement;

/**
 * 
 *
 */
public abstract class LabeledgrafElement extends grafElement implements IgrafElement, ILabeledgrafElement {
	private String label;

	public LabeledgrafElement() {
		this(null, "", "", null);
	}

	public LabeledgrafElement(Collection<String> styleClasses) {
		this(null, "", styleClasses, null);
	}

	public LabeledgrafElement(Collection<String> styleClasses, String id) {
		this(null, "", styleClasses, id);
	}

	protected LabeledgrafElement(LabeledgrafElement that) {
		super(that);
		this.label = that.label;
	}

	public LabeledgrafElement(Map<String, String> data) {
		this(data, "", "", null);
	}

	public LabeledgrafElement(Map<String, String> data, String styleClass) {
		this(null, "", styleClass, null);
	}

	public LabeledgrafElement(Map<String, String> data, String label, Collection<String> styleClasses, String id) {
		super(styleClasses, id);
		this.label = label;
		if(data != null)
			getUserData().putAll(data);
	}

	public LabeledgrafElement(Map<String, String> data, String styleClass, String id) {
		this(null, "", styleClass, id);
	}

	public LabeledgrafElement(Map<String, String> data, String label, String styleClass, String id) {
		this(data, label, Collections.singleton(styleClass), id);
	}

	public LabeledgrafElement(String styleClass) {
		this(null, "", styleClass, null);
	}

	public LabeledgrafElement(String label, Collection<String> styleClasses, String id) {
		this(null, label, styleClasses, id);
	}

	public LabeledgrafElement(String styleClass, String id) {
		this(null, "", styleClass, id);
	}

	public LabeledgrafElement(String label, String styleClass, String id) {
		this(null, label, styleClass, id);
	}

	/**
	 * @deprecated use {@link #getUserData()}
	 */
	@Deprecated
	public Map<String, String> getData() {
		return getUserData();
	}

	public String getLabel() {
		return label;
	}

	public void setData(Map<String, String> dataMap) {
		getUserData().putAll(dataMap);
	}
}
