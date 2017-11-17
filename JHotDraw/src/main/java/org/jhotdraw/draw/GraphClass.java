/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import org.jhotdraw.geom.*;

/**
 *
 * @author joach
 */
public class GraphClass extends AbstractAttributedFigure {

    private BezierPath graphPath;
    
    
    
    private void generatePath() {
        GraphMath graph = new GraphMath(5, 3);
        for (int i = 0; i < 10; i++) {
           graphPath.addPoint(i, graph.calcYCoordinate(i));
        }        
    }
    
    
    @Override
    protected void drawFill(Graphics2D g) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void drawStroke(Graphics2D g) {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Rectangle2D.Double getBounds() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contains(Point2D.Double p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getTransformRestoreData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void restoreTransformTo(Object restoreData) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void transform(AffineTransform tx) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}

   
