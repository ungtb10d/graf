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
package org.ungtb10d.graf.dot;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.ungtb10d.graf.ICancel;
import org.ungtb10d.graf.IClustergraf;
import org.ungtb10d.graf.IEdge;
import org.ungtb10d.graf.Igraf;
import org.ungtb10d.graf.IgrafElement;
import org.ungtb10d.graf.IRootgraf;
import org.ungtb10d.graf.IVertex;
import org.ungtb10d.graf.elements.Edge;
import org.ungtb10d.graf.elements.Rootgraf;
import org.ungtb10d.graf.elements.Vertex;
import org.ungtb10d.graf.grafcss.grafCSS;
import org.ungtb10d.graf.grafcss.StyleSet;
import org.ungtb10d.graf.style.StyleFactory;
import org.ungtb10d.graf.style.StyleType;

import com.google.common.collect.Iterators;
import com.google.inject.BindingAnnotation;
import com.google.inject.Inject;

/**
 * Produces dot output from a graf instance.
 */
public class DotRenderer {

	/**
	 * Annotation to use for the Dot Output Empty string.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.FIELD, ElementType.PARAMETER })
	@BindingAnnotation
	public @interface EmptyString {
	}

	private PrintStream out;

	private grafCSS theGCSS;

	StyleFactory.LabelFormat defaultNodeLabelFormat;

	StyleFactory.LabelFormat defaultEdgeLabelFormat;

	StyleFactory.LabelFormat defaultgrafLabelFormat;

	private StyleSet defaultgrafStyles;

	private StyleSet defaultNodeStyles;

	private StyleSet defaultEdgeStyles;

	private IRootgraf grafPrototype;

	private Vertex vertexPrototype;

	private Edge edgePrototype;

	private DotgrafElementRenderer elementRenderer;

	private grafCSS defaultGCSS;

	@Inject
	public DotRenderer(DotgrafElementRenderer elementRenderer) {
		this.elementRenderer = elementRenderer;
	}

	private String formatReference(IgrafElement element) {
		IgrafElement[] parents = Iterators.toArray(element.getContext(), IgrafElement.class);
		int plength = (parents == null)
				? 0
				: parents.length;
		StringBuffer buf = new StringBuffer(10 + 5 * plength);

		// the vertex can be an instance of Igraf in which case it should be handled as
		// a subgraf, and the name is different if the subgraf is a cluster
		if(element instanceof Igraf) {
			// all references to subgrafs must start with the keyword subgraf
			buf.append("subgraf ");
		}
		// enclose the constructed names in quotes
		buf.append("\"");

		if(element instanceof IClustergraf) {
			// if the graf is a cluster it's name must be prefixed with 'cluster'
			// ad a '_' to make it easier to read.
			buf.append("cluster_");
		}
		// add each parents id separated by - start with root (last in array)
		if(parents != null)
			for(int i = parents.length - 1; i >= 0; i--)
				buf.append((i == (plength - 1)
						? ""
						: "-") + parents[i].getId());
		buf.append("-");
		buf.append(element.getId());
		// close the "
		buf.append("\"");
		return buf.toString();
	}

	private void printDefaultEdgeStyling(ICancel cancel) {
		ByteArrayOutputStream tmp = new ByteArrayOutputStream();
		PrintStream tmpOut = new PrintStream(tmp);

		tmpOut.print("edge ");
		int numStyles = elementRenderer.printStyles(cancel, tmpOut, edgePrototype, defaultEdgeStyles, defaultGCSS);
		tmpOut.print(";\n");
		if(numStyles > 0)
			out.print(tmp.toString());
	}

	private void printDefaultgrafStyling(ICancel cancel) {
		ByteArrayOutputStream tmp = new ByteArrayOutputStream();
		PrintStream tmpOut = new PrintStream(tmp);

		tmpOut.print("graf ");
		int numStyles = elementRenderer.printStyles(cancel, tmpOut, grafPrototype, defaultgrafStyles, defaultGCSS);
		tmpOut.print(";\n");

		if(numStyles > 0)
			out.print(tmp.toString());
	}

	private void printDefaultNodeStyling(ICancel cancel) {
		ByteArrayOutputStream tmp = new ByteArrayOutputStream();
		PrintStream tmpOut = new PrintStream(tmp);

		tmpOut.print("node ");
		int numStyles = elementRenderer.printStyles(cancel, tmpOut, vertexPrototype, defaultNodeStyles, defaultGCSS);
		tmpOut.print(";\n");

		if(numStyles > 0)
			out.print(tmp.toString());
	}

	/**
	 * If a label is set, the edge will use it, otherwise edges are without labels.
	 * 
	 * @param edge
	 */
	private void printEdge(IEdge edge, ICancel cancel) {
		// produce edges that link to the "north" port, but use default (from center) linking
		// from the source node (this looks best).
		//
		out.printf("%s -> %s", formatReference(edge.getFrom()), formatReference(edge.getTo()));

		ByteArrayOutputStream tmp = new ByteArrayOutputStream();
		PrintStream tmpOut = new PrintStream(tmp);

		int numStyles = elementRenderer.printStyles(cancel, tmpOut, edge, theGCSS);
		if(numStyles > 0)
			out.print(tmp.toString());
		out.print(";\n");
	}

	/**
	 * Prints a subgraf on the form:
	 * subgraf "reference" { graf body }
	 * Where reference is the full "[cluster_]id-id...-id"
	 * 
	 * @param graf
	 */
	private void printgraf(Igraf graf, ICancel cancel) {
		out.printf("%s {\n", formatReference(graf));
		printgrafBody(graf, cancel);
		out.print("}\n");
	}

