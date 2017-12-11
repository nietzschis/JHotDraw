package org.jhotdraw.draw;

import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class SelectionTest {
    private static final Rectangle SELECTION_AREA = new Rectangle(5, 0, 40, 50);
    private static final Rectangle SELECTION_AREA2 = new Rectangle(30, 0, 40, 50);

    private static DrawingEditor editor;
    private static DrawingView view;

    private static Field rubberbandField;
    private static Method selectGroupMethod;

    @BeforeClass
    public static void setUpClass() throws NoSuchFieldException, NoSuchMethodException {
        // Reflection might not be the most optimal way, but it's definitely easier than creating widgets and mouse events
        rubberbandField = DefaultSelectAreaTracker.class.getDeclaredField("rubberband");
        rubberbandField.setAccessible(true);

        selectGroupMethod = DefaultSelectAreaTracker.class.getDeclaredMethod("selectGroup", boolean.class);
        selectGroupMethod.setAccessible(true);

        editor = new DefaultDrawingEditor();

        view = new DefaultDrawingView();
        editor.setActiveView(view);

        final Drawing drawing = new QuadTreeDrawing();
        view.setDrawing(drawing);

        drawing.add(new SVGRectFigure(10.0, 0.0, 10.0, 10.0));
        drawing.add(new SVGRectFigure(25.0, 0.0, 10.0, 10.0));
        drawing.add(new SVGRectFigure(40.0, 0.0, 10.0, 10.0));
        drawing.add(new SVGRectFigure(60.0, 0.0, 10.0, 10.0));
    }

    @Test
    public void testFindWithin() {
        final Collection<Figure> figures = view.findFiguresWithin(SELECTION_AREA);
        assertThat(figures.size(), is(2));
    }

    @Test
    public void testFiguresIntersecting() {
        final Collection<Figure> figures = view.findFigures(SELECTION_AREA);
        assertThat(figures.size(), is(3));
    }

    @Test
    public void testExpectedSelection() throws IllegalAccessException, InvocationTargetException {
        assertThat(view.getSelectionCount(), is(0));

        final SelectAreaTracker selectAreaTracker = new DefaultSelectAreaTracker();
        selectAreaTracker.activate(editor);

        final Rectangle bounds = (Rectangle) rubberbandField.get(selectAreaTracker);

        bounds.setBounds(SELECTION_AREA);
        selectGroupMethod.invoke(selectAreaTracker, false);
        assertThat(view.getSelectionCount(), is(3));

        selectAreaTracker.deactivate(editor);

        view.clearSelection();
        assertThat(view.getSelectionCount(), is(0));
    }

    @Test
    public void testSelectionInversion() throws IllegalAccessException, InvocationTargetException {
        assertThat(view.getSelectionCount(), is(0));

        final SelectAreaTracker selectAreaTracker = new DefaultSelectAreaTracker();
        selectAreaTracker.activate(editor);

        final Rectangle bounds = (Rectangle) rubberbandField.get(selectAreaTracker);

        bounds.setBounds(SELECTION_AREA);
        selectGroupMethod.invoke(selectAreaTracker, false);
        assertThat(view.getSelectionCount(), is(3));

        bounds.setBounds(SELECTION_AREA2);
        selectGroupMethod.invoke(selectAreaTracker, true);
        assertThat(view.getSelectionCount(), is(2));

        selectAreaTracker.deactivate(editor);

        view.clearSelection();
        assertThat(view.getSelectionCount(), is(0));
    }
}
