/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw;

import java.awt.event.ActionEvent;
import java.util.concurrent.Executors;
import javax.swing.JFrame;
import static org.jhotdraw.draw.AnimationToolDefinition.*;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lasca
 */
public class AnimationToolTest {
    
    AnimationTool animationTool;
    
    public AnimationToolTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @Test
    public void testChangingTool() {
        animationTool = new AnimationTool(ADD_FRAME_TOOL);
        assertEquals(animationTool.getTool(), ADD_FRAME_TOOL);
        animationTool.changeTool(PLAY_TOOL);
        assertEquals(animationTool.getTool(), PLAY_TOOL);
    }
    
    /**
     * Test if list is not empty after adding a frame
     */
    @Test
    public void testAddFrameTool() {
        animationTool = new AnimationTool(ADD_FRAME_TOOL);
        animationTool.actionPerformed(new ActionEvent(this, 0, "test"));
        assertFalse(animationTool.getAnimation().getFrames().isEmpty());
    }
    
    /**
     * Test if frame is removed
     * First checks if frame is added
     * Then checks if list is empty after removal
     */
    @Test
    public void testRemoveFrameTool() {
        JFrame something = new JFrame();
        
        animationTool = new AnimationTool(ADD_FRAME_TOOL);
        animationTool.actionPerformed(new ActionEvent(something, 0, "test"));
        assertFalse(animationTool.getAnimation().getFrames().isEmpty());
        
        animationTool.changeTool(REMOVE_FRAME_TOOL);
        animationTool.actionPerformed(new ActionEvent(something, 0, "test"));
        assertTrue(animationTool.getAnimation().getFrames().isEmpty());
    }
    
    /**
     * Adds 2 frames, and runs the animation in a thread.
     * The counter of how many times played will increment,
     * and the test checks if this counter is bigger than 0. 
     * 
     * @throws InterruptedException 
     */
    @Test
    public void testPlayTool() throws InterruptedException {
        animationTool = new AnimationTool(ADD_FRAME_TOOL);
        animationTool.actionPerformed(new ActionEvent(this, 0, "test"));
        animationTool.actionPerformed(new ActionEvent(this, 0, "test"));
        
        animationTool.changeTool(PLAY_TOOL);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                animationTool.actionPerformed(new ActionEvent(this, 0, "test"));
            }
        });
        Thread.sleep(300);
        assertTrue(animationTool.getTimesPlayed() > 0);
    }
    
    /**
     * Adds 2 frames, and plays the animation. 
     * While playing, the pause tool is used, and then 
     * the frame counter should be reset to 0. 
     * 
     * @throws InterruptedException 
     */
    @Test
    public void testPauseTool() throws InterruptedException {
        animationTool = new AnimationTool(ADD_FRAME_TOOL);
        animationTool.actionPerformed(new ActionEvent(this, 0, "test"));
        animationTool.actionPerformed(new ActionEvent(this, 0, "test"));
        
        animationTool.changeTool(PLAY_TOOL);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                animationTool.actionPerformed(new ActionEvent(this, 0, "test"));
            }
        });
        Thread.sleep(500);
        assertTrue(animationTool.getTimesPlayed() > 0);
        
        animationTool.changeTool(PAUSE_TOOL);
        animationTool.actionPerformed(new ActionEvent(this, 0, "test"));
        Thread.sleep(500);
        assertTrue(animationTool.getTimesPlayed() == 0);
    }
}
