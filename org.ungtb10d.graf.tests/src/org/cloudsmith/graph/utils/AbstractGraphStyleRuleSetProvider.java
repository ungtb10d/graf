package org.ungtb10d.graf.utils;

import org.ungtb10d.graf.ElementType;
import org.ungtb10d.graf.grafcss.grafCSS;
import org.ungtb10d.graf.grafcss.Rule;
import org.ungtb10d.graf.grafcss.Select;
import org.ungtb10d.graf.grafcss.StyleSet;
import org.ungtb10d.graf.grafviz.grafvizLayout;
import org.ungtb10d.graf.style.Arrow;
import org.ungtb10d.graf.style.Compass;
import org.ungtb10d.graf.style.EdgeRouting;
import org.ungtb10d.graf.style.LineType;
import org.ungtb10d.graf.style.NodeShape;
import org.ungtb10d.graf.style.RankDirection;
import org.ungtb10d.graf.style.StyleFactory;

/**
 * TODO: UNFINISHED, USES OLD STYLE FIELD NAMES m_
 * 
 * @author henrik
 * 
 */
public class AbstractgrafStyleRuleSetProvider implements IgrafStyleRuleSetProvider {

	protected grafvizLayout m_layout = org.ungtb10d.graf.grafviz.grafvizLayout.dot;

	protected Arrow m_headArrow = Arrow.vee;

	protected Arrow m_tailArrow = Arrow.none;

	protected Compass m_tailPort = Compass.NONE;

	protected Compass m_headPort = Compass.NONE;

	protected Double m_arrowScale = new Double(0.5);

	protected boolean m_concentrate = false;

	protected EdgeRouting m_routing = EdgeRouting.spline;

	protected NodeShape m_nodeShape = NodeShape.rectangle;

	protected LineType m_nodeLineType = LineType.solid;

	protected boolean m_nodeFilled = true;

	protected boolean m_nodeRounded = true;

	protected LineType m_edgeLineType = LineType.solid;

	protected boolean m_edgeDecorate = false;

	protected grafCSS CGSS = new grafCSS();

	protected void configureDefaults() {
		CGSS.addRule(getDefaultgrafStyle());
		CGSS.addRule(getDefaultVertexStyle());
		CGSS.addRule(getDefaultEdgeStyle());
	}

	public grafCSS getCGSS() {
		return CGSS;
	}

	/**
	 * Returns the default rule for ElementType.edge
	 * This implementation returns NULL_RULE as the default is to use the default for the rendering technology.
	 * 
	 * @return grafStyleRule.NULL_RULE
	 */
	protected Rule getDefaultEdgeStyle() {

		// There is no need to set the value below as they are default when rendering with
		// grafviz. Setting these values will just cause bloat in the generated graf.

		// StyleMap edgeStyle = new StyleMap();
		// edgeStyle.put(new grafStyle.EdgeBrush(m_edgeLineType, new Double(0.5))); // DEFAULT
		// edgeStyle.put(new grafStyle.HeadPort(m_headPort)); // DEFAULT
		// edgeStyle.put(new grafStyle.TailPort(m_tailPort)); // DEFAULT
		// edgeStyle.put(new grafStyle.ArrowHead(m_headArrow)); //DEFAULT
		// edgeStyle.put(new grafStyle.ArrowTail(m_tailArrow)); // DEFAULT
		// edgeStyle.put(new grafStyle.ArrowScale(m_arrowScale)); //DEFAULT
		// edgeStyle.put(new grafStyle.Decorate(Boolean.valueOf(m_edgeDecorate)));
		// grafStyleRule edgeRule = new grafStyleRule(
		// new grafStyleRule.Element(ElementType.edge), edgeStyle);
		// return edgeRule;
		return Rule.NULL_RULE;

	}

	/**
	 * Returns the default rule for ElementType.graf
	 * This implementation returns a graf style that concentrates edges, uses spline routing, LR rank
	 * direction, and with a 2.0 rank separation.
	 * 
	 * @return a configured rule for a graf
	 */
	protected Rule getDefaultgrafStyle() {
		// a StyleMap corresponds to the { } section in a CSS rule
		StyleSet grafStyle = new StyleSet();
		grafStyle.put(new StyleFactory.Concentrate(Boolean.valueOf(m_concentrate)));
		grafStyle.put(new StyleFactory.Routing(m_routing));
		grafStyle.put(new StyleFactory.RankDirectionStyle(RankDirection.LR));
		grafStyle.put(new StyleFactory.RankSeparation(new Double(2.0)));

		// a rule corresponds to the 'rules { }' in a CSS rule (example the 'A.Foo#x'is a CSS rule)
		return new Rule(new Select.Element(ElementType.graf), grafStyle);
	}

	/**
	 * Returns the default rule for ElementType.vertex.
	 * This implementation returns NULL_RULE as the default is to use the default for the rendering technology.
	 * 
	 * @return grafStyleRule.NULL_RULE
	 */
	protected Rule getDefaultVertexStyle() {
		// There is no need to set the value below as they are default when rendering with
		// grafviz. Setting these values will just cause bloat in the generated graf.

		// StyleMap nodeStyle = new StyleMap();
		// nodeStyle.put(new grafStyle.Shape(m_nodeShape));
		// nodeStyle.put(new grafStyle.NodeBrush(m_nodeLineType, new Double(0.5), m_nodeFilled, m_nodeRounded));
		// result.addRule(new grafStyleRule(
		// new grafStyleRule.Element(ElementType.vertex), nodeStyle));
		return Rule.NULL_RULE;
	}
}
