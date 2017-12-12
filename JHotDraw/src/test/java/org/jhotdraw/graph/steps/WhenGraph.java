package org.jhotdraw.graph.steps;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import java.awt.geom.Point2D;
import org.jhotdraw.app.AbstractApplication;
import org.jhotdraw.graph.GraphBezierFigure;
import org.jhotdraw.graph.LinearGraph;
import org.jhotdraw.graph.QuadraticGraph;
import org.jhotdraw.samples.svg.SVGView;

/**
 *
 * @author Joachim
 */
public class WhenGraph extends Stage<WhenGraph> {

    @ScenarioState
    AbstractApplication app; 

    @ScenarioState
    GraphBezierFigure graphBezier;
    
    @ScenarioState
    LinearGraph linearGraph;

    @ScenarioState
    QuadraticGraph quadraticGraph;
    

    public WhenGraph a_click_on_canvas_with_linear_graph() {
        graphBezier.setFunction(linearGraph);
        graphBezier.setBounds(new Point2D.Double(500, 500), new Point2D.Double(600, 600));
        return self();
    }

    public WhenGraph a_click_on_canvas_with_quadratic_graph() {
        graphBezier.setFunction(quadraticGraph);
        graphBezier.setBounds(new Point2D.Double(500, 500), new Point2D.Double(600, 600));
        return self();
    }

    public WhenGraph graph_is_added_to_canvas() {
        ((SVGView)app.getActiveView()).getDrawing().add(graphBezier);
        return self();
    }
}
