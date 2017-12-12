package org.jhotdraw.samples.svg.figures;

import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import org.jhotdraw.geom.Dimension2DDouble;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import org.jhotdraw.samples.svg.SVGAttributeKeys;

public class SVGRectFigureTest {

    private SVGRectFigure figure;

    @Before
    public void setUp() {
        figure = new SVGRectFigure(0d,0d,100d,100d);
        figure.setArc(30d, 30d);
        RoundRectangle2D.Double shadowfigure = new RoundRectangle2D.Double(150d,150d,50d,50d,0d,0d);
    }

    private Dimension2DDouble rectToDim(Rectangle2D.Double rect)
    {
        return new Dimension2DDouble(rect.width / 2d, rect.height / 2d);
    }

    @Test
    public void setArc() {
        figure.setArc(200d, 200d);
        Dimension2DDouble dim = rectToDim(figure.getBounds());
        assertEquals("Arc cant be bigger than half of rect bounds!", dim, figure.getArc());
    }

    @Test
    public void setArc1() {
        figure.setArc(-200d, -200d);
        assertEquals("Arc cant be smaller then zero!", new Dimension2DDouble(0d,0d), figure.getArc());
    }

    @Test
    public void setArc2() {
        figure.setArc(30d, 30d);
        assertNotEquals("Arc should be half of values set in bounds!", new Dimension2DDouble(15d,15d), figure.getArc());
    }

    @Test
    public void setBounds() {
        figure.setBounds(new Point2D.Double(0d, 0d), new Point2D.Double(200d, 200d));
        assertEquals("Arc should grow with bounds!", new Dimension2DDouble(60d,60d), figure.getArc());
    }

    @Test
    public void setBounds1() {
        figure.setBounds(new Point2D.Double(0d, 0d), new Point2D.Double(50d, 50d));
        assertEquals("Arc should shrink with bounds!", new Dimension2DDouble(15d,15d), figure.getArc());
    }
    
    
    
    
    @Test
    public void setShadow(){
        double shadowWidth = 10d;
        
        SVGAttributeKeys.SHADOWS.set(figure, shadowWidth);
        
        
        GeneralPath expecResult1 = new GeneralPath();
        expecResult1.moveTo(figure.getX() /*+ shadowWidth*/, figure.getY()); //PLACEMENT
        expecResult1.lineTo(figure.getX() + shadowWidth, figure.getY() - shadowWidth); //UP
        expecResult1.lineTo(figure.getX() + figure.getX() + shadowWidth, figure.getY() - shadowWidth); //RIGHT
        expecResult1.lineTo(figure.getX() + figure.getWidth() + shadowWidth, figure.getY() + figure.getHeight() - shadowWidth); //DOWN
        expecResult1.lineTo(figure.getX() + figure.getWidth(), figure.getY() + figure.getHeight() /* - shadowWidth*/); // LEFT
        expecResult1.lineTo(figure.getX() + figure.getWidth(), figure.getY() ); //UP
        expecResult1.lineTo(figure.getX(), figure.getY()); // LEFT            
        expecResult1.closePath();
        
        assertEquals(expecResult1.getBounds2D(), figure.pathShadow(figure.getX(),figure.getY(),figure.getWidth(),figure.getHeight()).getBounds2D());
    }
}
