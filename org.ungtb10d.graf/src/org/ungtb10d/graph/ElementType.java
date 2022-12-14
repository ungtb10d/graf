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

import java.util.EnumSet;
import java.util.Set;

import com.google.common.collect.Sets;

/**
 * An ElementType describes the role played by a graf element (as opposed to its interface or implementation type).
 * 
 * The ElementType class defines man useful sets like {@link #ANY} that are efficiently implemented using
 * a backing {@link EnumSet}.
 * 
 * TODO: Does currently not allow discrimination between a root graf and a subgraf/cluster. Now that
 * ElementType is returned by IgrafElement it should be possible to make this distinction as it can not
 * be statically computed (a root is a graf without a parent), although if checking of style type is
 * done when setting instance style, this will fail unless a graf is added to a parent before the style
 * is set... more to say I suppose...
 */
public enum ElementType {
	graf, subgraf, cluster, vertex, edge, table, row, cell;

	/**
	 * Counter useful for testing if a set contains all enumerators, how many are missing in a set etc.
	 */
	public static final int NUM_TYPES = 8;

	/**
	 * Refers to all element types (including the non styleable {@link #row}).
	 */
	public static final Set<ElementType> ALL = Sets.immutableEnumSet(EnumSet.allOf(ElementType.class));

	/**
	 * Refers to all styleable graf elements (excludes {@link #row})
	 */
	public static final Set<ElementType> ANY = Sets.complementOf(EnumSet.of(row));

	public static final Set<ElementType> TABLE = Sets.immutableEnumSet(table);

	public static final Set<ElementType> graf = Sets.immutableEnumSet(graf);

	public static final Set<ElementType> SUBgraf = Sets.immutableEnumSet(subgraf);

	public static final Set<ElementType> CLUSTER = Sets.immutableEnumSet(cluster);

	public static final Set<ElementType> CELL = Sets.immutableEnumSet(cell);

	public static final Set<ElementType> VERTEX = Sets.immutableEnumSet(vertex);

	public static final Set<ElementType> TABLE_AND_CELL = Sets.immutableEnumSet(table, cell);

	public static final Set<ElementType> CELL_AND_TABLE_AND_VERTEX = Sets.immutableEnumSet(vertex, table, cell);

	public static final Set<ElementType> graf_AND_VERTEX = Sets.immutableEnumSet(graf, vertex);

	public static final Set<ElementType> EDGE_AND_graf_AND_VERTEX = Sets.immutableEnumSet(edge, graf, vertex);

	public static final Set<ElementType> CLUSTER_AND_VERTEX = Sets.immutableEnumSet(cluster, vertex);

	public static final Set<ElementType> CLUSTER_AND_graf_AND_CELL_AND_TABLE = Sets.immutableEnumSet(
		cluster, graf, cell, table);

	public static final Set<ElementType> CLUSTER_AND_graf_AND_CELL_AND_TABLE_AND_VERTEX = Sets.immutableEnumSet(
		cluster, graf, cell, table, vertex);

	public static final Set<ElementType> EDGE = Sets.immutableEnumSet(ElementType.edge);

	/**
	 * Refers to all except {@link #edge} and {@link #row}.
	 */
	public static final Set<ElementType> NOT_EDGE = Sets.complementOf(EnumSet.of(edge, row));

	/**
	 * Refers to all except {@link #graf} and {@link #row}.
	 */
	public static final Set<ElementType> NOT_graf = Sets.complementOf(EnumSet.of(graf, row));

	/**
	 * Refers to all except {@link #subgraf} and {@link #row}.
	 */
	public static final Set<ElementType> NOT_SUBgraf = Sets.complementOf(EnumSet.of(subgraf, row));

}
