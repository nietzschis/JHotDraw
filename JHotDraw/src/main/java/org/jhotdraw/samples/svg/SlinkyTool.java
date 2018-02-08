package org.jhotdraw.samples.svg;

import java.awt.Paint;
import java.awt.Point;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.samples.svg.figures.SVGBezierFigure;
import org.jhotdraw.samples.svg.figures.SVGPathFigure;
import org.jhotdraw.samples.svg.figures.SVGEllipseFigure;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import org.jhotdraw.draw.BezierFigure;
import org.jhotdraw.draw.CreationTool;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.FigurePainter;
import java.util.Random;
/**
 *
 * @author Ľuboš
 */
public class SlinkyTool extends PathTool {
    
    
    //Delete this code
    
    protected Map<AttributeKey, Object> prototypeAttributes;
    protected Figure prototype = new SVGEllipseFigure();
    protected Figure createdFigure;
    HashMap<AttributeKey, Object> attributes;
    
    
    
    /**Creates a new instance. */
    public SlinkyTool(SVGPathFigure pathPrototype, SVGBezierFigure bezierPrototype) {
        super(pathPrototype, bezierPrototype);
    }
    /**Creates a new instance. */
    public SlinkyTool(SVGPathFigure pathPrototype, SVGBezierFigure bezierPrototype, Map<AttributeKey, Object> attributes) {
        super(pathPrototype, bezierPrototype, attributes);
    }

    
    @Override
    public void mouseDragged(MouseEvent evt) {
        
        
        FigurePainter painter = new FigurePainter();
        painter.paint(evt.getButton(), editor);
        getView().clearSelection();
        createdFigure = createSpray();
        Point2D.Double p = constrainPoint(viewToDrawing(anchor));
        anchor.x = evt.getX()-25;
        anchor.y = evt.getY()-25;
        createdFigure.setBounds(p, p);
        getDrawing().add(createdFigure);
        
        createdFigure.setBounds(constrainPoint(new Point(anchor.x+50, anchor.y+50)),p);

    
    }
    

    @SuppressWarnings("unchecked")
    protected Figure createSpray() {
        Figure f = (Figure) prototype.clone();
        getEditor().applyDefaultAttributesTo(f);
        if (prototypeAttributes != null) {
            for (Map.Entry<AttributeKey, Object> entry : prototypeAttributes.entrySet()) {
                entry.getKey().basicSet(f, entry.getValue());
            }
        }
        return f;
    }
    

    

}
