package org.jhotdraw.graph;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.jhotdraw.graph.steps.*;
import org.junit.After;
import org.junit.Test;

/**
 *
 * @author Joachim
 */
public class GraphAcceptanceTest extends ScenarioTest<GivenGraph, WhenGraph, ThenGraph> {
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void drawLinearGraph() {
        given().an_app().and().a_graph_figure().and().a_linear_graph();
        
        when().a_click_on_canvas_with_linear_graph().and().graph_is_added_to_canvas();
        
        then().graph_is_in_canvas();  
    }
    
    @Test
    public void drawQuadraticGraph() {
        given().an_app().and().a_graph_figure().and().a_quadratic_graph();
        
        when().a_click_on_canvas_with_quadratic_graph().and().graph_is_added_to_canvas();
        
        then().graph_is_in_canvas();  
    }
    
    @Test
    public void drawTwoGraphs() {
        given().an_app().and().a_graph_figure().and().a_quadratic_graph().and().a_linear_graph();
        
        when().a_click_on_canvas_with_quadratic_graph().and()
                .graph_is_added_to_canvas()
                .a_click_on_canvas_with_linear_graph().and().graph_is_added_to_canvas();
        
        then().two_graphs_is_in_canvas();  
    }

    
}
