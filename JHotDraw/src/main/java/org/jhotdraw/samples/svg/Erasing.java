/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg;

import javax.swing.undo.*;
import org.jhotdraw.samples.svg.figures.*;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.BezierTool;
import org.jhotdraw.util.*;
import org.jhotdraw.undo.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.*;
import org.jhotdraw.geom.*;
import org.jhotdraw.samples.svg.gui.AbstractToolBar;
import org.jhotdraw.beans.AbstractBean;
import javax.swing.event.*;
import org.jhotdraw.draw.action.DrawingEditorProxy;
import org.jhotdraw.samples.svg.EraserTool;
/**
 *
 * @author Frank Frederiksen-Moeller
 * This class handles the erasing of figures on the active Drawing canvas
 * Press and hold left mouse button. Then drag the eraser over a figure and it will be removed.
 */
public class Erasing
{
    public EraserTool eTool;

    public Erasing(EraserTool eTool){
        this.eTool = eTool;
        
    }
    //This method takes a given figure as input and removes it from the active Drawing canvas
    //It also updates the labels in the Edit menu for undo and redo Erased activities
    public void Erasing(Figure fig) 
        {            
            final Figure erasedFigure = fig;        
            eTool.atb.getEditor().getActiveView().getDrawing().remove(fig);
            final Drawing erasedDrawing = eTool.atb.getEditor().getActiveView().getDrawing();
            erasedDrawing.fireUndoableEditHappened(new AbstractUndoableEdit() 
            {
                @Override
                public String getPresentationName() 
                {
                    return eTool.presentationName;
                }

                @Override
                public void undo() throws CannotUndoException 
                {
                    super.undo();
                    erasedDrawing.add(erasedFigure);
                }

                @Override
                public void redo() throws CannotRedoException 
                {
                    super.redo();
                    erasedDrawing.remove(erasedFigure);
                }
                
            });           
            
        }
        
}
