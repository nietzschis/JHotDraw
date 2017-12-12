/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractButton;
import javax.swing.Action;
import org.jhotdraw.draw.DrawingView;

/**
 *
 * @author Lasse
 */
public class WorkspaceBGAction extends AbstractDrawingViewAction{
    private Color color;
    private AbstractButton button;
    private String label;
    private DrawingView view;
    
    public WorkspaceBGAction(DrawingView view, String color, AbstractButton button) {
        super(view);
        this.view = view;
        switch (color) {
            case "Black": this.color = Color.BLACK;
            break;
            case "Gray": this.color = Color.GRAY;
            break;
            case "White": this.color = Color.WHITE;
            break;
        }
        this.button = button;
        label = color;
        putValue(Action.DEFAULT, label);
        putValue(Action.NAME, label);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (button != null) {
            button.setText(label);
        }
        view.setWorkspaceBG(color);
        view.getComponent().repaint();
    }
}
