/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;
import java.util.*;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.AttributeKeys;
import static org.jhotdraw.draw.AttributeKeys.FILL_COLOR;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.gui.JPopupButton;
import org.jhotdraw.util.ResourceBundleUtil;

import javax.swing.*;

/**
 *
 * @author Jebi
 */
public class EyedropperAction extends AbstractSelectedAction {
    private ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels", Locale.getDefault());
    public static AbstractButton popup;
    Figure figure;
    DrawingEditor editor = getEditor();
    
    
    
    /** Creates a new instance. */
    public EyedropperAction(DrawingEditor editor, AbstractButton popup) {
        super(editor);
        this.popup = popup;
        labels.configureAction(this, "edit.eyedropper");
        setEnabled(true);
    }

    public void actionPerformed(java.awt.event.ActionEvent e) {
        eyedrop();
    }
    
    
    
    public void eyedrop() {
        Collection<Figure> selection = getView().getSelectedFigures();        
        if (selection.size() == 1) {
            figure = (Figure) selection.iterator().next();
            ButtonFactory.eyedropped.add(new ColorIcon(figure.getAttribute(AttributeKeys.FILL_COLOR)));
            //Adding the eyedropped color to the  color button
            setColorToButton();
        }
           
    }
    
   private void setColorToButton(){
            AttributeAction a;
            HashMap<AttributeKey, Object> attributes = new HashMap<AttributeKey, Object>(new HashMap<AttributeKey, Object>());
            attributes.put(FILL_COLOR, figure.getAttribute(AttributeKeys.FILL_COLOR));
            ((JPopupButton)popup).add(a = new AttributeAction(
                            editor,
                            attributes,
                            labels.getToolTipTextProperty("attribute.fillColor"),
                            new ColorIcon(ButtonFactory.eyedropped.getLast().getColor())));
          //Adding vales to the color
            a.putValue(Action.SHORT_DESCRIPTION, new ColorIcon(ButtonFactory.eyedropped.getLast().getColor()).getName());
        }
    
}