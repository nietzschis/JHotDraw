/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.jhotdraw.app.EditableComponent;
import org.jhotdraw.app.JHotDrawFeatures;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author Sadik
 */
public class FlipAction extends AbstractAction{
     public final static String ID = "edit.flip";
    
    /** Creates a new instance. */
    public FlipAction() {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        labels.configureAction(this, ID);
    }

    @FeatureEntryPoint(JHotDrawFeatures.BASIC_EDITING)
    public void actionPerformed(ActionEvent evt) {
        Component focusOwner = KeyboardFocusManager.
                getCurrentKeyboardFocusManager().
                getPermanentFocusOwner();
        if (focusOwner != null) {
            if (focusOwner instanceof EditableComponent) {
                ((EditableComponent) focusOwner).flip();
            } else {
                focusOwner.getToolkit().beep();
            }
        }
    }
    
    
    
    public Component getFocusOwner(){
        return KeyboardFocusManager.
                getCurrentKeyboardFocusManager().
                getPermanentFocusOwner();
    }
    
    public void setFocusOwner(){
        
    }
}