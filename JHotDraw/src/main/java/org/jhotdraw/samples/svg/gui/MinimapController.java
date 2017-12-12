package org.jhotdraw.samples.svg.gui;

import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import org.jhotdraw.draw.CompositeFigureEvent;
import org.jhotdraw.draw.CompositeFigureListener;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.FigureEvent;
import org.jhotdraw.draw.FigureListener;
import org.jhotdraw.samples.svg.ViewportModifier;

/**
 * Controller to update, and listen for events on {@link MinimapView}.
 */
public class MinimapController {
    
    private final FigureChangeListener figureListener = new FigureChangeListener();
    private final UndoableEditListener undoableEditListener = new UndoableEditListener() {
        @Override
        public void undoableEditHappened(UndoableEditEvent e) {
            minimapView.repaint();
        }
    };
    private Drawing activeDrawing;
    
    private final MinimapView minimapView;

    /**
     * Creates a new controller for {@link MinimapView}.
     * @param viewportModifier A function to be called whenever the viewport on the canvas should change position.
     * @param minimapView The view to control.
     */
    MinimapController(ViewportModifier viewportModifier, MinimapView minimapView) {
        this.minimapView = minimapView;
        
        if(viewportModifier != null){
            minimapView.addListener(viewportModifier::centerViewportOnPoint);
        }
    }
    
    /**
     * Sets the drawing that should be shown on the minimap.
     * @param d the new drawing
     */
    void setDrawing(Drawing d){
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
        
        minimapView.setDrawing(d);
        minimapView.repaint();
    }
    
    private class FigureChangeListener implements FigureListener, CompositeFigureListener{

        @Override
        public void areaInvalidated(FigureEvent e) {
            minimapView.repaint();
        }

        @Override
        public void attributeChanged(FigureEvent e) {
            //ignored
        }

        @Override
        public void figureHandlesChanged(FigureEvent e) {
            //ignored
        }

        @Override
        public void figureChanged(FigureEvent e) {
            //ignored
        }

        @Override
        public void figureAdded(FigureEvent e) {
            //ignored
        }

        @Override
        public void figureRemoved(FigureEvent e) {
            //ignored
        }

        @Override
        public void figureRequestRemove(FigureEvent e) {
            //ignored
        }

        @Override
        public void figureAdded(CompositeFigureEvent e) {
            //ignored
        }

        @Override
        public void figureRemoved(CompositeFigureEvent e) {
            //ignored
        }
        
    }
}