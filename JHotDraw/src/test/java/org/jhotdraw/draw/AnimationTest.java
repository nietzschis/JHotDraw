/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw;

import java.util.List;
import javax.swing.JFrame;
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
public class AnimationTest {
    
    public AnimationTest() {
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
     * Test of getInstance method, of class Animation.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        Animation expResult = null;
        Animation result = Animation.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFrames method, of class Animation.
     */
    @Test
    public void testGetFrames() {
        System.out.println("getFrames");
        Animation instance = new Animation();
        List<JFrame> expResult = null;
        List<JFrame> result = instance.getFrames();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentFrame method, of class Animation.
     */
    @Test
    public void testGetCurrentFrame() {
        System.out.println("getCurrentFrame");
        Animation instance = new Animation();
        JFrame expResult = null;
        JFrame result = instance.getCurrentFrame();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFrame method, of class Animation.
     */
    @Test
    public void testSetFrame() {
        System.out.println("setFrame");
        int frame = 0;
        Animation instance = new Animation();
        JFrame expResult = null;
        JFrame result = instance.setFrame(frame);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addFrame method, of class Animation.
     */
    @Test
    public void testAddFrame() {
        System.out.println("addFrame");
        JFrame frame = null;
        Animation instance = new Animation();
        instance.addFrame(frame);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeFrame method, of class Animation.
     */
    @Test
    public void testRemoveFrame() {
        System.out.println("removeFrame");
        JFrame frameToRemove = null;
        Animation instance = new Animation();
        instance.removeFrame(frameToRemove);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
