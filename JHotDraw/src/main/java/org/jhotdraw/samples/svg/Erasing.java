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
 * This class handles the erasing of figures on the canvas
 */
public class Erasing
{
    public EraserTool eTool;

    public Erasing(EraserTool eTool){
        this.eTool = eTool;
        
    }
    
    public void Erasing(Figure fig) 
        {
//            Point.Double p = new Point.Double(evt.getX(), evt.getY());
//       System.out.println(p);
//        eTool.fig = eTool.atb.getEditor().getActiveView().getDrawing().findFigure(p);
//        System.out.println(eTool.fig);
        
                
//        if (eTool.fig == null)
//        {
//            eTool.atb.getEditor().getActiveView().getDrawing().findFigure(p);
            
//        }
//        else if (eTool.fig.contains(p)){
            
            final Figure erasedFigure = fig;
            System.out.println(fig);
//            eTool.atb.getEditor().getActiveView().getDrawing().;
            eTool.atb.getEditor().getActiveView().getDrawing().remove(fig);
            final Drawing erasedDrawing = eTool.atb.getEditor().getActiveView().getDrawing();
//            final Drawing erasedDrawing = getDrawing();
//            final Figure erasedFigure = fig;  
            erasedDrawing.fireUndoableEditHappened(new AbstractUndoableEdit() {
//            getDrawing().fireUndoableEditHappened(new AbstractUndoableEdit() {
                   

            @Override
            public String getPresentationName() {
                return eTool.presentationName;
            }

            @Override
            public void undo() throws CannotUndoException {
                super.undo();
//                erasedDrawing.remove(erasedFigure);
                erasedDrawing.add(erasedFigure);
            }

            @Override
            public void redo() throws CannotRedoException {
                super.redo();
//                erasedDrawing.add(erasedFigure);
                erasedDrawing.remove(erasedFigure);
//                view.clearSelection();
//                view.getDrawing().add(fig);
//                view.addToSelection(fig);
            }
              });
            
            
            
        }
        
    
}
