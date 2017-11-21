package org.jhotdraw.draw;

import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.awt.*;
import java.util.ArrayList;

import static java.awt.Cursor.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class ResizeHandleKitTest {
    SVGRectFigure figure;
    ArrayList<Handle> handles = new ArrayList<>();

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    enum HandleDirections
    {
        S(S_RESIZE_CURSOR,RelativeLocator.south()),
        N(N_RESIZE_CURSOR,RelativeLocator.north()),
        E(E_RESIZE_CURSOR,RelativeLocator.east()),
        W(W_RESIZE_CURSOR,RelativeLocator.west()),
        SE(SE_RESIZE_CURSOR,RelativeLocator.southEast()),
        SW(SW_RESIZE_CURSOR,RelativeLocator.southWest()),
        NE(NE_RESIZE_CURSOR,RelativeLocator.northEast()),
        NW(NW_RESIZE_CURSOR,RelativeLocator.northWest());

        HandleDirections(int cursor, Locator locator)
        {
            this.cursor = cursor;
            this.locator = locator;
        }

        int cursor;
        Locator locator;
    }

    @Before
    public void setUp() throws Exception {
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
            collector.checkThat("Cursor type doesnt max when not transformable for "+ dir.name(),Cursor.DEFAULT_CURSOR, equalTo(handles.get(i).getCursor().getType()));

            figure.setTransformable(true);
            collector.checkThat("Cursor type doesnt max when not transformable for "+ dir.name(),dir.cursor, equalTo(handles.get(i).getCursor().getType()));
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
        assertEquals(true, false);
    }

    @Test
    public void keyPressed()
    {
        assertEquals(true, false);
    }

}
