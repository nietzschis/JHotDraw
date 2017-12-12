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
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author lasca
 */
public class AnimationToolTest {
    
    AnimationTool animationTool;
    
    public AnimationToolTest() {
    }
    
    @After
    public void setUpClass() {
        Animation.getInstance().setTimesPlayed(0);
        Animation.getInstance().getFrames().clear();
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
        JFrame frame = new JFrame();
        Animation.getInstance().setCurrentFrame(frame);
        
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
        JFrame newFrame = new JFrame();
        
        animationTool = new AnimationTool(ADD_FRAME_TOOL);
        Animation.getInstance().setCurrentFrame(newFrame);
        animationTool.actionPerformed(new ActionEvent(newFrame, 0, "test"));
        assertFalse(animationTool.getAnimation().getFrames().isEmpty());
        
        animationTool.changeTool(REMOVE_FRAME_TOOL);
        animationTool.actionPerformed(new ActionEvent(newFrame, 0, "test"));
        assertTrue(animationTool.getAnimation().getFrames().isEmpty());
    }
    
    @Test (expected = NullPointerException.class)
    public void testRemoveFrameWhenListIsEmpty() {
        JFrame newFrame = new JFrame();
        Animation.getInstance().getFrames().clear();
        assertTrue(animationTool.getAnimation().getFrames().isEmpty());
        
        animationTool = new AnimationTool(REMOVE_FRAME_TOOL);
        animationTool.actionPerformed(new ActionEvent(newFrame, 0, "test"));
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
    public void testPlayToolWithoutFrames() {
        animationTool = new AnimationTool(PLAY_TOOL);
        Animation.getInstance().getFrames().clear();
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                animationTool.actionPerformed(new ActionEvent(this, 0, "test"));
            }
        });
        assertEquals(0, Animation.getInstance().getTimesPlayed());
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
        JFrame frame = new JFrame();
        Animation.getInstance().setCurrentFrame(frame);
        
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
        assertTrue(Animation.getInstance().getTimesPlayed() > 0);
        
        animationTool.changeTool(PAUSE_TOOL);
        animationTool.actionPerformed(new ActionEvent(this, 0, "test"));
        Thread.sleep(1000);
        assertTrue(Animation.getInstance().getTimesPlayed() == 0);
    }
}
