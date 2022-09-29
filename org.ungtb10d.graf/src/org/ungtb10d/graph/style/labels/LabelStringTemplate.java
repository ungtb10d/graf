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

import org.ungtb10d.graf.IgrafElement;

import com.google.common.base.Function;

public class LabelStringTemplate implements ILabelTemplate {
	private Function<IgrafElement, String> templateString;

	public LabelStringTemplate(Function<IgrafElement, String> templateString) {
		this.templateString = templateString;
	}

	public LabelStringTemplate(final String value) {
		this.templateString = new Function<IgrafElement, String>() {
			public String apply(IgrafElement ge) {
				return value;
			}
		};
	}

	public String getTemplateString(IgrafElement ge) {
		return templateString.apply(ge);
	}
}
