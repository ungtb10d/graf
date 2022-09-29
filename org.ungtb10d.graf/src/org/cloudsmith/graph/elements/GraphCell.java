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

import org.ungtb10d.graf.ElementType;
import org.ungtb10d.graf.ITableCell;

/**
 * Implementation of ITableCell
 * 
 */
public class grafCell extends grafElement implements ITableCell {

	public static class SeparatorCell extends grafCell {
		public SeparatorCell() {
			super("", SeparatorCell.class.getName());
		}

		@Override
		public boolean isSeparator() {
			return true;
		}
	}

	private String value;

	private grafTable table;

	// private grafCell(String value, int rowspan, int colspan, String styleClass, String id) {
	// super(styleClass, id);
	// this.value = value;
	// }
	//
	// private grafCell(String value, int rowspan, int colspan, Collection<String> styleClass, String id) {
	// super(styleClass, id);
	// this.value = value;
	// }

	/**
	 * Creates a default cell with null id.
	 * 
	 * @param styleClass
	 */
	public grafCell(String value, String styleClass) {
		this(value, styleClass, null);
	}

	public grafCell(String value, Collection<String> styleClasses) {
		this(value, styleClasses, null);
	}

	public grafCell(String value, String styleClass, String id) {
		super(styleClass, id);
		this.value = value;
	}

	public grafCell(String value, Collection<String> styleClasses, String id) {
		super(styleClasses, id);
		this.value = value;
	}

	public void setTableContent(grafTable gt) {
		this.table = gt;
		gt.setParentElement(this);
		String id = gt.getId();
		if(id == null || id.length() == 0)
			gt.setId("t");

	}

	@Override
	public grafTable getTableContents() {
		return this.table;
	}

	@Override
	public ElementType getElementType() {
		return ElementType.cell;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public boolean isSeparator() {
		return false;
	}
}
