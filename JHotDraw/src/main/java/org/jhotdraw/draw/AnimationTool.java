/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw;

import java.awt.event.MouseEvent;

/**
 *
 * @author lasca
 */
public class AnimationTool extends AbstractTool {
    
    protected int tool;
    private final boolean isToolDoneAfterCreation = true;
    
    public AnimationTool(int toolmode) {
        
        tool = toolmode;
    }

    public boolean isIsToolDoneAfterCreation() {
        return isToolDoneAfterCreation;
    }
    
    @Override
    public void mouseClicked(MouseEvent evt) {
        super.mouseClicked(evt);
        fireToolDone();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }
}
