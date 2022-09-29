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
package org.ungtb10d.graf.graphviz;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.ungtb10d.graf.ICancel;
import org.ungtb10d.graf.IRootgraf;
import org.ungtb10d.graf.grafcss.grafCSS;

import com.google.inject.BindingAnnotation;

/**
 * Interface for a graphviz runner.
 * 
 */
public interface Igraphviz {

	/**
	 * Annotation to use for the Dot Output Empty string.
	 * 
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.FIELD, ElementType.PARAMETER })
	@BindingAnnotation
	public @interface SVGOutputFilter {
	}

	/**
	 * Returns a string with the generated dot output (which is normally fed to graphviz layout).
	 * This method is useful for testing graf -> graphviz -> DotRenderer -> dot-text.
	 * 
	 * @param cancel
	 *            An indicator of cancellation requested by user. Should be periodically checked and {@link CancellationException}
	 *            thrown on detection
	 *            of cancellation.
	 * @param graf
	 *            The root graf to render.
	 * @param defaultCSS
	 *            The graf CSS used to compute the dot defaults.
	 * @param styleSheets
	 *            List of graf CSS to apply to dot instances - have no effect on the dot defaults.
	 * 
	 * @return the dot output as text before graphviz layout processing
	 */
	public String getDotText(ICancel cancel, IRootgraf graf, grafCSS defaultCSS, grafCSS... styleSheets);

	/**
	 * Get HTML fragment that describes a clickable map with URL's from
	 * the graf.
	 * 
	 * @param cancel
	 *            An indicator of cancellation requested by user. Should be periodically checked and {@link CancellationException}
	 *            thrown on detection
	 *            of cancellation.
	 * @param layout
	 *            The graphviz layout algorithm to use.
	 * @param graf
	 *            The root graf to render.
	 * @param defaultCSS
	 *            The graf CSS used to compute the dot defaults.
	 * @param styleSheets
	 *            List of graf CSS to apply to dot instances - have no effect on the dot defaults.
	 * 
	 * @return an empty string if there where errors
	 */
	public String getUsemap(ICancel cancel, graphvizLayout layout, IRootgraf graf, grafCSS defaultStyle,
			grafCSS... styleSheets);

	/**
	 * Returns the image as a JPG image. The image is quite heavily compressed and shows many compression
	 * artifacts. This at least when using graphviz under windows - the result is quite ugly.
	 * 
	 * @param cancel
	 *            An indicator of cancellation requested by user. Should be periodically checked and {@link CancellationException}
	 *            thrown on detection
	 *            of cancellation.
	 * @param layout
	 *            The graphviz layout algorithm to use.
	 * @param graf
	 *            The root graf to render.
	 * @param defaultCSS
	 *            The graf CSS used to compute the dot defaults.
	 * @param styleSheets
	 *            List of graf CSS to apply to dot instances - have no effect on the dot defaults.
	 * 
	 * @return null if there where errors (this image may not be in jpg format!)
	 */
	public byte[] toJPG(ICancel cancel, graphvizLayout layout, IRootgraf graf, grafCSS defaultStyleSheet,
			grafCSS... styleSheets);

	/**
	 * Returns output as a PNG image.
	 * 
	 * @param cancel
	 *            An indicator of cancellation requested by user. Should be periodically checked and {@link CancellationException}
	 *            thrown on detection
	 *            of cancellation.
	 * @param layout
	 *            The graphviz layout algorithm to use.
	 * @param graf
	 *            The root graf to render.
	 * @param defaultCSS
	 *            The graf CSS used to compute the dot defaults.
	 * @param styleSheets
	 *            List of graf CSS to apply to dot instances - have no effect on the dot defaults.
	 * 
	 * @return a "noImage" image if there where errors
	 */
	public byte[] toPNG(ICancel cancel, graphvizLayout layout, IRootgraf graf, grafCSS defaultStyle,
			grafCSS... styleSheets);

	/**
	 * Return output as SVG text
	 * TODO: the svg output does not render correctly in FF - it needs post processing where fontsizes should
	 * be expressed using a pixel value (i.e. "8px" instead of a floating point value ("8.00"). Even when using
	 * pixel based specification, the font is too big - the value "8.00" should be translated into "7px".
	 * For the person that is interested, it would be quite simple to filter the returned text.
	 * 
	 * @param cancel
	 *            An indicator of cancellation requested by user. Should be periodically checked and {@link CancellationException}
	 *            thrown on detection
	 *            of cancellation.
	 * @param layout
	 *            The graphviz layout algorithm to use.
	 * @param graf
	 *            The root graf to render.
	 * @param defaultCSS
	 *            The graf CSS used to compute the dot defaults.
	 * @param styleSheets
	 *            List of graf CSS to apply to dot instances - have no effect on the dot defaults.
	 * 
	 * @return null if there where errors
	 */
	public String toSVG(ICancel cancel, graphvizLayout layout, IRootgraf graf, grafCSS defaultStyle,
			grafCSS... styleSheets);

