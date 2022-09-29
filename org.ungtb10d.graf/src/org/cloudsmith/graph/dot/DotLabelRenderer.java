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
import java.io.PrintStream;
import java.util.Collection;
import java.util.Set;

import org.ungtb10d.graf.ICancel;
import org.ungtb10d.graf.IgrafElement;
import org.ungtb10d.graf.ILabeledgrafElement;
import org.ungtb10d.graf.ITable;
import org.ungtb10d.graf.ITableCell;
import org.ungtb10d.graf.ITableRow;
import org.ungtb10d.graf.elements.grafCell;
import org.ungtb10d.graf.elements.grafRow;
import org.ungtb10d.graf.elements.grafTable;
import org.ungtb10d.graf.grafcss.grafCSS;
import org.ungtb10d.graf.grafcss.StyleSet;
import org.ungtb10d.graf.style.Alignment;
import org.ungtb10d.graf.style.IStyle;
import org.ungtb10d.graf.style.IStyleFactory;
import org.ungtb10d.graf.style.IStyleVisitor;
import org.ungtb10d.graf.style.Span;
import org.ungtb10d.graf.style.StyleFactory;
import org.ungtb10d.graf.style.StyleType;
import org.ungtb10d.graf.style.StyleVisitor;
import org.ungtb10d.graf.style.VerticalAlignment;
import org.ungtb10d.graf.style.labels.DynamicLabelTemplate;
import org.ungtb10d.graf.style.labels.ILabelTemplate;
import org.ungtb10d.graf.style.labels.LabelCell;
import org.ungtb10d.graf.style.labels.LabelMatrix;
import org.ungtb10d.graf.style.labels.LabelRow;
import org.ungtb10d.graf.style.labels.LabelStringTemplate;
import org.ungtb10d.graf.style.labels.LabelTable;
import org.ungtb10d.graf.utils.Counter;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Renders dot labels.
 * 
 */
@Singleton
public class DotLabelRenderer {

	@Inject
	IStyleFactory styles;

	private final String emptyString;

	@Inject
	public DotLabelRenderer(@DotRenderer.EmptyString String emptyString) {
		this.emptyString = emptyString;
	}

	private grafTable createTable(ILabeledgrafElement thegrafElement, LabelTable templateTable, ICancel cancel) {
		// Context methodContext = Contexts.getMethodContext();
		// methodContext.set("element", m_ge);

		// create the grafTable using a styleClass that is possibly set using
		// EL
		Set<String> tmp = templateTable.getStyleClasses(thegrafElement);

		grafTable gt = new grafTable(tmp);

		// set parent so containment selection for styles work
		gt.setParentElement(thegrafElement);

		// For all rows in the template, create a grafRow
		for(LabelRow r : templateTable.getRows()) {
			cancel.assertContinue();

			tmp = r.getStyleClasses(thegrafElement);
			grafRow gr = r.isSeparator()
					? new grafRow.SeparatorRow()
					: new grafRow(tmp);
			gt.addRow(gr);

			// for all cells in the template, create a grafCell
			for(LabelCell c : r.getCells()) {
				tmp = c.getStyleClass(thegrafElement);
				ILabelTemplate template = c.getValue(thegrafElement);
				if(template == null)
					template = new LabelStringTemplate("");

				// resolve dynamic template to depth 100
				for(int i = 0; template instanceof DynamicLabelTemplate; i++) {
					if(i > 100)
						throw new IllegalArgumentException("Dynamic Templates nested too deep > 100");
					template = ((DynamicLabelTemplate) template).getTemplate(thegrafElement);
				}

				grafCell gc = null;
				if(c.isSeparator()) {
					gc = new grafCell.SeparatorCell();
				}
				else if(template instanceof LabelStringTemplate) {
					String val = ((LabelStringTemplate) template).getTemplateString(thegrafElement);
					val = (val == null)
							? ""
							: val;

					gc = new grafCell(val, tmp);
				}
				else if(template instanceof LabelTable) {
					gc = new grafCell("", tmp);
					gc.setTableContent(createTable(thegrafElement, (LabelTable) template, cancel));
				}
				// TODO: MUST BE ABLE TO PICK UP INSTANCE STYLES FOR LABEL CELL

				// If the labelCell has instance styles, make sure they are included
				StyleSet instanceSet = c.getStyles();
				StyleSet styleMap = new StyleSet();
				styleMap.add(instanceSet);

				// If the label cell has a span that is not 1x1, set that as instance style
				// of the rendered IgrafCell.
				Span span = c.getSpan();
				if(span != null && span != Span.SPAN_1x1) {
					if(span.getRowspan() != 1)
						styleMap.put(new StyleFactory.RowSpan(span.getRowspan()));
					if(span.getColspan() != 1)
						styleMap.put(new StyleFactory.ColSpan(span.getColspan()));
				}
				// If there were any instance styles - apply them
				if(styleMap.getStyles().size() > 0)
					gc.setStyles(styleMap);
				gr.addCell(gc);
			}
		}
		return gt;
	}

