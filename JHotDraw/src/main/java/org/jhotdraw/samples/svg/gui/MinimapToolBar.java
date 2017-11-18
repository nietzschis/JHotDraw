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
import org.jhotdraw.draw.CompositeFigureEvent;
import org.jhotdraw.draw.CompositeFigureListener;
import org.jhotdraw.draw.Drawing;
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
    FigureChangeListener figureListener;
    MinimapView minimapView;
    DrawingEditorChangeListener editorListener = new DrawingEditorChangeListener();
    
    /** Creates new instance. */
    public MinimapToolBar() {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        setName(labels.getString(getID() + ".toolbar"));
        this.figureListener = new FigureChangeListener();
        minimapView = new MinimapView(this);
        minimapView.setPreferredSize(new Dimension(80,80));
        //getEditor().getActiveView()
    }
    
    @Override
    public void setEditor(DrawingEditor drawingEditor){
        if(getEditor() != null){
            getEditor().removePropertyChangeListener(editorListener);
        }
        super.setEditor(drawingEditor);
        if(drawingEditor != null){
            setDrawing(drawingEditor.getActiveView().getDrawing());
            drawingEditor.addPropertyChangeListener(editorListener);
        }
    } 
    
    @Override
    protected String getID() {
        return "minimap";
    }
    
    @Override
    protected JComponent createDisclosedComponent(int state) {        
        if(state == 1){
            return minimapView;
        }
        
        return null;
    }
    
    @Override
    protected int getDefaultDisclosureState() {
        return 1;
    }
    
    private class FigureChangeListener implements FigureListener, CompositeFigureListener{

        @Override
        public void areaInvalidated(FigureEvent e) {
            //System.out.println(e);
            minimapView.invalidate();
            //minimapView.repaint();
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

        @Override
        public void figureAdded(CompositeFigureEvent e) {
            System.out.println("comp figureAdded");
        }

        @Override
        public void figureRemoved(CompositeFigureEvent e) {
            System.out.println("comp figureRemoved");
        }
        
    }

    private class DrawingEditorChangeListener implements PropertyChangeListener {

        public DrawingEditorChangeListener() {
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if(evt.getPropertyName().equals(ACTIVE_VIEW_PROPERTY)){
                setDrawing((Drawing) evt.getNewValue());
            }
        }
    }
    
    private Drawing activeDrawing;
    private void setDrawing(Drawing d){
        if (activeDrawing != null){
            activeDrawing.removeFigureListener(figureListener);
            activeDrawing.removeCompositeFigureListener(figureListener);
        }
        activeDrawing = d;
        
        if (activeDrawing != null){
            activeDrawing.addFigureListener(figureListener);
            activeDrawing.addCompositeFigureListener(figureListener);
        }
        
        minimapView.invalidate();
    }
}
