/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import org.jhotdraw.geom.BezierPath;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pc
 */
public class BezierToolTest {
    
    public BezierToolTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getPresentationName method, of class BezierTool.
     */
    @Test
    public void testGetPresentationName() {
        System.out.println("getPresentationName");
        BezierTool instance = null;
        String expResult = "";
        String result = instance.getPresentationName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of activate method, of class BezierTool.
     */
    @Test
    public void testActivate() {
        System.out.println("activate");
        DrawingEditor editor = null;
        BezierTool instance = null;
        instance.activate(editor);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deactivate method, of class BezierTool.
     */
    @Test
    public void testDeactivate() {
        System.out.println("deactivate");
        DrawingEditor editor = null;
        BezierTool instance = null;
        instance.deactivate(editor);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mousePressed method, of class BezierTool.
     */
    @Test
    public void testMousePressed() {
        System.out.println("mousePressed");
        MouseEvent evt = null;
        BezierTool instance = null;
        instance.mousePressed(evt);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createFigure method, of class BezierTool.
     */
    @Test
    public void testCreateFigure() {
        System.out.println("createFigure");
        BezierTool instance = null;
        BezierFigure expResult = null;
        BezierFigure result = instance.createFigure();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCreatedFigure method, of class BezierTool.
     */
    @Test
    public void testGetCreatedFigure() {
        System.out.println("getCreatedFigure");
        BezierTool instance = null;
        Figure expResult = null;
        Figure result = instance.getCreatedFigure();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAddedFigure method, of class BezierTool.
     */
    @Test
    public void testGetAddedFigure() {
        System.out.println("getAddedFigure");
        BezierTool instance = null;
        Figure expResult = null;
        Figure result = instance.getAddedFigure();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addPointToFigure method, of class BezierTool.
     */
    @Test
    public void testAddPointToFigure() {
        System.out.println("addPointToFigure");
        Point2D.Double newPoint = null;
        BezierTool instance = null;
        instance.addPointToFigure(newPoint);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mouseClicked method, of class BezierTool.
     */
    @Test
    public void testMouseClicked() {
        System.out.println("mouseClicked");
        MouseEvent evt = null;
        BezierTool instance = null;
        instance.mouseClicked(evt);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fireUndoEvent method, of class BezierTool.
     */
    @Test
    public void testFireUndoEvent() {
        System.out.println("fireUndoEvent");
        Figure createdFigure = null;
        DrawingView creationView = null;
        BezierTool instance = null;
        instance.fireUndoEvent(createdFigure, creationView);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mouseReleased method, of class BezierTool.
     */
    @Test
    public void testMouseReleased() {
        System.out.println("mouseReleased");
        MouseEvent evt = null;
        BezierTool instance = null;
        instance.mouseReleased(evt);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of finishCreation method, of class BezierTool.
     */
    @Test
    public void testFinishCreation() {
        System.out.println("finishCreation");
        BezierFigure createdFigure = null;
        DrawingView creationView = null;
        BezierTool instance = null;
        instance.finishCreation(createdFigure, creationView);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mouseDragged method, of class BezierTool.
     */
    @Test
    public void testMouseDragged() {
        System.out.println("mouseDragged");
        MouseEvent evt = null;
        BezierTool instance = null;
        instance.mouseDragged(evt);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mouseMoved method, of class BezierTool.
     */
    @Test
    public void testMouseMoved() {
        System.out.println("mouseMoved");
        MouseEvent evt = null;
        BezierTool instance = null;
        instance.mouseMoved(evt);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculateFittedCurve method, of class BezierTool.
     */
    @Test
    public void testCalculateFittedCurve() {
        System.out.println("calculateFittedCurve");
        BezierPath path = null;
        BezierTool instance = null;
        BezierPath expResult = null;
        BezierPath result = instance.calculateFittedCurve(path);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setToolDoneAfterCreation method, of class BezierTool.
     */
    @Test
    public void testSetToolDoneAfterCreation() {
        System.out.println("setToolDoneAfterCreation");
        boolean b = false;
        BezierTool instance = null;
        instance.setToolDoneAfterCreation(b);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isToolDoneAfterCreation method, of class BezierTool.
     */
    @Test
    public void testIsToolDoneAfterCreation() {
        System.out.println("isToolDoneAfterCreation");
        BezierTool instance = null;
        boolean expResult = false;
        boolean result = instance.isToolDoneAfterCreation();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
