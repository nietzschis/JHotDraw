package org.jhotdraw.draw;

import com.tngtech.jgiven.annotation.As;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.junit.SimpleScenarioTest;
import javafx.util.Pair;
import org.jhotdraw.draw.action.DrawingEditorProxy;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.junit.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

import static org.jhotdraw.draw.ResizeHandleKit.*;
import static org.jhotdraw.draw.ResizeHandleKitTest.*;
import static org.junit.Assert.assertEquals;

public class ResizeHandleKitAcceptanceTest  extends SimpleScenarioTest<ResizeHandleKitAcceptanceTest.Steps> {

    /**
     * Acceptance test scenario where we for each point and direction initiate figure, simulate drag to point and check if boundaries are correctly changed.
     */
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
                then().checkBounds();
            }
        }
    }

    /**
     * Acceptance test scenario where we for each rect, key and direction initiate figure, simulate key press and check if boundaries are correctly changed.
     */
    @Test
    public void keyResizeScenario() {
        int keys[] = {
                KeyEvent.VK_UP,
                KeyEvent.VK_DOWN,
                KeyEvent.VK_LEFT,
                KeyEvent.VK_RIGHT
        };

        Rectangle2D.Double rectangles [] = {
                new Rectangle2D.Double(1d, 1d, 1d, 1d),
                new Rectangle2D.Double(50d, 50d, 100d, 100d),
        };

        for (int i = 0; i < HandleDirections.values().length; i++) {
            for (int key : keys) {
                for (Rectangle2D.Double rect : rectangles) {
                    HandleDirections dir = HandleDirections.values()[i];
                    given().figure(rect.x, rect.y, rect.width, rect.height);
                    when().keyPressed(key, dir);
                    then().checkBounds();
                    then().checkConsumption();
                }
            }
        }
    }

    static class Steps {

        @ProvidedScenarioState
        SVGRectFigure figure;

        @ProvidedScenarioState
        final ArrayList<Handle> handles = new ArrayList<>();

        @ProvidedScenarioState
        DrawingView view;

        @ExpectedScenarioState
        Pair<Rectangle2D.Double,Rectangle2D.Double> boundsPair;

        @ExpectedScenarioState
        Map.Entry<KeyEvent,KeyEvent> keyEventPair;

        /**
         * Function to initiate our test figure
         * @param x x of figure
         * @param y y of figure
         * @param width width of figure
         * @param height height of figure
         */
        @As( "figure with dimensions x: $, y: $, width: $, height: $" )
        void figure(double x, double y, double width, double height) {
            figure = new SVGRectFigure(x,y,width,height);
            handles.clear();
            ResizeHandleKit.addEdgeResizeHandles(figure,handles);
            ResizeHandleKit.addCornerResizeHandles(figure, handles);
            setUpView();
        }

        /**
         * Helper function to setup DrawingView needed for simulation
         */
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
        void mouseDragged(HandleDirections dir, Point lead)
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

        /**
         * Function that check if bounds pair created in scenario is correct
         */
        void checkBounds()
        {
            assertEquals("Bounds doesn't match", boundsPair.getKey(),boundsPair.getValue());
        }

        /**
         * Function that check if keyEvent pair used in scenario is correctly consumed
         */
        void checkConsumption()
        {
            assertEquals("Consumption doesn't match", keyEventPair.getKey().isConsumed(), keyEventPair.getValue().isConsumed());
        }

        /**
         * Function simulation trackStepNormalized for comparision
         * @param p new point
         * @param mask direction mask
         * @param r original bounds rectangle
         */
        private void trackStepNormalized(Point2D.Double p, int mask, Rectangle2D.Double r) {

            double left = (mask & DIR_W) != 0 ? Math.min(r.x + r.width - 1, p.x) : r.x;
            double right = (mask & DIR_E) != 0 ? Math.max(r.x + 1, p.x) : r.x + r.width;
            double bottom = (mask & DIR_S) != 0 ? Math.max(r.y + 1, p.y) : r.y + r.height;
            double top = (mask & DIR_N) != 0 ? Math.min(r.y + r.height - 1, p.y) : r.y;

            figure.setBounds(
                    new Point2D.Double(left, top),
                    new Point2D.Double(right, bottom));
        }

        /**
         * Test of keyPress event
         * @param key currently tested key
         * @param dir currently tested direction
         */
        @As( "key $ is pressed on handle $" )
        void keyPressed(int key, HandleDirections dir) {
            int mask = dir.dirMask;
            int i = dir.ordinal();
            Handle h = handles.get(i);
            Rectangle2D.Double rect = figure.getBounds();

            KeyEvent event1 = new KeyEvent(new Button(), 0, 0, 0, key, 'k');
            KeyEvent event2 = new KeyEvent(new Button(), 0, 0, 0, key, 'k');

            h.keyPressed(event1);
            Rectangle2D.Double actualBounds = figure.getBounds();
            figure.setBounds(rect);
            keyPressedHelper(event2, mask);
            Rectangle2D.Double expectedBounds = figure.getBounds();


            boundsPair = new Pair<>(actualBounds, expectedBounds);
            keyEventPair = new AbstractMap.SimpleEntry<>(event1, event2);
        }

        // ugly java alternative of !!
        private int nn(int x)
        {
            return x > 0 ? 1 : 0;
        }

        /**
         * Simulation of keyPress behaviour for comparision
         * @param evt Key event
         * @param mask Direction mask
         */
        private void keyPressedHelper(KeyEvent evt, int mask) {
            Rectangle2D.Double r = figure.getBounds();

            int up = 0;
            int down = 0;
            int left = 0;
            int right = 0;

            switch (evt.getKeyCode())
            {
                case KeyEvent.VK_UP:
                    if (r.height <= 1 && (mask & DIR_S) != 0)
                        break;
                    down = -nn(mask & DIR_S);
                    up = -nn(mask & DIR_N);
                    break;
                case KeyEvent.VK_DOWN:
                    if (r.height <= 1 && (mask & DIR_N) != 0)
                        break;
                    up = nn(mask & DIR_N);
                    down = nn(mask & DIR_S);
                    break;
                case KeyEvent.VK_LEFT:
                    if (r.width <= 1 && (mask & DIR_E) != 0)
                        break;
                    left = -nn(mask & DIR_W);
                    right = -nn(mask & DIR_E);
                    break;
                case KeyEvent.VK_RIGHT:
                    if (r.width <= 1 && (mask & DIR_W) != 0)
                        break;
                    left = nn(mask & DIR_W);
                    right = nn(mask & DIR_E);
                    break;

            }

            figure.setBounds(
                    new Point2D.Double(r.x + left, r.y + up),
                    new Point2D.Double(r.x + r.width + right, r.y + r.height + down));

            evt.consume();
        }
    }
}
