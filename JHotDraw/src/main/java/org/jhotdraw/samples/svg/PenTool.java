package org.jhotdraw.samples.svg;

import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.samples.svg.figures.SVGBezierFigure;
import org.jhotdraw.samples.svg.figures.SVGPathFigure;

import java.awt.event.MouseEvent;
import java.util.Map;
import org.jhotdraw.draw.BezierFigure;
import org.jhotdraw.draw.DrawingView;

public class PenTool extends PathTool {

    public PenTool(SVGPathFigure pathPrototype, SVGBezierFigure bezierPrototype) {
        super(pathPrototype, bezierPrototype);
    }

    public PenTool(SVGPathFigure pathPrototype, SVGBezierFigure bezierPrototype, Map<AttributeKey, Object> attributes) {
        super(pathPrototype, bezierPrototype, attributes);
    }

    @Override
    public void mouseReleased(MouseEvent evt) {
        super.mouseReleased(evt);

        if (createdFigure == null)
            return;

        finishWhenMouseReleased = null;

        finishCreation(createdFigure, creationView);
        createdFigure = null;
    }
    
    protected void finishCreation(BezierFigure createdFigure, DrawingView creationView) {
        creationView.getDrawing().remove(createdFigure);
        SVGPathFigure createdPath = createPath();
        createdPath.removeAllChildren();
        createdPath.add(createdFigure);
        creationView.getDrawing().add(createdPath);
        fireUndoEvent(createdPath, creationView);
        //creationView.addToSelection(createdPath);
        if (isToolDoneAfterCreation()) {
            fireToolDone();
        }
    }
}
