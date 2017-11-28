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
        animation = new Animation();
        stopPlaying = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(tool == ADD_FRAME_TOOL){
            animation.addFrame(new JFrame());
        }
        if(tool == REMOVE_FRAME_TOOL){
            animation.removeFrame(new JFrame());
        }
        if(tool == PLAY_TOOL){
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
        if(tool == PAUSE_TOOL){
            System.out.println("Du trykkede på pause");
        }
    }
    
    public AnimationToolDefinition getTool() {
        return tool;
    }

}
