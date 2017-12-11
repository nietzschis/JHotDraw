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
import javax.swing.JComponent;
import javax.swing.TransferHandler;
import org.jhotdraw.app.JHotDrawFeatures;
import static org.jhotdraw.app.action.CopyAction.ID;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author mutten
 */
public class ColorHotkeyAction extends AbstractAction{
    
    public final static String ID = "edit.colorHotkey";
    
    /** Creates a new instance. */
    public ColorHotkeyAction() {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        labels.configureAction(this, ID);
    }
    
    @FeatureEntryPoint(JHotDrawFeatures.BASIC_EDITING)
   public void actionPerformed(ActionEvent evt) {
        Component focusOwner = KeyboardFocusManager.
                getCurrentKeyboardFocusManager().
                getPermanentFocusOwner();
        if (focusOwner != null && focusOwner instanceof JComponent) {
            JComponent component = (JComponent) focusOwner;
            component.getTransferHandler().exportToClipboard(
                    component,
                    component.getToolkit().getSystemClipboard(),
                    TransferHandler.COPY
                    );
        }
    }
    
    
}