	/**
	 * Generic routine to produce output from graphviz.
	 * Input data (dot text notation) is passed in a buffer.
	 * This is fed to graphviz stdin, and the data from stdout is returned.
	 * graphviz will print output on stderr if there are errors, but will not exit unless the input stream
	 * is closed. This routine handles the needed multi-threading to handle these cases.
	 * 
	 * @param cancel
	 *            An indicator of cancellation requested by user. Should be periodically checked and {@link CancellationException}
	 *            thrown on detection
	 *            of cancellation.
	 * @param format
	 *            the wanted output format (PNG, JPG, SVG, etc.)
	 * @param renderer
	 *            the renderer to use (or null for default and non raster formats) - e.g. {@link graphvizRenderer#quartz}
	 * @param layout
	 *            the layout algorithm to use - e.g. {@link graphvizLayout#dot}.
	 * @param dotData
	 *            the dot text input
	 * 
	 * @return the output stream with the data, or null if there was an error
	 */
	OutputStream writegraphvizOutput(ICancel cancel, OutputStream output, graphvizFormat format,
			graphvizRenderer renderer, graphvizLayout layout, InputStream dotData);

	// byte[] dotData);

	/**
	 * Generic routine to produce output from graphviz.
	 * Input data is collected in a buffer (as the routines called to generate the output must run on
	 * the main thread (seam does not find contexts for EL evaluation otherwise).
	 * This is fed to graphviz stdin, and the data from stdout is returned.
	 * graphviz will print output on stderr if there are errors, but will not exit unless the input stream
	 * is closed. This routine handles the needed multithreading to handle these cases.
	 * 
	 * @param cancel
	 *            An indicator of cancellation requested by user. Should be periodically checked and {@link CancellationException}
	 *            thrown on detection
	 *            of cancellation.
	 * @param format
	 *            the wanted output format (PNG, JPG, SVG, etc.)
	 * @param renderer
	 *            the renderer to use (or null for default and non raster formats) - e.g. {@link graphvizRenderer#quartz}
	 * @param layout
	 *            the layout algorithm to use - e.g. {@link graphvizLayout#dot}.
	 * @param graf
	 *            the graf to render
	 * @param defaultStyleSheet
	 *            - the CSS used to produce the prototype styles
	 * @param styleSheets
	 *            - additional style sheets
	 * 
	 * @return the output stream with the data, or null if there was an error
	 */
	public OutputStream writegraphvizOutput(ICancel cancel, OutputStream output, graphvizFormat format,
			graphvizRenderer renderer, graphvizLayout layout, IRootgraf graf, grafCSS defaultStyleSheet,
			grafCSS... styleSheets);

	/**
	 * Writes output as PNG to given stream.
	 * 
	 * @param cancel
	 *            An indicator of cancellation requested by user. Should be periodically checked and {@link CancellationException}
	 *            thrown on detection
	 *            of cancellation.
	 * @param output
	 *            the output stream where the PNG output should be written
	 * @param layout
	 *            the layout algorithm to use - e.g. {@link graphvizLayout#dot}.
	 * @param graf
	 *            the graf to render
	 * @param defaultStyleSheet
	 *            - the CSS used to produce the prototype styles
	 * @param styleSheets
	 *            - additional style sheets
	 * 
	 * @return false if there where errors, true on success
	 */
	public boolean writePNG(ICancel cancel, OutputStream output, graphvizLayout layout, IRootgraf graf,
			grafCSS defaultStyle, grafCSS... styleSheets);

	/**
	 * Writes output as SVG text to given stream.
	 * 
	 * @param cancel
	 *            An indicator of cancellation requested by user. Should be periodically checked and {@link CancellationException}
	 *            thrown on detection
	 *            of cancellation.
	 * @param output
	 *            the output stream where the GZIP output should be written
	 * @param layout
	 *            the layout algorithm to use - e.g. {@link graphvizLayout#dot}.
	 * @param graf
	 *            the graf to render
	 * @param defaultStyle
	 *            the CSS used to produce the prototype styles
	 * @param styleSheets
	 *            additional style sheets
	 * 
	 * @return false if there where errors, true on success
	 */
	public boolean writeSVG(ICancel cancel, OutputStream output, graphvizLayout layout, IRootgraf graf,
			grafCSS defaultStyle, grafCSS... styleSheets);

	/**
	 * Writes output as SVGZ text to the given stream.
	 * Despite the fact that this method exists and is very convenient, the graphviz SVG output is not
	 * really UTF-8 even if the header claims it to be. graphviz reads the user name and includes that in
	 * a comment, and this name is in the encoding used on the platform where graphviz is running.
	 * It is a bad idea to GZIP that as it is not possible to convert it to UTF-8 once the data is
	 * deflated. Instead, get the regular SVG output and ensure it is in UTF-8 before deflating.
	 * 
	 * @param cancel
	 *            An indicator of cancellation requested by user. Should be periodically checked and {@link CancellationException}
	 *            thrown on detection
	 *            of cancellation.
	 * @param output
	 *            the output stream where the GZIP output should be written
	 * @param layout
	 *            the layout algorithm to use - e.g. {@link graphvizLayout#dot}.
	 * @param graf
	 *            the graf to render
	 * @param defaultStyle
	 *            the CSS used to produce the prototype styles
	 * @param styleSheets
	 *            additional style sheets
	 * 
	 * @return false if there where errors, true on success
	 */
	public boolean writeSVGZ(ICancel cancel, OutputStream output, graphvizLayout layout, IRootgraf graf,
			grafCSS defaultStyle, grafCSS... styleSheets);

}
