/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw;

import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import static org.jhotdraw.draw.AnimationToolDefinition.*;

/**
 *
 * @author lasca
 */
public class AnimationTool extends AbstractAction {
    
    protected AnimationToolDefinition tool;
    private boolean stopPlaying;
    private int framesPlayed;
    Animation animation;
    
    public AnimationTool(AnimationToolDefinition toolmode) {
        tool = toolmode;
        stopPlaying = false;
        framesPlayed = 0;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (tool) {
            case ADD_FRAME_TOOL:
                addFrame();
                break;
            
            case REMOVE_FRAME_TOOL:
                removeFrame((JFrame) e.getSource());
                break;
            
            case PLAY_TOOL: {
                try {
                    play();
                } catch (InterruptedException ex) {
                    System.out.println("Interrupt Error");
                }
            }
            break;
            
            case PAUSE_TOOL:
                pause();
                break;
        }
    }
    
    public void play() throws InterruptedException {
        stopPlaying = false;
        List<JFrame> list = getAnimation().getFrames();
        for (int i = 0; i < list.size(); i++) {
            list.get(i);
            if (i == list.size() - 1) {
                i = 0;
                framesPlayed++;
            }
            if (stopPlaying) {
                framesPlayed = 0;
                break;
            }
            Thread.sleep(100);
        }
    }
    
    public int getTimesPlayed() {
        return framesPlayed;
    }
    
    public void pause() {
        stopPlaying = true;
    }
    
    public void addFrame() {
        getAnimation().addFrame(new JFrame());
        animation = getAnimation();
    }
    
    public void removeFrame(JFrame frameToRemove) {
        getAnimation().getFrames().remove(getAnimation().getFrames().get(framesPlayed));
        animation = getAnimation();
    }
    
    public Animation getAnimation() {
        return Animation.getInstance();
    }
    
    public void changeTool(AnimationToolDefinition toolChangingTo) {
        tool = toolChangingTo;
    }
    
    public AnimationToolDefinition getTool() {
        return tool;
    }
}
