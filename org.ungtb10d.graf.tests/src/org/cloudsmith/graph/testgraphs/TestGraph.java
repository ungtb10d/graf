/*******************************************************************
 * Copyright (c) 2006-2011, ungtb10d Inc.
 * The code, documentation and other materials contained herein
 * are the sole and exclusive property of ungtb10d Inc. and may
 * not be disclosed, used, modified, copied or distributed without
 * prior written consent or license from ungtb10d Inc.
 ******************************************************************/

package org.ungtb10d.graf.testgrafs;

import java.util.Collection;
import java.util.List;

import org.ungtb10d.graf.IgrafProvider;
import org.ungtb10d.graf.IRootgraf;
import org.ungtb10d.graf.elements.Clustergraf;
import org.ungtb10d.graf.elements.Edge;
import org.ungtb10d.graf.elements.Rootgraf;
import org.ungtb10d.graf.elements.Vertex;
import org.ungtb10d.graf.grafcss.IFunctionFactory;
import org.ungtb10d.graf.grafcss.Rule;
import org.ungtb10d.graf.grafcss.Select;
import org.ungtb10d.graf.grafcss.StyleSet;
import org.ungtb10d.graf.style.Alignment;
import org.ungtb10d.graf.style.Arrow;
import org.ungtb10d.graf.style.Compass;
import org.ungtb10d.graf.style.EdgeRouting;
import org.ungtb10d.graf.style.IStyleFactory;
import org.ungtb10d.graf.style.LineType;
import org.ungtb10d.graf.style.NodeShape;
import org.ungtb10d.graf.style.labels.LabelTable;
import org.ungtb10d.graf.style.labels.LabelTableBuilder;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * A test and demo of the grafviz graf layout and rendering support.
 * TODO: UNFINISHED, AND HAS ISSUES...
 * 
 */
public class Testgraf implements IgrafProvider {

	private Arrow m_headArrow = Arrow.vee;

	// none is the default, and does not have to be set...
	// private Arrow m_tailArrow = Arrow.none;

	private Compass m_tailPort = Compass.NONE;

	private Compass m_headPort = Compass.n;

	private Double m_arrowScale = new Double(1.0);

	private boolean m_concentrate = false;

	private EdgeRouting m_routing = EdgeRouting.spline;

	private NodeShape m_nodeShape = NodeShape.rectangle;

	private LineType m_nodeLineType = LineType.solid;

	private boolean m_nodeFilled = true;

	private boolean m_nodeRounded = true;

	private LineType m_edgeLineType = LineType.solid;

	private boolean m_edgeDecorate = false;

	// private grafvizRenderer m_renderer = grafvizRenderer.gd;
	//
	// private DotRenderer dotRenderer;

	private IStyleFactory styleFactory;

	private IFunctionFactory functions;

	@Inject
	public Testgraf(IStyleFactory styleFactory, IFunctionFactory functions) {
		this.styleFactory = styleFactory;
		this.functions = functions;
	}

