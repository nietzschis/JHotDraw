/*
 * k0ngen
 */
package org.jhotdraw.samples.svg.action;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;
import javax.swing.Action;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoableEdit;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.CompositeFigureListener;
import org.jhotdraw.draw.ConnectionFigure;
import org.jhotdraw.draw.Connector;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.FigureListener;
import org.jhotdraw.draw.Handle;
import org.jhotdraw.draw.InputFormat;
import org.jhotdraw.draw.Layouter;
import org.jhotdraw.draw.OutputFormat;
import org.jhotdraw.draw.QuadTreeDrawing;
import org.jhotdraw.draw.Tool;
import org.jhotdraw.geom.BezierPath;
import org.jhotdraw.geom.Dimension2DDouble;
import org.jhotdraw.samples.svg.figures.SVGBezierFigure;
import org.jhotdraw.xml.DOMInput;
import org.jhotdraw.xml.DOMOutput;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author k0ngen
 */
public class SplitTest {
   
    DefaultDrawingView view;
    Drawing drawing;
    SVGBezierFigure lineFigure;
    SVGBezierFigure beizerLineFigure;
    
    public SplitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        view = new DefaultDrawingView();
        drawing = new QuadTreeDrawing();
        
        lineFigure = CreateLineFigure(2);
        beizerLineFigure = CreateLineFigure(8);
        
        view.setDrawing(drawing);
        view.getDrawing().add(lineFigure);
        view.getDrawing().add(beizerLineFigure);
    }
    
    private SVGBezierFigure CreateLineFigure(int nodes) {
        SVGBezierFigure figure = new SVGBezierFigure();
        
        Double randomCoord = Math.random()*100;
        
        for(int i = 0; i < nodes; i++) {
            Double randomFactor = Math.random()*100;
            Point2D.Double point = new Point2D.Double(randomCoord + randomFactor, randomCoord + randomFactor);
            figure.addNode(new BezierPath.Node(point));
        }
        
        Point2D.Double startPoint = figure.getPoint(0);
        Point2D.Double endPoint = figure.getPoint(figure.getNodeCount() - 1);
        
        figure.setStartPoint(startPoint);
        figure.setEndPoint(endPoint);
        return figure;
    }
    
    @After
    public void tearDown() {
        
    }

    /**
     * Test of isALine method, of class Split.
     */
    @Test
    public void testIsALine() {
        System.out.println("isALine");
        Split split = new Split();
        boolean resultOfALine = split.isALine(lineFigure);
        boolean resultOfANonLine = split.isALine(beizerLineFigure);
        assertTrue(resultOfALine);
        assertFalse(resultOfALine == resultOfANonLine);
    }

    /**
     * Test of line method, of class Split.
     */
    @Test
    public void testLine() {
        System.out.println("line");
        Split split = new Split();
        int expResult = 0;
        int resultOfLineSplit = split.line(lineFigure, view);
        int resultOfNonLineSplit = split.line(beizerLineFigure, view);
        assertEquals(expResult, resultOfLineSplit);
        assertFalse(resultOfLineSplit == -1);
        assertTrue(resultOfLineSplit != resultOfNonLineSplit);
    }

    /**
     * Test of fromCenter method, of class Split.
     */
    @Test
    public void testFromCenter() {
        System.out.println("fromCenter");
        Split split = new Split();
        int expResult = 0;
        int result = split.fromCenter(beizerLineFigure, view);
        assertEquals(expResult, result);
        assertFalse(result == -1);
    }
    
}
