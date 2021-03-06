package org.jhotdraw.collaboration.steps;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import java.awt.geom.Point2D;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.jhotdraw.app.AbstractApplication;
import org.jhotdraw.collaboration.client.CollaborationConnection;
import org.jhotdraw.collaboration.common.IRemoteObservable;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.samples.svg.SVGView;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;

/**
 *
 * @author Niels & Niclas
 */
public class WhenCollaboration extends Stage<WhenCollaboration> {

    @ExpectedScenarioState
    @ProvidedScenarioState
    IRemoteObservable server;
    
    @ExpectedScenarioState
    @ProvidedScenarioState
    CollaborationConnection client;
    
    @ProvidedScenarioState
    List<Figure> argument;
    
    @ExpectedScenarioState
    List<Figure> myList;
    
    @ExpectedScenarioState
    AbstractApplication clientsApplication;
    
    @ProvidedScenarioState
    Drawing clientsDrawing;
    
    @ProvidedScenarioState
    List<Figure> myUpdatedList;
    
    public WhenCollaboration they_are_connected() throws RemoteException, AlreadyBoundException, UnknownHostException {
        assertThat(server).isNotNull();
        assertThat(client).isNotNull();
        
        client.setCollaborationProxy(server);
        server.addCollaborator(client);
        
        return this;
    }
    
    public WhenCollaboration the_client_disconnects() throws RemoteException, AlreadyBoundException, UnknownHostException {
        client.setCollaborationProxy(null);
        server.removeCollaborator(client);
        return this;
    }
    
    public WhenCollaboration the_server_has_an_update() throws RemoteException {
        argument = new ArrayList<>();
        Figure rectFig = new SVGRectFigure();
        rectFig.setBounds(new Point2D.Double(2, 2), new Point2D.Double(10, 10));
        rectFig.setCollaborationId();
        argument.add(rectFig);
        server.notifyAllCollaborators(argument);
        
        return this;
    }
    
    public WhenCollaboration a_figure_is_added() throws RemoteException {
        List<Figure> figList = new ArrayList<>();
        Figure rectFig = new SVGRectFigure();
        rectFig.setBounds(new Point2D.Double(2, 2), new Point2D.Double(10, 10));
        rectFig.setCollaborationId();
        figList.add(rectFig);
        
        server.notifyAllCollaborators(figList);
        
        return this;
    }
    
    public WhenCollaboration the_client_is_waiting_for_updates() {
        assertThat(client).isNotNull();
        assertThat(clientsApplication).isNotNull();
        return this;
    }
    
    public WhenCollaboration I_add_a_figure() throws RemoteException {
        Figure rectFig = new SVGRectFigure();
        rectFig.setBounds(new Point2D.Double(2, 2), new Point2D.Double(10, 10));
        rectFig.setCollaborationId();
        myList.add(rectFig);
        
        client.update(myList);
        
        clientsDrawing = (((SVGView) clientsApplication.getActiveView()).getDrawing());
        myUpdatedList = myList;  
        return this;
    }
    
    public WhenCollaboration I_edit_my_drawing() throws RemoteException {
        Figure rectFig = myList.get(0);
        rectFig.setBounds(new Point2D.Double(4, 4), new Point2D.Double(12, 12));
        
        client.update(myList);
        
        clientsDrawing = (((SVGView) clientsApplication.getActiveView()).getDrawing());
        myUpdatedList = myList; 
        return this;
    }
    
    public WhenCollaboration I_remove_a_figure() throws RemoteException {
        Figure rectFig = myList.get(0);
        myList.remove(rectFig);
        
        client.update(myList);
        
        clientsDrawing = (((SVGView) clientsApplication.getActiveView()).getDrawing());
        myUpdatedList = myList;        
        return this;
    }

}
