package org.jhotdraw.samples.svg.figures;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import org.jhotdraw.samples.svg.SVGAttributeKeys;

/**
 *
 * @author Aleksander
 */
public class SVGEllipseTestAttributeKeys {

    private SVGEllipseFigure ellipseFigure;

    @Before
    public void setUp() {
        ellipseFigure = new SVGEllipseFigure(0d, 0d, 5d, 5d);
        ellipseFigure.setBounds(new Point2D.Double(10, 10), new Point2D.Double(110, 110));
    }

    @Test
    public void setAttributeHeight() {

        double testHeight = 200;
        assertFalse(testHeight == ellipseFigure.getHeight());
        SVGAttributeKeys.FIGURE_HEIGHT.set(ellipseFigure, testHeight);
        assertTrue(testHeight == ellipseFigure.getHeight());
    }

    @Test
    public void setAttributeWidth() {
        double testWidth = 200;
        assertFalse(testWidth == ellipseFigure.getWidth());
        SVGAttributeKeys.FIGURE_WIDTH.set(ellipseFigure, testWidth);
        assertTrue(testWidth == ellipseFigure.getWidth());
    }

    @Test
    public void setBoundsUpdateKey() {
        ellipseFigure.setBounds(new Point2D.Double(0, 0), new Point2D.Double(52, 52));
        assertTrue(SVGAttributeKeys.FIGURE_HEIGHT.get(ellipseFigure) == ellipseFigure.getHeight());
        assertTrue(SVGAttributeKeys.FIGURE_WIDTH.get(ellipseFigure) == ellipseFigure.getWidth());
    }

}
