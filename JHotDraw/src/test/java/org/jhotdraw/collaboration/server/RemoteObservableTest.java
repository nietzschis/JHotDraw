package org.jhotdraw.collaboration.server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import org.jhotdraw.collaboration.client.CollaborationConnection;
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
public class RemoteObservableTest {

    private static IRemoteObservable server;
    private IRemoteObserver client;
    private ArgumentCaptor<List<Figure>> argument;

    @BeforeClass
    public static void setUpClass() throws RemoteException {
        server = RemoteObservable.getInstance();
    }

    @AfterClass
    public static void tearDownClass() {
        server = null;
    }

    @Before
    public void setUp() {
        client = mock(CollaborationConnection.class);
        argument = ArgumentCaptor.forClass(List.class);
    }

    @After
    public void tearDown() {
        try {
            ((RemoteObservable) RemoteObservable.getInstance()).clearAllCollaborators();
        }
        catch (RemoteException e) {
        }
        client = null;
        argument = null;
    }

    @Test
    public void testAddCollaborator() throws RemoteException {
        server.addCollaborator(client);
        server.notifyAllCollaborators(new ArrayList<>());

        verify(client, times(1)).update(argument.capture());
    }

    @Test
    public void testRemoveCollaborator() throws RemoteException {
        server.addCollaborator(client);
        server.removeCollaborator(client);
        server.notifyAllCollaborators(new ArrayList<>());

        verify(client, never()).update(argument.capture());
    }

}
