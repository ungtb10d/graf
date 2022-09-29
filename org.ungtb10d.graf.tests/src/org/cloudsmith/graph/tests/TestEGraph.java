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

import org.ungtb10d.geppetto.pp.AssignmentExpression;
import org.ungtb10d.geppetto.pp.Expression;
import org.ungtb10d.geppetto.pp.PPFactory;
import org.ungtb10d.geppetto.pp.PuppetManifest;
import org.ungtb10d.geppetto.pp.SingleQuotedString;
import org.ungtb10d.geppetto.pp.VariableExpression;
import org.ungtb10d.graf.ICancel;
import org.ungtb10d.graf.IgrafProvider;
import org.ungtb10d.graf.IRootgraf;
import org.ungtb10d.graf.dot.DotRenderer;
import org.ungtb10d.graf.emf.DefaultEgrafModule;
import org.ungtb10d.graf.emf.HorizontalArrayListEgrafProvider;
import org.ungtb10d.graf.grafcss.grafCSS;
import org.ungtb10d.graf.graphviz.graphvizLayout;
import org.ungtb10d.graf.graphviz.Igraphviz;
import org.ungtb10d.graf.style.themes.IStyleTheme;
import org.eclipse.emf.common.util.EList;
import org.junit.Test;

import com.google.inject.Module;

/**
 * Tests Egraf
 * 
 */
public class TestEgraf extends AbstractgrafTests {

	private AssignmentExpression getAssignmentExpression(String varName, String value) {
		final PPFactory pf = PPFactory.eINSTANCE;

		AssignmentExpression assign = pf.createAssignmentExpression();
		VariableExpression varX = pf.createVariableExpression();
		varX.setVarName(varName);
		assign.setLeftExpr(varX);
		SingleQuotedString aString = pf.createSingleQuotedString();
		aString.setText(value);
		assign.setRightExpr(aString);
		return assign;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.tests.AbstractgrafTests#getModule()
	 */
	@Override
	protected Module getModule() {
		return new DefaultEgrafModule();
	}

	@Test
	public void test_smokeTest() throws IOException {
		// Need a model
		// $x = "Hello graf World"
		final PPFactory pf = PPFactory.eINSTANCE;
		PuppetManifest manifest = pf.createPuppetManifest();
		EList<Expression> statements = manifest.getStatements();
		AssignmentExpression assign = getAssignmentExpression("$x", "Hello Egraf World!");
		statements.add(assign);
		AssignmentExpression assign2 = getAssignmentExpression("$y", "Goodbye Egraf World!");
		statements.add(assign2);

		// Render it
		Igraphviz graphviz = get(Igraphviz.class);
		grafCSS themeSheet = get(grafCSS.class);

		IgrafProvider grafProvider = get(IgrafProvider.class);
		IRootgraf testgraf = grafProvider.computegraf(manifest);

		IStyleTheme theme = get(IStyleTheme.class);
		// append the theme's styles with those from the provider
		themeSheet.addAll(theme.getInstanceRules());
		themeSheet.addAll(grafProvider.getRules());

		// IStyleFactory styles = get(IStyleFactory.class);
		// themeSheet.addRule(Select.graf("Rootgraf").withStyle(styles.backgroundColor("#cccccc")));

		File output = getTestOutputFolder("output", true);
		FileOutputStream tmp = new FileOutputStream(new File(output, "e_smokeTest.png"));

		// Render without the default styles. Use styles from Simplegraf1
		FileOutputStream dot = new FileOutputStream(new File(output, "e_smokeTest.dot"));
		DotRenderer dotRenderer = get(DotRenderer.class);
		dotRenderer.write(ICancel.NullIndicator, dot, testgraf, theme.getDefaultRules(), themeSheet);

		// Render without the default styles. Use styles from Simplegraf1
		assertTrue("Writing PNG", graphviz.writePNG(
			ICancel.NullIndicator, tmp, graphvizLayout.dot, testgraf, theme.getDefaultRules(), themeSheet));

	}

	@Test
	public void test_verticalIndex() throws IOException {
		// Need a model
		// $x = "Hello graf World"
		final PPFactory pf = PPFactory.eINSTANCE;
		PuppetManifest manifest = pf.createPuppetManifest();
		EList<Expression> statements = manifest.getStatements();
		AssignmentExpression assign = getAssignmentExpression("$x", "Hello Egraf World!");
		statements.add(assign);
		AssignmentExpression assign2 = getAssignmentExpression("$y", "Goodbye Egraf World!");
		statements.add(assign2);

		// Render it
		Igraphviz graphviz = get(Igraphviz.class);
		grafCSS themeSheet = get(grafCSS.class);

		IgrafProvider grafProvider = get(HorizontalArrayListEgrafProvider.class);
		IRootgraf testgraf = grafProvider.computegraf(manifest);

		IStyleTheme theme = get(IStyleTheme.class);
		// append the theme's styles with those from the provider
		themeSheet.addAll(theme.getInstanceRules());
		themeSheet.addAll(grafProvider.getRules());

		// IStyleFactory styles = get(IStyleFactory.class);
		// themeSheet.addRule(Select.graf("Rootgraf").withStyle(styles.backgroundColor("#cccccc")));

		File output = getTestOutputFolder("output", true);
		FileOutputStream tmp = new FileOutputStream(new File(output, "e_horizontalArrayIndex.png"));

		// Render without the default styles. Use styles from Simplegraf1
		FileOutputStream dot = new FileOutputStream(new File(output, "e_horizontalArrayIndex.png.dot"));
		DotRenderer dotRenderer = get(DotRenderer.class);
		dotRenderer.write(ICancel.NullIndicator, dot, testgraf, theme.getDefaultRules(), themeSheet);

		// Render without the default styles. Use styles from Simplegraf1
		assertTrue("Writing PNG", graphviz.writePNG(
			ICancel.NullIndicator, tmp, graphvizLayout.dot, testgraf, theme.getDefaultRules(), themeSheet));

	}
}
