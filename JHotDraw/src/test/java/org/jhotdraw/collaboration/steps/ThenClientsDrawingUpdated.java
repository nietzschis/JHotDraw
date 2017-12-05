package org.jhotdraw.collaboration.steps;

import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import java.util.List;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.Figure;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Niclas
 */
public class ThenClientsDrawingUpdated {

    @ExpectedScenarioState
    Drawing clientsDrawing;
    
    @ExpectedScenarioState
    List<Figure> myUpdatedList;
    
    public void the_figure_has_been_added_to_client() {
        assertEquals(1, myUpdatedList.size());
        assertEquals(1, clientsDrawing.getChildCount());
        
        Figure myFigure = myUpdatedList.get(0);
        Figure clientsFigure = clientsDrawing.getChild(0);
           
        assertEquals(myFigure.getCollaborationId(), clientsFigure.getCollaborationId());
    }
    
    public void the_figure_has_been_changed_on_the_client() {
        Figure myFigure = myUpdatedList.get(0);
        Figure clientsFigure = clientsDrawing.getChild(0);
        
        assertEquals(myFigure.getBounds().height, clientsFigure.getBounds().height, 0.0001);
        assertEquals(myFigure.getBounds().width, clientsFigure.getBounds().width, 0.0001);
        assertEquals(myFigure.getBounds().x, clientsFigure.getBounds().x, 0.0001);
        assertEquals(myFigure.getBounds().y, clientsFigure.getBounds().y, 0.0001);
        
        assertEquals(myFigure.getAttributes(), clientsFigure.getAttributes());
    }
    
    public void the_figure_has_been_removed_on_the_client() {
        assertEquals(0, myUpdatedList.size());
        assertEquals(0, clientsDrawing.getChildCount());
    }
}
