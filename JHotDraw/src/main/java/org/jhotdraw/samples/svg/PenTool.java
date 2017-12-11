package org.jhotdraw.samples.svg;

import java.awt.Point;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.samples.svg.figures.SVGBezierFigure;
import org.jhotdraw.samples.svg.figures.SVGPathFigure;

import java.awt.event.MouseEvent;
import java.util.Map;
import org.jhotdraw.draw.BezierFigure;
import org.jhotdraw.draw.DrawingView;
/**
 *
 * @author Ľuboš
 */
public class PenTool extends PathTool {
    /**
     * This tool is based on BezierTool, is using same classes but changing them
     * to make in the end tool-pen
     */
    
    /**Creates a new instance. */
    public PenTool(SVGPathFigure pathPrototype, SVGBezierFigure bezierPrototype) {
        super(pathPrototype, bezierPrototype);
    }
    /**Creates a new instance. */
    public PenTool(SVGPathFigure pathPrototype, SVGBezierFigure bezierPrototype, Map<AttributeKey, Object> attributes) {
        super(pathPrototype, bezierPrototype, attributes);
    }

    /**
     * mouseRealeased overrides function mouseRealeased in BezierTool and makes line stop drawing after Releasing Mouse
     */
    @Override
    public void mouseReleased(MouseEvent evt) {
        super.mouseReleased(evt);

        if (createdFigure == null)
            return;

        finishWhenMouseReleased = null;

        finishCreation(createdFigure, creationView);
        createdFigure = null;
    }
    @Override
    public void mouseDragged(MouseEvent evt) {
        int x = evt.getX();
        int y = evt.getY();
        addPointToFigure(getView().viewToDrawing(new Point(x, y)));
    }

    /**
     * finishCreation overrides finishCreation in Pathtool, in this case Path for drawing is not added
     */
    @Override
    protected void finishCreation(BezierFigure createdFigure, DrawingView creationView) {
        creationView.getDrawing().remove(createdFigure);
        SVGPathFigure createdPath = createPath();
        createdPath.removeAllChildren();
        createdPath.add(createdFigure);
        createdPath.setSelectable(false);
        creationView.getDrawing().add(createdPath);
        fireUndoEvent(createdPath, creationView);
        if (isToolDoneAfterCreation()) {
            fireToolDone();
        }
    }
}
