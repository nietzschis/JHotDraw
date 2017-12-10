/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw;

import org.junit.Assert;
import org.jhotdraw.geom.Dimension2DDouble;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Sadik
 */
public class SimpleDrawingViewTest {
    
    private SimpleDrawing drawing;
    private Figure f;
    public SimpleDrawingViewTest() {
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
     * Test of flip method, of class DefaultDrawingView.
     */
    @Test
    public void testFlip() {
        System.out.println("flip");
        DefaultDrawingView instance = new DefaultDrawingView();
        SimpleDrawing drawing = new SimpleDrawing();
        
        Dimension2DDouble dimension = new Dimension2DDouble(10, 10);
        drawing.setCanvasSize(dimension);
        
        Figure f = new SVGRectFigure(1, 1, 1, 1);
        
        Assert.assertEquals(0, drawing.getChildCount()); //make sure theres no figures
        
        
        drawing.add(f); //adding figure to drawing/canvas. 
        instance.setDrawing(drawing); //"attach" canvas to view instance
        
        Assert.assertEquals(1, drawing.getChildCount()); //add first figure
        
        instance.toggleSelection(f); //select the figure
        instance.flip("Vertical");//flip the selected figure
        instance.removeFromSelection(f);
        
        Assert.assertEquals(2, drawing.getChildCount()); //check flipped is within canvas with other figure
        
        //---------------------------

        instance.toggleSelection(f);
        instance.flip("Vertical");
        instance.removeFromSelection(f);
        
        Figure f_flip = drawing.getChild(2);
                
        Assert.assertNotSame(f.getDrawingArea().getY(), f_flip.getDrawingArea().getY()); //One is flipped, so not identical

    }
}
