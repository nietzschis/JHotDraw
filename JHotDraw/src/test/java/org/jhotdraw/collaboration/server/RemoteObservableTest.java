package org.jhotdraw.collaboration.server;

import java.awt.geom.Point2D;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import org.jhotdraw.collaboration.client.CollaborationConnection;
import org.jhotdraw.collaboration.common.IRemoteObservable;
import org.jhotdraw.collaboration.common.IRemoteObserver;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
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
import static org.mockito.Mockito.when;

/**
 *
 * @author Niels
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RemoteObservableTest {

    private static IRemoteObservable server;
    private IRemoteObserver client, client2;
    private List<Figure> argument;
    private ArgumentCaptor<List<Figure>> argumentCaptor;

    @BeforeClass
    public static void setUpClass() throws RemoteException {
        server = RemoteObservable.getInstance();
    }

    @AfterClass
    public static void tearDownClass() {
        server = null;
    }

    @Before
    public void setUp() throws RemoteException {
        client = mock(CollaborationConnection.class);
        when(client.getName()).thenReturn("client1");
        client2 = mock(CollaborationConnection.class);
        when(client2.getName()).thenReturn("client2");
        argument = new ArrayList<>();
        argumentCaptor = ArgumentCaptor.forClass(List.class);
    }

    @After
    public void tearDown() {
        ((RemoteObservable) server).clearAllCollaborators();
        client = null;
        client2 = null;
        argument = null;
        argumentCaptor = null;
    }

    @Test
    public void testAddCollaborator() throws RemoteException {
        server.notifyAllCollaborators(argument);
        verify(client, never()).update(argument);
        
        server.addCollaborator(client);
        
        server.notifyAllCollaborators(argument);
        verify(client, times(1)).update(argument);
    }

    @Test
    public void testRemoveCollaborator() throws RemoteException {
        server.notifyAllCollaborators(argument);
        verify(client, never()).update(argument);
        
        server.addCollaborator(client);
        server.removeCollaborator(client);
        
        server.notifyAllCollaborators(argument);
        verify(client, never()).update(argument);
    }

    @Test
    public void testClearCollaborators() throws RemoteException {
        server.addCollaborator(client);
        server.addCollaborator(client2);
        
        ((RemoteObservable) server).clearAllCollaborators();
        
        server.notifyAllCollaborators(new ArrayList<>());
        verify(client, never()).update(argument);
        verify(client2, never()).update(argument);
    }
    
    @Test
    public void testNotifyAllCollaborators() throws RemoteException {
        Figure figure = new SVGRectFigure();
        figure.setBounds(new Point2D.Double(2, 2), new Point2D.Double(10, 10));
        figure.setCollaborationId();
        argument.add(figure);
        
        server.addCollaborator(client);
        server.addCollaborator(client2);
        
        server.notifyAllCollaborators(argument);
        
        verify(client, times(1)).update(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue(), argument);
        
        verify(client2, times(1)).update(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue(), argument);
    }
    
    @Test
    public void testGetCollaboratorNames() throws RemoteException {
        server.addCollaborator(client);
        server.addCollaborator(client2);
        
        String[] names = ((RemoteObservable) server).getCollaboratorNames().split("\n");
        
        assertEquals("client1", names[0]);
        assertEquals("client2", names[1]);
    }

}
