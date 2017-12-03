/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.collaboration.client;

import com.google.common.collect.Lists;
import java.awt.geom.Point2D;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;
import org.jhotdraw.app.AbstractApplication;
import org.jhotdraw.app.DefaultSDIApplication;
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
import org.junit.Test;

/**
 *
 * @author Niclas
 */
public class CollaborationDrawingHandlerTest {

    static AbstractApplication clientApplication;
    static Drawing clientDrawing;
    static CollaborationDrawingHandler drawingHandler;

    @BeforeClass
    public static void beforeClass() throws RemoteException, AlreadyBoundException {
        clientApplication = new DefaultSDIApplication();

        SVGView clientView = new SVGView();

        clientView.init();
        
        clientDrawing = clientView.getDrawing();

        clientApplication.setActiveView(clientView);

        drawingHandler = new CollaborationDrawingHandler();
        drawingHandler.setDrawing(clientDrawing);
    }

    @Before
    public void before() {
        Figure rectFig = new SVGRectFigure();
        rectFig.setBounds(new Point2D.Double(2, 2), new Point2D.Double(10, 10));
        clientDrawing.add(rectFig);
    }
    
    @After
    public void after() {
        clientDrawing.removeAllChildren();
    }

    @Test
    public void addFigure() throws RemoteException {
        Figure rectFig = new SVGRectFigure();
        rectFig.setBounds(new Point2D.Double(10, 10), new Point2D.Double(20, 20));
        rectFig.setCollaborationId();
        
        assertEquals("The drawing does not contains the figure from before test", 1, clientDrawing.getChildCount());
        drawingHandler.addFigure(rectFig);
        assertEquals("The drawing have not, gotten the new figure", 2, clientDrawing.getChildCount());
        assertEquals("The new figure is not, what was created here", rectFig.getCollaborationId(), clientDrawing.getChild(1).getCollaborationId());
        
    }

    @Test
    public void changeBoundPosition() throws RemoteException {
        Figure clientFig = clientDrawing.getChild(0);
        Figure newBoundFig = (Figure) clientFig.clone();

        assertEquals("The figure is not where it is supposed to be.", 2, clientFig.getBounds().x, 0.0001);
        assertEquals("The figure is not where it is supposed to be.", 2, clientFig.getBounds().y, 0.0001);

        newBoundFig.setBounds(new Point2D.Double(4, 4), new Point2D.Double(12, 12));
        drawingHandler.changeBounds(clientFig, newBoundFig);

        assertEquals("The figure is not where it is supposed to be.", 4, clientDrawing.getChild(0).getBounds().x, 0.0001);
        assertEquals("The figure is not where it is supposed to be.", 4, clientDrawing.getChild(0).getBounds().y, 0.0001);
    }

    @Test
    public void changeBoundSize() throws RemoteException {
        Figure clientFig = clientDrawing.getChild(0);
        Figure newBoundFig = (Figure) clientFig.clone();

        assertEquals("The figure is not the size it is supposed to be.", 8, clientFig.getBounds().height, 0.0001);
        assertEquals("The figure is not the size it is supposed to be.", 8, clientFig.getBounds().width, 0.0001);

        newBoundFig.setBounds(new Point2D.Double(2, 2), new Point2D.Double(12, 12));
        drawingHandler.changeBounds(clientFig, newBoundFig);
        
        assertEquals("The figure is not the size it is supposed to be.", 10, clientDrawing.getChild(0).getBounds().height, 0.0001);
        assertEquals("The figure is not the size it is supposed to be.", 10, clientDrawing.getChild(0).getBounds().width, 0.0001);
    }
    
    @Test
    public void removeFigure() throws RemoteException {
        Figure figToBeRemoved = clientDrawing.getChild(0);
        
        assertEquals("Server list does not have the figure.", 1, clientDrawing.getChildCount());
        drawingHandler.removeFigures(Arrays.asList(figToBeRemoved));
        assertEquals("Server list have not removed the figure", 0, clientDrawing.getChildCount());
    }
}
