/**
 * Copyright (c) 2010, ungtb10d Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * - ungtb10d Inc - initial API and implementation.
 */
package org.ungtb10d.graf.emf;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

public class grafElementAdapterFactory extends AdapterFactoryImpl {
	public static grafElementAdapterFactory eINSTANCE = new grafElementAdapterFactory();

	public grafElementAdapter adapt(EObject target) {
		return adapt(target, grafElementAdapter.class);
	}

	/**
	 * Type safe variant of adapt
	 * 
	 * @param <T>
	 * @param target
	 * @param type
	 * @return
	 */
	protected <T> T adapt(EObject target, Class<T> type) {
		return type.cast(super.adapt(target, type));
	}

	@Override
	protected Adapter createAdapter(Notifier target, Object type) {
		return new grafElementAdapter();
	}

	@Override
	public boolean isFactoryForType(Object type) {
		return type == grafElementAdapter.class;
	}
}
