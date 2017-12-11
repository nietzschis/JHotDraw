/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.graph;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static org.jhotdraw.draw.AttributeKeys.CLOSED;

import org.jhotdraw.draw.PredefinedBezierFigure;
import org.jhotdraw.draw.PredefinedFunction;
import org.jhotdraw.geom.*;

/**
 *
 * @author joach
 */
public class GraphBezierFigure extends PredefinedBezierFigure {

    //private BezierPath path;
    private Graph graph;
    private Point2D.Double graphStartPos;
    
    
    public GraphBezierFigure() {
        path = new BezierPath();
        this.setClosed(false);
    }
     
    public BezierPath generatePath() {
        double x = graphStartPos.getX();
        double y = graphStartPos.getY();
        path.clear();
        GraphMath graphCalc = GraphMath.getInstance();
        for (double i = 0+x; i < graph.getLength()+x; i =i+5) {
            double yPoint = graphCalc.calcYCoordinate(i-x, graph, y);
            if(yPoint < 0) {
                return path;
            }
            path.addPoint(i, yPoint);
        }
        return path;
    }
    
    @Override
    public boolean canConnect() {
        return false;
    }
    
    @Override
    public void setBounds(Point2D.Double anchor, Point2D.Double lead) {
        graphStartPos = anchor;
        
        generatePath();
        invalidate();
    }   
    
    @Override
    protected void drawFill(Graphics2D g) {
        super.drawFill(g);
    }

    @Override
    protected void drawStroke(Graphics2D g) {
        super.drawStroke(g);
    }
    
    public boolean isClosed() {
        return (Boolean) getAttribute(CLOSED);
    }
    public void setClosed(boolean newValue) {
        CLOSED.set(this, newValue);
    }

    @Override
    public Rectangle2D.Double getBounds() {
        Rectangle2D.Double bounds =path.getBounds2D();
        return bounds;
    }

    @Override
    public boolean contains(Point2D.Double p) {
        return super.contains(p);
    }

    @Override
    public Object getTransformRestoreData() {
        return super.getAttributesRestoreData();
    }

    @Override
    public void restoreTransformTo(Object restoreData) {
        super.restoreTransformTo(restoreData);
    }

    @Override
    public void transform(AffineTransform tx) {
        super.transform(tx);
    }    



    @Override
    public void setFunction(PredefinedFunction function) {
        this.graph = (Graph) function;
    }
    
}

   
