package org.jhotdraw.draw;

import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Niels
 */
public class QuadTreeDrawingTest {
    
    private static Drawing drawing;
    private static Figure figure;
    
    @BeforeClass
    public static void init() {
        drawing = new QuadTreeDrawing();
        figure = new SVGRectFigure();
    }
    
    @Test
    public void testAdd() {
        drawing.basicAdd(0, figure);
        assertEquals(figure, drawing.getChild(0));
    }
    
    @Test
    public void testRemove() {
        assertEquals(figure, drawing.basicRemoveChild(0));
    }
    
}
