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
import org.jhotdraw.draw.AbstractAttributedFigure;
import org.jhotdraw.draw.AttributeKeys;
import static org.jhotdraw.draw.AttributeKeys.CLOSED;
import static org.jhotdraw.draw.AttributeKeys.STROKE_MITER_LIMIT;
import org.jhotdraw.draw.BezierFigure;
import org.jhotdraw.geom.*;

/**
 *
 * @author joach
 */
//public class GraphClass extends AbstractAttributedFigure {
public class GraphClass extends BezierFigure {

    //private BezierPath path;
    private double graphLength;
    private double graphHeight;
    private Point2D.Double graphStartPos;
    
    
    public GraphClass() {
        path = new BezierPath();
        this.setClosed(false);
    }
     
    public BezierPath generatePath(Graph graph) {
        double x = graphStartPos.getX();
        double y = graphStartPos.getY();
        path.clear();
//        GraphMath graphCalc = GraphMath.getInstance();
//        for (double i = 0+x; i < 300+x; i =i+5) {
//            double yPoint = graphCalc.calcYCoordinate(i-x, graph, y);
//            System.out.println("y: " + yPoint + " height: " + graphHeight);
//            if(yPoint < 0) {
//                return path;
//            }
//            System.out.println("Y:" + yPoint + " and x: " + i);
//            path.addPoint(i, yPoint);
//        }
        path.addPoint(50, 50);
        path.addPoint(55, 75);
        path.addPoint(60, 100);
        
        return path;
    }
    
    @Override
    public boolean canConnect() {
        return false;
    }
    
    @Override
    public void setBounds(Point2D.Double anchor, Point2D.Double lead) {        
        //graphLength = lead.getX() - anchor.getX();
        //graphHeight = lead.getY() - anchor.getY();
        graphStartPos = anchor;
        path.addAll(generatePath(new QuadraticGraph(0.05, 0,0)));
        invalidate();
    }   
    
    @Override
    protected void drawFill(Graphics2D g) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void drawStroke(Graphics2D g) {
        super.drawStroke(g);
//        BezierPath.Node oldNode = null;
//        BezierPath.Node newNode = null;
//        
//        for (BezierPath.Node node : path) {
//            BezierPath linePath = new BezierPath();
//            newNode = node;
//            if (oldNode != null) {
//                linePath.add(oldNode);
//                linePath.add(newNode);
//                g.draw(linePath);
//                oldNode = newNode;
//                newNode = null;
//            }
//        }
        
        
//        double grow = AttributeKeys.getPerpendicularDrawGrowth(this);
//        if (grow == 0d) {
//                g.draw(path);
//            } else {
//                GrowStroke gs = new GrowStroke((float) grow,
//                        (float) (AttributeKeys.getStrokeTotalWidth(this) *
//                        STROKE_MITER_LIMIT.get(this))
//                        );
//                g.draw(gs.createStrokedShape(path));
//            }
//        
//        g.draw(path);
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

   
}

   
