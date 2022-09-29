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
package org.ungtb10d.graf.grafviz;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.log4j.Logger;
import org.ungtb10d.graf.ICancel;
import org.ungtb10d.graf.IRootgraf;
import org.ungtb10d.graf.dot.DotRenderer;
import org.ungtb10d.graf.grafcss.grafCSS;
import org.ungtb10d.graf.utils.ByteArrayOutputStream2;

import com.google.inject.Inject;

/**
 * A grafviz runner.
 * 
 * The grafviz runner executes one of the grafviz executables to produce
 * one of: a PNG, JPG, SVG, or a HTML USEMAP.
 * TODO: possibly extend output to also include PS (this is
 * just a different "-Ttype", but may require attention to handling of fonts).
 * 
 * This grafviz runner assumes that the layout algorithms are available on the
 * execution path of the jvm (i.e. the executables with the same name as the values of
 * the {@link grafvizLayout}).
 * 
 * TODO: ideally, the set of available types:renderers should be discovered at runtime from
 * the environment, and then bound in the runtime guice module.
 * 
 */
public class grafviz implements Igrafviz {
	private IgrafvizConfig config;

	private DotRenderer dotRenderer;

	@Inject
	public grafviz(IgrafvizConfig config, DotRenderer dotRenderer) {
		this.config = config;
		this.dotRenderer = dotRenderer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.impl.dot.Igrafviz#getDotText(org.ungtb10d.graf.Igraf, org.ungtb10d.graf.impl.style.RuleSet)
	 */
	@Override
	public String getDotText(final ICancel cancel, IRootgraf graf, grafCSS defaultCSS, grafCSS... gCSS) {
		ByteArrayOutputStream bufferStream = new ByteArrayOutputStream();
		dotRenderer.write(cancel, bufferStream, graf, defaultCSS, gCSS);
		return bufferStream.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.impl.dot.Igrafviz#getUsemap(org.ungtb10d.graf.Igraf, org.ungtb10d.graf.impl.style.RuleSet,
	 * org.ungtb10d.graf.impl.dot.grafviz.Layout)
	 */
	@Override
	public String getUsemap(final ICancel cancel, grafvizLayout layout, IRootgraf graf, grafCSS defaultStyle,
			grafCSS... styleSheets) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		if(writegrafvizOutput(cancel, stream, grafvizFormat.cmapx, null, layout, graf, defaultStyle, styleSheets) == null)
			return "";
		return stream.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.impl.dot.Igrafviz#getJpgImage(org.ungtb10d.graf.Igraf, org.ungtb10d.graf.impl.style.RuleSet,
	 * org.ungtb10d.graf.impl.dot.grafviz.Layout)
	 */
	@Override
	public byte[] toJPG(final ICancel cancel, grafvizLayout layout, IRootgraf graf, grafCSS defaultStyleSheet,
			grafCSS... styleSheets) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		if(writegrafvizOutput(
			cancel, stream, grafvizFormat.jpg, config.getRenderer(), layout, graf, defaultStyleSheet, styleSheets) == null)
			return null;
		byte[] ret = stream.toByteArray();
		return (ret == null || ret.length < 1)
				? null
				: ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.impl.dot.Igrafviz#getPngImage(org.ungtb10d.graf.Igraf, org.ungtb10d.graf.impl.style.RuleSet,
	 * org.ungtb10d.graf.impl.dot.grafviz.Layout)
	 */
	@Override
	public byte[] toPNG(final ICancel cancel, grafvizLayout layout, IRootgraf graf, grafCSS defaultStyle,
			grafCSS... styleSheets) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		if(writegrafvizOutput(
			cancel, stream, grafvizFormat.png, config.getRenderer(), layout, graf, defaultStyle, styleSheets) == null)
			return null;
		byte[] ret = stream.toByteArray();
		return (ret == null || ret.length < 1)
				? null
				: ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.impl.dot.Igrafviz#getSvgImage(org.ungtb10d.graf.Igraf, org.ungtb10d.graf.impl.style.RuleSet,
	 * org.ungtb10d.graf.impl.dot.grafviz.Layout)
	 */
	@Override
	public String toSVG(final ICancel cancel, grafvizLayout layout, IRootgraf graf, grafCSS defaultStyle,
			grafCSS... styleSheets) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		if(writegrafvizOutput(
			cancel, stream, grafvizFormat.svg, config.getRenderer(), layout, graf, defaultStyle, styleSheets) == null)
			return null;
		return stream.toString();
	}

	@Override
	public OutputStream writegrafvizOutput(final ICancel cancel, final OutputStream output, grafvizFormat format,
			grafvizRenderer renderer, //
			grafvizLayout layout, //
			final InputStream dotData
	// final byte[] dotData //
	) {
		Process p;
		// grafviz -T format:renderer is something like -T png:cairo
		// Construct renderer string (':renderer' after the format) if renderer is specified - generally
		// a bad idea as you need to know what renderers are available.
		//
		String r = (renderer == null || renderer == grafvizRenderer.standard)
				? ""
				: ":" + config.getRenderer().toString();
		try {

			p = Runtime.getRuntime().exec(layout.toString() + " -T" + format.toString() + r);
		}
		catch(IOException e1) {
			Logger log = Logger.getLogger(grafviz.class);
			log.error("Could not execute " + layout.toString() + " -T" + format.toString() + r);
			// TODO: need to close the process streams or they may remain open
			return null;
		}
		final OutputStream out = new BufferedOutputStream(p.getOutputStream());
		final InputStream in = new BufferedInputStream(p.getInputStream());
		final InputStream err = new BufferedInputStream(p.getErrorStream());

		// use the stream connected to the command's stdin
		/*
		 * A thread is needed when producing output to grafviz. If there is something
		 * wrong grafviz will not read the input and the writer will block.
		 */
		Thread writer = new Thread() {
			@Override
			public void run() {
				// print the dot output on the stream
				try {
					byte[] buf = new byte[1024];
					int length = 0;
					while((length = dotData.read(buf)) != -1)
						out.write(buf, 0, length); // dotOutput.toByteArray());

					// close the stream, or grafviz will read for ever
					out.close();
				}
				catch(IOException e) {
					Logger log = Logger.getLogger(grafviz.class);
					log.error("error closing output stream to grafviz ", e);
				}
			}
		};

		writer.start();

		class ReaderThread extends Thread {
			public boolean done = false;

			@Override
			public void run() {
				byte[] buffer = new byte[1024];

				try {
					int read = in.read(buffer);
					while(read != -1) {
						try {
							output.write(buffer, 0, read);
						}
						catch(Throwable e) {
							Logger log = Logger.getLogger(grafviz.class);
							log.error("Exception while writing result read from grafviz", e);
						}
						cancel.assertContinue();
						read = in.read(buffer);
					}
					// close the input - we are finished
					in.close();
					// flag that we are done reading in a normal way.
					done = true;
				}
				catch(IOException e) {
					Logger log = Logger.getLogger(grafviz.class);
					log.error("error reading output from grafviz ", e);
				}
			}
		}
		;
		ReaderThread reader = new ReaderThread();
		// start reading the output from grafviz
		reader.start();

		final ByteArrayOutputStream eout = new ByteArrayOutputStream();
		Thread errorHandler = new Thread() {
			@Override
			public void run() {
				byte[] buffer = new byte[512];
				try {
					int read = err.read(buffer);
					while(read != -1) {
						eout.write(buffer, 0, read);
						read = err.read(buffer);
					}

					Logger log = Logger.getLogger(grafviz.class);
					// if there was no output this could be because EOF was reached due to normal end.
					String tmp = eout.toString();
					if(tmp != null && tmp.length() > 0)
						log.error("grafviz error: " + tmp);
				}
				catch(IOException e) {
					// an IO Exception here is the expected outcome
					// we are reading stderr, and there should be no output to read
					// so this thread should (in the normal case) just hang on the read,
					// and then be interrupted when the stderr is closed as part of normal
					// processing.
				}
			}
		};
		errorHandler.start();
		try {
			cancel.assertContinue();
			// wait for process to finish
			p.waitFor();
			// wait until everything has been read from process
			cancel.assertContinue();
			reader.join();

			cancel.assertContinue();

			// TODO: it may be needed to check the error output, if it is an error or a warning
			// warnings could be ignored - now they also terminate the output if the warning occurs before
			// the writer is done.
			if(p.exitValue() != 0) {
				throw new grafvizException(eout.toString());
			}

		}
		catch(InterruptedException e) {
			Logger log = Logger.getLogger(grafviz.class);
			log.error("grafviz reading interupted");
			return null;
		}
		finally {
			// close ALL input
			// This terminates the reader and writer as the pipe is forcefully
			// closed on them - errors may occur after input has already been
			// closed or after output has been closed - make sure all three
			// are closed.
			try {
				in.close();
			}
			catch(IOException ioe) {
			}
			try {
				out.close();
			}
			catch(IOException ioe) {
			}
			try {
				err.close();
			}
			catch(IOException ioe) {
			}

		}
		if(!reader.done)
			return null;
		return output;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.impl.dot.Igrafviz#getgrafvizOutput(java.io.OutputStream, org.ungtb10d.graf.impl.dot.grafviz.Format,
	 * org.ungtb10d.graf.impl.dot.grafviz.Renderer, org.ungtb10d.graf.Igraf, org.ungtb10d.graf.impl.style.RuleSet,
	 * org.ungtb10d.graf.impl.dot.grafviz.Layout)
	 */
	@Override
	public OutputStream writegrafvizOutput(ICancel cancel, OutputStream output, grafvizFormat format,
			grafvizRenderer renderer, grafvizLayout layout, IRootgraf graf, grafCSS defaultStyleSheet,
			grafCSS... styleSheets) {
		// Produce the dot output to a buffer (at one point we could not run this in a thread because JBoss Seam
		// got confused over context - maybe possible to revisit
		//
		final ByteArrayOutputStream2 dotOutput = new ByteArrayOutputStream2();
		dotRenderer.write(cancel, dotOutput, graf, defaultStyleSheet, styleSheets);
		return writegrafvizOutput(cancel, output, format, renderer, layout, dotOutput.toInputStream(false));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.grafviz.Igrafviz#writePNG(java.io.OutputStream, org.ungtb10d.graf.grafviz.grafvizLayout,
	 * org.ungtb10d.graf.IRootgraf, org.ungtb10d.graf.grafcss.grafCSS, org.ungtb10d.graf.grafcss.grafCSS[])
	 */
	@Override
	public boolean writePNG(ICancel cancel, OutputStream output, grafvizLayout layout, IRootgraf graf,
			grafCSS defaultStyle, grafCSS... styleSheets) {
		if(writegrafvizOutput(
			cancel, output, grafvizFormat.png, config.getRenderer(), layout, graf, defaultStyle, styleSheets) == null)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.impl.dot.Igrafviz#getSvgImage(java.io.OutputStream, org.ungtb10d.graf.Igraf,
	 * org.ungtb10d.graf.impl.style.RuleSet, org.ungtb10d.graf.impl.dot.grafviz.Layout)
	 */
	@Override
	public boolean writeSVG(ICancel cancel, OutputStream output, grafvizLayout layout, IRootgraf graf,
			grafCSS defaultStyle, grafCSS... styleSheets) {
		if(writegrafvizOutput(
			cancel, output, grafvizFormat.svg, config.getRenderer(), layout, graf, defaultStyle, styleSheets) == null)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.impl.dot.Igrafviz#getSvgzImage(java.io.OutputStream, org.ungtb10d.graf.Igraf,
	 * org.ungtb10d.graf.impl.style.RuleSet, org.ungtb10d.graf.impl.dot.grafviz.Layout)
	 */
	@Override
	public boolean writeSVGZ(ICancel cancel, OutputStream output, grafvizLayout layout, IRootgraf graf,
			grafCSS defaultStyle, grafCSS... styleSheets) {
		try {
			GZIPOutputStream stream = new GZIPOutputStream(output);
			if(writegrafvizOutput(
				cancel, stream, grafvizFormat.svg, config.getRenderer(), layout, graf, defaultStyle, styleSheets) == null)
				return false;
			stream.finish();
			stream.flush();
		}
		catch(IOException e) {
			return false;
		}
		return true;
	}
}
