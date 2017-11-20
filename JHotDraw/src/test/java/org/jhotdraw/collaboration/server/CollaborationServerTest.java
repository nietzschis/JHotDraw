package org.jhotdraw.collaboration.server;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.collaboration.common.CollaborationConfig;
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

    private static Application app;

    @BeforeClass
    public static void init() {
        app = new DefaultSDIApplication();
    }

    @Test(expected = ExportException.class)
    public void testStartServer() throws RemoteException, AlreadyBoundException {
        app.startServer();

        LocateRegistry.createRegistry(CollaborationConfig.PORT).bind(CollaborationConfig.NAME, (Remote) CollaborationServer.getInstance());
    }

    @Test(expected = NotBoundException.class)
    public void testStopServer() throws RemoteException, NotBoundException {
        app.stopServer();

        LocateRegistry.getRegistry(CollaborationConfig.PORT).lookup(CollaborationConfig.NAME);
    }

}
