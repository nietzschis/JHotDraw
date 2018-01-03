package org.jhotdraw.samples.svg.figures.svgrectfigure;

import org.jhotdraw.geom.Dimension2DDouble;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import org.jhotdraw.samples.svg.SVGAttributeKeys;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;

/**
 *
 * @author Aleksander
 */
public class SVGRectTestAttributeKeys {

    private SVGRectFigure rectFigure;

    @Before
    public void setUp() {
        rectFigure = new SVGRectFigure(0d, 0d, 5d, 5d);
        rectFigure.setBounds(new Point2D.Double(10, 10), new Point2D.Double(110, 110));
    }

    @Test
    public void setAttributeHeight() {
        
        double testHeight = 200;
        assertFalse(testHeight == rectFigure.getHeight());
        SVGAttributeKeys.FIGURE_HEIGHT.set(rectFigure, testHeight);
        assertTrue(testHeight == rectFigure.getHeight());
    }

    @Test
    public void setAttributeWidth() {
        double testWidth = 200;
        assertFalse(testWidth == rectFigure.getWidth());
        SVGAttributeKeys.FIGURE_WIDTH.set(rectFigure, testWidth);
        assertTrue(testWidth == rectFigure.getWidth());
    }

    
    @Test
    public void setBoundsUpdateKey() {
        rectFigure.setBounds(new Point2D.Double(0, 0), new Point2D.Double(52, 52));
        assertTrue(SVGAttributeKeys.FIGURE_HEIGHT.get(rectFigure) == rectFigure.getHeight());
        assertTrue(SVGAttributeKeys.FIGURE_WIDTH.get(rectFigure) == rectFigure.getWidth());
    }

}
