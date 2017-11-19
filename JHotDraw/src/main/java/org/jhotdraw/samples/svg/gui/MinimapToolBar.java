/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.gui;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import org.jhotdraw.draw.CompositeFigureEvent;
import org.jhotdraw.draw.CompositeFigureListener;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingEditor;
import static org.jhotdraw.draw.DrawingEditor.ACTIVE_VIEW_PROPERTY;
import org.jhotdraw.draw.FigureEvent;
import org.jhotdraw.draw.FigureListener;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 * A toolbar to display a minimap on the panel.
 */
public class MinimapToolBar extends AbstractToolBar {
    
    private final FigureChangeListener figureListener;
    private final MinimapView minimapView;
    private final DrawingEditorChangeListener editorListener = new DrawingEditorChangeListener();
    private final UndoableEditListener undoableEditListener = new UndoableEditListener() {
        @Override
        public void undoableEditHappened(UndoableEditEvent e) {
            minimapView.repaint();
        }
    };
    private Drawing activeDrawing;
    private Dimension size = new Dimension();
    
    public MinimapToolBar(int width, int height) {
        size.setSize(width, height);
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        setName(labels.getString(getID() + ".toolbar"));
        this.figureListener = new FigureChangeListener();
        minimapView = new MinimapView(this);
        minimapView.setPreferredSize(size);
    }
    
    /** Creates new instance. */
    public MinimapToolBar() {
        this(80,80);
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
            minimapView.repaint();
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
                setDrawing((Drawing) evt.getNewValue()); // update whenever the active drawing changes.
            }
            minimapView.repaint();
        }
    }
    
    /**
     * Sets the drawing that should be shown on the minimap.
     * @param d 
     */
    private void setDrawing(Drawing d){
        if (activeDrawing != null){
            activeDrawing.removeFigureListener(figureListener);
            activeDrawing.removeCompositeFigureListener(figureListener);
            activeDrawing.removeUndoableEditListener(undoableEditListener);
        }
        activeDrawing = d;
        
        if (activeDrawing != null){
            activeDrawing.addFigureListener(figureListener);
            activeDrawing.addCompositeFigureListener(figureListener);
            activeDrawing.addUndoableEditListener(undoableEditListener);
        }
        
        minimapView.invalidate();
    }
}
