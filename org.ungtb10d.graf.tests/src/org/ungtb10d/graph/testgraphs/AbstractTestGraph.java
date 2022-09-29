/*******************************************************************
 * Copyright (c) 2006-2011, ungtb10d Inc.
 * The code, documentation and other materials contained herein
 * are the sole and exclusive property of ungtb10d Inc. and may
 * not be disclosed, used, modified, copied or distributed without
 * prior written consent or license from ungtb10d Inc.
 ******************************************************************/

package org.ungtb10d.graf.testgrafs;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.ungtb10d.graf.ElementType;
import org.ungtb10d.graf.IgrafProvider;
import org.ungtb10d.graf.IRootgraf;
import org.ungtb10d.graf.grafcss.IFunctionFactory;
import org.ungtb10d.graf.grafcss.Rule;
import org.ungtb10d.graf.grafcss.Select;
import org.ungtb10d.graf.grafcss.StyleSet;
import org.ungtb10d.graf.style.IStyleFactory;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * Abstract test graf. Produces simplest possible labels.
 * 
 */
public abstract class AbstractTestgraf implements IgrafProvider {

	private IStyleFactory styleFactory;

	private IFunctionFactory functions;

	@Inject
	public AbstractTestgraf(IStyleFactory styleFactory, IFunctionFactory functions) {
		this.styleFactory = styleFactory;
		this.functions = functions;
	}

	public IRootgraf computegraf() {
		return computegraf(null, "a test graf", "root");
	}

	@Override
	public IRootgraf computegraf(Object modelObj) {
		return computegraf();
	}

	/**
	 * @modelObj - ignored, returns same graf at all times.
	 */
	public abstract IRootgraf computegraf(Object modelObj, String title, String id);

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
			Select.element(ElementType.graf).withStyle(simpleLabelFormat));

		return result;
	}
}
