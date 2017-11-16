/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.jhotdraw.draw.DrawingEditor;
import static org.jhotdraw.draw.DrawingEditor.ACTIVE_VIEW_PROPERTY;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.FigureEvent;
import org.jhotdraw.draw.FigureListener;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author Jakob Andersen
 */
public class MinimapToolBar extends AbstractToolBar {
    
    DrawingView drawingView;
    //MinimapFigureListener figureListener = new MinimapFigureListener();
    //DrawingEditorChangeListener editorListener = new DrawingEditorChangeListener();
    
    /** Creates new instance. */
    public MinimapToolBar() {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        setName(labels.getString(getID() + ".toolbar"));
        
        //getEditor().getActiveView()
    }
    
    @Override
    public void setEditor(DrawingEditor drawingEditor){
        //getEditor().removePropertyChangeListener(editorListener);
        super.setEditor(drawingEditor);
        
        //drawingEditor.getActiveView().getDrawing();
        //System.out.println(drawingEditor.getActiveView());
        //drawingEditor.addPropertyChangeListener(editorListener);
        
        /*drawingEditor.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println(evt);
            }
        });*/
    } 
    
    @Override
    protected String getID() {
        return "minimap";
    }
    
    @Override
    protected JComponent createDisclosedComponent(int state) {
        JPanel p = null;
        
        if(state == 1){
            p = new MinimapView();
            
            p.setPreferredSize(new Dimension(80,80));
            //p.invalidate();
        }
        
        return p;
    }
    
    @Override
    protected int getDefaultDisclosureState() {
        return 1;
    }
    
    /*private class MinimapFigureListener implements FigureListener{

        @Override
        public void areaInvalidated(FigureEvent e) {
            System.out.println(e);
        }

        @Override
        public void attributeChanged(FigureEvent e) {
            System.out.println("attributeChanged");
        }

        @Override
        public void figureHandlesChanged(FigureEvent e) {
            System.out.println("figureHandlesChanged");
        }

        @Override
        public void figureChanged(FigureEvent e) {
            System.out.println("figureChanged");
        }

        @Override
        public void figureAdded(FigureEvent e) {
            System.out.println("figureAdded");
        }

        @Override
        public void figureRemoved(FigureEvent e) {
            System.out.println("figureRemoved");
        }

        @Override
        public void figureRequestRemove(FigureEvent e) {
            System.out.println("figureRequestRemove");
        }
        
    }

    private static class DrawingEditorChangeListener implements PropertyChangeListener {

        public DrawingEditorChangeListener() {
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if(evt.getPropertyName().equals(ACTIVE_VIEW_PROPERTY)){
                
            }
        }
    }*/
}
