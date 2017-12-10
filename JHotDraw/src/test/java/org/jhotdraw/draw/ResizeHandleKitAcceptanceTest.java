package org.jhotdraw.draw;

import com.tngtech.jgiven.annotation.As;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.junit.SimpleScenarioTest;
import javafx.util.Pair;
import org.jhotdraw.draw.action.DrawingEditorProxy;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import static org.jhotdraw.draw.ResizeHandleKit.*;
import static org.jhotdraw.draw.ResizeHandleKitTest.*;
import static org.junit.Assert.assertEquals;

public class ResizeHandleKitAcceptanceTest  extends SimpleScenarioTest<ResizeHandleKitAcceptanceTest.Steps> {

    @Test
    public void mouseDragScenario() {
        Point[] points = {
                new Point(0,0), // north east from rect
                new Point(200,200), // south west from rect
        };

        for (int i = 0; i < HandleDirections.values().length; i++) {
            for (Point lead : points) {
                HandleDirections dir = HandleDirections.values()[i];
                given().figure(50d,50d,50d,50d);
                when().mouseDragged(dir, lead);
                then().checkBounds(dir);
            }
        }
    }

    public static class Steps {

        @ProvidedScenarioState
        SVGRectFigure figure;

        @ProvidedScenarioState
        final ArrayList<Handle> handles = new ArrayList<>();

        @ProvidedScenarioState
        DrawingView view;

        @ProvidedScenarioState
        Pair<Rectangle2D.Double,Rectangle2D.Double> boundsPair;


        @As( "figure with dimensions x: $, y: $, width: $, height: $" )
        public void figure(double x, double y, double width, double height) {
            figure = new SVGRectFigure(x,y,width,height);
            handles.clear();
            ResizeHandleKit.addEdgeResizeHandles(figure,handles);
            ResizeHandleKit.addCornerResizeHandles(figure, handles);
            setUpView();
        }

        private void setUpView()
        {
            view = new DefaultDrawingView();
            DrawingEditorProxy editor = new DrawingEditorProxy();
            editor.setTarget(new DefaultDrawingEditor());
            view.addNotify(editor);
            for (Handle h : handles)
                h.setView(view);
        }

        @As( "mouse dragged from handle $ to $" )
        public void mouseDragged(HandleDirections dir, Point lead)
        {
            int i = dir.ordinal();

            int mask = dir.dirMask;
            Handle h = handles.get(i);

            Point location = ((LocatorHandle)h).getLocation();
            int dx = location.x;
            int dy = location.y;

            Point2D.Double p = view.viewToDrawing(new Point(lead.x + dx, lead.y + dy));
            view.getConstrainer().constrainPoint(p);
            Rectangle2D.Double originalBounds = figure.getBounds();

            handles.get(i).trackStart(new Point(0, 0),0);
            handles.get(i).trackStep(new Point(0,0), new Point(lead.x, lead.y),0);
            Rectangle2D.Double bounds = figure.getBounds();

            trackStepNormalized(p, mask, originalBounds);
            boundsPair = new Pair<>(figure.getBounds(), bounds);
        }

        @As( "checkBounds" )
        void checkBounds(HandleDirections dir)
        {
            assertEquals("Bounds doesn't match for "+ dir.name(), boundsPair.getKey(),boundsPair.getValue());
        }

        private void trackStepNormalized(Point2D.Double p, int mask, Rectangle2D.Double r) {

            double left = (mask & DIR_W) != 0 ? Math.min(r.x + r.width - 1, p.x) : r.x;
            double right = (mask & DIR_E) != 0 ? Math.max(r.x + 1, p.x) : r.x + r.width;
            double bottom = (mask & DIR_S) != 0 ? Math.max(r.y + 1, p.y) : r.y + r.height;
            double top = (mask & DIR_N) != 0 ? Math.min(r.y + r.height - 1, p.y) : r.y;

            figure.setBounds(
                    new Point2D.Double(left, top),
                    new Point2D.Double(right, bottom));
        }

    }

}