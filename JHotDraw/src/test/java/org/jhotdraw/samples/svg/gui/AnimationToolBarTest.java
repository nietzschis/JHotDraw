/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.gui;

import java.util.Collection;
import javax.swing.Action;
import javax.swing.JComponent;
import org.jhotdraw.draw.DrawingEditor;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lasca
 */
public class AnimationToolBarTest {
    
    AnimationToolBar animationToolBar;
    
    public AnimationToolBarTest() {
        animationToolBar = new AnimationToolBar();
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
     * Test of createDisclosedComponent method, of class AnimationToolBar.
     */
    @Test
    public void testCreateDisclosedComponent() {
        int state = 0;
        JComponent expResult = null;
        JComponent result = animationToolBar.createDisclosedComponent(state);
        assertEquals(expResult, result);
    }

    /**
     * Test of createSelectionActions method, of class AnimationToolBar.
     */
    @Test
    public void testCreateSelectionActions() {
        System.out.println("createSelectionActions");
        DrawingEditor editor = null;
        Collection<Action> expResult = null;
        Collection<Action> result = AnimationToolBar.createSelectionActions(editor);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getID method, of class AnimationToolBar.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        AnimationToolBar instance = new AnimationToolBar();
        String expResult = "";
        String result = instance.getID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDefaultDisclosureState method, of class AnimationToolBar.
     */
    @Test
    public void testGetDefaultDisclosureState() {
        System.out.println("getDefaultDisclosureState");
        AnimationToolBar instance = new AnimationToolBar();
        int expResult = 0;
        int result = instance.getDefaultDisclosureState();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
