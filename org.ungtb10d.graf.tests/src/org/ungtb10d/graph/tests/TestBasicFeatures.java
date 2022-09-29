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
package org.ungtb10d.graf.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;

import org.ungtb10d.graf.ICancel;
import org.ungtb10d.graf.Igraf;
import org.ungtb10d.graf.IgrafProvider;
import org.ungtb10d.graf.IRootgraf;
import org.ungtb10d.graf.dot.DotRenderer;
import org.ungtb10d.graf.elements.Edge;
import org.ungtb10d.graf.elements.Rootgraf;
import org.ungtb10d.graf.elements.Vertex;
import org.ungtb10d.graf.grafcss.grafCSS;
import org.ungtb10d.graf.grafcss.IFunctionFactory;
import org.ungtb10d.graf.graphviz.graphvizFormat;
import org.ungtb10d.graf.graphviz.graphvizLayout;
import org.ungtb10d.graf.graphviz.graphvizRenderer;
import org.ungtb10d.graf.graphviz.Igraphviz;
import org.ungtb10d.graf.style.IStyleFactory;
import org.ungtb10d.graf.testgrafs.AbstractTestgraf;
import org.junit.Test;

import com.google.inject.Inject;

/**
 * Tests basic production of graphviz output:
 * - that the DotRenderer is producing expected dot output
 * - that the graphviz renderer can be called, and that it produces different types of output
 */
public class TestBasicFeatures extends AbstractgrafTests {

	/**
	 * Tests that vertexes with explicit ids keep these ids in dot and that edges
	 * use the user specified ids.
	 * 
	 */
	public static class IdentityTestgraf extends AbstractTestgraf {
		@Inject
		public IdentityTestgraf(IStyleFactory styleFactory, IFunctionFactory functions) {
			super(styleFactory, functions);
		}

		/**
		 * @modelObj - ignored, returns same graf at all times.
		 */
		@Override
		public IRootgraf computegraf(Object modelObj, String title, String id) {
			Rootgraf g = new Rootgraf(title, "Rootgraf", id);
			Vertex a = new Vertex("a", "v", "a");
			Vertex b = new Vertex("b", "v", "b");
			Vertex c = new Vertex("c", "v", "c");
			g.addVertex(a, b, c);

			Edge abEdge = new Edge(a, b);
			Edge bcEdge = new Edge(b, c);
			g.addEdge(abEdge, bcEdge);

			return g;
		}
	}

	/**
	 * Tests that vertexes that do not have explicit ids gets generated ids and that edges between
	 * these vertexes work.
	 */
	public static class NoIdentityTestgraf extends AbstractTestgraf {
		@Inject
		public NoIdentityTestgraf(IStyleFactory styleFactory, IFunctionFactory functions) {
			super(styleFactory, functions);
		}

		/**
		 * @modelObj - ignored, returns same graf at all times.
		 */
		@Override
		public IRootgraf computegraf(Object modelObj, String title, String id) {
			Rootgraf g = new Rootgraf(title, "Rootgraf", id);
			Vertex a = new Vertex("a", "v");
			Vertex b = new Vertex("b", "v");
			Vertex c = new Vertex("c", "v");
			g.addVertex(a, b, c);

			Edge abEdge = new Edge(a, b);
			Edge bcEdge = new Edge(b, c);
			g.addEdge(abEdge, bcEdge);

			return g;
		}
	}

	/**
	 * Output produced by a very simple graf with no identities.
	 */
	public static final String testgraf_noIdentities_expected = "digraf root {\n" + //
			"\"root-v1\" [\n" + "label=\"a\"];\n" + //
			"\"root-v2\" [\n" + "label=\"b\"];\n" + //
			"\"root-v3\" [\n" + "label=\"c\"];\n" + //
			"\"root-v1\" -> \"root-v2\";\n" + //
			"\"root-v2\" -> \"root-v3\";\n" + //
			"label=\"a test graf\";\n" + //
			"}\n";

