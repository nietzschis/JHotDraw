/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg;

import javax.swing.JFrame;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.assertj.swing.launcher.ApplicationLauncher.application;

/**
 *
 * @author Henrik Bastholm
 */
public class CoordinateToolTest {
    
    private FrameFixture window;
    
    public CoordinateToolTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        FailOnThreadViolationRepaintManager.install();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        CoordinateTool ct = new CoordinateTool();
        JFrame frame = GuiActionRunner.execute(() -> new JFrame());
        window = new FrameFixture(frame);
        window.show();
    }
    
    @After
    public void tearDown() {
        window.cleanUp();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
