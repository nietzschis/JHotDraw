package org.jhotdraw.collaboration.steps;

import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.jhotdraw.collaboration.client.CollaborationConnection;
import org.jhotdraw.collaboration.common.IRemoteObservable;
import org.jhotdraw.collaboration.server.RemoteObservable;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.Figure;
import static org.junit.Assert.assertEquals;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 *
 * @author Niels & Niclas
 */
public class ThenCollaboration {
    
    @ExpectedScenarioState
    IRemoteObservable server;
    
    @ExpectedScenarioState
    CollaborationConnection client;
    
    @ExpectedScenarioState
    List<Figure> argument;

    @ExpectedScenarioState
    Drawing clientsDrawing;
    
    @ExpectedScenarioState
    List<Figure> myUpdatedList;
    
    private ArgumentCaptor<List<Figure>> argumentCaptor;
    
    public void the_server_has_a_collaborator() throws RemoteException {
        assertThat(server).isNotNull();
        assertThat(client).isNotNull();
        
        assertEquals(client.getName(), ((RemoteObservable) server).getCollaboratorNames().trim());
    }
    
    public void the_server_has_no_collaborators() throws RemoteException {
        assertThat(server).isNotNull();
        assertThat(client).isNotNull();
        
        assertEquals("", ((RemoteObservable) server).getCollaboratorNames().trim());
    }
    
    public void the_client_receives_the_update() throws RemoteException {
        assertThat(server).isNotNull();
        assertThat(client).isNotNull();
        argumentCaptor = ArgumentCaptor.forClass(List.class);
        
        verify(client, times(1)).update(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue(), argument);
    }
    
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
