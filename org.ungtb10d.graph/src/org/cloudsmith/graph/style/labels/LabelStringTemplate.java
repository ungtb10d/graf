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
package org.ungtb10d.graph.style.labels;

import org.ungtb10d.graph.IGraphElement;

import com.google.common.base.Function;

public class LabelStringTemplate implements ILabelTemplate {
	private Function<IGraphElement, String> templateString;

	public LabelStringTemplate(Function<IGraphElement, String> templateString) {
		this.templateString = templateString;
	}

	public LabelStringTemplate(final String value) {
		this.templateString = new Function<IGraphElement, String>() {
			public String apply(IGraphElement ge) {
				return value;
			}
		};
	}

	public String getTemplateString(IGraphElement ge) {
		return templateString.apply(ge);
	}
}