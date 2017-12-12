package org.jhotdraw.samples.svg.figures;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;

/**
 *
 * @author Alexander
 */
public class SVGAnimationFrameFigure extends SVGGroupFigure {

    /** The variable acv is used for generating the locations of the control
     * points for the rounded rectangle using path.curveTo. */
    private static final double acv;
    /**
     */
    private RoundRectangle2D.Double roundrect = new RoundRectangle2D.Double();
    /**
     * This is used to perform faster drawing.
     */
    private transient Shape cachedTransformedShape;
    /**
     * This is used to perform faster hit testing.
     */
    private transient Shape cachedHitShape;


    static {
        double angle = Math.PI / 4.0;
        double a = 1.0 - Math.cos(angle);
        double b = Math.tan(angle);
        double c = Math.sqrt(1.0 + b * b) - 1 + a;
        double cv = 4.0 / 3.0 * a * b / c;
        acv = (1.0 - cv);
    }
    
    @Override
    public void setBounds(Point2D.Double anchor, Point2D.Double lead) {
        invalidateTransformedShape();
        double arcWidthRatio = roundrect.width > 0d ? roundrect.arcwidth / 2d / roundrect.width : 0d;
        double arcHeighRatio = roundrect.height > 0d ? roundrect.archeight / 2d / roundrect.height : 0d;
        roundrect.x = Math.min(anchor.x, lead.x);
        roundrect.y = Math.min(anchor.y, lead.y);
        roundrect.width = Math.max(0.1, Math.abs(lead.x - anchor.x));
        roundrect.height = Math.max(0.1, Math.abs(lead.y - anchor.y));

        setArc(roundrect.width*arcWidthRatio,roundrect.height*arcHeighRatio);
        invalidate();
    }
    
        protected void drawStroke(Graphics2D g) {
        if (roundrect.archeight == 0 && roundrect.arcwidth == 0) {
            g.draw(roundrect.getBounds2D());
        } else {
            // We have to generate the path for the round rectangle manually,
            // because the path of a Java RoundRectangle is drawn counter clockwise
            // whereas an SVG rect needs to be drawn clockwise.
            GeneralPath p = new GeneralPath();
            double aw = roundrect.arcwidth / 2d;
            double ah = roundrect.archeight / 2d;
            p.moveTo((float) (roundrect.x + aw), (float) roundrect.y);
            p.lineTo((float) (roundrect.x + roundrect.width - aw), (float) roundrect.y);
            p.curveTo((float) (roundrect.x + roundrect.width - aw * acv), (float) roundrect.y, //
                    (float) (roundrect.x + roundrect.width), (float)(roundrect.y + ah * acv), //
                    (float) (roundrect.x + roundrect.width), (float) (roundrect.y + ah));
            p.lineTo((float) (roundrect.x + roundrect.width), (float) (roundrect.y + roundrect.height - ah));
            p.curveTo(
                    (float) (roundrect.x + roundrect.width), (float) (roundrect.y + roundrect.height - ah * acv),//
                    (float) (roundrect.x + roundrect.width - aw * acv), (float) (roundrect.y + roundrect.height),//
                    (float) (roundrect.x + roundrect.width - aw), (float) (roundrect.y + roundrect.height));
            p.lineTo((float) (roundrect.x + aw), (float) (roundrect.y + roundrect.height));
            p.curveTo((float) (roundrect.x + aw*acv), (float) (roundrect.y + roundrect.height),//
                    (float) (roundrect.x), (float) (roundrect.y + roundrect.height - ah*acv),//
                   (float) roundrect.x, (float) (roundrect.y + roundrect.height - ah));
            p.lineTo((float) roundrect.x, (float) (roundrect.y + ah));
            p.curveTo((float) (roundrect.x), (float) (roundrect.y + ah*acv),//
                    (float) (roundrect.x + aw*acv), (float)(roundrect.y),//
                    (float)(roundrect.x + aw), (float)(roundrect.y));
            p.closePath();
            g.draw(p);
        }
    }

    private void invalidateTransformedShape() {
        cachedTransformedShape = null;
        cachedHitShape = null;
    }

    public void setArc(double w, double h) {
        roundrect.arcwidth = Math.max(0d, Math.min(roundrect.width, w * 2d));
        roundrect.archeight = Math.max(0d, Math.min(roundrect.height, h * 2d));
    }
    
    @Override
    public void invalidate() {
        super.invalidate();
        invalidateTransformedShape();
    }
    
}
