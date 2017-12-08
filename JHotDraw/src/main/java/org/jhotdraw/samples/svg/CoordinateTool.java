/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg;

import java.util.HashMap;
import org.jhotdraw.draw.*;
import static org.jhotdraw.draw.AttributeKeys.CLOSED;
import org.jhotdraw.draw.action.DrawingEditorProxy;
import org.jhotdraw.samples.svg.figures.SVGPathFigure;

/**
 * This class is a tool for inserting a coordinate system.
 * @author Henrik Bastholm
 */
public class CoordinateTool extends LineTool {

    private CoordinateView cv;

    /**
     * Initiates a new instance of CoordinateTool.
     * Used to make a button in ToolsToolbar.
     */
    public CoordinateTool() {
        cv = new CoordinateView();
        editorProxy = new DrawingEditorProxy();
        attributes = new HashMap<AttributeKey, Object>();
        attributes.put(AttributeKeys.FILL_COLOR, null);
        attributes.put(CLOSED, false);
        Figure prototype = new SVGPathFigure();
        this.prototype = prototype;
        this.prototypeAttributes = attributes;
        this.presentationName = "Line";
    }
    
    /**
     * Called upon when button is marked.
     */
    @Override
    public void activate(DrawingEditor editor) {
        this.editor = editor;
        editorProxy.setTarget(editor);
        isActive = true;
        coordinateOptions();
    }

    /**
     * called upon when button is unmarked.
     */
    @Override
    public void deactivate(DrawingEditor editor) {
        this.editor = editor;
        isActive = false;
    }
    
    @Override
    public boolean isActive() {
        return isActive;
    }

    /**
     * Will ask CoordinateView to draw the coordinate with the input from the user.
     */
    private void coordinateOptions() {
        // Call upon CoordinateView and make it return buttonOptions
        int buttonOptions = cv.checkBox();
        // buttonOptions will indicate which button that the user clicks on
        switch (buttonOptions) {
            case 0:
                // Insert without figures
                // Clears the drawing panel
                getDrawing().removeAllChildren();
                cv.insertACoordinateSystem(getDrawing());
                break;
            case 1:
                // Insert with figures
                cv.insertACoordinateSystem(getDrawing());
                break;
            default: 
                // Cancel
                break;
        }
    }
}
