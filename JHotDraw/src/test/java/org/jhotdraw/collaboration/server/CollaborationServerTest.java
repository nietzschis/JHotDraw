package org.jhotdraw.collaboration.server;

import java.rmi.AlreadyBoundException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;
import java.util.List;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.collaboration.client.CollaborationConnection;
import org.jhotdraw.collaboration.common.CollaborationConfig;
import org.jhotdraw.collaboration.common.IRemoteObservable;
import org.jhotdraw.collaboration.common.IRemoteObserver;
import org.jhotdraw.draw.Figure;
import org.junit.After;
import org.junit.Before;
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
    private IRemoteObservable server;

    @Before
    public void init() throws RemoteException {
        app = new DefaultSDIApplication();
        server = (IRemoteObservable) CollaborationServer.getInstance();
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
    }

    //IServer interface
    @Test(expected = ExportException.class)
    public void testStartServer() throws RemoteException, AlreadyBoundException {
        app.startServer();

        LocateRegistry.createRegistry(CollaborationConfig.PORT).bind(CollaborationConfig.NAME, (Remote) CollaborationServer.getInstance());
    }

    @Test(expected = NotBoundException.class)
    public void testStopServer() throws RemoteException, NotBoundException, AlreadyBoundException {
        app.startServer();
        app.stopServer();

        LocateRegistry.getRegistry(CollaborationConfig.PORT).lookup(CollaborationConfig.NAME);
    }
    
    @Test
    public void testAddCollaborator() throws RemoteException {
        IRemoteObserver client = mock(CollaborationConnection.class);
        ArgumentCaptor<List<Figure>> argument = ArgumentCaptor.forClass(List.class);

        server.addCollaborator(client);
        server.notifyAllCollaborators(argument.capture());

        verify(client, times(1)).update(argument.capture());
    }
    
    @Test
    public void testRemoveCollaborator() throws RemoteException {
        IRemoteObserver client = mock(CollaborationConnection.class);
        ArgumentCaptor<List<Figure>> argument = ArgumentCaptor.forClass(List.class);

        server.addCollaborator(client);
        server.removeCollaborator(client);
        server.notifyAllCollaborators(argument.capture());

        verify(client, never()).update(argument.capture());
    }

}
