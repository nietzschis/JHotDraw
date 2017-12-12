/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;

import java.awt.Color;
import java.util.LinkedList;
import javax.swing.AbstractButton;
import org.jhotdraw.app.AbstractApplication;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.DefaultDrawing;
import org.jhotdraw.draw.DefaultDrawingEditor;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.gui.JPopupButton;
import org.jhotdraw.samples.svg.SVGView;
import org.jhotdraw.samples.svg.figures.SVGEllipseFigure;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jebi
 */
public class EyedropperTest {

    SVGEllipseFigure circle;
    DrawingEditor editor;
    DrawingView drawingView;
    AbstractButton popup;
    EyedropperAction eyedropper;
    Drawing drawing;

    @Before
    public void setUp() {

        editor = new DefaultDrawingEditor();
        drawingView = new DefaultDrawingView();
        popup = new JPopupButton();
        drawing = new DefaultDrawing();
        eyedropper = new EyedropperAction(editor, popup);
        ButtonFactory.eyedropped.clear();

        //Creating ellipse and setting the fill color;       
        circle = new SVGEllipseFigure(200, 200, 200, 200);
        circle.setAttribute(AttributeKeys.FILL_COLOR, new Color(0, 0, 255));

        //Setting the drawingview and editor
        eyedropper.getEditor().setActiveView(drawingView);
        eyedropper.setEditor(editor);
    }

    @Test
    public void testIfFillColorIsBlue() {
        assertEquals(circle.getAttribute(AttributeKeys.FILL_COLOR), Color.BLUE);
        assertNotEquals(circle.getAttribute(AttributeKeys.FILL_COLOR), Color.RED);
        assertNotEquals(circle.getAttribute(AttributeKeys.FILL_COLOR), Color.YELLOW);
        assertNotEquals(circle.getAttribute(AttributeKeys.FILL_COLOR), Color.GREEN);

    }

    @Test
    public void testIfThereAreNoEyedroppedColors() {
        //Tests that is no eyedropped colors yet.
        assertEquals(0, ButtonFactory.eyedropped.size());
    }

    @Test
    public void testIfColorIsEyedropped() {
        //Selecting and eyedropping the circle
        drawingView.addToSelection(circle);
        eyedropper.eyedrop();

        //Tests that is 1 eyedropped color. 
        assertEquals(1, ButtonFactory.eyedropped.size());
    }

    @After
    public void tearDown() {
        editor = null;
        drawingView = null;
        popup = null;
        eyedropper = null;
        drawing = null;
    }

}