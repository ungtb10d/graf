/*******************************************************************
 * Copyright (c) 2006-2011, ungtb10d Inc.
 * The code, documentation and other materials contained herein
 * are the sole and exclusive property of ungtb10d Inc. and may
 * not be disclosed, used, modified, copied or distributed without
 * prior written consent or license from ungtb10d Inc.
 ******************************************************************/

package org.ungtb10d.graph.testgraphs;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.ungtb10d.graph.ElementType;
import org.ungtb10d.graph.IGraphProvider;
import org.ungtb10d.graph.IRootGraph;
import org.ungtb10d.graph.graphcss.IFunctionFactory;
import org.ungtb10d.graph.graphcss.Rule;
import org.ungtb10d.graph.graphcss.Select;
import org.ungtb10d.graph.graphcss.StyleSet;
import org.ungtb10d.graph.style.IStyleFactory;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * Abstract test graph. Produces simplest possible labels.
 * 
 */
public abstract class AbstractTestGraph implements IGraphProvider {

	private IStyleFactory styleFactory;

	private IFunctionFactory functions;

	@Inject
	public AbstractTestGraph(IStyleFactory styleFactory, IFunctionFactory functions) {
		this.styleFactory = styleFactory;
		this.functions = functions;
	}

	public IRootGraph computeGraph() {
		return computeGraph(null, "a test graph", "root");
	}

	@Override
	public IRootGraph computeGraph(Object modelObj) {
		return computeGraph();
	}

	/**
	 * @modelObj - ignored, returns same graph at all times.
	 */
	public abstract IRootGraph computeGraph(Object modelObj, String title, String id);

	/**
	 * Produces simplest possible labels.
	 */
	public Collection<Rule> getRules() {
		// the rule set that contains all rules and styling
		List<Rule> result = Lists.newArrayList();

		StyleSet simpleLabelFormat = new StyleSet();
		simpleLabelFormat.put(styleFactory.labelFormat(styleFactory.labelTemplate(functions.label())));

		// use a simple label format for edges, as this reduces dot data a lot
		//
		Collections.addAll(result, //
			Select.element(ElementType.vertex).withStyle(simpleLabelFormat),//
			Select.element(ElementType.edge).withStyle(simpleLabelFormat), //
			Select.element(ElementType.graph).withStyle(simpleLabelFormat));

		return result;
	}
}
