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

import java.util.WeakHashMap;

import org.ungtb10d.graf.IgrafElement;
import org.eclipse.emf.common.notify.impl.AdapterImpl;

/**
 * The grafElementAdapter associates an instance of IgrafElement with a (weak) key.
 * The intended use is to associate a Vertex or Edge with an EObject keyed by some context (like a graf, but it is
 * not limited to any graf related class).
 * The adapter allows association of one graf element per key.
 */
public class grafElementAdapter extends AdapterImpl {
	WeakHashMap<Object, IgrafElement> associatedInfo = new WeakHashMap<Object, IgrafElement>();

	public IgrafElement getgrafElement(Object key) {
		return associatedInfo.get(key);
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return type == grafElementAdapter.class;
	}

	public void setAssociatedInfo(Object key, IgrafElement info) {
		associatedInfo.put(key, info);
	}
}
