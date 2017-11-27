/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw;

import java.awt.event.MouseEvent;
import org.jhotdraw.samples.svg.figures.SVGPathFigure;

/**
 *
 * @author Jonas
 */
public class FreeFormSelection extends AbstractTool implements ToolListener {

    private Tool tracker;
    
    private SelectAreaTracker selectAreaTracker;
    
    private DragTracker dragTracker;
    
    public FreeFormSelection(SVGPathFigure path){
        
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
                if (getView() != null && getView().isEnabled()) {
            tracker.mouseDragged(e);
        }
    }
    
    protected DragTracker getDragTracker(Figure f) {
        if (dragTracker == null) {
            dragTracker = new DefaultDragTracker();
        }
        dragTracker.setDraggedFigure(f);
        return dragTracker;
    }

    @Override
    public void toolStarted(ToolEvent event) {
        
    }

    @Override
    public void toolDone(ToolEvent event) {
              Tool newTracker = getSelectAreaTracker();

        if (newTracker != null) {
            if (tracker != null) {
                tracker.deactivate(getEditor());
                tracker.removeToolListener(this);
            }
            tracker = newTracker;
            tracker.activate(getEditor());
            tracker.addToolListener(this);
        }
        fireToolDone();
    }
    
    protected SelectAreaTracker getSelectAreaTracker() {
        if (selectAreaTracker == null) {
            selectAreaTracker = new DefaultSelectAreaTracker();
        }
        return selectAreaTracker;
    }

    @Override
    public void areaInvalidated(ToolEvent e) {
        fireAreaInvalidated(e.getInvalidatedArea());
    }
    
}
