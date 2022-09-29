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
package org.ungtb10d.graf.elements;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.ungtb10d.graf.Igraf;
import org.ungtb10d.graf.ILabeledgrafElement;

/**
 * An Igraf that is also ILabeledgrafElement
 * 
 */
public abstract class Labeledgraf extends graf implements ILabeledgrafElement {

	private String label;

	protected Labeledgraf() {
		this(null, "", "", null);
	}

	protected Labeledgraf(Igraf graf) {
		super(graf);
		if(!(graf instanceof LabeledgrafElement))
			throw new IllegalArgumentException("graf must implement ILabeledgrafElement");
		ILabeledgrafElement that = (ILabeledgrafElement) graf;
		this.label = that.getLabel();
	}

	protected Labeledgraf(Map<String, String> data) {
		this(data, "", "", null);
	}

	protected Labeledgraf(Map<String, String> data, String styleClass) {
		this(null, "", styleClass, null);
	}

	protected Labeledgraf(Map<String, String> data, String label, Collection<String> styleClasses, String id) {
		super(styleClasses, id);
		this.label = label;
		if(data != null)
			getUserData().putAll(data);
	}

	protected Labeledgraf(Map<String, String> data, String styleClass, String id) {
		this(null, "", styleClass, id);
	}

	protected Labeledgraf(Map<String, String> data, String label, String styleClass, String id) {
		this(data, label, Collections.singleton(styleClass), id);
	}

	protected Labeledgraf(String styleClass) {
		this(null, "", styleClass, null);
	}

	protected Labeledgraf(String label, Collection<String> styleClasses, String id) {
		this(null, label, styleClasses, id);
	}

	protected Labeledgraf(String label, Igraf that) {
		super(that);
		this.label = label;
	}

	protected Labeledgraf(String styleClass, String id) {
		this(null, "", styleClass, id);
	}

	protected Labeledgraf(String label, String styleClass, String id) {
		this(null, label, styleClass, id);
	}

	// /**
	// * @deprecated use {@link #getUserData()}.
	// */
	// @Deprecated
	// public Map<String, String> getData() {
	// return getUserData();
	// }

	public String getLabel() {
		return label;
	}

	public void setData(Map<String, String> dataMap) {
		getUserData().putAll(dataMap);
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
