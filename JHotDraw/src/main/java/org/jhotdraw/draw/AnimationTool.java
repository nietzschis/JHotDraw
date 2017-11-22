/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author lasca
 */
public class AnimationTool extends AbstractAction {
    
    protected int tool;
    
    public AnimationTool(int toolmode) {
        
        tool = toolmode;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(tool == 1){
            System.out.println("Du trykkede på add");
        }
        if(tool == 2){
            System.out.println("Du trykkede på remove");
        }
        if(tool == 3){
            System.out.println("Du trykkede på play");
        }
        if(tool == 4){
            System.out.println("Du trykkede på pause");
        }
    }
    
    public int getTool() {
        return tool;
    }

}
