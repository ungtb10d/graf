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
package org.ungtb10d.graf.emf;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.ungtb10d.graf.IgrafProvider;
import org.ungtb10d.graf.IRootgraf;
import org.ungtb10d.graf.elements.Rootgraf;
import org.ungtb10d.graf.grafcss.Rule;
import org.eclipse.emf.ecore.EObject;

import com.google.inject.Inject;

/**
 * Provides an instance graf for a model.
 * 
 */
public abstract class AbstractEgrafProvider implements IgrafProvider {

	@Inject
	private IELabelStyleProvider labelStyleProvider;

	private Collection<Rule> rules;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.IgrafProvider#computegraf()
	 */
	@Override
	public IRootgraf computegraf() {
		throw new UnsupportedOperationException("An EgrafProvider must have a model to transform.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.IgrafProvider#computegraf(java.lang.Object)
	 */
	@Override
	public IRootgraf computegraf(Object model) {
		return computegraf(model, "an EMF instance graf", "root");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.emf.IEgrafProvider#computegraf(org.eclipse.emf.ecore.EObject, java.lang.String, java.lang.String)
	 */
	@Override
	public IRootgraf computegraf(Object modelObj, String label, String id) {
		if(!(modelObj instanceof EObject))
			throw new IllegalArgumentException("EgrafProvider can only compute a graf for an EObject");
		EObject model = (EObject) modelObj;
		// Create the main graf
		Rootgraf g = new Rootgraf(label, "Rootgraf", id);

		// Compute the label style for all classes in the model
		rules = Collections.unmodifiableCollection(labelStyleProvider.configureLabelStyles(model));

		// Add a root vertex for the model itself.
		EVertex v = new EVertex(model);
		grafElementAdapterFactory.eINSTANCE.adapt(model).setAssociatedInfo(g, v);
		g.addVertex(v);
		computegraf(g, v, model);
		return g;
	}

	/**
	 * Compute the content of graf g, for the given vertex v representing the given model.
	 * 
	 * @param g
	 * @param v
	 * @param model
	 */

	abstract protected void computegraf(Rootgraf g, EVertex v, EObject model);

	@Override
	public Iterable<Rule> getRules() {
		if(rules == null)
			rules = Collections.emptyList();
		return rules;
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> safeListCast(Object o, Class<T> clazz) {
		if(!(o instanceof List))
			throw new ClassCastException("Can not cast object to a List. Class: " + o.getClass().getName());
		List<?> x = (List<?>) o;
		if(x.size() > 0) {
			clazz.cast(x.get(0)); // cast for side effect only
		}
		return (List<T>) x;
	}
}
