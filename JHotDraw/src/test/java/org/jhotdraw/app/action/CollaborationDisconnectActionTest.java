/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import org.jhotdraw.app.AbstractApplication;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.samples.svg.SVGView;
import org.junit.After;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Niclas
 */
public class CollaborationDisconnectActionTest {
    AbstractApplication app;
     
    @Before
    public void setup() throws RemoteException, AlreadyBoundException {
        app = new DefaultSDIApplication();
        SVGView view = new SVGView();
        view.init();
        app.setActiveView(view);
        
        app.startServer();
    }
    
    @Test
    public void menuEnabledTest() throws UnknownHostException {
        
        AbstractApplicationAction disconnectAction = new CollaborationDisconnectAction(app);
                        
        assertFalse(disconnectAction.isEnabled());
        app.connectToServer(InetAddress.getLocalHost().getHostAddress());
        assertTrue(disconnectAction.isEnabled());
        app.disconnectFromServer();
        assertFalse(disconnectAction.isEnabled());
    }
    
    @After
    public void teardown() throws RemoteException, NotBoundException {
        app.stopServer();
    }
}
