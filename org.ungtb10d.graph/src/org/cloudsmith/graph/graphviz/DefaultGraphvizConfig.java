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
package org.ungtb10d.graph.graphviz;

import com.google.inject.Singleton;

/**
 * A default graphviz configration for Mac, Linux using {@link GraphvizRenderer#cairo} on all platforms
 * except OSx where {@link GraphvizRenderer#quartz} produces better looking results.
 * 
 */
@Singleton
public class DefaultGraphvizConfig implements IGraphvizConfig {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.impl.dot.IGraphvizConfig#getRenderer()
	 */
	@Override
	public GraphvizRenderer getRenderer() {
		String osName = System.getProperty("os.name", "");
		osName = osName.toLowerCase();
		osName = osName.replace(" ", "");
		if(osName.contains("macosx"))
			return GraphvizRenderer.quartz;
		return GraphvizRenderer.cairo;
	}

}
