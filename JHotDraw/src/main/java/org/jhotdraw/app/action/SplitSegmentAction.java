/*
 * k0ngen
 */
package org.jhotdraw.app.action;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import org.jhotdraw.app.EditableComponent;
import org.jhotdraw.app.JHotDrawFeatures;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.util.ResourceBundleUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 *
 * @author k0ngen
 */
public class SplitSegmentAction extends AbstractAction {
    public final static String ID = "edit.split";
    protected final DrawingEditor editor;
    
    public SplitSegmentAction(DrawingEditor editor) {
        this.editor = editor;
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        labels.configureAction(this, ID);
    }

    @FeatureEntryPoint(JHotDrawFeatures.BASIC_EDITING)
    public void actionPerformed(ActionEvent e) {
        Component focusOwner = KeyboardFocusManager.
                getCurrentKeyboardFocusManager().
                getPermanentFocusOwner();
        
        if (focusOwner == null)
            return;
        
        if (!(focusOwner instanceof EditableComponent))
            return;
        
        ((EditableComponent) focusOwner).split();
    }
}
