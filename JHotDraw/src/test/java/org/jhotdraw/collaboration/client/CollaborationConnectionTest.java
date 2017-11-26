/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.collaboration.client;

import java.awt.geom.Point2D;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import org.jhotdraw.app.AbstractApplication;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.collaboration.common.IRemoteObserver;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.samples.svg.SVGView;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.mockito.Mockito.mock;

/**
 *
 * @author Niclas
 */
public class CollaborationConnectionTest {

    static AbstractApplication server;
    static AbstractApplication client;
    static CollaborationConnection serverCollaboration;
    static Drawing serverDrawing;
    static Drawing clientDrawing;

    @BeforeClass
    public static void beforeClass() throws RemoteException, AlreadyBoundException {
        server = new DefaultSDIApplication();
        client = new DefaultSDIApplication();

        SVGView serverView = new SVGView();
        SVGView clientView = new SVGView();

        serverView.init();
        clientView.init();
        
        serverDrawing = serverView.getDrawing();
        clientDrawing = clientView.getDrawing();

        server.setActiveView(serverView);
        client.setActiveView(clientView);

        serverCollaboration = CollaborationConnection.getInstance();
        serverCollaboration.setDrawing((Drawing) serverDrawing);

        server.startServer();
    }

    @Before
    public void before() {
        Figure rectFig = new SVGRectFigure();
        rectFig.setBounds(new Point2D.Double(2, 2), new Point2D.Double(10, 10));
        clientDrawing.add(rectFig);
        serverDrawing.add(rectFig);
    }
    
    @After
    public void after() {
        clientDrawing.removeAllChildren();
        serverDrawing.removeAllChildren();
    }

    @Test
    public void connectTest() throws UnknownHostException {
        assertTrue("Could not connect to server", client.connectToServer(InetAddress.getLocalHost().getHostAddress()));
    }

    @Test
    public void addFigure() throws RemoteException {
        Figure rectFig = new SVGRectFigure();
        rectFig.setBounds(new Point2D.Double(10, 10), new Point2D.Double(20, 20));
        clientDrawing.add(rectFig);

        assertEquals("Server list is not empty beforehand.", 1, serverDrawing.getChildCount());
        serverCollaboration.update(clientDrawing.getChildren());
        assertEquals("Server list has not gotten the figure.", 2, serverDrawing.getChildCount());
        assertEquals("The figures on the server and client are not the same.", clientDrawing.getChild(1).getCollaborationId(), serverDrawing.getChild(1).getCollaborationId());
    }

    @Test
    public void changeBoundPosition() throws RemoteException {

        Figure figFromServer = serverDrawing.getChildren().get(0);

        assertEquals("The figure is not where it is supposed to be.", 2, figFromServer.getBounds().x, 0.0001);
        assertEquals("The figure is not where it is supposed to be.", 2, figFromServer.getBounds().y, 0.0001);

        Figure figFromClient = clientDrawing.getChild(0);
        figFromClient.setBounds(new Point2D.Double(4, 4), new Point2D.Double(12, 12));
        serverCollaboration.update(clientDrawing.getChildren());

        assertEquals("The figure is not where it is supposed to be.", 4, serverDrawing.getChild(0).getBounds().x, 0.0001);
        assertEquals("The figure is not where it is supposed to be.", 4, serverDrawing.getChild(0).getBounds().y, 0.0001);
    }

    @Test
    public void changeBoundSize() throws RemoteException {

        Figure figFromServer = serverDrawing.getChild(0);

        assertEquals("The figure is not the size it is supposed to be.", 8, figFromServer.getBounds().height, 0.0001);
        assertEquals("The figure is not the size it is supposed to be.", 8, figFromServer.getBounds().width, 0.0001);

        Figure figFromClient = clientDrawing.getChild(0);
        figFromClient.setBounds(new Point2D.Double(2, 2), new Point2D.Double(12, 12));
        serverCollaboration.update(clientDrawing.getChildren());

        assertEquals("The figure is not the size it is supposed to be.", 10, serverDrawing.getChild(0).getBounds().height, 0.0001);
        assertEquals("The figure is not the size it is supposed to be.", 10, serverDrawing.getChild(0).getBounds().width, 0.0001);
    }

    @Test
    public void removeFigure() throws RemoteException {
        Figure figToBeRemoved = clientDrawing.getChild(0);
        assertEquals("Server list does not have the figure.", 1, serverDrawing.getChildCount());
        clientDrawing.remove(figToBeRemoved);
        serverCollaboration.update(clientDrawing.getChildren());
        assertEquals("Server list have not removed the figure", 0, serverDrawing.getChildCount());
    }

    @AfterClass
    public static void teardown() throws RemoteException, NotBoundException {
        server.stopServer();
    }

}
