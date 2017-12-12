package org.jhotdraw.samples.svg.gui;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.samples.svg.ViewportModifier;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 * A toolbar to display a minimap on the panel.
 */
public class MinimapToolBar extends AbstractToolBar {
    
    private final PropertyChangeListener editorListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            minimapController.setDrawing(getEditor().getActiveView().getDrawing());
        }
    };
    
    private final MinimapView minimapView;
    private final MinimapController minimapController;
    
    /**
     * Creates a new {@link MinimapToolBar} with custom size.
     * @param viewportModifier used to move the viewport on the canvas then moved.
     * @param width The width of the {@link MinimapView}
     * @param height The height of the {@link MinimapView}
     */
    public MinimapToolBar(ViewportModifier viewportModifier, int width, int height) {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        setName(labels.getString(getID() + ".toolbar"));
        minimapView = new MinimapView();
        minimapView.setPreferredSize(new Dimension(width, height));
        minimapController = new MinimapController(viewportModifier, minimapView);
    }
    
    /**
     * Creates a new {@link MinimapToolBar} with fixed size.
     */
    public MinimapToolBar() {
        this((Point2D.Double p) -> {
            // do nothing
        }, 80, 80);
    }

     /**
     * Creates a new {@link MinimapToolBar} with fixed size, using given {@link ViewportModifier}.
     * 
     * @param viewportModifier 
     */
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
}