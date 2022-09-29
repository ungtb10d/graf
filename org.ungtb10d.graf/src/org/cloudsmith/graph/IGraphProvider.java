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
package org.ungtb10d.graf;

import org.ungtb10d.graf.grafcss.Rule;

/**
 * Interface for a provider of a an optionally styled graf.
 * An implementation of a graf provider should strive to provide all methods, but
 * may throw {@link UnsupportedOperationException} if not capable of producing a graf without
 * an input model.
 */
public interface IgrafProvider {

	/**
	 * Method that computes/provides a graf.
	 * An implementation may throw {@link UnsupportedOperationException} if not capable of producing
	 * a graf without a model, but should consider returning an empty graf (with some message).
	 * 
	 * @return
	 */
	public IRootgraf computegraf();

	/**
	 * Method that transforms/computes the given model to a graf, using a label and id determined
	 * by the producer.
	 * This method should also compute any graf specific styling rules (returned by {@link #getRules()}).
	 * 
	 * @param model
	 * @param label
	 * @param id
	 * @return
	 */
	public IRootgraf computegraf(Object model);

	/**
	 * Method that transforms/computes the given model to a graf, using the given label and id as graf label/id.
	 * This method should also compute any graf specific styling rules (returned by {@link #getRules()}).
	 * 
	 * @param model
	 * @param label
	 * @param id
	 * @return
	 */
	public Igraf computegraf(Object model, String label, String id);

	/**
	 * Returns a collection of Rule containing styling rules for the specific graf.
	 * Never returns null. The list is only valid after {@link #computegraf(Object, String, String)} has
	 * been called. The returned collection is not modifiable.
	 * 
	 * @return
	 */
	public Iterable<Rule> getRules();

}
