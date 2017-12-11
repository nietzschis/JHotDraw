/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw;

import java.awt.Color;
import java.awt.event.MouseEvent;
import static org.jhotdraw.draw.AttributeKeys.STROKE_COLOR;
import static org.jhotdraw.draw.AttributeKeys.STROKE_COLOR_LEFT_MOUSE;
import static org.jhotdraw.draw.AttributeKeys.STROKE_COLOR_RIGHT_MOUSE;

/**
 *
 * @author karim
 */
public class FigurePainter {

    /**
     * Paints a figure the color choosent to left or right mouse button.
     * @param figure the figure that will be pained.
     * @param int MouseEvent for getting which mouse button is pressed 1 = left mouse button 3 = right mouse button.
     * @param editor For getting the color of left or right mouse button.
     */
    public void paint(Figure figure, int MouseButton, DrawingEditor editor) {
        
        switch (MouseButton) {
            case 1:
                // LEFT MOUSE
                figure.setAttribute(STROKE_COLOR, figure.getAttribute(STROKE_COLOR_RIGHT_MOUSE));
                figure.setAttribute(AttributeKeys.FILL_COLOR, figure.getAttribute(AttributeKeys.FILL_COLOR_RIGHT_MOUSE));
                break;
            case 3:
                // RIGHT MOUSE
                figure.setAttribute(STROKE_COLOR, figure.getAttribute(STROKE_COLOR_LEFT_MOUSE));
                figure.setAttribute(AttributeKeys.FILL_COLOR, figure.getAttribute(AttributeKeys.FILL_COLOR_LEFT_MOUSE));
                break;
            default:
                   System.out.println("Mouse button not reconized, mouse button number: " + MouseButton);
                
        }
    }

}
