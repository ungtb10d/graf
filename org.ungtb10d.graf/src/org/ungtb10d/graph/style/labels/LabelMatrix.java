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
package org.ungtb10d.graf.style.labels;

import org.ungtb10d.graf.IgrafElement;

import com.google.common.base.Function;

/**
 * A Label Matrix provides a 2 dimensional label template useful for rendering
 * 1 | 2 | 3 | ... or
 * 1 |
 * 2 |
 * 3
 * or a matrix.
 * It is up to the label renderer how the matrix is represented.
 */
public class LabelMatrix implements ILabelTemplate {

	private Function<IgrafElement, String> styleClass;

	private final int rows;

	private final int columns;

	public LabelMatrix(final Function<IgrafElement, String> styleClass, int rows, int columns) {
		this.styleClass = styleClass;
		this.rows = rows;
		this.columns = columns;
	}

	public LabelMatrix(final String styleClass, int rows, int columns) {
		this.styleClass = new Function<IgrafElement, String>() {
			public String apply(IgrafElement ge) {
				return styleClass;
			}
		};
		this.rows = rows;
		this.columns = columns;
	}

	public String getCellStyleClass(IgrafElement ge) {
		return styleClass.apply(ge) + "c";
	}

	public int getColumns() {
		return columns;
	}

	public int getRows() {
		return rows;
	}

	public String getRowStyleClass(IgrafElement ge) {
		return styleClass.apply(ge) + "r";
	}

	public String getStyleClass(IgrafElement ge) {
		return styleClass.apply(ge);
	}

	/**
	 * Returns row:column, row, or column depending on dimensions.
	 * 
	 * @param ge
	 * @param row
	 * @param column
	 * @return
	 */
	public String getValue(int row, int column) {
		if(rows > 1 && columns > 1)
			return Integer.toString(row) + "_" + Integer.toString(column);
		if(rows == 1)
			return Integer.toString(column);
		return Integer.toString(row);
	}

}
