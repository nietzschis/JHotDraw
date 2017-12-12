/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import java.awt.Color;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.TransferHandler;
import org.jhotdraw.app.JHotDrawFeatures;
import static org.jhotdraw.app.action.CopyAction.ID;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.action.AbstractSelectedAction;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author mutten
 */
public class ColorHotkeyAction extends AbstractSelectedAction {

    public final static String ID = "edit.colorHotkey";
    public Color currentColor = null;
    
    /**
     * Creates a new instance.
     */
    
    public ColorHotkeyAction(DrawingEditor editor) {
        super(editor);
    }

    @FeatureEntryPoint(JHotDrawFeatures.BASIC_EDITING)
    public void actionPerformed(ActionEvent evt) {
        for (Figure f : getView().getSelectedFigures()) {
            f.willChange();
            f.setAttribute(AttributeKeys.STROKE_COLOR, currentColor);
            f.changed();
        }
    }

    public static class Red extends ColorHotkeyAction {
        public final static String ID = "edit.red";
        public Red(DrawingEditor editor) {
            super(editor);
            labels.configureAction(this, ID);
            System.out.println("1");
            super.currentColor = Color.red;
        }
    }

    public static class Green extends ColorHotkeyAction {
        public final static String ID = "edit.green";
        public Green(DrawingEditor editor) {
            super(editor);
            labels.configureAction(this, ID);
            System.out.println("2");
            super.currentColor = Color.green;
        }
    }
    
    public static class Blue extends ColorHotkeyAction {
        public final static String ID = "edit.blue";
        public Blue(DrawingEditor editor) {
            super(editor);
            labels.configureAction(this, ID);
            System.out.println("3");
            super.currentColor = Color.blue;
        }
    }

}