	public IRootgraf computegraf() {
		return computegraf(null, "a graf with subgrafs", "root");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ungtb10d.graf.IgrafProvider#computegraf()
	 */
	@Override
	public IRootgraf computegraf(Object modelObj) {
		return computegraf();
	}

	// public byte[] getgraf() {
	// Igraf graf = getMockgraf();
	// Igrafviz g = new grafviz(new DefaultgrafvizConfig(), dotRenderer);
	// byte[] img = g.getPngImage(m_layout, graf, getCGSS(), null);
	// // TODO: get the bytes of the no_image.png under images folder
	// return img == null
	// ? ImageUtils.loadImage("/static/img/no_image.png")
	// : img;
	//
	// }

	/**
	 * @modelObj - ignored, returns same graf at all times.
	 */
	public IRootgraf computegraf(Object modelObj, String title, String id) {
		Rootgraf g = new Rootgraf(title, "Rootgraf", id);
		Clustergraf sub1 = getMockgraf("x1");
		g.addSubgraf(sub1);
		Clustergraf sub2 = getMockgraf("x2");
		sub2.addSubgraf(getNestedMockgraf());
		g.addSubgraf(sub2);
		g.addEdge(new Edge("between", Iterators.get(sub1.getVertices().iterator(), 0), Iterators.get(
			sub2.getVertices().iterator(), 0)));
		g.addEdge(new Edge("between2", Iterators.get(sub1.getVertices().iterator(), 3), Iterators.get(
			sub2.getVertices().iterator(), 3)));
		return g;
	}

	public Clustergraf getMockgraf(String id) {
		Clustergraf g = new Clustergraf(id, "", id);
		Vertex[] vv = new Vertex[5];
		for(int i = 0; i < vv.length; i++) {
			vv[i] = new Vertex("label_" + Integer.toString(i) + "\nsome text", "");
			vv[i].setStyles(StyleSet.withImmutableStyles(styleFactory.href("http://www.google.com")));
			g.addVertex(vv[i]);
		}
		vv[1].addStyleClass("MakeMePink");
		Edge[] ev = new Edge[4];
		for(int i = 0; i < ev.length; i++) {
			ev[i] = new Edge("label_" + Integer.toString(i), vv[0], vv[i + 1]);
			ev[i].setStyles(StyleSet.withImmutableStyles(styleFactory.href("http://www.google.com")));
			g.addEdge(ev[i]);
		}
		g.addEdge(new Edge(vv[1], vv[2]));
		g.addEdge(new Edge(vv[2], vv[3]));

		// add some data to a node
		vv[4].getUserData().put("name", "John Doe");
		vv[4].getUserData().put("version", "1.2.3");
		vv[4].getUserData().put("type", "demo");
		vv[4].addStyleClass("WithData");

		ev[1].addStyleClass("SpecialArrows");
		ev[2].addStyleClass("Testing");
		ev[3].addStyleClass("MakeMePink");

		return g;
	}

	private Clustergraf getNestedMockgraf() {
		Clustergraf g = new Clustergraf("x11", "a graf with subgrafs", "Rootgraf");
		Clustergraf sub1 = getMockgraf("x12");
		g.addSubgraf(sub1);
		Clustergraf sub2 = getMockgraf("x13");
		g.addSubgraf(sub2);
		g.addEdge(new Edge(Iterators.get(sub1.getVertices().iterator(), 0), Iterators.get(
			sub2.getVertices().iterator(), 0)));
		g.addEdge(new Edge(Iterators.get(sub1.getVertices().iterator(), 3), Iterators.get(
			sub2.getVertices().iterator(), 3)));
		return g;
	}

	/**
	 * Produces a CGSS (Cascading graf Style Sheet) based on the user's input.
	 * This is also a good demonstration of how to use the Styling capabilities of
	 * the grafviz support.
	 * 
	 * @return the rule set to use as CGSS
	 */
	public Collection<Rule> getRules() {

		// the rule set that contains all rules and styling
		List<Rule> result = Lists.newArrayList();

		// Styles for the graf
		result.add(Select.graf().withStyles( //
			styleFactory.concentrate(m_concentrate), //
			styleFactory.routing(m_routing)));

		// styles for vertexes
		result.add(Select.vertex().withStyles( //
			styleFactory.shape(m_nodeShape), //
			styleFactory.shapeBrush(m_nodeLineType, 0.5, m_nodeFilled, m_nodeRounded)));

		result.add(Select.edge().withStyles( //
			styleFactory.lineBrush(m_edgeLineType, 0.5), //
			styleFactory.headPort(m_headPort), //
			styleFactory.tailPort(m_tailPort), //
			styleFactory.arrowHead(m_headArrow), //
			styleFactory.arrowScale(m_arrowScale), //
			styleFactory.decorate(m_edgeDecorate)));

		// Create a label format - a table with 3 rows, and a cell in each. The cells pick up
		// data called 'name', 'type' and 'version'
		//
		LabelTableBuilder tableBuilder = new LabelTableBuilder(styleFactory, functions) {

			@Override
			public LabelTable build() {
				return table("DataTable", //
					row("FirstRow", cell("NameCell", "name")), //
					row("SecondRow", cell("TypeCell", "type")), //
					row("ThirdRow", cell("VersionCell", "version")) //
				);
			}

		};

		result.add(Select.vertex("WithData").withStyle(styleFactory.labelFormat(tableBuilder.build())));

		// Make the cells in a "DataTable" left aligned, and in orange color
		result.add(Select.and(Select.cell(), Select.containment(Select.table("DataTable"))).withStyles( //
			styleFactory.align(Alignment.left), //
			styleFactory.color("#FFA144")));

		// Style all "NameCell" cells to define a port called "name"
		//
		result.add(Select.element("VersionCell", null).withStyle(styleFactory.port("name")));

		// this demonstrates the Between rule for edges - i.e. between "any" and "WithData"
		// - Make all nodes that point to a vertex with style class "WithData" point to the
		// port "name" - and make the line orange
		// Note that ports can be a combination of a named port, and a compass - here we always use
		// "name" and the headport that the use can change
		//
		result.add(Select.between(Select.any(), Select.element("WithData")).withStyles( //
			styleFactory.lineColor("#FFA144"), //
			styleFactory.headPort("name", m_headPort)));

		return result;
	}

	// public String getUsemap() {
	// Igraf graf = getMockgraf();
	// // usemap does not support different renderers (?)
	// // TODO: check if usemap is ok for cairo as well as gd
	// // right now the grafviz runner sets the renderer to null for usemap rendering.
	// //
	// Igrafviz g = new grafviz(new DefaultgrafvizConfig(), dotRenderer);
	// return g.getUsemap(m_layout, graf, getCGSS(), null);
	// }
}
