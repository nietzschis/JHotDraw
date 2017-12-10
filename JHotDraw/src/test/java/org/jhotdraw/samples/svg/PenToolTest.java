/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg;

import java.awt.event.MouseEvent;
import org.jhotdraw.draw.BezierFigure;
import org.jhotdraw.draw.DrawingView;
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
public class PenToolTest {
    
    public PenToolTest() {
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
     * Test of mouseReleased method, of class PenTool.
     */
    @org.junit.Test
    public void testMouseReleased() {
        System.out.println("mouseReleased");
        MouseEvent evt = null;
        PenTool instance = null;
        instance.mouseReleased(evt);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of finishCreation method, of class PenTool.
     */
    @org.junit.Test
    public void testFinishCreation() {
        System.out.println("finishCreation");
        BezierFigure createdFigure = null;
        DrawingView creationView = null;
        PenTool instance = null;
        instance.finishCreation(createdFigure, creationView);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
