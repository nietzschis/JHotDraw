/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.graph;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import org.jhotdraw.geom.BezierPath;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Joachim
 */
public class GraphClassTest {
    
    public GraphClassTest() {
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
     * Test of generatePath method, of class GraphClass.
     */
    @Test
    public void testGeneratePath() {
        System.out.println("generatePath");
        GraphClass instance = new GraphClass();
        BezierPath expResult = null;
        BezierPath result = instance.generatePath(new QuadraticGraph(0.05, 0,0));
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of canConnect method, of class GraphClass.
     */
    @Test
    public void testCanConnect() {
        System.out.println("canConnect");
        GraphClass instance = new GraphClass();
        boolean expResult = false;
        boolean result = instance.canConnect();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setBounds method, of class GraphClass.
     */
    @Test
    public void testSetBounds() {
        System.out.println("setBounds");
        Point2D.Double anchor = null;
        Point2D.Double lead = null;
        GraphClass instance = new GraphClass();
        instance.setBounds(anchor, lead);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of drawFill method, of class GraphClass.
     */
    @Test
    public void testDrawFill() {
        System.out.println("drawFill");
        Graphics2D g = null;
        GraphClass instance = new GraphClass();
        instance.drawFill(g);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of drawStroke method, of class GraphClass.
     */
    @Test
    public void testDrawStroke() {
        System.out.println("drawStroke");
        Graphics2D g = null;
        GraphClass instance = new GraphClass();
        instance.drawStroke(g);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isClosed method, of class GraphClass.
     */
    @Test
    public void testIsClosed() {
        System.out.println("isClosed");
        GraphClass instance = new GraphClass();
        boolean expResult = false;
        boolean result = instance.isClosed();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setClosed method, of class GraphClass.
     */
    @Test
    public void testSetClosed() {
        System.out.println("setClosed");
        boolean newValue = false;
        GraphClass instance = new GraphClass();
        instance.setClosed(newValue);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBounds method, of class GraphClass.
     */
    @Test
    public void testGetBounds() {
        System.out.println("getBounds");
        GraphClass instance = new GraphClass();
        Rectangle2D.Double expResult = null;
        Rectangle2D.Double result = instance.getBounds();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
