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
package org.ungtb10d.graf.grafcss;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.ungtb10d.graf.IgrafElement;
import org.ungtb10d.graf.ILabeledgrafElement;
import org.ungtb10d.graf.style.labels.ILabelTemplate;
import org.ungtb10d.graf.style.labels.LabelStringTemplate;
import org.ungtb10d.graf.style.labels.LabelTable;
import org.ungtb10d.graf.utils.Base64;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import com.google.inject.Singleton;

/**
 * A FunctionFactory producing values that are dynamically produced when applying a style to
 * a graf element.
 * 
 */
@Singleton
public class FunctionFactory implements IFunctionFactory {

	private static class EmptyLabel implements Function<IgrafElement, Boolean> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.google.common.base.Function#apply(java.lang.Object)
		 */
		@Override
		public Boolean apply(IgrafElement ge) {
			if(!(ge instanceof ILabeledgrafElement))
				return false;
			String item = ((ILabeledgrafElement) ge).getLabel();
			return item == null || item.length() == 0;
		}

	}

	private static class EmptyLabelData implements Function<IgrafElement, Boolean> {

		private Object key;

		public EmptyLabelData(Object key) {
			this.key = key;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.google.common.base.Function#apply(java.lang.Object)
		 */
		@Override
		public Boolean apply(IgrafElement ge) {
			if(!(ge instanceof ILabeledgrafElement))
				return false;
			String item = ((ILabeledgrafElement) ge).getUserData().get(key);
			return item == null || item.length() == 0;
		}

	}

	/**
	 * A Function that translates a IgrafElement into a base64 encoded string containing
	 * id="ID" class="CLASS" where:
	 * <ul>
	 * <li>ID is the user data {@link #ID_KEY} value from the element, or if missing the fully qualified id of the given element.</li>
	 * <li>CLASS</li> is the type of element and the style class of the given element</li>
	 * </ul>
	 * The intention is to combine the user of this function to set the id style of
	 * an element and use a SVG post processor that replaces the generated id="..." class="..."
	 * sequence with that encoded in what this function produces.
	 * 
	 * @author henrik
	 * 
	 */
	public static class IdClassReplacerFunction implements Function<IgrafElement, String> {

		@Override
		public String apply(IgrafElement from) {

			String idString = from.getUserData(ID_KEY);
			if(idString == null)
				idString = computeID(from);
			String allStyleClasses = from.getAllStyleClasses();
			StringBuilder builder = new StringBuilder(idString.length() + allStyleClasses.length() + 20);
			builder.append("id=\"");
			builder.append(idString);
			builder.append("\" class=\"");
			builder.append(from.getElementType()); // e.g. "vertex", "edge", etc.
			builder.append(" ");
			builder.append(allStyleClasses);
			builder.append("\"");
			try {
				return "base64:" + Base64.byteArrayToBase64(builder.toString().getBytes("UTF8"));
			}
			catch(UnsupportedEncodingException e) {
				throw new IllegalStateException(e);
			}
		}

		private String computeID(IgrafElement element) {
			IgrafElement[] parents = Iterators.toArray(element.getContext(), IgrafElement.class);
			int plength = (parents == null)
					? 0
					: parents.length;
			StringBuilder buf = new StringBuilder(10 + 5 * plength);
			// add each parents id separated by - start with root (last in array)
			if(parents != null)
				for(int i = parents.length - 1; i >= 0; i--)
					buf.append((i == (plength - 1)
							? ""
							: "-") + parents[i].getId());
			buf.append("-");
			buf.append(element.getId());
			return buf.toString();
		}
	}

	private static class Label implements Function<IgrafElement, ILabelTemplate> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.google.common.base.Function#apply(java.lang.Object)
		 */
		@Override
		public ILabelTemplate apply(IgrafElement ge) {
			String text = "";
			if(ge instanceof ILabeledgrafElement)
				text = ((ILabeledgrafElement) ge).getLabel();
			return new LabelStringTemplate(text);
		}

	}

	private static class LabelData implements Function<IgrafElement, String> {

		private Object key;

		public LabelData(Object key) {
			this.key = key;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.google.common.base.Function#apply(java.lang.Object)
		 */
		@Override
		public String apply(IgrafElement ge) {
			String text = "";
			if(ge instanceof ILabeledgrafElement)
				text = ((ILabeledgrafElement) ge).getUserData().get(key);
			return text;
		}
	}

	private static class LiteralLabelTemplate implements Function<IgrafElement, ILabelTemplate> {
		final private ILabelTemplate value;

		public LiteralLabelTemplate(String value) {
			this.value = new LabelStringTemplate(value);
		}

		public LiteralLabelTemplate(LabelTable value) {
			this.value = value;
		}

		@Override
		public ILabelTemplate apply(IgrafElement from) {
			return value;
		}
	}

	private static class LiteralString implements Function<IgrafElement, String> {

		private String value;

		public LiteralString(String value) {
			this.value = value;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.google.common.base.Function#apply(java.lang.Object)
		 */
		@Override
		public String apply(IgrafElement ge) {
			return value;
		}
	}

	private static class LiteralStringSet implements Function<IgrafElement, Set<String>> {

		private Set<String> value;

		public LiteralStringSet(Set<String> value) {
			this.value = value;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.google.common.base.Function#apply(java.lang.Object)
		 */
		@Override
		public Set<String> apply(IgrafElement ge) {
			return value;
		}
	}

	public static class Not implements Function<IgrafElement, Boolean> {

		Function<IgrafElement, Boolean> function;

		public Not(Function<IgrafElement, Boolean> function) {
			this.function = function;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.google.common.base.Function#apply(java.lang.Object)
		 */
		@Override
		public Boolean apply(IgrafElement ge) {
			return !function.apply(ge);
		}

	}

	private static final EmptyLabel theEmptyLabelFunction = new EmptyLabel();

	private static final Not theNotEmptyLabelFunction = new Not(theEmptyLabelFunction);

	private static final Label theLabelFunction = new Label();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.grafcss.IFunctionFactory#emptyLabel()
	 */
	@Override
	public Function<IgrafElement, Boolean> emptyLabel() {
		return theEmptyLabelFunction;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.grafcss.IFunctionFactory#emptyLabelData(java.lang.Object)
	 */
	@Override
	public Function<IgrafElement, Boolean> emptyLabelData(Object key) {
		return new EmptyLabelData(key);
	}

	@Override
	public Function<IgrafElement, String> idClassReplacer() {
		return new IdClassReplacerFunction();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.grafcss.IFunctionFactory#label()
	 */
	@Override
	public Function<IgrafElement, ILabelTemplate> label() {
		return theLabelFunction;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.grafcss.IFunctionFactory#labelData(java.lang.Object)
	 */
	@Override
	public Function<IgrafElement, String> labelData(Object key) {
		return new LabelData(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.grafcss.IFunctionFactory#literalString(java.lang.String)
	 */
	@Override
	public Function<IgrafElement, String> literalString(String s) {
		return new LiteralString(s);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.grafcss.IFunctionFactory#notEmptyLabel()
	 */
	@Override
	public Function<IgrafElement, Boolean> notEmptyLabel() {
		return theNotEmptyLabelFunction;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.grafcss.IFunctionFactory#notEmptyLabelData(java.lang.Object)
	 */
	@Override
	public Function<IgrafElement, Boolean> notEmptyLabelData(Object key) {
		return new Not(emptyLabelData(key));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.grafcss.IFunctionFactory#literalStringSet(java.lang.String)
	 */
	@Override
	public Function<IgrafElement, Set<String>> literalStringSet(String s) {
		return new LiteralStringSet(Collections.singleton(s));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.grafcss.IFunctionFactory#literalStringSet(java.util.Collection)
	 */
	@Override
	public Function<IgrafElement, Set<String>> literalStringSet(Collection<String> s) {
		return new LiteralStringSet(Sets.newHashSet(s));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.grafcss.IFunctionFactory#literalStringTemplate(java.lang.String)
	 */
	@Override
	public Function<IgrafElement, ILabelTemplate> literalLabelTemplate(String s) {
		return new LiteralLabelTemplate(s);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.grafcss.IFunctionFactory#tableStringTemplate(org.ungtb10d.graf.style.labels.LabelTable)
	 */
	@Override
	public Function<IgrafElement, ILabelTemplate> literalLabelTemplate(LabelTable t) {
		return new LiteralLabelTemplate(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.grafcss.IFunctionFactory#labelTemplate(com.google.common.base.Function)
	 */
	@Override
	public Function<IgrafElement, ILabelTemplate> labelTemplate(final Function<IgrafElement, String> stringFunc) {
		return new Function<IgrafElement, ILabelTemplate>() {
			@Override
			public ILabelTemplate apply(IgrafElement from) {
				return new LiteralLabelTemplate(stringFunc.apply(from)).apply(from);
			}
		};
	}
}
