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

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All graf Tests.
 */
// @fmtOff
@SuiteClasses({
	TestBase64.class,
	TestSVGFixer.class,
	TestBasicFeatures.class,
	TestMockgraf.class,
	TestRenderingToPng.class,
	TestEgraf.class,
	TestCircularByteBuffer.class,
	TestRuleBasedFilterStream.class
})
// @fmtOn
@RunWith(Suite.class)
public class grafTests {
}
