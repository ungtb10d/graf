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
package org.ungtb10d.graf.grafviz;

import com.google.inject.Singleton;

/**
 * A default grafviz configration for Mac, Linux using {@link grafvizRenderer#cairo} on all platforms
 * except OSx where {@link grafvizRenderer#quartz} produces better looking results.
 * 
 */
@Singleton
public class DefaultgrafvizConfig implements IgrafvizConfig {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.impl.dot.IgrafvizConfig#getRenderer()
	 */
	@Override
	public grafvizRenderer getRenderer() {
		String osName = System.getProperty("os.name", "");
		osName = osName.toLowerCase();
		osName = osName.replace(" ", "");
		if(osName.contains("macosx"))
			return grafvizRenderer.quartz;
		return grafvizRenderer.cairo;
	}

}
