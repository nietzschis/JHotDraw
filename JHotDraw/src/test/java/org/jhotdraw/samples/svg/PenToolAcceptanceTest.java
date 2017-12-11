package org.jhotdraw.samples.svg;

import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.junit.SimpleScenarioTest;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.action.DrawingEditorProxy;
import org.jhotdraw.geom.BezierPath;
import org.jhotdraw.samples.svg.figures.SVGBezierFigure;
import org.jhotdraw.samples.svg.figures.SVGPathFigure;
import org.junit.Test;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ľuboš
 */
public class PenToolAcceptanceTest extends SimpleScenarioTest<PenToolAcceptanceTest.Steps> {
    /**
     *  Scenario for acceptance test
     *  generates tool (PenTool),inicialiazes points that gonna be used and controls if drawed line is valid
     */
    @Test
    public void drawingScenario() {
        given().penTool();
        when().drawing();
        then().validateDrawing();
     }
    
    public static class Steps {

        @ProvidedScenarioState
        PenTool penTool;

        @ProvidedScenarioState
        Drawing drawing;

        @ProvidedScenarioState
        DrawingView view;
        
        /**
         * penTool() generates PenTool and propertirs for PenTool
         * inicializes everything needed for simulation
         */
        public void penTool() {
            view = new DefaultDrawingView();
            DrawingEditorProxy editor = new DrawingEditorProxy();
            editor.setTarget(new DefaultDrawingEditor());
            view.addNotify(editor);
            editor.add(view);
            editor.setActiveView(view);
            drawing = new DefaultDrawing();
            view.setDrawing(drawing);
            penTool = new PenTool(new SVGPathFigure(), new SVGBezierFigure(false), null);
            penTool.setToolDoneAfterCreation(false);
            penTool.activate(editor);
        }

        Point[] points = {
                new Point(0,0),
                new Point(11,10),
                new Point(20,22),
                new Point(31,33),
                new Point(40,40),
        };
        /**
         *  drawing() inicailizes points that are used for mouseEvents (mousePressed,mouseDragged and mouseReleased)
         * 
         */
        public void drawing() {

            MouseEvent press = new MouseEvent(view.getComponent(), 0, 0, 0, points[0].x, points[0].y, 1, false);
            penTool.mousePressed(press);
            for (int i = 1; i < points.length; i++) {
                MouseEvent move = new MouseEvent(view.getComponent(), i, 0, 0, points[i].x, points[i].y, 1, false);
                penTool.mouseDragged(move);

            }

            MouseEvent release = new MouseEvent(view.getComponent(), points.length, 0, 0, points[points.length-1].x, points[points.length-1].y, 1, false);
            penTool.mouseReleased(release);
        }
        /**
         * validateDrawing() validates if drawed points are drawed there where they have to be
         */
        
        public void validateDrawing() {
            SVGPathFigure pathFigure = (SVGPathFigure)drawing.getChild(0);
            pathFigure.getChildren();
            SVGBezierFigure bezierFigure = pathFigure.getChild(0);
            
            assertEquals(bezierFigure.getNodeCount(), points.length);

            for (int i = 0; i < points.length; i++) {
                BezierPath.Node node = bezierFigure.getNode(i);
                assertEquals(points[i].x, node.x[0], 1E-10);
                assertEquals(points[i].y, node.y[0], 1E-10);
            }
        }
    }
}
