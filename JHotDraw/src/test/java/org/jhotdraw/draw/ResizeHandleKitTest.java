package org.jhotdraw.draw;

import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import static java.awt.Cursor.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.jhotdraw.draw.ResizeHandleKit.*;

public class ResizeHandleKitTest {

    private SVGRectFigure figure;
    private final ArrayList<Handle> handles = new ArrayList<>();

    @Rule
    public ErrorCollector collector = new ErrorCollector();

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

    enum eAspectDir
    {
        S(DIR_S,   new Rectangle2D.Double(0,0,0,20)),
        N(DIR_N,   new Rectangle2D.Double(0,-20,0,20)),
        E(DIR_E,   new Rectangle2D.Double(0,0,20,0)),
        W(DIR_W,   new Rectangle2D.Double(-20,0,20,0)),
        SE(DIR_SE, new Rectangle2D.Double(0,0,20,20)),
        SW(DIR_SW, new Rectangle2D.Double(-20,0,20,20)),
        NE(DIR_NE, new Rectangle2D.Double(0,-20,20,20)),
        NW(DIR_NW, new Rectangle2D.Double(-20,-20,20,20));

        eAspectDir(int mask, Rectangle2D.Double change)
        {
            this.dirMask = mask;
            this.rect = change;
        }

        final int dirMask;
        final  Rectangle2D.Double rect;
    }

    @Test
    public void applyAspectRatioTest()
    {
        Rectangle2D.Double rectangles[] =
                {
                        new Rectangle2D.Double(100d, 100d, 200d, 200d),
                        new Rectangle2D.Double(50d, 73d, 150d, 300d)
                };

        for (Rectangle2D.Double originalRect : rectangles) {
            Point2D.Double aspectRatio = new Point2D.Double(originalRect.width / originalRect.height, originalRect.height / originalRect.width);

            for (eAspectDir aspChange : eAspectDir.values()) {
                Rectangle2D.Double rect = new Rectangle2D.Double(originalRect.x + aspChange.rect.x, originalRect.y + aspChange.rect.y, originalRect.width + aspChange.rect.width, originalRect.height + aspChange.rect.height);
                Point2D.Double topLeft = new Point2D.Double(rect.x, rect.y);
                Point2D.Double bottomRight = new Point2D.Double(rect.x + rect.width, rect.y + rect.height);

                ResizeHandleKit.applyAspectRatio(aspChange.dirMask, topLeft, bottomRight, aspectRatio);
                Rectangle2D.Double newRect = new Rectangle2D.Double(topLeft.x, topLeft.y, bottomRight.x - topLeft.x, bottomRight.y - topLeft.y);
                Point2D.Double newAspectRatio = new Point2D.Double(newRect.width / newRect.height, newRect.height / newRect.width);

                collector.checkThat("Aspect ration doesn't match for " + aspChange.name(), aspectRatio, equalTo(newAspectRatio));
            }
        }
    }
}
