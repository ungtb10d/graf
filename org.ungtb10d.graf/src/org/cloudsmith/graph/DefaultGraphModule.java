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
package org.ungtb10d.graph;

import org.ungtb10d.graph.dot.DotRenderer;
import org.ungtb10d.graph.graphcss.FunctionFactory;
import org.ungtb10d.graph.graphcss.IFunctionFactory;
import org.ungtb10d.graph.graphviz.DefaultGraphvizConfig;
import org.ungtb10d.graph.graphviz.Graphviz;
import org.ungtb10d.graph.graphviz.IGraphviz;
import org.ungtb10d.graph.graphviz.IGraphvizConfig;
import org.ungtb10d.graph.style.IStyleFactory;
import org.ungtb10d.graph.style.StyleFactory;
import org.ungtb10d.graph.style.themes.DefaultStyleTheme;
import org.ungtb10d.graph.style.themes.IStyleTheme;
import org.ungtb10d.graph.utils.IOutputStreamFilterFactory;
import org.ungtb10d.graph.utils.TransparentOutputStreamFilterFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * A default graph runtime module.
 * 
 */
public class DefaultGraphModule extends AbstractModule {

	/**
	 * Binds the main Dot Renderer, and the utility DotLabelRenderer and a DotGraphElementRenderer.
	 * Other renderers may require a different configuration.
	 */
	protected void bindDotRenderer() {
		// bind(DotRenderer.class).to(DotRenderer.class); // default, but make it explicit
		// bind(DotLabelRenderer.class).to(DotLabelRenderer.class); // default made explicit
		// bind(DotGraphElementRenderer.class).to(DotGraphElementRenderer.class); // default made explicit.
	}

	/**
	 * Binds a normal empty string (i.e. "") for dot output.
	 * For SVG output, the {@link org.ungtb10d.graph.graphviz.SVGFixerOutputStreamSVGFixerOutputStream#EMPTY_STRING_BUG} can be bound in combination
	 * with overriding {@link #bindSVGOutputFilterProvider()}.
	 */
	protected void bindEmptyStringConstant() {
		bindConstant().annotatedWith(DotRenderer.EmptyString.class).to("");
	}

	/**
	 * Binds the standard style value function factory.
	 */
	protected void bindIFunctionFactory() {
		bind(IFunctionFactory.class).to(FunctionFactory.class);
	}

	/**
	 * Binds implementation of graphviz runner.
	 */
	protected void bindIGraphviz() {
		bind(IGraphviz.class).to(Graphviz.class);
	}

	/**
	 * Binds the standard graphviz configuration to use. This binding is environment dependent.
	 */
	protected void bindIGraphvizConfig() {
		bind(IGraphvizConfig.class).to(DefaultGraphvizConfig.class);
	}

	/**
	 * Binds the standard style (data) factory.
	 */
	protected void bindIStyleFactory() {
		bind(IStyleFactory.class).to(StyleFactory.class);
	}

	protected void bindIStyleTheme() {
		bind(IStyleTheme.class).to(DefaultStyleTheme.class);
	}

	/**
	 * Binds a filter factory for SVG output.
	 */
	protected void bindSVGOutputFilterProvider() {

		bind(IOutputStreamFilterFactory.class).annotatedWith(IGraphviz.SVGOutputFilter.class).to(
			TransparentOutputStreamFilterFactory.class).in(Singleton.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		bindIGraphviz();
		bindIGraphvizConfig();
		bindIFunctionFactory();
		bindIStyleFactory();
		bindDotRenderer();
		bindIStyleTheme();
		bindSVGOutputFilterProvider();
		bindEmptyStringConstant();
	}

}
