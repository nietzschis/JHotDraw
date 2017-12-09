package org.jhotdraw.collaboration.server;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;
import org.jhotdraw.collaboration.common.CollaborationConfig;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Niels
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CollaborationServerTest {

    private static IServer server;

    @BeforeClass
    public static void setUpClass() {
        server = CollaborationServer.getInstance();
    }

    @AfterClass
    public static void tearDownClass() {
        server = null;
    }

    @After
    public void tearDown() {
        try {
            server.stopServer();
        }
        catch (RemoteException | NotBoundException e) {
        }
    }

    @Test(expected = ExportException.class)
    public void testStartServer() throws RemoteException, AlreadyBoundException {
        server.startServer();
        server.startServer();
    }

    @Test(expected = NotBoundException.class)
    public void testStopServerWhenServerIsRunning() throws RemoteException, NotBoundException, AlreadyBoundException {
        server.startServer();
        
        server.stopServer();

        LocateRegistry.getRegistry(CollaborationConfig.PORT).lookup(CollaborationConfig.NAME);
    }
    
    @Test(expected = NotBoundException.class)
    public void testStopServerWhenServerIsNotRunning() throws RemoteException, NotBoundException {
        server.stopServer();
    }

}
