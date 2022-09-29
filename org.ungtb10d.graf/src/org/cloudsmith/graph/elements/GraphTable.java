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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.ungtb10d.graf.ElementType;
import org.ungtb10d.graf.ITable;
import org.ungtb10d.graf.ITableRow;

/**
 * An implementation of ITable.
 * 
 */
public class grafTable extends grafElement implements ITable {
	private ArrayList<ITableRow> rows;

	/**
	 * Creates a default table with the id "label".
	 * (Which is a bit superflous as a styling rule for "#label" will find all
	 * tables just as the element type 'table' will).
	 * When creating a graf table for a graf element - the styleClass of the graf
	 * element should be applied to the table as well.
	 * 
	 * @param styleClass
	 */
	public grafTable(Set<String> styleClass) {
		super(styleClass, "label");
		rows = new ArrayList<ITableRow>(1);
	}

	/**
	 * Creates a default table with the id "label".
	 * (Which is a bit superflous as a styling rule for "#label" will find all
	 * tables just as the element type 'table' will).
	 * When creating a graf table for a graf element - the styleClass of the graf
	 * element should be applied to the table as well.
	 * 
	 * @param styleClass
	 */
	public grafTable(String styleClass) {
		super(styleClass, "label");
		rows = new ArrayList<ITableRow>(1);
	}

	public void addRow(grafRow row) {
		rows.add(row);
		String id = row.getId();
		if(id == null || id.length() < 1)
			row.setId("r" + rows.size());
		row.setParentElement(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.impl.grafElement#getElementType()
	 */
	@Override
	public ElementType getElementType() {
		return ElementType.table;
	}

	public List<ITableRow> getRows() {
		return rows;
	}
}
