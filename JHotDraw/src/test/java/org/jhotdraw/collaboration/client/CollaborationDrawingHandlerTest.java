/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.collaboration.client;

import java.awt.geom.Point2D;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jhotdraw.app.AbstractApplication;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.samples.svg.SVGView;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.junit.After;
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
    public void addFigureTest() throws RemoteException {
        Figure rectFig = new SVGRectFigure();
        rectFig.setBounds(new Point2D.Double(10, 10), new Point2D.Double(20, 20));
        rectFig.setCollaborationId();

        assertEquals("The drawing does not contains the figure from before test", 1, clientDrawing.getChildCount());
        drawingHandler.addFigure(rectFig);
        assertEquals("The drawing have not, gotten the new figure", 2, clientDrawing.getChildCount());
        assertEquals("The new figure is not, what was created here", rectFig.getCollaborationId(), clientDrawing.getChild(1).getCollaborationId());

    }

    @Test
    public void changeBoundPositionTest() throws RemoteException {
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
    public void changeBoundSizeTest() throws RemoteException {
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
    public void changeAttributesTest() {
        Figure clientFig = (Figure) clientDrawing.getChild(0);
        Figure newAttributeFig = (Figure) clientFig.clone();
        AttributeKey newKey = null;

        System.out.println(clientFig.getAttributes().keySet());
        for (AttributeKey key : clientFig.getAttributes().keySet()) {
            if (newKey == null) {
                newKey = (key.getKey().equals("strokeWidth")) ? key : null;
            }
        }
        newAttributeFig.setAttribute(newKey, 2.0);

        drawingHandler.changeAttributes(clientFig, newAttributeFig);
        assertEquals(newAttributeFig.getAttributes(), clientFig.getAttributes());
    }

    @Test
    public void changeArcTest() {
        SVGRectFigure clientFig = (SVGRectFigure) clientDrawing.getChild(0);
        SVGRectFigure newArcFig = (SVGRectFigure) clientFig.clone();

        assertEquals(0, clientFig.getArc().height, 0.0001);
        assertEquals(0, clientFig.getArc().width, 0.0001);

        newArcFig.setArc(3, 3);
        drawingHandler.changeArc(clientFig, newArcFig);

        assertEquals(3, clientFig.getArc().height, 0.0001);
        assertEquals(3, clientFig.getArc().width, 0.0001);
    }

    @Test
    public void removeFigureTest() throws RemoteException {
        Figure figToBeRemoved = clientDrawing.getChild(0);

        assertEquals("Server list does not have the figure.", 1, clientDrawing.getChildCount());
        drawingHandler.removeFigures(Arrays.asList(figToBeRemoved));
        assertEquals("Server list have not removed the figure", 0, clientDrawing.getChildCount());
    }

    @Test
    public void mergeFigureAddTest() {
        clientDrawing.removeAllChildren();
        List<Figure> otherClientList = new ArrayList<>();
        Figure newFig = new SVGRectFigure();
        newFig.setBounds(new Point2D.Double(10, 10), new Point2D.Double(20, 20));
        newFig.setCollaborationId();
        otherClientList.add(newFig);
        
        drawingHandler.mergeFigures(otherClientList);
        
        assertEquals(otherClientList.size(), clientDrawing.getChildCount());
        assertEquals(otherClientList.get(0).getCollaborationId(), clientDrawing.getChild(0).getCollaborationId());     
    }
    
    @Test
    public void mergeFigureChangeTest() {
        List<Figure> otherClientList = Arrays.asList((Figure) clientDrawing.getChild(0).clone());
        
        otherClientList.get(0).setBounds(new Point2D.Double(4, 4), new Point2D.Double(12, 12));
        
        drawingHandler.mergeFigures(otherClientList);
        
        assertEquals(otherClientList.get(0).getCollaborationId(), clientDrawing.getChild(0).getCollaborationId());
        assertEquals(4, clientDrawing.getChild(0).getBounds().x, 0.0001);
        assertEquals(4, clientDrawing.getChild(0).getBounds().y, 0.0001);      
    }
    
    @Test
    public void mergeFigureRemoveTest() {
        List<Figure> otherClientList = new ArrayList<>();
        otherClientList.add((Figure) clientDrawing.getChild(0).clone());
        
        otherClientList.remove(0);
        
        assertEquals(1, clientDrawing.getChildCount());
        
        drawingHandler.mergeFigures(otherClientList);
        
        assertEquals(otherClientList.size(), clientDrawing.getChildCount());
        assertEquals(0, clientDrawing.getChildCount());
    }
}
