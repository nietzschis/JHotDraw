/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg;

import org.jhotdraw.draw.*;
import org.jhotdraw.draw.action.DrawingEditorProxy;
import org.jhotdraw.samples.svg.figures.SVGBezierFigure;
import org.jhotdraw.samples.svg.figures.SVGPathFigure;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.event.MouseEvent;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Ľuboš
 */
public class PenToolTest {

    PenTool penTool;

    @Before
    public void setUp() {
        penTool = new PenTool(new SVGPathFigure(), new SVGBezierFigure(false), null);
        penTool.setToolDoneAfterCreation(false);

    }


    @Test
    public void mouseReleasedTest() {

        DrawingView view = new DefaultDrawingView();
        DrawingEditorProxy editor = new DrawingEditorProxy();
        editor.setTarget(new DefaultDrawingEditor());
        view.addNotify(editor);
        editor.add(view);
        editor.setActiveView(view);
        Drawing drawing = new DefaultDrawing();
        view.setDrawing(drawing);


        penTool.activate(editor);
        MouseEvent press = new MouseEvent(view.getComponent(), 0, 0, 0, 50, 50, 1, false);
        MouseEvent move = new MouseEvent(view.getComponent(), 1, 0, 0, 75, 75, 1, false);
        MouseEvent release = new MouseEvent(view.getComponent(), 2, 0, 0, 100, 100, 1, false);

        penTool.mousePressed(press);
        penTool.mouseDragged(move);
        penTool.mouseReleased(release);

        assertEquals("Failed to create drawing", 1, drawing.getChildren().size());
    }
}
