package org.jhotdraw.collaboration.steps;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.collaboration.client.CollaborationConnection;
import org.jhotdraw.collaboration.server.CollaborationServer;
import org.jhotdraw.collaboration.server.IServer;

/**
 *
 * @author Niels & Niclas
 */
public class GivenServerAndClient extends Stage<GivenServerAndClient> {
    
    @ProvidedScenarioState
    IServer server;
    
    @ProvidedScenarioState
    CollaborationConnection client;
    
    public GivenServerAndClient the_server() {
        server = CollaborationServer.getInstance();
        return this;
    }
    
    public GivenServerAndClient the_client() {
        client = CollaborationConnection.getInstance();
        return this;
    }
    
}
