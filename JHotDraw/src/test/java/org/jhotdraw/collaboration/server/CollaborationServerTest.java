package org.jhotdraw.collaboration.server;

import java.rmi.AlreadyBoundException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.collaboration.common.CollaborationConfig;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
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
    public static void initClass() {
        server = CollaborationServer.getInstance();
    }

    @Before
    public void init() throws RemoteException {
        try {
            server.startServer();
        }
        catch (AlreadyBoundException e) {
        }
    }

    @After
    public void destroy() {
        try {
            server.stopServer();
        }
        catch (RemoteException | NotBoundException e) {
        }
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
    public void testStopServer() throws RemoteException, NotBoundException {
        server.stopServer();

        LocateRegistry.getRegistry(CollaborationConfig.PORT).lookup(CollaborationConfig.NAME);
    }

}
