/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.figures;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import org.jhotdraw.draw.ConnectionFigure;
import org.jhotdraw.draw.Connector;
import org.jhotdraw.draw.Handle;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mathias
 */
public class SVGTriangleFigureTest {
    
    public SVGTriangleFigureTest() {
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
     * Test of class SVGTriangleFigure.
     * Creating a triangle figure should create
     * a figure in a bounding box
     */
    @Test
    public void testTriangleFigure1() {
        SVGTriangleFigure instance = new SVGTriangleFigure();
        Rectangle2D.Double expResult = 
                new Rectangle2D.Double(
                        instance.getX(), instance.getY(), 
                        instance.getWidth(), instance.getHeight() );
        Rectangle2D.Double result = instance.getBounds();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testTriangleFigure2() {
        SVGTriangleFigure instance = new SVGTriangleFigure(-5,-5,10,10);
        Rectangle2D.Double expResult = 
                new Rectangle2D.Double(
                        instance.getX(), instance.getY(), 
                        instance.getWidth(), instance.getHeight() );
        Rectangle2D.Double result = instance.getBounds();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testTriangleFigure3() {
        SVGTriangleFigure instance = new SVGTriangleFigure(2,3,(-4),(-5));
        Rectangle2D.Double expResult = 
                new Rectangle2D.Double(
                        instance.getX(), instance.getY(), 
                        instance.getWidth(), instance.getHeight() );
        Rectangle2D.Double result = instance.getBounds();
        assertEquals(expResult, result);
    }

}
