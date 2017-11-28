/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.figures.svgtrianglefigure;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import org.jhotdraw.draw.ConnectionFigure;
import org.jhotdraw.draw.Connector;
import org.jhotdraw.draw.Handle;
import org.jhotdraw.samples.svg.figures.SVGTriangleFigure;
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
        SVGTriangleFigure instance1 = new SVGTriangleFigure();
        Rectangle2D.Double expResult1 = 
                new Rectangle2D.Double(
                        instance1.getX(), instance1.getY(), 
                        instance1.getWidth(), instance1.getHeight() );
        Rectangle2D.Double result1 = instance1.getBounds();
        assertEquals(expResult1, result1);
    }
    
    @Test
    public void testTriangleFigure2() {
        SVGTriangleFigure instance2 = new SVGTriangleFigure(-5,-5,10,10);
        Rectangle2D.Double expResult2 = 
                new Rectangle2D.Double(
                        instance2.getX(), instance2.getY(), 
                        instance2.getWidth(), instance2.getHeight() );
        Rectangle2D.Double result2 = instance2.getBounds();
        assertEquals(expResult2, result2);
    }
    
    @Test
    public void testTriangleFigure3() {
        SVGTriangleFigure instance3 = new SVGTriangleFigure(2,3,(-4),(-5));
        Rectangle2D.Double expResult3 = 
                new Rectangle2D.Double(
                        instance3.getX(), instance3.getY(), 
                        instance3.getWidth(), instance3.getHeight() );
        Rectangle2D.Double result3 = instance3.getBounds();
        assertEquals(expResult3, result3);
    }

}
