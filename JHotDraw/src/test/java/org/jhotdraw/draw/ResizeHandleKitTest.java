package org.jhotdraw.draw;

import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.awt.*;
import java.util.ArrayList;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class ResizeHandleKitTest {
    SVGRectFigure figure;
    ArrayList<Handle> handles = new ArrayList<>();

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    enum Direction
    {
        S,
        N,
        E,
        W,
        SE,
        SW,
        NE,
        NW,
        MAX_HANDLES
    }

    final static int[] cursors =
            {
                    Cursor.S_RESIZE_CURSOR,
                    Cursor.N_RESIZE_CURSOR,
                    Cursor.E_RESIZE_CURSOR,
                    Cursor.W_RESIZE_CURSOR,

                    Cursor.SE_RESIZE_CURSOR,
                    Cursor.SW_RESIZE_CURSOR,
                    Cursor.NE_RESIZE_CURSOR,
                    Cursor.NW_RESIZE_CURSOR,
            };

    @Before
    public void setUp() throws Exception {
        figure = new SVGRectFigure(50d,50d,50d,50d);
        ResizeHandleKit.addEdgeResizeHandles(figure,handles);
        ResizeHandleKit.addCornerResizeHandles(figure, handles);

    }

    @Test
    public void cursorTest()
    {
        for (int i = 0; i < Direction.MAX_HANDLES.ordinal(); i++)
        {
            figure.setTransformable(false);
            collector.checkThat("Cursor type doesnt max when not transformable for "+ Direction.values()[i].name(),Cursor.DEFAULT_CURSOR, equalTo(handles.get(i).getCursor().getType()));

            figure.setTransformable(true);
            collector.checkThat("Cursor type doesnt max when not transformable for "+ Direction.values()[i].name(),cursors[i], equalTo(handles.get(i).getCursor().getType()));
        }
    }

}
