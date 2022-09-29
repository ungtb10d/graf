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

import org.ungtb10d.graf.DefaultgrafModule;
import org.ungtb10d.graf.IgrafProvider;

/**
 * A Default guice module for producing grafs for EMF models. Derived classes can override individual
 * bind methods, but should not override {@link #configure()}.
 * 
 */
public class DefaultEgrafModule extends DefaultgrafModule {

	/**
	 * Binds a EMF model to graf producer. This implementation binds {@link AbstractEgrafProvider}.
	 */
	protected void bindIEgrafProvider() {
		bind(IgrafProvider.class).to(ChainedListEgrafProvider.class);
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
		bindIEgrafProvider();
		bindIELabelStyleProvider();
	}

}
