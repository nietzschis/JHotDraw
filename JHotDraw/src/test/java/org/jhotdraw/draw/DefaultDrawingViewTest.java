/*
 * k0ngen
 */
package org.jhotdraw.draw;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Set;
import javax.swing.JComponent;
import org.jhotdraw.geom.BezierPath;
import org.jhotdraw.samples.svg.figures.SVGBezierFigure;
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
public class DefaultDrawingViewTest {
    
    DefaultDrawingView view;
    DefaultDrawing drawing;
    DefaultDrawingEditor editor;
    SVGBezierFigure lineFigure;
    SVGBezierFigure bezierLineFigure;
    
    public DefaultDrawingViewTest() {
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
        drawing = new DefaultDrawing();
        editor = new DefaultDrawingEditor();
        
        lineFigure = CreateLineFigure(2);
        bezierLineFigure = CreateLineFigure(6);
        
        editor.setActiveView(view);
        
        view.addNotify(editor);
        view.setDrawing(drawing);
        view.getDrawing().add(lineFigure);
        view.getDrawing().add(bezierLineFigure);
        
        view.selectAll();
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
     * Test of split method, of class DefaultDrawingView.
     */
    @Test
    public void testSplit() {
        System.out.println("split");
        int expResult = 0;
        int result = view.split();
        assertEquals(expResult, result);
    }
    
}
