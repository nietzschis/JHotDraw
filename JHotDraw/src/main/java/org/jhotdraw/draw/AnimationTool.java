/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import static org.jhotdraw.draw.AnimationToolDefinition.*;

/**
 *
 * @author lasca
 */
public class AnimationTool extends AbstractAction {
    
    protected AnimationToolDefinition tool;
    
    public AnimationTool(AnimationToolDefinition toolmode) {
        tool = toolmode;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(tool == ADD_FRAME_TOOL){
            System.out.println("Du trykkede på add");
        }
        if(tool == REMOVE_FRAME_TOOL){
            System.out.println("Du trykkede på remove");
        }
        if(tool == PLAY_TOOL){
            System.out.println("Du trykkede på play");
        }
        if(tool == PAUSE_TOOL){
            System.out.println("Du trykkede på pause");
        }
    }
    
    public AnimationToolDefinition getTool() {
        return tool;
    }

}
