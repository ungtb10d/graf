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

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.ungtb10d.graf.ICancel;
import org.ungtb10d.graf.IgrafProvider;
import org.ungtb10d.graf.IRootgraf;
import org.ungtb10d.graf.dot.DotRenderer;
import org.ungtb10d.graf.grafcss.grafCSS;
import org.ungtb10d.graf.grafcss.Select;
import org.ungtb10d.graf.grafviz.grafvizLayout;
import org.ungtb10d.graf.grafviz.Igrafviz;
import org.ungtb10d.graf.style.IStyleFactory;
import org.ungtb10d.graf.style.RankDirection;
import org.ungtb10d.graf.style.themes.IStyleTheme;
import org.ungtb10d.graf.testgrafs.Simplegraf1;
import org.ungtb10d.graf.testgrafs.Simplegraf2;
import org.junit.Test;

/**
 * Tests rendering to PNG. Manual inspection of result is required.
 * 
 */
public class TestRenderingToPng extends AbstractgrafTests {

	@Test
	public void testPNG_abc_abc_vertical_default() throws IOException {
		Igrafviz grafviz = get(Igrafviz.class);
		grafCSS themeSheet = get(grafCSS.class);

		IgrafProvider grafProvider = get(Simplegraf2.class);
		IRootgraf testgraf = grafProvider.computegraf();

		IStyleTheme theme = get(IStyleTheme.class);
		// append the theme's styles with those from the provider
		themeSheet.addAll(theme.getInstanceRules());
		themeSheet.addAll(grafProvider.getRules());

		// IStyleFactory styles = get(IStyleFactory.class);
		// themeSheet.addRule(Select.graf("Rootgraf").withStyle(styles.backgroundColor("#cccccc")));

		File output = getTestOutputFolder("output", true);
		FileOutputStream tmp = new FileOutputStream(new File(output, "abc_abc_vertical_default.png"));

		// Render without the default styles. Use styles from Simplegraf1
		FileOutputStream dot = new FileOutputStream(new File(output, "abc_abc_vertical_default.dot"));
		DotRenderer dotRenderer = get(DotRenderer.class);
		dotRenderer.write(ICancel.NullIndicator, dot, testgraf, theme.getDefaultRules(), themeSheet);

		// Render without the default styles. Use styles from Simplegraf1
		assertTrue("Writing PNG", grafviz.writePNG(
			ICancel.NullIndicator, tmp, grafvizLayout.dot, testgraf, theme.getDefaultRules(), themeSheet));

	}

	@Test
	public void testPNG_abc_horizontal_default() throws IOException {
		Igrafviz grafviz = get(Igrafviz.class);
		grafCSS themeSheet = get(grafCSS.class);

		IgrafProvider grafProvider = get(Simplegraf1.class);
		IRootgraf testgraf = grafProvider.computegraf();

		IStyleTheme theme = get(IStyleTheme.class);
		IStyleFactory styles = get(IStyleFactory.class);
		// append the theme's styles with those from the provider
		themeSheet.addAll(theme.getInstanceRules());
		themeSheet.addAll(grafProvider.getRules());
		themeSheet.addRule(Select.graf().withStyle(styles.rankDirectionStyle(RankDirection.LR)));

		File output = getTestOutputFolder("output", true);
		FileOutputStream png = new FileOutputStream(new File(output, "abc_horizontal_default.png"));

		// Render without the default styles. Use styles from Simplegraf1
		FileOutputStream dot = new FileOutputStream(new File(output, "abc_horizontal_default.dot"));
		DotRenderer dotRenderer = get(DotRenderer.class);
		dotRenderer.write(ICancel.NullIndicator, dot, testgraf, theme.getDefaultRules(), themeSheet);

		// Render without the default styles. Use styles from Simplegraf1
		assertTrue("Wriging PNG", grafviz.writePNG(
			ICancel.NullIndicator, png, grafvizLayout.dot, testgraf, theme.getDefaultRules(), themeSheet));

	}

	@Test
	public void testPNG_abc_vertical_default() throws IOException {
		Igrafviz grafviz = get(Igrafviz.class);
		grafCSS themeSheet = get(grafCSS.class);

		IgrafProvider grafProvider = get(Simplegraf1.class);
		IRootgraf testgraf = grafProvider.computegraf();

		IStyleTheme theme = get(IStyleTheme.class);
		// append the theme's styles with those from the provider
		themeSheet.addAll(theme.getInstanceRules());
		themeSheet.addAll(grafProvider.getRules());

		File output = getTestOutputFolder("output", true);
		FileOutputStream tmp = new FileOutputStream(new File(output, "abc_vertical_default.png"));

		// Render without the default styles. Use styles from Simplegraf1
		FileOutputStream dot = new FileOutputStream(new File(output, "abc_vertical_default.dot"));
		DotRenderer dotRenderer = get(DotRenderer.class);
		dotRenderer.write(ICancel.NullIndicator, dot, testgraf, theme.getDefaultRules(), themeSheet);

		// Render without the default styles. Use styles from Simplegraf1
		assertTrue("Writing PNG", grafviz.writePNG(
			ICancel.NullIndicator, tmp, grafvizLayout.dot, testgraf, theme.getDefaultRules(), themeSheet));
	}

	@Test
	public void testPNG_abc_vertical_unstyled() throws IOException {
		Igrafviz grafviz = get(Igrafviz.class);
		grafCSS themeSheet = get(grafCSS.class);

		IgrafProvider grafProvider = get(Simplegraf1.class);
		IRootgraf testgraf = grafProvider.computegraf();
		themeSheet.addAll(grafProvider.getRules());

		File output = getTestOutputFolder("output", true);
		FileOutputStream tmp = new FileOutputStream(new File(output, "abc_vertical_unstyled.png"));

		// Render without the default styles. Use styles from Simplegraf1
		assertTrue("Writing PNG", grafviz.writePNG(
			ICancel.NullIndicator, tmp, grafvizLayout.dot, testgraf, get(grafCSS.class), themeSheet));
	}

}
