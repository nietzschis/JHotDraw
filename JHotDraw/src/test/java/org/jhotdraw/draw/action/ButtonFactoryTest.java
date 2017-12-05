/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Tool;
import org.jhotdraw.gui.JPopupButton;
import org.jhotdraw.util.ResourceBundleUtil;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;

/**
 *
 * @author Christian
 */
public class ButtonFactoryTest {
    
    public ButtonFactoryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
                   
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createMagnifyButton method, of class ButtonFactory.
     */
    @Test
    public void testCreateMagnifyButton() {
        System.out.println("createMagnifyButton");
        DrawingView view = Mockito.mock(DrawingView.class, Mockito.CALLS_REAL_METHODS);
        AbstractButton result = ButtonFactory.createMagnifyButton(view);
        assertNotNull(result);
        
    }
    
}
