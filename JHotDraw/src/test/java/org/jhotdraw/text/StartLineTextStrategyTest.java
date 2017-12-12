/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.text;

import java.awt.Font;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sebastian
 */
public class StartLineTextStrategyTest {
    
    public StartLineTextStrategyTest() {
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
     * Tests if the produced shape from textShape, is drawn by the shape it was called with.
     */
    @Test
    public void testTextShape() {
        Shape shape = null;
        Font font = new Font("Garamond", Font.BOLD | Font.ITALIC , 30);
        StartLineTextStrategy instance = new StartLineTextStrategy("test", font);
        Path2D p = new Path2D.Double();
        p.moveTo(10, 10);
        p.lineTo(10, 100);
        shape = p.createTransformedShape(null);
        Rectangle2D r = new Rectangle2D.Double(11,10,10,90);
        Shape result = instance.textShape(shape);
        assertEquals(true, result.intersects(r));
    }
    
}
