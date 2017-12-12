package org.jhotdraw.graph.steps;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import org.jhotdraw.app.AbstractApplication;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.graph.GraphBezierFigure;
import org.jhotdraw.graph.LinearGraph;
import org.jhotdraw.graph.QuadraticGraph;
import org.jhotdraw.samples.svg.SVGView;

/**
 *
 * @author Joachim
 */
public class GivenGraph extends Stage<GivenGraph> {
    
    @ScenarioState
    AbstractApplication app; 

    @ScenarioState
    GraphBezierFigure graphBezier;
    
    @ScenarioState
    LinearGraph linearGraph;

    @ScenarioState
    QuadraticGraph quadraticGraph;
    
    public GivenGraph a_graph_figure() {
        graphBezier = new GraphBezierFigure();
        return self();
    }
    
    public GivenGraph a_linear_graph() {
        linearGraph = new LinearGraph(2, 8, 50);
        return self();
    }
    
    public GivenGraph a_quadratic_graph() {
        quadraticGraph = new QuadraticGraph(0.5, 2, 5, 50);
        return self();
    }
    
    public GivenGraph an_app() {
        app = new DefaultSDIApplication();
        SVGView view = new SVGView();
        view.init();
        app.setActiveView(view);
        return self();
    }
    
}