	/**
	 * Output produced by a very simple graf with no identities.
	 */
	public static final String testgraf_Identities_expected = "digraf root {\n" + //
			"\"root-a\" [\n" + "label=\"a\"];\n" + //
			"\"root-b\" [\n" + "label=\"b\"];\n" + //
			"\"root-c\" [\n" + "label=\"c\"];\n" + //
			"\"root-a\" -> \"root-b\";\n" + //
			"\"root-b\" -> \"root-c\";\n" + //
			"label=\"a test graf\";\n" + //
			"}\n";

	/**
	 * Test that output is correct when there are no styles and identities have not
	 * been set.
	 */
	@Test
	public void testgraf_identities() {
		grafCSS themeSheet = get(grafCSS.class);

		IgrafProvider grafProvider = get(IdentityTestgraf.class);
		Igraf testgraf = grafProvider.computegraf();
		themeSheet.addAll(grafProvider.getRules());

		DotRenderer dotRenderer = get(DotRenderer.class);
		ByteArrayOutputStream tmp = new ByteArrayOutputStream();

		// Render without the default styles. Use styles from Simplegraf1
		dotRenderer.write(ICancel.NullIndicator, tmp, testgraf, get(grafCSS.class), themeSheet);
		assertEquals("Expected result differs", testgraf_Identities_expected, tmp.toString());
	}

	/**
	 * Test that output is correct when there are no styles and identities have not
	 * been set.
	 */
	@Test
	public void testgraf_noIdentities() {
		grafCSS themeSheet = get(grafCSS.class);

		IgrafProvider grafProvider = get(NoIdentityTestgraf.class);
		Igraf testgraf = grafProvider.computegraf();
		themeSheet.addAll(grafProvider.getRules());

		DotRenderer dotRenderer = get(DotRenderer.class);
		ByteArrayOutputStream tmp = new ByteArrayOutputStream();

		// Render without the default styles. Use styles from Simplegraf1
		dotRenderer.write(ICancel.NullIndicator, tmp, testgraf, get(grafCSS.class), themeSheet);
		assertEquals("Expected result differs", testgraf_noIdentities_expected, tmp.toString());
	}

	/**
	 * Tests running graphviz to produce output. The test is performed by producing output in svg format
	 * (this format is textual) and checks that text is in svg tags, that there are calls to path and polygon
	 * (to ensure there is some drawing information, and not just empty svg), and that the end tag is present
	 * (i.e. that output is not truncated).
	 */
	@Test
	public void testgraf_rungraphviz_svg() {
		Igraphviz graphviz = get(Igraphviz.class);
		grafCSS themeSheet = get(grafCSS.class);

		IgrafProvider grafProvider = get(IdentityTestgraf.class);
		IRootgraf testgraf = grafProvider.computegraf();
		themeSheet.addAll(grafProvider.getRules());

		ByteArrayOutputStream tmp = new ByteArrayOutputStream();
		graphviz.writegraphvizOutput(
			ICancel.NullIndicator, tmp, graphvizFormat.svg, graphvizRenderer.standard, graphvizLayout.dot, testgraf,
			get(grafCSS.class), themeSheet);
		String output = tmp.toString();
		assertTrue("Should contain <svg", output.contains("<svg"));
		assertTrue("Should contain <polygon", output.contains("<polygon"));
		assertTrue("Should contain <path", output.contains("<path"));
		assertTrue("Should end with </svg>", output.endsWith("</svg>\n"));
	}

	/**
	 * Tests running graphviz to produce output. The test is performed by producing output in xdot format
	 * (as this format is textual) and checks that text contains calls to "_draw_".
	 */
	@Test
	public void testgraf_rungraphviz_xdot() {
		Igraphviz graphviz = get(Igraphviz.class);
		grafCSS themeSheet = get(grafCSS.class);

		IgrafProvider grafProvider = get(IdentityTestgraf.class);
		IRootgraf testgraf = grafProvider.computegraf();
		themeSheet.addAll(grafProvider.getRules());

		ByteArrayOutputStream tmp = new ByteArrayOutputStream();
		graphviz.writegraphvizOutput(
			ICancel.NullIndicator, tmp, graphvizFormat.xdot, graphvizRenderer.standard, graphvizLayout.dot, testgraf,
			get(grafCSS.class), themeSheet);
		String output = tmp.toString();
		assertTrue("Should contain _draw_ calls", output.contains("_draw_"));
	}
}
