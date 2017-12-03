package org.jhotdraw.collaboration.steps;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import static org.assertj.core.api.Assertions.assertThat;
import org.jhotdraw.app.AbstractApplication;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.collaboration.client.CollaborationConnection;
import org.jhotdraw.collaboration.server.IServer;
import org.jhotdraw.samples.svg.SVGView;

/**
 *
 * @author Niels & Niclas
 */
public class WhenConnection extends Stage<WhenConnection> {

    @ExpectedScenarioState
    IServer server;

    @ExpectedScenarioState
    CollaborationConnection client;
    
    @ProvidedScenarioState
    boolean connected;

    public WhenConnection they_are_connected() throws RemoteException, AlreadyBoundException, UnknownHostException {
        assertThat(server).isNotNull();
        assertThat(client).isNotNull();
        
        AbstractApplication app = new DefaultSDIApplication();
        SVGView view = new SVGView();
        view.init();
        app.setActiveView(view);

        app.startServer();

        connected = app.connectToServer(InetAddress.getLocalHost().getHostAddress());
        return this;
    }
    
    public WhenConnection I_update_my_drawing() {
        assertThat(connected).isTrue();
        
        
        return this;
    }

}
