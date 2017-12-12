/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
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

    @Test
    public void testSingleton() {
        assertTrue(Animation.getInstance() != null);
    }
    
    @Test
    public void testFrameIsAdded() {
        Animation.getInstance().addFrame(new JFrame());
        assertFalse(Animation.getInstance().getFrames().isEmpty());
        Animation.getInstance().getFrames().clear();
    }
    
    @Test
    public void testAddingNullAsFrame() {
        Animation.getInstance().getFrames().clear();
        Animation.getInstance().addFrame(null);
        assertTrue(Animation.getInstance().getFrames().isEmpty());
        Animation.getInstance().getFrames().clear();
    }
    
    @Test
    public void testFrameIsRemoved() {
        JFrame frame = new JFrame();
        Animation.getInstance().addFrame(frame);
        assertFalse(Animation.getInstance().getFrames().isEmpty());
        Animation.getInstance().removeFrame(frame);
        assertTrue(Animation.getInstance().getFrames().isEmpty());
    }
}
