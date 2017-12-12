package org.jhotdraw.draw;

import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.app.View;
import org.jhotdraw.samples.svg.SVGApplicationModel;
import org.jhotdraw.samples.svg.SVGDrawingPanel;
import org.jhotdraw.samples.svg.SVGView;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.lang.reflect.Field;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SwapDrawingTest {
    private static Application app;
    private static SVGApplicationModel model;

    private static DrawingEditor editor;
    private static SVGView svgView;
    private static SVGDrawingPanel svgPanel;

    @BeforeClass
    public static void setUpClass() throws InterruptedException, NoSuchFieldException, IllegalAccessException {
        app = new DefaultSDIApplication();
        model = new SVGApplicationModel();
        model.setViewClassName("org.jhotdraw.samples.svg.SVGView");
        app.setModel(model);
        app.launch(null);

        while (app.getFrame() == null)
            Thread.sleep(100);

        svgView = (SVGView) app.getActiveView();
        editor = svgView.getEditor();

        final Field svgPanelField = SVGView.class.getDeclaredField("svgPanel");
        svgPanelField.setAccessible(true);

        svgPanel = (SVGDrawingPanel) svgPanelField.get(svgView);

        final Drawing drawing = new QuadTreeDrawing();
        final DrawingView view = new DefaultDrawingView();
        view.setDrawing(drawing);

        editor.add(view);
        editor.setActiveView(view);
        svgPanel.setDrawing(drawing);
    }

    @AfterClass
    public static void tearDownClass() {
        app.stop();
    }

    /**
     * If this test case is failing, it's because tabs still don't work
     * consistently, with how the core functionality of the program worked,
     * before tabs was merged into master.
     *
     * Issue: 201
     * URL: https://github.com/sweat-tek/SB5-MAI-E17/issues/201
     */
    @Test
    public void testSwapDrawingView() {
        assertThat(editor.getActiveView().getDrawing(), is(svgView.getDrawing()));
        assertThat(editor.getActiveView().getDrawing(), is(svgPanel.getDrawing()));

        final Drawing drawing = new QuadTreeDrawing();
        final DrawingView view = new DefaultDrawingView();
        view.setDrawing(drawing);

        editor.add(view);
        editor.setActiveView(view);
        svgPanel.setDrawing(drawing);

        assertThat(editor.getActiveView().getDrawing(), is(svgView.getDrawing()));
        assertThat(editor.getActiveView().getDrawing(), is(svgPanel.getDrawing()));
    }

    @Test
    public void testCheckView() {
        assertThat(svgView, is(notNullValue()));
        assertThat(svgView, is(instanceOf(View.class)));
    }

    @Test
    public void testCheckEditor() {
        assertThat(editor, is(notNullValue()));
        assertThat(editor, is(instanceOf(DrawingEditor.class)));
    }

    @Test
    public void testCheckPanel() {
        assertThat(svgPanel, is(notNullValue()));
        assertThat(svgPanel, is(instanceOf(SVGDrawingPanel.class)));
    }
}
