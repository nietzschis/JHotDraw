package org.jhotdraw.collaboration.server;

import java.rmi.AlreadyBoundException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.collaboration.client.CollaborationConnection;
import org.jhotdraw.collaboration.common.CollaborationConfig;
import org.jhotdraw.collaboration.common.IRemoteObservable;
import org.jhotdraw.collaboration.common.IRemoteObserver;
import org.jhotdraw.draw.Figure;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 *
 * @author Niels
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CollaborationServerTest {

    private Application app;
    private static IServer server;
    
    @BeforeClass
    public static void initClass() {
        server = CollaborationServer.getInstance();
    }

    @Before
    public void init() throws RemoteException {
        app = new DefaultSDIApplication();
        try {
            app.startServer();
        }
        catch (AlreadyBoundException ex) {
        }
    }

    @After
    public void destroy() {
        try {
            app.stopServer();
        }
        catch(ConnectException e) {
        }
        catch(RemoteException | NotBoundException e) {
        }
        app = null;
    }
    
    @AfterClass
    public static void destroyClass() {
        server = null;
    }
    
    @Test(expected = ExportException.class)
    public void testStartServer() throws RemoteException, AlreadyBoundException {
        LocateRegistry.createRegistry(CollaborationConfig.PORT).bind(CollaborationConfig.NAME, (Remote) CollaborationServer.getInstance());
    }

    @Test(expected = NotBoundException.class)
    public void testStopServer() throws RemoteException, NotBoundException, AlreadyBoundException {
        app.stopServer();

        LocateRegistry.getRegistry(CollaborationConfig.PORT).lookup(CollaborationConfig.NAME);
    }
    
    

}
