/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw;

import java.awt.Component;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.jhotdraw.samples.svg.figures.SVGPathFigure;
import org.junit.Assert;

/**
 * Unit Test
 * @author karim
 */
public class FigurePainterTest {
    
    private FigurePainter painter;
    private int rightMouse;
    private int leftMouse;
    private DefaultDrawingEditor editor;
    private Figure figure;
    
    @Before
    public void setUp() {
        painter = new FigurePainter();
        rightMouse = 1;
        leftMouse = 3;
        editor = new DefaultDrawingEditor();
        figure = new SVGPathFigure();
    }
    
    @Test
    public void paintTestLeftMouse() {
        editor.applyDefaultAttributesTo(figure);
        painter.paint(leftMouse, editor);
        Assert.assertEquals(figure.getAttribute(AttributeKeys.STROKE_COLOR), figure.getAttribute(AttributeKeys.STROKE_COLOR_LEFT_MOUSE));
    }
    
    @Test
    public void paintTestRightMouse() {
        editor.applyDefaultAttributesTo(figure);
        painter.paint(rightMouse, editor);
        Assert.assertEquals(figure.getAttribute(AttributeKeys.STROKE_COLOR), figure.getAttribute(AttributeKeys.STROKE_COLOR_RIGHT_MOUSE));
    }
    
    
    @After
    public void tearDown() {
        painter = null;
        editor = null;
        figure = null;
    }
}