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
import org.jhotdraw.samples.svg.ViewportModifier;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 * A toolbar to display a minimap on the panel.
 */
public class MinimapToolBar extends AbstractToolBar {
    
    private final DrawingEditorChangeListener editorListener = new DrawingEditorChangeListener();
    private final MinimapView minimapView;
    private final MinimapController minimapController;
    
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
        minimapView = new MinimapView(this);
        minimapView.setPreferredSize(size);
        minimapController = new MinimapController(viewportModifier, minimapView);
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
            minimapController.setDrawing(drawingEditor.getActiveView().getDrawing());
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
    
    private class DrawingEditorChangeListener implements PropertyChangeListener {

        public DrawingEditorChangeListener() {
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if(evt.getPropertyName().equals(ACTIVE_VIEW_PROPERTY)){
                minimapController.setDrawing((Drawing) evt.getNewValue()); // update whenever the active drawing changes.
            }
            minimapView.repaint();
        }
    }
}
