/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.figures.RoundRectangle;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import org.jhotdraw.geom.Dimension2DDouble;
import org.jhotdraw.samples.svg.figures.SVGRoundedRectangle;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jonas
 */
public class SVGRoundedRectangleTest {
    
    SVGRoundedRectangle roundedRectangle;
    
    @Before
    public void setUp() throws Exception
    {
        roundedRectangle = new SVGRoundedRectangle(0,0,100,100,50,50);
    }
    
    @Test
    public void setBounds() {
        roundedRectangle.setBounds(new Point2D.Double(0, 0), new Point2D.Double(20, 20));
        assertNotEquals("Should not be within the bounds", new Dimension2DDouble(60,60), roundedRectangle.getBounds());
    }
    
    @Test
    public void setBounds1() {
        roundedRectangle.setBounds(new Point2D.Double(0, 0), new Point2D.Double(40, 40));
        assertEquals("Should be within the bounds", new Dimension2DDouble(40,40), new Dimension2DDouble(roundedRectangle.getHeight(),roundedRectangle.getWidth()));
    }
    
    @Test
    public void pointWithin() {
        roundedRectangle.setBounds(new Point2D.Double(0, 0), new Point2D.Double(40, 40));
        assertTrue(roundedRectangle.contains(new Point2D.Double(5, 5)));  
    }
    
    @Test
    public void pointWithin1() {
        roundedRectangle.setBounds(new Point2D.Double(0, 0), new Point2D.Double(40, 40));
        assertTrue(!roundedRectangle.contains(new Point2D.Double(54, 55)));  
    }
    
}
