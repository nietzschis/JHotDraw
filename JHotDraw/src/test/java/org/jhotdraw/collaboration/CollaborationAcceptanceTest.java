package org.jhotdraw.collaboration;

import com.tngtech.jgiven.junit.ScenarioTest;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import org.jhotdraw.collaboration.server.RemoteObservable;
import org.jhotdraw.collaboration.steps.GivenCollaboration;
import org.jhotdraw.collaboration.steps.ThenCollaboration;
import org.jhotdraw.collaboration.steps.WhenCollaboration;
import org.junit.After;
import org.junit.Test;

/**
 *
 * @author Niels & Niclas
 */
public class CollaborationAcceptanceTest extends ScenarioTest<GivenCollaboration, WhenCollaboration, ThenCollaboration> {
    
    @After
    public void tearDown() throws RemoteException {
        ((RemoteObservable) RemoteObservable.getInstance()).clearAllCollaborators();
    }
    
    @Test
    public void add_collaborator_to_server_test() throws RemoteException, AlreadyBoundException, UnknownHostException {
        given().the_server()
                .and()
                .the_mock_client();
        
        when().they_are_connected();
        
        then().the_server_has_a_collaborator();
    }
    
    @Test
    public void remove_collaborator_to_server_test() throws RemoteException, AlreadyBoundException, UnknownHostException {
        given().the_server()
                .and()
                .the_mock_client();
        
        when().they_are_connected()
                .and().the_client_disconnects();
        
        then().the_server_has_no_collaborators();
    }
    
    @Test
    public void send_update_from_server_to_client_test() throws RemoteException, AlreadyBoundException, UnknownHostException {
        given().the_server()
                .and()
                .the_mock_client();
        
        when().they_are_connected()
                .and()
                .the_server_has_an_update();
        
        then().the_client_receives_the_update();
    }
    
    @Test
    public void collaborationAcceptenceTestAddFigure() throws RemoteException {
        given().the_client().
                and().my_drawing();
        
        when().the_client_is_waiting_for_updates().
                and().I_add_a_figure();
        
        then().the_figure_has_been_added_to_client();
    }
    
    @Test
    public void collaborationAcceptenceTestChangeFigure() throws RemoteException {
        given().the_client().
                and().my_drawing();
        
        when().the_client_is_waiting_for_updates().
                and().I_add_a_figure().
                and().I_edit_my_drawing();
        
        then().the_figure_has_been_changed_on_the_client();
    }
    
    @Test
    public void collaborationAcceptenceTestRemoveFigure() throws RemoteException {
        given().the_client().
                and().my_drawing();
        
        when().the_client_is_waiting_for_updates().
                and().I_add_a_figure().
                and().I_remove_a_figure();
        
        then().the_figure_has_been_removed_on_the_client();
    }
    
}
