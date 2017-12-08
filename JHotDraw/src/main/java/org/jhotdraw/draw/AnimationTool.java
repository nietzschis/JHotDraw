/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
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
    protected Animation animation;
    private boolean stopPlaying;
    
    public AnimationTool(AnimationToolDefinition toolmode) {
        tool = toolmode;
        stopPlaying = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(tool){
            case ADD_FRAME_TOOL: addFrame();
                break;
                
            case REMOVE_FRAME_TOOL: removeFrame();
                break;
                
            case PLAY_TOOL: play();
                break;
                
            case PAUSE_TOOL: pause();
                break;
        }
    }
    
    private void play() {
        List<JFrame> list = animation.getFrames();
            for (int i = 0; i < list.size(); i++) {
                list.get(i);
                if(i == list.size() && !stopPlaying){
                    i = 0;
                }else {
                    break;
                }
            }
    }
    
    private void pause() {
        stopPlaying = true;
    }
    
    private void addFrame() {
        getAnimation().addFrame(new JFrame());
    }
    
    private void removeFrame() {
        getAnimation().removeFrame(new JFrame());
    }
    
    private Animation getAnimation() {
        return Animation.getInstance();
    }
    
    public AnimationToolDefinition getTool() {
        return tool;
    }
}
