package org.jhotdraw.draw;

import org.jhotdraw.draw.action.DrawingEditorProxy;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import static java.awt.Cursor.*;
import static org.hamcrest.core.IsEqual.equalTo;

public class ResizeHandleKitTest {

    private SVGRectFigure figure;
    private final ArrayList<Handle> handles = new ArrayList<>();

    @Rule
    public ErrorCollector collector = new ErrorCollector();


    private static final int DIR_S = 1;
    private static final int DIR_N = 2;
    private static final int DIR_E = 4;
    private static final int DIR_W = 8;
    private static final int DIR_SE = DIR_S|DIR_E;
    private static final int DIR_SW = DIR_S|DIR_W;
    private static final int DIR_NE = DIR_N|DIR_E;
    private static final int DIR_NW = DIR_N|DIR_W;

    enum HandleDirections
    {
        S(S_RESIZE_CURSOR,RelativeLocator.south(), DIR_S),
        N(N_RESIZE_CURSOR,RelativeLocator.north(), DIR_N),
        E(E_RESIZE_CURSOR,RelativeLocator.east(), DIR_E),
        W(W_RESIZE_CURSOR,RelativeLocator.west(), DIR_W),
        SE(SE_RESIZE_CURSOR,RelativeLocator.southEast(), DIR_SE),
        SW(SW_RESIZE_CURSOR,RelativeLocator.southWest(), DIR_SW),
        NE(NE_RESIZE_CURSOR,RelativeLocator.northEast(), DIR_NE),
        NW(NW_RESIZE_CURSOR,RelativeLocator.northWest(), DIR_NW);

        HandleDirections(int cursor, Locator locator, int mask)
        {
            this.cursor = cursor;
            this.locator = locator;
            this.dirMask = mask;
        }

        final int cursor;
        final Locator locator;
        final int dirMask;
    }

    @Before
    public void setUp() {
        figure = new SVGRectFigure(50d,50d,50d,50d);
        ResizeHandleKit.addEdgeResizeHandles(figure,handles);
        ResizeHandleKit.addCornerResizeHandles(figure, handles);
    }

    @Test
    public void cursorTest()
    {
        for (int i = 0; i < HandleDirections.values().length; i++)
        {
            HandleDirections dir = HandleDirections.values()[i];
            figure.setTransformable(false);
            int handleCursorType = handles.get(i).getCursor().getType();

            collector.checkThat("Cursor type doesn't max when not transformable for "+ dir.name(),Cursor.DEFAULT_CURSOR, equalTo(handleCursorType));

            figure.setTransformable(true);
            handleCursorType = handles.get(i).getCursor().getType();
            collector.checkThat("Cursor type doesn't max when not transformable for "+ dir.name(),dir.cursor, equalTo(handleCursorType));
        }
    }

    @Test
    public void locatorTest()
    {
        for (int i = 0; i < HandleDirections.values().length; i++)
        {
            HandleDirections dir = HandleDirections.values()[i];
            collector.checkThat("Locator does not match for "+ dir.name(),dir.locator.locate(figure), equalTo(((LocatorHandle)handles.get(i)).getLocationOnDrawing()));
        }
    }

    @Test
    public void trackStepNormalizedTest()
    {
        DrawingView view = setUpView();

        Point[] points = {
                new Point(0,0), // north east from rect
                new Point(200,200), // south west from rect
        };

        for (Point lead : points)
        {
            for (int i = 0; i < HandleDirections.values().length; i++)
            {
                figure.setBounds(new Point2D.Double(50d,50d),new Point2D.Double(100d,100d));

                HandleDirections dir = HandleDirections.values()[i];
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
                collector.checkThat("Bounds doesn't match for "+ dir.name(), figure.getBounds(),equalTo(bounds));

            }
        }
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

    // Function to set up editor and view needed for keyPressTest and trackStepNormalizedTest
    private DrawingView setUpView()
    {
        DrawingView view = new DefaultDrawingView();
        DrawingEditorProxy editor = new DrawingEditorProxy();
        editor.setTarget(new DefaultDrawingEditor());
        view.addNotify(editor);
        for (Handle h : handles)
            h.setView(view);

        return view;
    }

    @Test
    public void keyPressedTest() {
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

        setUpView();

        for (int key : keys) {
            for (Rectangle2D.Double rect : rectangles) {
                for (int i = 0; i < HandleDirections.values().length; i++) {

                    HandleDirections dir = HandleDirections.values()[i];
                    int mask = dir.dirMask;
                    Handle h = handles.get(i);

                    KeyEvent event1 = new KeyEvent(new Button(), 0, 0, 0, key, 'k');
                    KeyEvent event2 = new KeyEvent(new Button(), 0, 0, 0, key, 'k');

                    figure.setBounds(rect);
                    h.keyPressed(event1);
                    Rectangle2D.Double actualBounds = figure.getBounds();
                    figure.setBounds(rect);
                    keyPressed(event2, mask);
                    Rectangle2D.Double expectedBounds = figure.getBounds();

                    collector.checkThat("Bounds doesn't match for " + dir.name() + " eventKey " + key, actualBounds, equalTo(expectedBounds));
                    collector.checkThat("Consumption doesn't match for " + dir.name() + " eventKey " + key, event1.isConsumed(), equalTo(event2.isConsumed()));


                }
            }
        }
    }

    // ugly java alternative of !!
    private int nn(int x)
    {
        return x > 0 ? 1 : 0;
    }

    private void keyPressed(KeyEvent evt, int mask) {
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
