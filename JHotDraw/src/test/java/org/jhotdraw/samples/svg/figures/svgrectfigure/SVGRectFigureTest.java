package org.jhotdraw.samples.svg.figures.svgrectfigure;

import org.jhotdraw.geom.Dimension2DDouble;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;

public class SVGRectFigureTest {

    private SVGRectFigure figure;

    @Before
    public void setUp() {
        figure = new SVGRectFigure(0d,0d,100d,100d);
        figure.setArc(30d, 30d);
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
}
