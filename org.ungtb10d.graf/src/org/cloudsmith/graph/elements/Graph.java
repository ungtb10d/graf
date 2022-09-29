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
package org.ungtb10d.graf.elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.ungtb10d.graf.ElementType;
import org.ungtb10d.graf.IEdge;
import org.ungtb10d.graf.Igraf;
import org.ungtb10d.graf.ISubgraf;
import org.ungtb10d.graf.IVertex;

import com.google.common.collect.Iterables;

/**
 * A graf is a container of other graf elements (which include other grafs as subgrafs, vertexes and edges).
 * This implementation of {@link Igraf} works with instances of other graf elements in the same package
 * as it manages their containment and identity (if not set).
 * 
 */
public class graf extends grafElement implements Igraf {
	private ArrayList<IEdge> edges;

	private ArrayList<ISubgraf> subgrafs;

	private ArrayList<IVertex> vertices;

	protected graf(Collection<String> styleClasses) {
		this(styleClasses, null);
	}

	protected graf(Collection<String> styleClasses, String id) {
		super(styleClasses, id);

		edges = new ArrayList<IEdge>();
		vertices = new ArrayList<IVertex>();
		subgrafs = new ArrayList<ISubgraf>();
	}

	protected graf(Igraf that) {
		super(that);
		edges = new ArrayList<IEdge>();
		Iterables.addAll(edges, that.getEdges());

		vertices = new ArrayList<IVertex>();
		Iterables.addAll(vertices, that.getVertices());

		subgrafs = new ArrayList<ISubgraf>();
		Iterables.addAll(subgrafs, that.getSubgrafs());
	}

	protected graf(String styleClass) {
		this(Collections.singleton(styleClass), null);
	}

	protected graf(String styleClass, String id) {
		this(Collections.singleton(styleClass), id);
	}

	/**
	 * Not part of the API - how edges are created is up to the implementation.
	 * 
	 * @param edge
	 */
	public void _addEdge(Edge edge) {
		edges.add(edge);
		if(edge.getId() == null)
			edge.setId("e" + edges.size());
		edge.setParentElement(this);
	}

	/**
	 * Adds a subgraf - the given graf must implement ISubgraf. Note that the method is private
	 * as it is required that the graf implements {@link grafElement}.
	 * 
	 * @param graf
	 */
	private void _addSubgraf(graf graf) {
		if(!(graf instanceof ISubgraf))
			throw new IllegalArgumentException("Can only add subgrafs to a graf");
		subgrafs.add((ISubgraf) graf);
		if(graf.getId() == null)
			graf.setId("g" + subgrafs.size());
		graf.setParentElement(this);
	}

	private void _addVertex(Vertex vertex) {
		vertices.add(vertex);
		if(vertex.getId() == null)
			vertex.setId("v" + vertices.size());
		vertex.setParentElement(this);
	}

	/**
	 * Not part of the API - how edges are created is up to the implementation.
	 * 
	 * @param edge
	 */
	public void addEdge(Edge edge, Edge... edges) {
		_addEdge(edge);
		for(Edge e : edges)
			_addEdge(e);
	}

	/**
	 * Not part of the API - how subgrafs are created and added is up to the implementation.
	 * 
	 * @param graf
	 */
	public void addSubgraf(Clustergraf graf) {
		_addSubgraf(graf);
	}

	/**
	 * Not part of the API - how subgrafs are created and added is up to the implementation.
	 * 
	 * @param graf
	 */
	public void addSubgraf(Subgraf graf) {
		_addSubgraf(graf);
	}

	/**
	 * Not part of the API - how vertexes are created and added is up to the implementation.
	 * 
	 * @param vertex
	 */
	public void addVertex(Vertex v0, Vertex... vn) {
		_addVertex(v0);
		for(Vertex v : vn)
			_addVertex(v);
	}

	@Override
	public Iterable<IEdge> getEdges() {
		return edges;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.elements.Vertex#getElementType()
	 */
	@Override
	public ElementType getElementType() {
		return ElementType.graf;
	}

	@Override
	public Iterable<ISubgraf> getSubgrafs() {
		return subgrafs;
	}

	@Override
	public Iterable<IVertex> getVertices() {
		return vertices;
	}
}
