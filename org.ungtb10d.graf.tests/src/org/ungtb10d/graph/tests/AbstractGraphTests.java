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

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.ungtb10d.geppetto.common.os.FileUtils;
import org.ungtb10d.graf.DefaultgrafModule;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.service.datalocation.Location;
import org.junit.Before;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 *
 */
public class AbstractgrafTests {
	public static File getTestOutputFolder(String name, boolean purge) throws IOException {
		Location instanceLocation = Platform.getInstanceLocation();
		URL url = instanceLocation != null
				? instanceLocation.getURL()
				: null;
		File testFolder;
		if(instanceLocation == null || !instanceLocation.isSet() || url == null) {
			String tempDir = System.getProperty("java.io.tmpdir");
			testFolder = new File(tempDir, name);
		}
		else {
			testFolder = new File(toFile(url), name);
		}
		testFolder.mkdirs();
		if(purge) {
			// Ensure that the folder is empty
			for(File file : testFolder.listFiles())
				FileUtils.rmR(file);
		}
		return testFolder;
	}

	public static File toFile(URL url) throws IOException {
		return new File(new Path(FileLocator.toFileURL(url).getPath()).toOSString());
	}

	private Injector injector;

	protected <T> T get(Class<T> clazz) {
		return getInjector().getInstance(clazz);
	}

	protected Injector getInjector() {
		return injector;
	}

	/**
	 * This implementation returns a DefaultgrafModule.
	 * 
	 * @return
	 */
	protected Module getModule() {
		return new DefaultgrafModule();
	}

	@Before
	public void setUp() throws Exception {
		injector = Guice.createInjector(getModule());
	}
}
