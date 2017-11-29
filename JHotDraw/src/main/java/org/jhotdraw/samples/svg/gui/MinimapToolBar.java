package org.jhotdraw.samples.svg.gui;

import java.awt.Dimension;
import java.awt.geom.Point2D;
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
import org.jhotdraw.samples.svg.ViewportModifier;
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
    private final Dimension size = new Dimension();
    
    /**
     * Creates a new {@link MinimapToolBar} with custom size.
     * @param viewportModifier used to move the viewport on the canvas then moved.
     * @param width The width of the {@link MinimapView}
     * @param height The height of the {@link MinimapView}
     */
    public MinimapToolBar(ViewportModifier viewportModifier, int width, int height) {
        size.setSize(width, height);
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        setName(labels.getString(getID() + ".toolbar"));
        this.figureListener = new FigureChangeListener();
        minimapView = new MinimapView(this);
        minimapView.setPreferredSize(size);
        minimapView.addListener(viewportModifier::centerPointOnCanvas);
    }
    
    /**
     * Creates a new {@link MinimapToolBar} with fixed size.
     */
    public MinimapToolBar() {
        this(null, 80, 80);
    }

    public MinimapToolBar(ViewportModifier viewportModifier) {
        this(viewportModifier, 80, 80);
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
     * @param d the new drawing
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
