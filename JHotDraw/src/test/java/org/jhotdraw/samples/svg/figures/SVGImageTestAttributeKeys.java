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
public class SVGImageTestAttributeKeys {

    private SVGImageFigure imgFigure;

    @Before
    public void setUp() {
        imgFigure = new SVGImageFigure(0d, 0d, 5d, 5d);
        imgFigure.setBounds(new Point2D.Double(10, 10), new Point2D.Double(110, 110));
    }

    @Test
    public void setAttributeHeight() {
        
        double testHeight = 200;
        assertFalse(testHeight == imgFigure.getHeight());
        SVGAttributeKeys.FIGURE_HEIGHT.set(imgFigure, testHeight);
        assertTrue(testHeight == imgFigure.getHeight());
    }

    @Test
    public void setAttributeWidth() {
        double testWidth = 200;
        assertFalse(testWidth == imgFigure.getWidth());
        SVGAttributeKeys.FIGURE_WIDTH.set(imgFigure, testWidth);
        assertTrue(testWidth == imgFigure.getWidth());
    }

    
    @Test
    public void setBoundsUpdateKey() {
        imgFigure.setBounds(new Point2D.Double(0, 0), new Point2D.Double(52, 52));
        assertTrue(SVGAttributeKeys.FIGURE_HEIGHT.get(imgFigure) == imgFigure.getHeight());
        assertTrue(SVGAttributeKeys.FIGURE_WIDTH.get(imgFigure) == imgFigure.getWidth());
    }

}