	private void printgrafBody(Igraf graf, ICancel cancel) {

		// print the root graf's attributes

		// grafs have their styles set as statements instead of as a list after the body.
		// i.e.
		// graf { a; b; a->b; color="blue"; }
		// and not
		// graf { a; b; a->b; }[color="blue"];
		//

		// Print all the vertices
		for(IVertex v : graf.getVertices()) {
			cancel.assertContinue();
			printVertex(v, cancel);
		}
		// and all the edges
		for(IEdge e : graf.getEdges()) {
			cancel.assertContinue();
			printEdge(e, cancel);
		}

		// Print all the subgrafs first so they do not inherit settings intended for the root
		// graf. All inherited styles should have been set as defaults per element type.
		for(Igraf g : graf.getSubgrafs()) {
			cancel.assertContinue();
			printgraf(g, cancel);
		}

		ByteArrayOutputStream tmp = new ByteArrayOutputStream();
		PrintStream tmpOut = new PrintStream(tmp);

		int numStyles = elementRenderer.printStyleStatements(cancel, tmpOut, graf, theGCSS);
		if(numStyles > 0) {
			out.print(tmp.toString());
			out.print("\n");
		}

	}

	private void printVertex(IVertex vertex, ICancel cancel) {
		// get the full name as it is used in references
		String reference = formatReference(vertex);
		if(reference == null || reference.length() == 0)
			throw new IllegalStateException("A vertext produced empty identity");
		out.printf("%s ", formatReference(vertex));

		ByteArrayOutputStream tmp = new ByteArrayOutputStream();
		PrintStream tmpOut = new PrintStream(tmp);

		int numStyles = elementRenderer.printStyles(cancel, tmpOut, vertex, theGCSS);
		if(numStyles > 0) {
			tmpOut.flush();
			out.print(tmp.toString());
		}
		out.print(";\n");
	}

	/**
	 * The defaultRules are the rules that are used to set dot (static) defaults per ElementType.
	 * These rules can not contain EL statements, nor any styles that needs to be set per instance.
	 * 
	 * The styleRules are the rules that are added to an instance rule set. Lowest priority first.
	 * 
	 * @throws IllegalArgumentException
	 *             for invalid input
	 */
	private void processGCSS(ICancel cancel, grafCSS defaultGCSS, grafCSS... styleRules) {
		if(defaultGCSS == null)
			throw new IllegalArgumentException("default style rules is null");

		theGCSS = new grafCSS();
		for(grafCSS gcss : styleRules)
			theGCSS.addAll(gcss);

		this.defaultGCSS = defaultGCSS;
		grafPrototype = new Rootgraf("", "", "prototype");
		vertexPrototype = new Vertex("", "", "prototype");
		edgePrototype = new Edge(vertexPrototype, vertexPrototype, "prototype");

		defaultgrafStyles = defaultGCSS.collectStyles(grafPrototype, cancel);
		defaultNodeStyles = defaultGCSS.collectStyles(vertexPrototype, cancel);
		defaultEdgeStyles = defaultGCSS.collectStyles(edgePrototype, cancel);

		// assert that label formats are available - look up label formats in the instance rules.
		//
		if(theGCSS.collectStyles(grafPrototype, cancel).getStyleValue(StyleType.labelFormat, grafPrototype) == null)
			throw new IllegalArgumentException("Default graf label format is null");
		if(theGCSS.collectStyles(vertexPrototype, cancel).getStyleValue(StyleType.labelFormat, vertexPrototype) == null)
			throw new IllegalArgumentException("Default graf label format is null");
		if(theGCSS.collectStyles(edgePrototype, cancel).getStyleValue(StyleType.labelFormat, edgePrototype) == null)
			throw new IllegalArgumentException("Default graf label format is null");

	}

	/**
	 * Produces output in Dot notation on the given stream. The defaultCSS should contain
	 * static rules per element. No reference to style class, instance id, or use of EL is allowed in
	 * this style set. The styleCheets is one or more instance style sheets in increasing order of
	 * importance. Note that as a minimum the style must contain label format styles for the three
	 * element types Vertex, graf, and Edge. Failing to supply these will result in an IllegalArgumentException.
	 * 
	 * @param cancel
	 *            A cancel indicator that hould periodically be checked for cancellation.
	 * @param stream
	 *            where the dot output should be written
	 * @param graf
	 *            the graf to render
	 * @param defaultCSS
	 *            the default (static CSS)
	 * @param styleSheets
	 *            - use case specific stylesheets.
	 */
	public void write(ICancel cancel, OutputStream stream, Igraf graf, grafCSS defaultCSS, grafCSS... styleSheets) {
		if(stream == null)
			throw new IllegalArgumentException("stream is null");
		if(!(stream instanceof PrintStream))
			out = new PrintStream(stream, true);
		else
			out = (PrintStream) stream;

		processGCSS(cancel, defaultCSS, styleSheets);

		// a directed graf (this is the root graf).
		out.printf("digraf %s {\n", graf.getId());

		// print the default styling for graf, node and edge
		printDefaultgrafStyling(cancel);
		printDefaultNodeStyling(cancel);
		printDefaultEdgeStyling(cancel);

		// print the graf
		printgrafBody(graf, cancel);

		// printgraf(graf);

		// close
		out.printf("}\n");
	}
}
