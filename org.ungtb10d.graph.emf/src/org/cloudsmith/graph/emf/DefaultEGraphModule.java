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
package org.ungtb10d.graph.emf;

import org.ungtb10d.graph.DefaultGraphModule;
import org.ungtb10d.graph.IGraphProvider;

/**
 * A Default guice module for producing graphs for EMF models. Derived classes can override individual
 * bind methods, but should not override {@link #configure()}.
 * 
 */
public class DefaultEGraphModule extends DefaultGraphModule {

	/**
	 * Binds a EMF model to Graph producer. This implementation binds {@link AbstractEGraphProvider}.
	 */
	protected void bindIEGraphProvider() {
		bind(IGraphProvider.class).to(ChainedListEGraphProvider.class);
	}

	/**
	 * Binds a provider of labels for EObjects in a model. This implementation binds {@link EAttributeBasedLabelStyleProvider}.
	 */
	protected void bindIELabelStyleProvider() {
		bind(IELabelStyleProvider.class).to(EAttributeBasedLabelStyleProvider.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		super.configure();
		bindIEGraphProvider();
		bindIELabelStyleProvider();
	}

}