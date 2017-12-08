/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhotdraw.draw;

import java.awt.event.MouseEvent;
import java.util.Collection;
import javax.swing.Action;

/**
 *
 * @author Alexander
 */
public class FrameEditorTool extends AbstractTool
{
    private Tool creationTool;
    private Tool selectionTool;

    @Override
    public void mouseDragged(MouseEvent e) {
        selectionTool.mouseDragged(e);
    }
    
    public FrameEditorTool(Tool selectionTool, Tool creationTool) {
        this.selectionTool = selectionTool;
        this.creationTool = creationTool;
    }

    @Override
    public void mouseReleased(MouseEvent evt) {
        selectionTool.mouseReleased(evt);
    }

    @Override
    public void mousePressed(MouseEvent evt) {
        selectionTool.mousePressed(evt);
    }
    
    

    
   
    
    
}