	private String emptyString(String x) {
		if(x == null || x.length() == 0)
			x = emptyString;
		return x;
	}

	/**
	 * Escapes characters that are unsafe in a dot table label.
	 * 
	 * @param s
	 * @return
	 */
	private String escapeUnsafe(String s) {
		s = s.replace("&", "&amp;");
		s = s.replace("<", "&lt;");
		s = s.replace(">", "&gt;");
		s = s.replace("\n", "<BR/>");
		return s;
	}

	/**
	 * 
	 * @param ge
	 * @param labelnode
	 * @return a vector of three strings - the parsed element attributes, attributes for font, and attribute for rendered.
	 */
	private String[] parsegrafTableAttributes(final ILabeledgrafElement ge, final IgrafElement labelnode,
			grafCSS styleRules, ICancel cancel) {
		final String[] result = new String[3];
		// in case there are no attributes to set
		result[0] = "";
		result[1] = "";
		result[2] = "true";

		final ByteArrayOutputStream elementText = new ByteArrayOutputStream();
		final ByteArrayOutputStream fontText = new ByteArrayOutputStream();
		final PrintStream elementStream = new PrintStream(elementText);
		final PrintStream fontStream = new PrintStream(fontText);

		// get the styling for the gt
		Collection<IStyle<?>> s = styleRules.collectStyles(labelnode, cancel).getStyles();
		if(s == null || s.size() < 1)
			return result; // no attributes to set

		final Counter count = new Counter();
		final Counter fontCount = new Counter();

		IStyleVisitor visitor = new StyleVisitor() {
			@Override
			public void align(Alignment x) {
				elementStream.printf(" ALIGN=\"%s\"", x);
			}

			@Override
			public void backgroundColor(String x) {
				elementStream.printf(" BGCOLOR=\"%s\"", x);
			}

			@Override
			public void borderWidth(int x) {
				elementStream.printf(" BORDER=\"%s\"", x);
			}

			@Override
			public void cellBorderWidth(int x) {
				if(!(labelnode instanceof ITable))
					throw new IllegalArgumentException("cellBorderWidth is not a supported style attribute of a - " +
							labelnode.getClass() + ". Use borderWidth on a cell.");
				elementStream.printf(" CELLBORDER=\"%s\"", x);
			}

			@Override
			public void cellPadding(int x) {
				if(!(labelnode instanceof ITable || labelnode instanceof ITableCell))
					throw new IllegalArgumentException("cellPadding is not a supported style attribute of a " +
							labelnode.getClass());
				elementStream.printf(" CELLPADDING=\"%s\"", x);
			}

			@Override
			public void cellSpacing(int x) {
				elementStream.printf(" CELLSPACING=\"%s\"", x);
			}

			@Override
			public void color(String x) {
				count.decrement(); // don't count as element's attribute
				fontStream.printf(" COLOR=\"%s\"", x);
			}

			@Override
			public void colSpan(int x) {
				if(!(labelnode instanceof ITableCell))
					throw new IllegalArgumentException("colSpan is not a supported style attribute of a " +
							labelnode.getClass());
				elementStream.printf(" COLSPAN=\"%s\"", x);
			}

			@Override
			public void fixedSize(boolean x) {
				elementStream.printf(" FIXEDSIZE=\"%s\"", x);
			}

			@Override
			public void fontFamily(String x) {
				count.decrement(); // don't count as element's attribute
				fontStream.printf(" FACE=\"%s\"", x);
			}

			@Override
			public void fontSize(int x) {
				count.decrement(); // don't count as element's attribute
				fontStream.printf(" POINT-SIZE=\"%d\"", x);
				fontCount.increment();
			}

			/**
			 * height is a floating point number as some heights are specified as points and need the decimal
			 * the stylesheet should contain an integral number - but better be safe and round it before
			 * using "0.5" would otherwise be size "0".
			 * The right thing to do would be to have just one measuring system and actually transform
			 * printers points 1/72" to "pixels" - but this requires resolution of the output device to be accurate
			 * and is just too much bother - simply specify integral numbers in the stylesheet and know that it is
			 * interpreted as "pixels" (or whatever graphviz things the integral number means.
			 */
			@Override
			public void height(double x) {
				elementStream.printf(" HEIGHT=\"%s\"", Math.round(x));
			}

			@Override
			public void href(String x) {
				if(x == null || x.length() < 1)
					count.decrement();
				else
					elementStream.printf(" HREF=\"%s\"", x);
			}

			@Override
			public void port(String x) {
				elementStream.printf(" PORT=\"%s\"", x);
			}

			@Override
			public void rendered(boolean x) {
				result[2] = String.valueOf(x);
				count.decrement(); // no output
			}

			@Override
			public void rowSpan(int x) {
				if(!(labelnode instanceof ITableCell))
					throw new IllegalArgumentException("rowSpan is not a supported style attribute of a " +
							labelnode.getClass());
				elementStream.printf(" ROWSPAN=\"%s\"", x);
			}

			@Override
			public void target(String x) {
				elementStream.printf(" TARGET=\"%s\"", x);
			}

			@Override
			public void tooltip(String x) {
				elementStream.printf(" TOOLTIP=\"%s\"", emptyString(x));
			}

			@Override
			public void unsupported(StyleType type) {
				throw new IllegalArgumentException(type + "is not a supported style for element of class " +
						labelnode.getClass().toString());
			}

			@Override
			public void verticalAlign(VerticalAlignment x) {
				elementStream.printf(" VALIGN=\"%s\"", x);
			}

			/**
			 * @see #height regarding rounding
			 */
			@Override
			public void width(double x) {
				elementStream.printf(" WIDTH=\"%s\"", Math.round(x));
			}
		};

		for(IStyle<?> style : s) {
			style.visit(ge, visitor);
			count.increment();
			cancel.assertContinue();
		}
		result[0] = elementText.toString();
		result[1] = fontText.toString();
		return result;
	}

