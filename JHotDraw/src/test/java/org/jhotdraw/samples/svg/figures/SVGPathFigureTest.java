/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.figures;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.HashMap;
import javax.swing.Action;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.ConnectionFigure;
import org.jhotdraw.draw.Connector;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.Handle;
import org.jhotdraw.draw.TextHolderFigure;
import org.jhotdraw.draw.Tool;
import org.jhotdraw.geom.Insets2D;
import org.jhotdraw.xml.DOMInput;
import org.jhotdraw.xml.DOMOutput;
import org.jhotdraw.samples.svg.io.DefaultSVGFigureFactory;
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
public class SVGPathFigureTest {
    
    public SVGPathFigureTest() {
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
     * Checks if the bounds of the textPath and the path are contained within the bounds of the FigurePath.
     */
    @Test
    public void testGetFigurePath() {
        Point2D.Double a = new Point2D.Double(0, 0);
        Point2D.Double b = new Point2D.Double(10, 0);
        Point2D.Double c = new Point2D.Double(10, 10);
        Point2D.Double d = new Point2D.Double(0, 10);
        Point2D.Double[] points = new Point2D.Double[]{a,b,c,d};
        DefaultSVGFigureFactory f = new DefaultSVGFigureFactory();
        HashMap<AttributeKey, Object> attributes;
        attributes = new HashMap<AttributeKey, Object>();
        SVGPathFigure instance = (SVGPathFigure) f.createPolygon(points,attributes);
        GeneralPath result = instance.getFigurePath();
        assertEquals(true, result.getBounds2D().contains(instance.getTextPath().getBounds2D())&&result.getBounds2D().contains(instance.getPath().getBounds2D()));
    }

 
    /**
     * Tests if the Figure is within the Drawing area
     */
    @Test
    public void testGetDrawingArea() {
        Point2D.Double a = new Point2D.Double(0, 0);
        Point2D.Double b = new Point2D.Double(10, 0);
        Point2D.Double c = new Point2D.Double(10, 10);
        Point2D.Double d = new Point2D.Double(0, 10);
        Point2D.Double[] points = new Point2D.Double[]{a,b,c,d};
        DefaultSVGFigureFactory f = new DefaultSVGFigureFactory();
        HashMap<AttributeKey, Object> attributes;
        attributes = new HashMap<AttributeKey, Object>();
        SVGPathFigure instance = (SVGPathFigure) f.createPolygon(points,attributes);
        instance.setText("testtest");
        Rectangle2D.Double result = instance.getDrawingArea();
        assertEquals(true, result.contains(instance.getFigurePath().getBounds2D()));
    }

    
     /**
     * Tests if the bounds contain the entire figure
     */
    
    @Test
    public void testGetBounds() {
        System.out.println("getBounds");
        Point2D.Double a = new Point2D.Double(0, 0);
        Point2D.Double b = new Point2D.Double(10, 0);
        Point2D.Double c = new Point2D.Double(10, 10);
        Point2D.Double d = new Point2D.Double(0, 10);
        Point2D.Double[] points = new Point2D.Double[]{a,b,c,d};
        DefaultSVGFigureFactory f = new DefaultSVGFigureFactory();
        HashMap<AttributeKey, Object> attributes;
        attributes = new HashMap<AttributeKey, Object>();
        SVGPathFigure instance = (SVGPathFigure) f.createPolygon(points,attributes);
        Rectangle2D.Double result = instance.getBounds();
        assertEquals(true, result.contains(instance.getFigurePath().getBounds2D()));
    }

}
