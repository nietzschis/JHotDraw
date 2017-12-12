/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;

import javax.swing.AbstractButton;
import org.jhotdraw.draw.DrawingView;
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
