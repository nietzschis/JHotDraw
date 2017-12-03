package org.jhotdraw.collaboration.steps;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import java.util.ArrayList;
import java.util.List;
import org.jhotdraw.app.AbstractApplication;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.collaboration.client.CollaborationConnection;
import org.jhotdraw.collaboration.server.CollaborationServer;
import org.jhotdraw.collaboration.server.IServer;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.samples.svg.SVGView;

/**
 *
 * @author Niels & Niclas
 */
public class GivenAClient extends Stage<GivenAClient> {
    
    /*@ProvidedScenarioState
    IServer server;
    */
    @ProvidedScenarioState
    CollaborationConnection client;
    
    @ProvidedScenarioState
    List<Figure> myList;
    
    @ProvidedScenarioState
    AbstractApplication clientsApplication;
    
    /*public GivenServerAndClient the_server() {
        server = CollaborationServer.getInstance();
        return this;
    }*/
    
    public GivenAClient my_drawing() {
       myList = new ArrayList<>();
       
       return this;
    }
    
    public GivenAClient the_client() {
        
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
