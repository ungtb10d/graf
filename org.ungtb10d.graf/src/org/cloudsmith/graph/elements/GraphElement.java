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
package org.ungtb10d.graf.elements;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.ungtb10d.graf.ElementType;
import org.ungtb10d.graf.IgrafElement;
import org.ungtb10d.graf.grafcss.StyleSet;
import org.ungtb10d.graf.style.IStyle;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * Base implementation of an IgrafElement.
 * 
 */
public abstract class grafElement implements IgrafElement {
	private static class ParentIterator implements Iterator<IgrafElement> {

		private IgrafElement current;

		public ParentIterator(IgrafElement ge) {
			this.current = ge;
		}

		@Override
		public boolean hasNext() {
			return current.getParentElement() != null;
		}

		@Override
		public IgrafElement next() {
			current = current.getParentElement();
			return current;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Can not remove from this iterator.");
		}

	}

	private String id;

	private Set<String> styleClasses;

	private Set<String> unmodifiableStyleClasses;

	private String urlString;

	private IgrafElement parentElement;

	private StyleSet instanceStyleMap;

	private Map<String, String> data;

	/**
	 * If a graf element is created without an id, it must either be set explicitly
	 * by user code - or set by the graf where it is added. (The default implementation
	 * of graf does this).
	 */
	public grafElement() {
		this("", null);
	}

	public grafElement(Collection<String> styleClasses) {
		this(styleClasses, null);
	}

	public grafElement(Collection<String> styleClasses, String id) {
		this.id = id;
		this.styleClasses = Sets.newHashSet();
		// do this to handle null and "" styleClass
		addAllStyleClasses(styleClasses);
		this.unmodifiableStyleClasses = Collections.unmodifiableSet(this.styleClasses);
		this.data = Maps.newHashMap();
	}

	/**
	 * Copy constructor.
	 * 
	 * @param that
	 */
	protected grafElement(IgrafElement that) {
		this.id = that.getId();
		this.styleClasses = Sets.newHashSet(that.getStyleClasses());
		this.unmodifiableStyleClasses = Collections.unmodifiableSet(this.styleClasses);
		this.data = Maps.newHashMap();
		this.data.putAll(that.getUserData());
		if(that.getStyles() != null) {
			this.instanceStyleMap = new StyleSet();
			this.instanceStyleMap.add(that.getStyles());
		}
	}

	public grafElement(String styleClass) {
		this(styleClass, null);
	}

	public grafElement(String styleClass, String id) {
		this(Collections.singleton(styleClass), id);
	}

	/**
	 * Add all styles (and filter out nulls and empty strings).
	 */
	@Override
	public boolean addAllStyleClasses(Collection<String> styleClasses) {
		boolean result = false;
		for(String s : styleClasses)
			if(isValidClass(s))
				if(this.styleClasses.add(s))
					result = true;
		return result;
	}

	@Override
	public boolean addStyleClass(String styleClass) {
		if(styleClass == null || styleClass.length() == 0)
			return false;
		return this.styleClasses.add(styleClass);
	}

	@Override
	public String getAllStyleClasses() {
		StringBuilder builder = new StringBuilder();
		for(String s : styleClasses) {
			builder.append(s);
			builder.append(" ");
		}
		return builder.substring(0, builder.length() - 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.IgrafElement#getContext()
	 */
	@Override
	public Iterator<IgrafElement> getContext() {
		return new ParentIterator(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.IgrafElement#getElementType()
	 */
	@Override
	public abstract ElementType getElementType();

	public String getId() {
		return id;
	}

	@Override
	public IgrafElement getParentElement() {
		return parentElement;
	}

	@Override
	public Set<String> getStyleClasses() {
		return this.unmodifiableStyleClasses;
	}

	@Override
	public StyleSet getStyles() {
		return instanceStyleMap;
	}

	public String getUrlString() {
		return urlString;
	}

	public Map<String, String> getUserData() {
		return data;
	}

	@Override
	public String getUserData(String key) {
		return data.get(key);
	}

	@Override
	public boolean hasStyleClass(String styleClass) {
		return this.styleClasses.contains(styleClass);
	}

	/**
	 * Check if style class name is valid. (not null and have length).
	 * 
	 * @param s
	 * @return
	 */
	private boolean isValidClass(String s) {
		if(s == null || s.length() < 1)
			return false;
		return true;
	}

	@Override
	public void putUserData(String key, String value) {
		data.put(key, value);
	}

	@Override
	public boolean removeStyleClass(String styleClass) {
		return this.styleClasses.remove(styleClass);
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setParentElement(IgrafElement parent) {
		this.parentElement = parent;
	}

	public void setStyles(IStyle<?>... styles) {
		if(styles.length > 0) {
			if(instanceStyleMap != null)
				instanceStyleMap.add(StyleSet.withStyles(styles));
			else
				instanceStyleMap = StyleSet.withStyles(styles);
		}
	}

	/**
	 * Sets a copy of the given style map as this element's map. It is illegal to modify the given
	 * StyleSet once it has been set.
	 * 
	 * @param styleMap
	 */
	public void setStyles(StyleSet styleMap) {

		this.instanceStyleMap = styleMap;
	}

	public void setUrlString(String urlString) {
		this.urlString = urlString;
	}
}
