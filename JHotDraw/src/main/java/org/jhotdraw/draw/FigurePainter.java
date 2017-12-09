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

    public void paint(Figure figure, MouseEvent event, DrawingEditor editor) {
        
        switch (event.getButton()) {
            case 1:
                // LEFT MOUSE
                figure.setAttribute(STROKE_COLOR, figure.getAttribute(STROKE_COLOR_RIGHT_MOUSE));
                figure.setAttribute(AttributeKeys.FILL_COLOR, figure.getAttribute(AttributeKeys.FILL_COLOR_RIGHT_MOUSE));
                
                break;
            case 3:
                // RIGHT MOUSE
                figure.setAttribute(STROKE_COLOR, figure.getAttribute(STROKE_COLOR_LEFT_MOUSE));
                figure.setAttribute(AttributeKeys.FILL_COLOR, figure.getAttribute(AttributeKeys.FILL_COLOR_LEFT_MOUSE));
        }
    }

}
