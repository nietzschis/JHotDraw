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
    
    
    
    
    protected Map<AttributeKey, Object> prototypeAttributes;
    protected Figure prototype = new SVGEllipseFigure();
    protected Figure createdFigure;
    HashMap<AttributeKey, Object> attributes;
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * This tool is based on BezierTool, is using same classes but changing them
     * to make in the end tool-pen
     */
    
    /**Creates a new instance. */
    public SlinkyTool(SVGPathFigure pathPrototype, SVGBezierFigure bezierPrototype) {
        super(pathPrototype, bezierPrototype);
    }
    /**Creates a new instance. */
    public SlinkyTool(SVGPathFigure pathPrototype, SVGBezierFigure bezierPrototype, Map<AttributeKey, Object> attributes) {
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

        finish(createdFigure, creationView);
        createdFigure = null;
    }
    
    /**
     * mouseDragged in BezietTool was disabling Finish creation, due to that I had to cut first three line of that 
     */
    
    @Override
    public void mouseDragged(MouseEvent evt) {
        //int x = evt.getX();
        //int y = evt.getY();
        
        //SVGEllipseFigure (1,1,1,1);
        //addPointToFigure(getView().viewToDrawing(new Point(x, y)));
     
        
        //Paint paint = SVGAttributeKeys.getFillPaint(this);
        
        
        double rand = Math.random();
        int rando = (int)(rand * 10);
        System.out.println(rando);
        
        
        //super.mousePressed(evt);
        //Makes sure the drawn ellipses are the choosen colour.
        FigurePainter painter = new FigurePainter();
        painter.paint(evt.getButton(), editor);
        getView().clearSelection();
        createdFigure = createSpray();
        Point2D.Double p = constrainPoint(viewToDrawing(anchor));
        anchor.x = evt.getX()-25;
        anchor.y = evt.getY()-25;
        createdFigure.setBounds(p, p);
        getDrawing().add(createdFigure);
        
        //Point2D.Double p2 = constrainPoint(new Point(anchor.x, anchor.y));
        createdFigure.setBounds(constrainPoint(new Point(anchor.x+50, anchor.y+50)),p);
           //createdFigure.changed();
        
        
        
        
        
    }
    
    

    protected void finish(Figure createdFigure, DrawingView creationView) {
        creationView.getDrawing().remove(createdFigure);
        SVGPathFigure createdPath = createPath();
        createdPath.removeAllChildren();
        createdPath.add(createdFigure);
        createdPath.setSelectable(false);
        creationView.getDrawing().add(createdPath);
        fireUndoEvent(createdPath, creationView);
        isToolDoneAferCreation();
    }
    
    /*
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
    
    */
    
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
    

    
    
    protected void isToolDoneAferCreation(){
        if (isToolDoneAfterCreation()) {
            fireToolDone();
        }
    } 
    /**
     * finishCreation overrides finishCreation in Pathtool, in this case Path for drawing is not added
     * and also also sets selecion afterwards off
     */
    
    /*
    @Override
    protected void finishCreation(BezierFigure createdFigure, DrawingView creationView) {
        creationView.getDrawing().remove(createdFigure);
        SVGPathFigure createdPath = createPath();
        createdPath.removeAllChildren();
        createdPath.add(createdFigure);
        createdPath.setSelectable(false);
        creationView.getDrawing().add(createdPath);
        fireUndoEvent(createdPath, creationView);
        isToolDoneAferCreation();
    }
    */
    
    
    /*
    protected void isToolDoneAferCreation(){
        if (isToolDoneAfterCreation()) {
            fireToolDone();
        }
    } 
*/
}
