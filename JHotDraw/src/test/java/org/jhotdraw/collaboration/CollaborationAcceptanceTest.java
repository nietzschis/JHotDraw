package org.jhotdraw.collaboration;

import com.tngtech.jgiven.junit.ScenarioTest;
import java.rmi.RemoteException;
import org.jhotdraw.collaboration.steps.GivenAClient;
import org.jhotdraw.collaboration.steps.ThenClientsDrawingUpdated;
import org.jhotdraw.collaboration.steps.WhenUpdatingDrawing;
import org.junit.Test;

/**
 *
 * @author Niels & Niclas
 */
public class CollaborationAcceptanceTest extends ScenarioTest<GivenAClient, WhenUpdatingDrawing, ThenClientsDrawingUpdated> {
    
    @Test
    public void collaborationAcceptenceTestAddFigure() throws RemoteException {
        given().the_client().
                and().my_drawing();
        
        when().a_client_waiting_for_updates().
                and().I_add_a_figure();
        
        then().figure_have_been_added_to_client();
    }
    
    @Test
    public void collaborationAcceptenceTestChangeFigure() throws RemoteException {
        given().the_client().
                and().my_drawing();
        
        when().a_client_waiting_for_updates().
                and().I_add_a_figure().
                and().I_update_my_drawing();
        
        then().figure_have_been_changed_on_client();
    }
    
    @Test
    public void collaborationAcceptenceTestRemoveFigure() throws RemoteException {
        given().the_client().
                and().my_drawing();
        
        when().a_client_waiting_for_updates().
                and().I_add_a_figure().
                and().I_remove_a_figure();
        
        then().figure_have_been_removed_on_client();
    }
    
}
