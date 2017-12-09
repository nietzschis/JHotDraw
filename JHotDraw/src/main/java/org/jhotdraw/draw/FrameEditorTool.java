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
public class FrameEditorTool extends AbstractTool implements ToolListener{

    private Tool creationTool;
    private Tool selectionTool;
    private DrawingEditor editor;

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
    public void mouseMoved(MouseEvent evt) {
        //selectionTool.mouseMoved(evt);
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
        Collection<Figure> figuresSelected = event.getView().findFigures(event.getInvalidatedArea());
        SVGGroupFigure groupFigure = new SVGGroupFigure();
        groupFigure.addAll(figuresSelected);
        event.getView().getDrawing().add(groupFigure);
        
    }

    @Override
    public void areaInvalidated(ToolEvent e) {
        
    }

    private void addToolListenerToSelectionTool() {
        this.selectionTool.addToolListener(this);
    }

}
