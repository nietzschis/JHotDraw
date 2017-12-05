package org.jhotdraw.collaboration.steps;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import java.awt.geom.Point2D;
import java.rmi.RemoteException;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.jhotdraw.app.AbstractApplication;
import org.jhotdraw.collaboration.client.CollaborationConnection;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.samples.svg.SVGView;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;

/**
 *
 * @author Niels & Niclas
 */
public class WhenUpdatingDrawing extends Stage<WhenUpdatingDrawing> {

    /*@ExpectedScenarioState
    IServer server;
    */
    @ExpectedScenarioState
    CollaborationConnection client;
    
    @ExpectedScenarioState
    List<Figure> myList;
    
    @ExpectedScenarioState
    AbstractApplication clientsApplication;
    
    @ProvidedScenarioState
    Drawing clientsDrawing;
    
    @ProvidedScenarioState
    List<Figure> myUpdatedList;
    /*
    @ProvidedScenarioState
    boolean connected;
    */
    /*public WhenConnection they_are_connected() throws RemoteException, AlreadyBoundException, UnknownHostException {
        assertThat(server).isNotNull();
        assertThat(client).isNotNull();
        
        AbstractApplication app = new DefaultSDIApplication();
        SVGView view = new SVGView();
        view.init();
        app.setActiveView(view);

        app.startServer();

        connected = app.connectToServer(InetAddress.getLocalHost().getHostAddress());
        return this;
    }*/
    
    public WhenUpdatingDrawing the_client_is_waiting_for_updates() {
        assertThat(client).isNotNull();
        assertThat(clientsApplication).isNotNull();
        return this;
    }
    
    public WhenUpdatingDrawing I_add_a_figure() throws RemoteException {
        Figure rectFig = new SVGRectFigure();
        rectFig.setBounds(new Point2D.Double(2, 2), new Point2D.Double(10, 10));
        rectFig.setCollaborationId();
        myList.add(rectFig);
        
        client.update(myList);
        
        clientsDrawing = (((SVGView) clientsApplication.getActiveView()).getDrawing());
        myUpdatedList = myList;  
        return this;
    }
    
    public WhenUpdatingDrawing I_edit_my_drawing() throws RemoteException {
        Figure rectFig = myList.get(0);
        rectFig.setBounds(new Point2D.Double(4, 4), new Point2D.Double(12, 12));
        
        client.update(myList);
        
        clientsDrawing = (((SVGView) clientsApplication.getActiveView()).getDrawing());
        myUpdatedList = myList; 
        return this;
    }
    
    public WhenUpdatingDrawing I_remove_a_figure() throws RemoteException {
        Figure rectFig = myList.get(0);
        myList.remove(rectFig);
        
        client.update(myList);
        
        clientsDrawing = (((SVGView) clientsApplication.getActiveView()).getDrawing());
        myUpdatedList = myList;        
        return this;
    }

}
