package org.jhotdraw.samples.svg.gui;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.gui.JPopupButton;
import org.jhotdraw.samples.svg.Main;
import org.jhotdraw.samples.svg.SVGApplicationModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Nam
 */
public class FigureToolBarTestGui_AssertJ_Swing {

    private FrameFixture window;
    private Application app;

    public FigureToolBarTestGui_AssertJ_Swing() {
    }

    @Before
    public void setUp() {
        SVGApplicationModel model = new SVGApplicationModel();
        model.setName("JHotDraw SVG");
        model.setVersion(Main.class.getPackage().getImplementationVersion());
        model.setCopyright("Copyright 2006-2009 (c) by the authors of JHotDraw\n"
                + "This software is licensed under LGPL or Creative Commons 3.0 BY");
        model.setViewClassName("org.jhotdraw.samples.svg.SVGView");

       app = new DefaultSDIApplication();
        
       
       app.setModel(model);
        app.launch(null);
          while (app.getFrame() == null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(FigureToolBarTestGui_AssertJ_Swing.class.getName()).log(Level.SEVERE, null, ex);
            }
            window = new FrameFixture(app.getFrame());
        }

  
    }

    @Test
    public void sliderTest() {
        window.show();
        
       
            
        assertNotNull(app.getFrame());
        assertNotNull(window);
        
//           assertNotNull(window.button("abc"));
//            assertNotNull(window.slider("cde"));
            assertNotNull(window.button("rec"));
        //  assertNotNull(window.slider("test111"));
    }

    @After
    public void tearDown() {
        app = null;
        window = null;
        assertNull(app);
        assertNull(window);

    }

}
