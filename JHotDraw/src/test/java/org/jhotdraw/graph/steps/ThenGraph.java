package org.jhotdraw.graph.steps;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import static org.assertj.core.api.Assertions.assertThat;
import org.jhotdraw.app.AbstractApplication;
import org.jhotdraw.graph.GraphBezierFigure;
import org.jhotdraw.graph.LinearGraph;
import org.jhotdraw.graph.QuadraticGraph;
import org.jhotdraw.samples.svg.SVGView;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Joachim
 */
public class ThenGraph extends Stage<ThenGraph> {
    
    @ScenarioState
    AbstractApplication app; 

    @ScenarioState
    GraphBezierFigure graphBezier;
    
    @ScenarioState
    LinearGraph linearGraph;

    @ScenarioState
    QuadraticGraph quadraticGraph;
    
    public ThenGraph graph_is_in_canvas() {
        assertThat(((SVGView)app.getActiveView()).getDrawing().contains(graphBezier));
        assertEquals(1, ((SVGView)app.getActiveView()).getDrawing().getChildren().size());
        return self();
    }
    
    public ThenGraph two_graphs_is_in_canvas() {
        assertThat(((SVGView)app.getActiveView()).getDrawing().contains(graphBezier));
        assertEquals(2, ((SVGView)app.getActiveView()).getDrawing().getChildren().size());
        return self();
    }
}
