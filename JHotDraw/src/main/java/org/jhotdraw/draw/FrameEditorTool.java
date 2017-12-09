/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.util.Collection;
import javax.swing.Action;
import org.jhotdraw.samples.svg.figures.SVGGroupFigure;

/**
 *
 * @author Alexander
 */
public class FrameEditorTool extends AbstractTool implements ToolListener {

    private CreationTool creationTool;
    private Tool selectionTool;
    private DrawingEditor editor;
    private SVGGroupFigure groupFigure = new SVGGroupFigure();

    public FrameEditorTool(Tool selectionTool, CreationTool creationTool, DrawingEditor editor) {
        this.selectionTool = selectionTool;
        this.creationTool = creationTool;
        this.editor = editor;
        addToolListenerToSelectionTool();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        editor.setTool(selectionTool);
        selectionTool.mouseDragged(e);
    }

    @Override
    public void activate(DrawingEditor editor) {
        super.activate(editor);
        getView().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

    @Override
    public void mouseReleased(MouseEvent evt) {
        SelectionTool selectionToolFromEvent = (SelectionTool) selectionTool;
    }
    
    
    @Override
    public void mousePressed(MouseEvent evt) {
        super.mousePressed(evt);
        selectionTool.activate(editor);
        selectionTool.mousePressed(evt);
        activate(editor);
    }

    @Override
    public void toolStarted(ToolEvent event) {

    }

    @Override
    public void toolDone(ToolEvent event) {
        //TODO: create figure when selection tool is done.
        System.out.println("tool done is fired");

    }

    @Override
    public void areaInvalidated(ToolEvent event) {
        SelectionTool selectionToolFromEvent = (SelectionTool) event.getTool();
        if (!event.getView().findFigures(event.getInvalidatedArea()).isEmpty() && !selectionToolFromEvent.isWorking) {
            //creationTool.createFigure();
            Collection<Figure> figuresSelected = event.getView().findFigures(event.getInvalidatedArea());
            
            groupFigure.basicAddAll(0, figuresSelected);
            event.getView().getDrawing().add(groupFigure);
        }

    }

    private void addToolListenerToSelectionTool() {
        this.selectionTool.addToolListener(this);
    }

}
