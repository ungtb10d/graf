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

import java.util.List;

import org.ungtb10d.graf.elements.Edge;
import org.ungtb10d.graf.elements.Rootgraf;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * Creates a graf with lists being represented as chained nodes.
 * 
 */
public class ChainedListEgrafProvider extends AbstractEgrafProvider {

	/**
	 * Compute the content of graf g, for the given vertex v representing the given model.
	 * 
	 * @param g
	 * @param v
	 * @param model
	 */
	@Override
	protected void computegraf(Rootgraf g, EVertex v, EObject model) {
		// Process everything the root references (contained, and referenced)
		// Generated Edges have the capability to treat containment and reference differently.
		for(EReference reference : model.eClass().getEAllReferences()) {
			if(reference.isMany()) {
				List<EObject> allReferenced = safeListCast(model.eGet(reference), EObject.class);
				int counter = 0;
				EVertex previousVertex = v;
				for(EObject referenced : allReferenced) {
					EVertex v2 = new EVertex(referenced);
					grafElementAdapterFactory.eINSTANCE.adapt(referenced).setAssociatedInfo(g, v2);
					g.addVertex(v2);

					// set the index key so that it can be displayed in the resulting
					// label/table.
					//
					v2.getUserData().put(EVertex.DATA_KEY_INDEX, Integer.toString(counter));

					// The main edge is an EEdge configured from the reference
					if(counter == 0) {
						EEdge edge_1 = new EEdge(reference, previousVertex, v2);
						g.addEdge(edge_1);

					}
					else {
						// link the subsequent element to the previous using and index-to-index edge
						// (it will be connected to the index ports and rendered in a special style)
						// It has no label.
						//
						Edge edge_n = new Edge(null, //
							EAttributeBasedLabelStyleProvider.STYLE__INDEX_TO_INDEX_EDGE, //
							previousVertex, v2);
						g.addEdge(edge_n);
					}
					counter++;
					previousVertex = v2;
					computegraf(g, v2, referenced);
				}
			}
			else {
				// reference is not a list
				EObject referenced = EObject.class.cast(model.eGet(reference));
				EVertex v2 = new EVertex(referenced);
				grafElementAdapterFactory.eINSTANCE.adapt(referenced).setAssociatedInfo(g, v2);
				g.addVertex(v2);
				EEdge edge = new EEdge(reference, v, v2);
				g.addEdge(edge);
				computegraf(g, v2, referenced);
			}
		}
	}
}
