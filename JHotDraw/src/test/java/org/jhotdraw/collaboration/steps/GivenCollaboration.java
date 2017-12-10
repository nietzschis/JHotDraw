package org.jhotdraw.collaboration.steps;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import org.jhotdraw.app.AbstractApplication;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.collaboration.client.CollaborationConnection;
import org.jhotdraw.collaboration.common.IRemoteObservable;
import org.jhotdraw.collaboration.server.RemoteObservable;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.samples.svg.SVGView;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.CALLS_REAL_METHODS;

/**
 *
 * @author Niels & Niclas
 */
public class GivenCollaboration extends Stage<GivenCollaboration> {
    
    @ProvidedScenarioState
    IRemoteObservable server;
    
    @ProvidedScenarioState
    CollaborationConnection client;
    
    @ProvidedScenarioState
    List<Figure> myList;
    
    @ProvidedScenarioState
    AbstractApplication clientsApplication;
    
    public GivenCollaboration the_server() throws RemoteException {
        server = RemoteObservable.getInstance();
        return this;
    }
    
    public GivenCollaboration the_mock_client() throws RemoteException {
        client = mock(CollaborationConnection.class, CALLS_REAL_METHODS);
        client.setName("Mock client");
        
        return this;
    }
    
    public GivenCollaboration my_drawing() {
       myList = new ArrayList<>();
       
       return this;
    }
    
    public GivenCollaboration the_client() {
        
        AbstractApplication app = new DefaultSDIApplication();
        SVGView view = new SVGView();
        view.init();
        app.setActiveView(view);
        client = CollaborationConnection.getInstance();
        client.setDrawing(((SVGView) app.getActiveView()).getDrawing());
        
        clientsApplication = app;
        return this;
    }
    
}
