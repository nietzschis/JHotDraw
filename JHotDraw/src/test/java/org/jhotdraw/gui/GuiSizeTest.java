/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhotdraw.gui;

import dk.sdu.mmmi.featuretracer.test.example5.Main;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import org.assertj.swing.fixture.FrameFixture;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.app.View;
import org.jhotdraw.samples.svg.SVGApplicationModel;
import org.jhotdraw.util.GuiSizes;
import org.junit.*;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author JOnes
 */
public class GuiSizeTest {
    
    private FrameFixture window;
    private DefaultSDIApplication app;
    private int width, height;
    
    
    @Before
    public void setup() throws InterruptedException {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        width = gd.getDisplayMode().getWidth();
        height = gd.getDisplayMode().getHeight();
        app = new DefaultSDIApplication();
        SVGApplicationModel model = new SVGApplicationModel();
        model.setViewClassName("org.jhotdraw.samples.svg.SVGView");
        app.setModel(model);
        app.launch(null);
        while (app.getFrame() == null) {
            Thread.sleep(500);
            window = new FrameFixture(app.getFrame());
        }
    }
    
    @Test
    public void testButtonSize() {
        JToggleButton myButton = window.toggleButton("select").target();
        assertEquals(new Dimension(width / 87, height / 49), myButton.getPreferredSize());
        
    }
    
    @After
    public void tearDown() {
        window.cleanUp();
    }
}