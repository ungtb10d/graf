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

import java.io.ByteArrayOutputStream;

import org.ungtb10d.graf.ICancel;
import org.ungtb10d.graf.Igraf;
import org.ungtb10d.graf.IgrafProvider;
import org.ungtb10d.graf.dot.DotRenderer;
import org.ungtb10d.graf.grafcss.grafCSS;
import org.ungtb10d.graf.style.themes.IStyleTheme;
import org.ungtb10d.graf.testgrafs.Simplegraf1;
import org.ungtb10d.graf.testgrafs.Testgraf;
import org.junit.Test;

/**
 * 
 *
 */
public class TestMockgraf extends AbstractgrafTests {

	/**
	 * Output produced by a very simple graf with no styling.
	 */
	public static final String testSimplegraf1_dotOutput_text = "digraf root {\n" + //
			"\"root-v1\" [\n" + //
			"label=\"a\"];\n" + //
			"\"root-v2\" [\n" + //
			"label=\"b\"];\n" + //
			"\"root-v3\" [\n" + //
			"label=\"c\"];\n" + //
			"\"root-v1\" -> \"root-v2\";\n" + //
			"\"root-v2\" -> \"root-v3\";\n" + //
			"label=\"a graf\";\n" + //
			"}\n";

	@Test
	public void testSimplegraf1_dotOutput() {
		grafCSS themeSheet = get(grafCSS.class);

		IgrafProvider grafProvider = get(Simplegraf1.class);
		Igraf testgraf = grafProvider.computegraf();
		themeSheet.addAll(grafProvider.getRules());

		DotRenderer dotRenderer = get(DotRenderer.class);
		ByteArrayOutputStream tmp = new ByteArrayOutputStream();

		// Render without the default styles. Use styles from Simplegraf1
		dotRenderer.write(ICancel.NullIndicator, tmp, testgraf, get(grafCSS.class), themeSheet);
		assertEquals("Expected result differs", testSimplegraf1_dotOutput_text, tmp.toString());
	}

	@Test
	public void testSmokeTest() {
		// Igrafviz grafviz = get(Igrafviz.class);

		IStyleTheme theme = get(IStyleTheme.class);
		grafCSS themeSheet = get(grafCSS.class);
		themeSheet.addAll(theme.getInstanceRules());

		IgrafProvider grafProvider = get(Testgraf.class);
		Igraf testgraf = grafProvider.computegraf();
		themeSheet.addAll(grafProvider.getRules());

		DotRenderer dotRenderer = get(DotRenderer.class);
		dotRenderer.write(ICancel.NullIndicator, System.err, testgraf, theme.getDefaultRules(), themeSheet);
		// String dotText = grafviz.getDotText(testgraf, theme.getDefaultRules(), themeSheet);
		// System.err.println(dotText);
	}
}
