/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg;

import java.util.Map;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.BezierFigure;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.samples.svg.figures.SVGBezierFigure;
import org.jhotdraw.samples.svg.figures.SVGPathFigure;

/**
 *
 * @author Pc
 */
public class PathSecondTool extends PathTool {
    
    public PathSecondTool(SVGPathFigure pathPrototype, SVGBezierFigure bezierPrototype) {
        super(pathPrototype, bezierPrototype);
    }

    public PathSecondTool(SVGPathFigure pathPrototype, SVGBezierFigure bezierPrototype, Map<AttributeKey, Object> attributes) {
        super(pathPrototype, bezierPrototype, attributes);
    }
    
    protected void finishCreation(BezierFigure createdFigure, DrawingView creationView) {
        creationView.getDrawing().remove(createdFigure);
        SVGPathFigure createdPath = createPath();
        createdPath.removeAllChildren();
        createdPath.add(createdFigure);
        creationView.getDrawing().add(createdPath);
        fireUndoEvent(createdPath, creationView);
        creationView.addToSelection(createdPath);
        if (isToolDoneAfterCreation()) {
            fireToolDone();
        }
    }
}
