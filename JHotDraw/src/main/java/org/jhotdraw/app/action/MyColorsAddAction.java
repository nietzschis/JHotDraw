/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.action.AttributeAction;
import org.jhotdraw.draw.action.ColorIcon;
import org.jhotdraw.gui.JPopupButton;
import org.jhotdraw.util.ResourceBundleUtil;
/**
 *
 * @author lefoz
 */
public class MyColorsAddAction {
    
    public void add(AttributeKey<Color> attributeKey,DrawingEditor editor,Color color,JPopupButton parent){
    HashMap<AttributeKey, Object> attributes = new HashMap<AttributeKey, Object>();
    Action a;
    attributes.put(attributeKey, color);
    parent.add( a =
    new AttributeAction(
        editor,
        attributes,
        color.toString(),
        new ColorIcon(color)));
    
    }
}