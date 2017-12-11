/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.assertj.swing.fixture.FrameFixture;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.app.View;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.SimpleDrawing;
import org.jhotdraw.geom.Dimension2DDouble;
import org.jhotdraw.samples.svg.SVGApplicationModel;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.junit.After;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author ville
 */
public class DuplicateCanvasActionTest {
    private FrameFixture window;
    private Application app;

    @Before
    public void setUp() throws InterruptedException {
        app = new DefaultSDIApplication();
        SVGApplicationModel model = new SVGApplicationModel();
        model.setViewClassName("org.jhotdraw.samples.svg.SVGView");
        app.setModel(model);
        app.launch(null);
//        while (app.getFrame() == null) {
//            Thread.sleep(1000);
//            window = new FrameFixture(app.getFrame());
//        }
    }
    
    @After
    public void tearDown() {
        app = null;
        // window.cleanUp();
    }
    
    @Test
    public void testDuplicateWindow() throws URISyntaxException {
        assertNotNull(app);
        DuplicateCanvasAction dca = new DuplicateCanvasAction(app);
        
//        URL url = getTestImageUrl(getClass());
//        File f = new File();
        
        URL url = DuplicateCanvasActionTest.class.getResource("duplicatecanvas/test21.svg");
        URI uri = url.toURI(); //this line
        
        File f = new File(uri.getPath());
        
        View testView = app.createView();
        testView.setFile(f);
        
        app.add(testView);
//        DefaultDrawingView instance = new DefaultDrawingView();
//        SimpleDrawing drawing = new SimpleDrawing();
//        
//        Dimension2DDouble dimension = new Dimension2DDouble(10, 10);
//        drawing.setCanvasSize(dimension);
//        
//        Figure f = new SVGRectFigure(1, 1, 1, 1);
//        
//        assertEquals(0, drawing.getChildCount()); //make sure theres no figures
//        
//        drawing.add(f); //adding figure to drawing/canvas. 
//        instance.setDrawing(drawing); //"attach" canvas to view instance
//        
//        app.add((View) instance);
        
        dca.actionPerformed(null);
        
        try {
            Thread.sleep(5000);
//        assertNotNull(window);
//
//        window.show();
//
//        window.menuItem("file").click();
//        window.menuItem("window.duplicate").click();
        } catch (InterruptedException ex) {
            Logger.getLogger(DuplicateCanvasActionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static URL getTestImageUrl(Class c) {
        return c.getClassLoader().getResource("org/jhotdraw/draw/action/duplicatecanvas/test21.png");
    }
    
}