	/**
	 * Prints the label on the form label=<
	 * <TABLE>
	 * ...
	 * </TABLE>>
	 * - returns true if a label was printed.
	 * 
	 * @param ge
	 * @param printComma
	 *            , if true a comma is printed before the label.
	 * @return
	 */
	public boolean print(PrintStream out, ILabeledgrafElement thegrafElement, ILabelTemplate labelTemplate,
			boolean printComma, char sepChar, grafCSS gcss, ICancel cancel) {
		// resolve dynamic template to depth 100
		for(int i = 0; labelTemplate instanceof DynamicLabelTemplate; i++) {
			if(i > 100)
				throw new IllegalArgumentException("Dynamic Templates nested too deep > 100");
			labelTemplate = ((DynamicLabelTemplate) labelTemplate).getTemplate(thegrafElement);
		}

		if(labelTemplate instanceof LabelStringTemplate)
			return printStringLabel(
				out, thegrafElement, ((LabelStringTemplate) labelTemplate).getTemplateString(thegrafElement),
				printComma, sepChar);
		else if(labelTemplate instanceof LabelMatrix)
			return printMatrix(out, thegrafElement, (LabelMatrix) labelTemplate, printComma, sepChar, gcss, cancel);
		return printTable(out, thegrafElement, (LabelTable) labelTemplate, printComma, sepChar, gcss, cancel);

	}

	private void printgrafCell(PrintStream out, ILabeledgrafElement ge, ITableCell gc, grafCSS gcss, ICancel cancel) {
		if(gc.isSeparator()) {
			out.print("<VR/>");
			return;
		}
		String[] p = parsegrafTableAttributes(ge, gc, gcss, cancel);
		// if "rendered" == false, do not output anything
		if(p[2].toLowerCase().equals("false"))
			return;

		ITable gt = gc.getTableContents();
		String cellText = gc.getValue();

		// if there are font attributes - output that around the text in the cell
		// (unless text is empty string = graphviz error).
		boolean withFontData = gt == null && p[1] != null && p[1].length() > 0 && cellText.length() > 0;
		out.printf("<TD %s>", p[0]);
		if(withFontData)
			out.printf("<FONT %s>", p[1]);

		// the value has already been interpolated when the grafCell was set up
		// so just output the table or a string here.
		if(gt != null)
			printgrafTable(out, ge, gt, gcss, cancel);
		else
			out.print(escapeUnsafe(cellText));
		if(withFontData)
			out.print("</FONT>");
		out.print("</TD>");
	}

