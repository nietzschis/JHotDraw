package org.jhotdraw.samples.svg;

import org.jhotdraw.draw.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import org.jhotdraw.draw.action.DrawingEditorProxy;

/**
 * This class is a tool for drawing a line.
 * @author Henrik Bastholm
 */
public class LineTool extends AbstractTool {
    
    protected Map<AttributeKey, Object> prototypeAttributes;
    protected String presentationName;
    protected Dimension minimalSizeTreshold = new Dimension(2, 2);
    protected Dimension minimalSize = new Dimension(40, 40);
    protected Figure prototype;
    protected Figure createdFigure;
    protected DrawingEditorProxy editorProxy;
    HashMap<AttributeKey, Object> attributes;
    /**
     * If this is set to false, the CreationTool does not fire toolDone
     * after a new Figure has been created. This allows to create multiple
     * figures consecutively.
     */
    private boolean isToolDoneAfterCreation = true;
    
    // This is set to true, if this is the active tool of the editor.
    protected boolean isActive;
    

    @Override
    public void addToolListener(ToolListener l) {
    }

    @Override
    public void removeToolListener(ToolListener l) {
    }

    /**
     * Called upon when mouse is pressed on drawing while this tool is active.
     */
    @Override
    public void mousePressed(MouseEvent e) {        
        
        super.mousePressed(e);
        getView().clearSelection();
        createdFigure = createFigure();
        Point2D.Double p = constrainPoint(viewToDrawing(anchor));
        anchor.x = e.getX();
        anchor.y = e.getY();
        createdFigure.setBounds(p, p);
        getDrawing().add(createdFigure);
    }

    /**
     * Called upon when mouse is released from drawing while this tool is active.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        
        if (createdFigure != null) {
            Rectangle2D.Double bounds = createdFigure.getBounds();
            if (bounds.width == 0 && bounds.height == 0) {
                getDrawing().remove(createdFigure);
                if (isToolDoneAfterCreation()) {
                    fireToolDone();
                }
            } else {
                if (Math.abs(anchor.x - e.getX()) < minimalSizeTreshold.width &&
                        Math.abs(anchor.y - e.getY()) < minimalSizeTreshold.height) {
                    createdFigure.willChange();
                    createdFigure.setBounds(
                            constrainPoint(new Point(anchor.x, anchor.y)),
                            constrainPoint(new Point(
                            anchor.x + (int) Math.max(bounds.width, minimalSize.width),
                            anchor.y + (int) Math.max(bounds.height, minimalSize.height))));
                    createdFigure.changed();
                }
                if (createdFigure instanceof CompositeFigure) {
                    ((CompositeFigure) createdFigure).layout();
                }
                final Figure addedFigure = createdFigure;
                final Drawing addedDrawing = getDrawing();
                getDrawing().fireUndoableEditHappened(new AbstractUndoableEdit() {

                    @Override
                    public String getPresentationName() {
                        return presentationName;
                    }

                    @Override
                    public void undo() throws CannotUndoException {
                        super.undo();
                        addedDrawing.remove(addedFigure);
                    }

                    @Override
                    public void redo() throws CannotRedoException {
                        super.redo();
                        addedDrawing.add(addedFigure);
                    }
                });
                creationFinished(createdFigure);
                createdFigure = null;
            }
        } else {
            if (isToolDoneAfterCreation()) {
                fireToolDone();
            }
        }
        isToolDoneAfterCreation = false;
    }
    
    /**
     * Called upon when mouse is pressed and dragged drawing while this tool is active.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (createdFigure != null) {
            Point2D.Double p = constrainPoint(new Point(e.getX(), e.getY()));
            createdFigure.willChange();
            createdFigure.setBounds(
                    constrainPoint(new Point(anchor.x, anchor.y)),
                    p);
            createdFigure.changed();
        }
    }

    /**
     * Returns a newly created figure with prototypeAttributes.
     */
    @SuppressWarnings("unchecked")
    protected Figure createFigure() {
        Figure f = (Figure) prototype.clone();
        getEditor().applyDefaultAttributesTo(f);
        if (prototypeAttributes != null) {
            for (Map.Entry<AttributeKey, Object> entry : prototypeAttributes.entrySet()) {
                entry.getKey().basicSet(f, entry.getValue());
            }
        }
        return f;
    }
    
    public boolean isToolDoneAfterCreation() {
        return isToolDoneAfterCreation;
    }

    /**
     * Final touches being made to figure.
     * We don't wish to change the figure anymore, when this is called.
     */
    protected void creationFinished(Figure createdFigure) {
        if (createdFigure.isSelectable()) {
            getView().addToSelection(createdFigure);
        }
        if (isToolDoneAfterCreation()) {
            fireToolDone();
        }
    }
    
}
