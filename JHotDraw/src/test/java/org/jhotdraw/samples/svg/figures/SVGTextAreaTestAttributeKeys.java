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
public class SVGTextAreaTestAttributeKeys {

    private SVGTextAreaFigure TextArea;

    @Before
    public void setUp() {
        TextArea = new SVGTextAreaFigure();
        TextArea.setBounds(new Point2D.Double(10, 10), new Point2D.Double(110, 110));
    }

    @Test
    public void setAttributeHeight() {
        
        double testHeight = 200;
        assertFalse(testHeight == TextArea.getBounds().getHeight());
        SVGAttributeKeys.FIGURE_HEIGHT.set(TextArea, testHeight);
        assertTrue(testHeight == TextArea.getBounds().getHeight());
    }

    @Test
    public void setAttributeWidth() {
        double testWidth = 200;
        assertFalse(testWidth == TextArea.getBounds().getWidth());
        SVGAttributeKeys.FIGURE_WIDTH.set(TextArea, testWidth);
        assertTrue(testWidth == TextArea.getBounds().getWidth());
    }

    
    @Test
    public void setBoundsUpdateKey() {
        TextArea.setBounds(new Point2D.Double(0, 0), new Point2D.Double(52, 52));
        assertTrue(SVGAttributeKeys.FIGURE_HEIGHT.get(TextArea) == TextArea.getBounds().getHeight());
        assertTrue(SVGAttributeKeys.FIGURE_WIDTH.get(TextArea) == TextArea.getBounds().getWidth());
    }

}