	private void printgrafRow(PrintStream out, ILabeledgrafElement ge, ITableRow gr, grafCSS gcss, ICancel cancel) {
		if(gr.isSeparator()) {
			out.print("<HR/>");
			return;
		}

		out.print("<TR>");
		for(ITableCell gc : gr.getCells())
			printgrafCell(out, ge, gc, gcss, cancel);
		out.print("</TR>");
	}

	private boolean printgrafTable(PrintStream out, ILabeledgrafElement thegrafElement, ITable gt, grafCSS gcss,
			ICancel cancel) {
		String[] p = parsegrafTableAttributes(thegrafElement, gt, gcss, cancel);
		// if "rendered" == false, do not output anything
		if(p[2].toLowerCase().equals("false"))
			return false;

		// if there are font attributes - output that around the table
		//
		boolean withFontData = p[1] != null && p[1].length() > 0;
		// out.print("<");
		if(withFontData)
			out.printf("<FONT %s>", p[1]);
		out.printf("<TABLE %s>", p[0]);
		for(ITableRow r : gt.getRows())
			printgrafRow(out, thegrafElement, r, gcss, cancel);
		out.print("</TABLE>");
		if(withFontData)
			out.print("</FONT>");
		// out.print(">");
		return true;
	}

	private boolean printMatrix(PrintStream out, ILabeledgrafElement thegrafElement, LabelMatrix templateMatrix,
			boolean printComma, char sepChar, grafCSS gcss, ICancel cancel) {

		// create the grafTable using a styleClass that is possibly set using
		// EL
		String tmp = templateMatrix.getStyleClass(thegrafElement);

		grafTable gt = new grafTable(tmp);

		// set parent so containment selection for styles work
		gt.setParentElement(thegrafElement);
		// set a port "pt" on the table itself so it can be pointed to
		gt.setStyles(styles.port("pt"));

		// For all rows in the template, create a grafRow
		for(int r = 0; r < templateMatrix.getRows(); r++) {
			tmp = templateMatrix.getRowStyleClass(thegrafElement);
			grafRow gr = new grafRow(tmp);
			gt.addRow(gr);
			// for all cells in the template, create a grafCell
			for(int c = 0; c < templateMatrix.getColumns(); c++) {
				tmp = templateMatrix.getCellStyleClass(thegrafElement);
				String val = templateMatrix.getValue(r, c);
				grafCell gc = new grafCell(val, tmp);
				gr.addCell(gc);

				StyleSet styleMap = new StyleSet();
				styleMap.put(styles.port("p" + templateMatrix.getValue(r, c)));
				gc.setStyles(styleMap);

			}
		}
		// Now armed with the label graf - we need to visit those nodes, get the styling of them, and provide
		// output!
		//
		String[] p = parsegrafTableAttributes(thegrafElement, gt, gcss, cancel);
		// if "rendered" == false, do not output anything
		if(p[2].toLowerCase().equals("false"))
			return false;

		out.printf("%slabel=", printComma
				? sepChar + " "
				: "");

		return printgrafTable(out, thegrafElement, gt, gcss, cancel);
	}

	private boolean printStringLabel(PrintStream out, ILabeledgrafElement thegrafElement, String simpleTemplate,
			boolean printComma, char sepChar) {
		String tmp = simpleTemplate; // already interpolated

		// if there is a result output that as the label without any styling.
		if(tmp != null && tmp.length() < 1)
			return false;
		out.printf("%slabel=\"", printComma
				? sepChar + " "
				: "");
		out.print(tmp); // use print to preserve /n
		out.print("\"");
		return true;
	}

	/**
	 * Print the top level table label.
	 * 
	 * @param out
	 * @param thegrafElement
	 * @param templateTable
	 * @param printComma
	 * @param sepChar
	 * @param gcss
	 * @param cancel
	 * @return
	 */
	private boolean printTable(PrintStream out, ILabeledgrafElement thegrafElement, LabelTable templateTable,
			boolean printComma, char sepChar, grafCSS gcss, ICancel cancel) {
		grafTable gt = createTable(thegrafElement, templateTable, cancel);

		// Now armed with the label graf - we need to visit those nodes, get the styling of them, and provide
		// output!
		//
		String[] p = parsegrafTableAttributes(thegrafElement, gt, gcss, cancel);
		// if "rendered" == false, do not output anything
		if(p[2].toLowerCase().equals("false"))
			return false;

		out.printf("%slabel=", printComma
				? sepChar + " "
				: "");

		out.print("<");
		boolean result = printgrafTable(out, thegrafElement, gt, gcss, cancel);
		out.print(">");
		return result;
	}

}
