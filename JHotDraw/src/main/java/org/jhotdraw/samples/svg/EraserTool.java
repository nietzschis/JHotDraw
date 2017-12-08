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
import javax.swing.ImageIcon;
import org.jhotdraw.draw.action.DrawingEditorProxy;
import org.jhotdraw.samples.svg.Erasing;

/**
 *
 * @author Frank Frederiksen-Moeller
 * //This tool will completely remove a figure or a drawing from the canvas
 */
public class EraserTool extends AbstractTool
{
    protected DrawingEditor drawEdit;
//    private Drawing drawing;
//    private DrawingView view;
    protected Point hotspot = new Point(0,0);
    public Figure fig;
//    protected EventListenerList listenerList1 = new EventListenerList();
//    private DrawingEditorProxy editorProxy1;
//    private Tool track;
    public AbstractToolBar atb;
    public String name;
    
    public Erasing erase;
    public String presentationName;
    
    
    public EraserTool(AbstractToolBar atb)
    {
        this.atb = atb;
        erase = new Erasing(this);             
//        track.addToolListener(this);
//        tb.getEditor().getActiveView().getDrawing().removeAllChildren();
//        tb.getEditor().getActiveView().getDrawing().getChildren().contains(fg);
       
                
    }
    
    @Override
    public void activate(DrawingEditor editor) {
        this.drawEdit = editor;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        ResourceBundleUtil eraser = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
//        Image image1 = eraser.getString("createEraserToolCursor");
//        Image image = toolkit.getImage(eraser.getString("createEraserToolCursor"));
//        Image image = toolkit.getClass().getResource(eraser.getString("createEraserToolCursor"));
//        Image image = Toolkit.getDefaultToolkit().getImage("JHotDraw\\src\\main\\resources\\org\\jhotdraw\\samples\\svg\\action\\images\\createEraserToolCursor.png");
        Image image = new ImageIcon("src\\main\\resources\\org\\jhotdraw\\samples\\svg\\action\\images\\createEraserToolCursor.png").getImage();
//        Image image = toolkit.getImage(eraser.getString("createEraserToolCursor.png"));
//        anchor1 = 
        Cursor c = toolkit.createCustomCursor(image, hotspot, "eraser");
        System.out.println(image);
        System.out.println(eraser.getString("createEraserToolCursor"));
        atb.getEditor().getActiveView().setCursor(c);
        
        
        
//        getView().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                
        if (name == null) {
            ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
            name = labels.getString("edit.erasedFigure.text");
            
        }
        this.presentationName = name;
       
//        editor.getActiveView().getDrawing().removeAllChildren();
//        editor.getActiveView().s
        //editorProxy1.setTarget(editor);
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
//    public void deactivate(DrawingEditor editor) {
//        
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void addToolListener(ToolListener l) {
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void removeToolListener(ToolListener l) {
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void draw(Graphics2D g) {
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

//    @Override
//    public void editDelete() {
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

//    @Override
//    public void editCut() {
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void editCopy() {
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void editDuplicate() {
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void editPaste() {
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

//    @Override
//    public String getToolTipText(DrawingView view, MouseEvent evt) {
//        return null;
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public boolean supportsHandleInteraction() {
//        return false;
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

//    @Override
//    public void mouseClicked(MouseEvent e) {
//        
//            
////        System.out.println("MouseClicked");
//        
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void mousePressed(MouseEvent e) {
//        
////        System.out.println("MousePressed");
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

//    @Override
//    public void mouseReleased(MouseEvent evt) {
//                
////            final Figure erasedFigure = fig;
////            final Drawing erasedDrawing = getDrawing();
////            getDrawing().fireUndoableEditHappened(new AbstractUndoableEdit() {
//                   
//
////            public String getPresentationName() {
////                return presentationName;
////            }
//
////            @Override
////            public void undo() throws CannotUndoException {
////                super.undo();
////                erasedDrawing.remove(erasedFigure);
////                erasedDrawing.add(erasedFigure);
////            }
//
////            @Override
////            public void redo() throws CannotRedoException {
////                super.redo();
////                erasedDrawing.add(erasedFigure);
////                erasedDrawing.remove(erasedFigure);
////                view.clearSelection();
////                view.getDrawing().add(fig);
////                view.addToSelection(fig);
////            }
////              });
////        System.out.println("MouseReleased");
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

//    @Override
//    public void mouseEntered(MouseEvent e) {
////        System.out.println("MouseEntered");
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void mouseExited(MouseEvent e) {
////        System.out.println("MouseExited");
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    
    
@Override
    public void mouseDragged(MouseEvent evt) {
//        Point p = new Point();
       Point.Double p = new Point.Double(evt.getX(), evt.getY());
       System.out.println(p);
        fig = atb.getEditor().getActiveView().getDrawing().findFigure(p);
        System.out.println(fig);
        
                
        if (fig == null)
        {
            atb.getEditor().getActiveView().getDrawing().findFigure(p);
            
        }
        else if (fig.contains(p)) { 
//           erase = new Erasing();
           erase.Erasing(fig);
           
           
           
//            System.out.println(view
//                    .getDrawing()
//                    .remove(fig) + " test ");
//            final Figure erasedFigure = fig;
//            atb.getEditor().getActiveView().getDrawing().remove(fig);
//            final Drawing erasedDrawing = atb.getEditor().getActiveView().getDrawing();
//            final Drawing erasedDrawing = getDrawing();
//            final Figure erasedFigure = fig;  
//            erasedDrawing.fireUndoableEditHappened(new AbstractUndoableEdit() {
//            getDrawing().fireUndoableEditHappened(new AbstractUndoableEdit() {
                   

//            @Override
//            public String getPresentationName() {
//                return presentationName;
//            }

//            @Override
//            public void undo() throws CannotUndoException {
//                super.undo();
//                erasedDrawing.remove(erasedFigure);
//                erasedDrawing.add(erasedFigure);
//            }

//            @Override
//            public void redo() throws CannotRedoException {
//                super.redo();
//                erasedDrawing.add(erasedFigure);
//                erasedDrawing.remove(erasedFigure);
//                view.clearSelection();
//                view.getDrawing().add(fig);
//                view.addToSelection(fig);
//            }
//              });
    }
//        else
//        {
//            System.out.println("Hello_figure");
//        }
////        tb.getEditor().getActiveView().getDrawing().findFigure(new Point.Double(evt.getX(),evt.getY()));
//        // Figure fig = getEditor().getActiveView().getDrawing().findFigureInside(new Point.Double(evt.getX(),evt.getY())); 
//        // finder inderste figure, ved ikke om det er bedre end findFigure()
//        System.out.println(fig);
        
//        if(fig.getDrawingArea().isEmpty() != true){
//            System.out.println("Hello_figure");
            
//        }
//        else
//        {
            
//        }
        }
    

//
//    @Override
//    public void mouseDragged(MouseEvent e) {
////        System.out.println(e.getPoint());
//        
////        tb.getEditor().getActiveView().getDrawing().getChildren().contains(fg);
//        editor1.getActiveView().getEditor()
//
//        if (editor1.getActiveView().findFigure(anchor1) == null)
//        {
//            editor1.getActiveView().getDrawing().removeAllChildren();            
//        }
//        
////        if (editor1.getActiveView().getDrawing().findFigure(p))
////        {
////            
////            System.out.println("figure");
////        }
//////        if (editor1.getActiveView().findFigure(anchor1) != null)
////        {
////            System.out.println("figure");
////        }
////        System.out.println(editor1.getActiveView().findFigure(anchor1));
//        System.out.println("MouseDragged");
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

//    @Override
//    public void mouseMoved(MouseEvent e) {
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void keyTyped(KeyEvent e) {
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void keyPressed(KeyEvent e) {
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void keyReleased(KeyEvent e) {
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    
}
