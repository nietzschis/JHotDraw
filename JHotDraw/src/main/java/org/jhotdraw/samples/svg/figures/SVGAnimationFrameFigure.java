package org.jhotdraw.samples.svg.figures;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 *
 * @author Alexander
 */
public class SVGAnimationFrameFigure extends SVGGroupFigure {

    private Object roundrect;

    public SVGAnimationFrameFigure() {
    }

    @Override
    public void draw(Graphics2D g) {
        
        
    }

    @Override
    public void setBounds(Point2D.Double anchor, Point2D.Double lead) {
//        invalidateTransformedShape();
//        double arcWidthRatio = roundrect.width > 0d ? roundrect.arcwidth / 2d / roundrect.width : 0d;
//        double arcHeighRatio = roundrect.height > 0d ? roundrect.archeight / 2d / roundrect.height : 0d;
//        roundrect.x = Math.min(anchor.x, lead.x);
//        roundrect.y = Math.min(anchor.y, lead.y);
//        roundrect.width = Math.max(0.1, Math.abs(lead.x - anchor.x));
//        roundrect.height = Math.max(0.1, Math.abs(lead.y - anchor.y));
//
//        setArc(roundrect.width*arcWidthRatio,roundrect.height*arcHeighRatio);
//        invalidate();
    }

    private void invalidateTransformedShape() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
