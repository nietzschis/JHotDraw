/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.io;

import java.util.Map;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.Figure;
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
public class DefaultSVGFigureFactoryTest {
    
    public DefaultSVGFigureFactoryTest() {
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
     * Test of createTriangle method, of class DefaultSVGFigureFactory.
     */
    @Test
    public void testCreateTriangle() {
        double x1 = 0.0;
        double y1 = 0.0;
        double w1 = 5.0;
        double h1 = 2.0;
        
        double x2 = 1.0;
        double y2 = 1.0;
        double w2 = 19.0;
        double h2 = 1.0;
        
        double x3 = -3.0;
        double y3 = -3.0;
        double w3 = 9.0;
        double h3 = 24.0;
        
        DefaultSVGFigureFactory instance = new DefaultSVGFigureFactory();
        
        Figure expResult1 = new SVGTriangleFigure(x1, y1, w1, h1);
        Figure expResult2 = new SVGTriangleFigure(x2, y2, w2, h2);
        Figure expResult3 = new SVGTriangleFigure(x3, y3, w3, h3);
        
        Map<AttributeKey, Object> a = expResult1.getAttributes();
        
        Figure result1 = instance.createTriangle(x1, y1, w1, h1, a);
        Figure result2 = instance.createTriangle(x2, y2, w2, h2, a);
        Figure result3 = instance.createTriangle(x3, y3, w3, h3, a);
       
        assertEquals(expResult1.getBounds(), result1.getBounds());
        assertEquals(expResult1.getDrawingArea(), result1.getDrawingArea());
        
        assertEquals(expResult2.getBounds(), result2.getBounds());
        assertEquals(expResult2.getDrawingArea(), result2.getDrawingArea());
        
        assertEquals(expResult3.getBounds(), result3.getBounds());
        assertEquals(expResult3.getDrawingArea(), result3.getDrawingArea());

    }
    
}
