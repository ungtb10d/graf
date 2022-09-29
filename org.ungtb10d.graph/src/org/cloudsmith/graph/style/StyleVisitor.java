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
package org.ungtb10d.graph.style;

import org.ungtb10d.graph.IClusterGraph;
import org.ungtb10d.graph.style.labels.ILabelTemplate;

/**
 * A default implementation of IStyleVisitor.
 * All methods do nothing, and does not have to be called.
 * 
 */
public class StyleVisitor implements IStyleVisitor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#align(org.ungtb10d.graph.style.Alignment)
	 */
	@Override
	public void align(Alignment x) {
		unsupported(StyleType.align);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#arrowHead(org.ungtb10d.graph.style.Arrow)
	 */
	@Override
	public void arrowHead(Arrow x) {
		unsupported(StyleType.arrowHead);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#arrows(org.ungtb10d.graph.style.Arrow)
	 */
	@Override
	public void arrows(Arrow x) {
		unsupported(StyleType.arrows);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#arrowScale(java.lang.Double)
	 */
	@Override
	public void arrowScale(double x) {
		unsupported(StyleType.arrowScale);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#arrowTail(org.ungtb10d.graph.style.Arrow)
	 */
	@Override
	public void arrowTail(Arrow x) {
		unsupported(StyleType.arrowTail);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#backgroundColor(java.lang.String)
	 */
	@Override
	public void backgroundColor(String x) {
		unsupported(StyleType.backgroundColor);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#borderWidth(int)
	 */
	@Override
	public void borderWidth(int x) {
		unsupported(StyleType.borderWidth);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#cellBorderWidth(int)
	 */
	@Override
	public void cellBorderWidth(int x) {
		unsupported(StyleType.cellBorderWidth);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#cellPadding(int)
	 */
	@Override
	public void cellPadding(int x) {
		unsupported(StyleType.cellPadding);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#cellSpacing(int)
	 */
	@Override
	public void cellSpacing(int x) {
		unsupported(StyleType.cellSpacing);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#color(java.lang.String)
	 */
	@Override
	public void color(String x) {
		unsupported(StyleType.color);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#colSpan(int)
	 */
	@Override
	public void colSpan(int x) {
		unsupported(StyleType.colSpan);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#compound(boolean)
	 */
	@Override
	public void compound(boolean value) {
		unsupported(StyleType.compound);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#concentrate(boolean)
	 */
	@Override
	public void concentrate(boolean x) {
		unsupported(StyleType.concentrate);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#decorate(boolean)
	 */
	@Override
	public void decorate(boolean x) {
		unsupported(StyleType.decorate);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#direction(org.ungtb10d.graph.style.EdgeDirection)
	 */
	@Override
	public void direction(EdgeDirection x) {
		unsupported(StyleType.direction);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#backgroundColor(java.lang.String)
	 */
	@Override
	public void fillColor(String x) {
		unsupported(StyleType.fillColor);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#fixedSize(boolean)
	 */
	@Override
	public void fixedSize(boolean x) {
		unsupported(StyleType.fixedSize);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#fontFamily(java.lang.String)
	 */
	@Override
	public void fontFamily(String x) {
		unsupported(StyleType.fontFamily);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#fontSize(int)
	 */
	@Override
	public void fontSize(int x) {
		unsupported(StyleType.fontSize);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#fromCluster(org.ungtb10d.graph.IClusterGraph)
	 */
	@Override
	public void fromCluster(IClusterGraph value) {
		unsupported(StyleType.fromCluster);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#headPort(java.lang.String)
	 */
	@Override
	public void headPort(String x) {
		unsupported(StyleType.headPort);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#height(double)
	 */
	@Override
	public void height(double d) {
		unsupported(StyleType.height);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#href(java.lang.String)
	 */
	@Override
	public void href(String x) {
		unsupported(StyleType.href);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#id(java.lang.String)
	 */
	@Override
	public void id(String value) {
		unsupported(StyleType.id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#labelFormat(org.ungtb10d.graph.style.labels.ILabelTemplate)
	 */
	@Override
	public void labelFormat(ILabelTemplate x) {
		unsupported(StyleType.labelFormat);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#lineBrush(org.ungtb10d.graph.style.LineType, double)
	 */
	@Override
	public void lineBrush(LineBrush brush) {
		unsupported(StyleType.lineBrush);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#lineColor(java.lang.String)
	 */
	@Override
	public void lineColor(String x) {
		unsupported(StyleType.lineColor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#mclimit(double)
	 */
	@Override
	public void mclimit(double value) {
		unsupported(StyleType.mclimit);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#port(java.lang.String)
	 */
	@Override
	public void port(String x) {
		unsupported(StyleType.port);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#rankDirectionStyle(org.ungtb10d.graph.style.RankDirection)
	 */
	@Override
	public void rankDirection(RankDirection x) {
		unsupported(StyleType.rankDirection);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#rankSeparation(double)
	 */
	@Override
	public void rankSeparation(double x) {
		unsupported(StyleType.rankSeparation);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#remincross(boolean)
	 */
	@Override
	public void remincross(boolean value) {
		unsupported(StyleType.remincross);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#rendered(java.lang.String)
	 */
	@Override
	public void rendered(boolean x) {
		unsupported(StyleType.rendered);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#routing(org.ungtb10d.graph.style.EdgeRouting)
	 */
	@Override
	public void routing(EdgeRouting x) {
		unsupported(StyleType.edgeRouting);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#rowSpan(int)
	 */
	@Override
	public void rowSpan(int x) {
		unsupported(StyleType.rowSpan);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#shape(org.ungtb10d.graph.style.NodeShape)
	 */
	@Override
	public void shape(NodeShape x) {
		unsupported(StyleType.shape);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#shapeBrush(org.ungtb10d.graph.style.ShapeBrush)
	 */
	@Override
	public void shapeBrush(ShapeBrush brush) {
		unsupported(StyleType.shapeBrush);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#tailPort(java.lang.String)
	 */
	@Override
	public void tailPort(String x) {
		unsupported(StyleType.tailPort);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#target(java.lang.String)
	 */
	@Override
	public void target(String x) {
		unsupported(StyleType.target);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#toCluster(org.ungtb10d.graph.IClusterGraph)
	 */
	@Override
	public void toCluster(IClusterGraph value) {
		unsupported(StyleType.toCluster);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#tooltip(java.lang.String)
	 */
	@Override
	public void tooltip(String x) {
		unsupported(StyleType.tooltip);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#tooltipForHead(java.lang.String)
	 */
	@Override
	public void tooltipForHead(String value) {
		unsupported(StyleType.tooltipForHead);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#tooltipForLabel(java.lang.String)
	 */
	@Override
	public void tooltipForLabel(String value) {
		unsupported(StyleType.tooltipForLabel);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#tooltipForTail(java.lang.String)
	 */
	@Override
	public void tooltipForTail(String value) {
		unsupported(StyleType.tooltipForTail);

	}

	/**
	 * This implementation does nothing. A derived class can implement this method as a "catch all" for
	 * unimplemented methods.
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#unsupported(org.ungtb10d.graph.IStyle)
	 */
	protected void unsupported(StyleType style) {
		// does nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#verticalAlign(org.ungtb10d.graph.style.VerticalAlignment)
	 */
	@Override
	public void verticalAlign(VerticalAlignment x) {
		unsupported(StyleType.valign);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#weight(java.lang.Double)
	 */
	@Override
	public void weight(double value) {
		unsupported(StyleType.weight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graph.style.IStyleVisitor#width(double)
	 */
	@Override
	public void width(double x) {
		unsupported(StyleType.width);

	}

}
