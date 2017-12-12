
package org.jhotdraw.samples.svg.figures;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import org.jhotdraw.app.JHotDrawFeatures;
import org.jhotdraw.draw.*;
import static org.jhotdraw.samples.svg.SVGAttributeKeys.*;
import org.jhotdraw.geom.*;
import org.jhotdraw.samples.svg.*;

/**
 *
 * @author Jonas
 */
    public class SVGRoundedRectangle extends SVGAttributedFigure implements SVGFigure {

    private RoundRectangle2D.Double roundedRectangle;

    private transient Shape cachedTransformedShape;

    private transient Shape cachedHitShape;

    // Creates a new instance
    public SVGRoundedRectangle() {
        this(0, 0, 0, 0, 0, 0);
    }


    public SVGRoundedRectangle(double x, double y, double width, double height, double arcWidth, double arcHeigth) {
        roundedRectangle = new RoundRectangle2D.Double(x, y, width, height, arcWidth, arcHeigth);
        SVGAttributeKeys.setDefaults(this);
    }

    // DRAWING
    protected void drawFill(Graphics2D g) {
        if (roundedRectangle.width > 0 && roundedRectangle.height > 0) {
            g.fill(roundedRectangle);
        }
    }

    @FeatureEntryPoint(JHotDrawFeatures.ELLIPSE_TOOL)
    protected void drawStroke(Graphics2D g) {
        if (roundedRectangle.width > 0 && roundedRectangle.height > 0) {
            g.draw(roundedRectangle);
        }
    }

    public double getX() {
        return roundedRectangle.x;
    }

    public double getY() {
        return roundedRectangle.y;
    }

    public double getWidth() {
        return roundedRectangle.getWidth();
    }

    public double getHeight() {
        return roundedRectangle.getHeight();
    }
    
    public double getArcWidth() {
        return roundedRectangle.getArcWidth();
    }
    public double getArcHeigth() {
        return roundedRectangle.getArcHeight();
    }
    public Rectangle2D.Double getBounds() {
        return (Rectangle2D.Double) roundedRectangle.getBounds2D();
    }

    @Override
    public Rectangle2D.Double getDrawingArea() {
        Rectangle2D rx = getTransformedShape().getBounds2D();
        Rectangle2D.Double r = (rx instanceof Rectangle2D.Double) ? (Rectangle2D.Double) rx : new Rectangle2D.Double(rx.getX(), rx.getY(), rx.getWidth(), rx.getHeight());
        if (TRANSFORM.get(this) == null) {
            double g = SVGAttributeKeys.getPerpendicularHitGrowth(this) * 2d + 1;
            Geom.grow(r, g, g);
        } else {
            double strokeTotalWidth = AttributeKeys.getStrokeTotalWidth(this);
            double width = strokeTotalWidth / 2d;
            width *= Math.max(TRANSFORM.get(this).getScaleX(), TRANSFORM.get(this).getScaleY()) + 1;
            Geom.grow(r, width, width);
        }
        return r;
    }

    /**
     * Checks if a Point2D.Double is inside the figure.
     */
    public boolean contains(Point2D.Double p) {
        return getHitShape().contains(p);
    }

    private Shape getTransformedShape() {
        if (cachedTransformedShape == null) {
            if (TRANSFORM.get(this) == null) {
                cachedTransformedShape = roundedRectangle;
            } else {
                cachedTransformedShape = TRANSFORM.get(this).createTransformedShape(roundedRectangle);
            }
        }
        return cachedTransformedShape;
    }
    private Shape getHitShape() {
        if (cachedHitShape == null) {
            if (FILL_COLOR.get(this) != null || FILL_GRADIENT.get(this) != null) {
                cachedHitShape = new GrowStroke(
                        (float) SVGAttributeKeys.getStrokeTotalWidth(this) / 2f,
                        (float) SVGAttributeKeys.getStrokeTotalMiterLimit(this)).createStrokedShape(getTransformedShape());
            } else {
                cachedHitShape = SVGAttributeKeys.getHitStroke(this).createStrokedShape(getTransformedShape());
            }
        }
        return cachedHitShape;
    }

    @Override
    public void setBounds(Point2D.Double anchor, Point2D.Double lead) {
        roundedRectangle.x = Math.min(anchor.x, lead.x);
        roundedRectangle.y = Math.min(anchor.y, lead.y);
        roundedRectangle.width = Math.max(0.1, Math.abs(lead.x - anchor.x));
        roundedRectangle.height = Math.max(0.1, Math.abs(lead.y - anchor.y));
        roundedRectangle.arcwidth = 30;
        roundedRectangle.archeight = 30;
        invalidate();
    }

   
    @Override
    public void transform(AffineTransform tx) {
        if (TRANSFORM.get(this) != null ||
                (tx.getType() & (AffineTransform.TYPE_TRANSLATION)) != tx.getType()) {
            if (TRANSFORM.get(this) == null) {
                TRANSFORM.basicSetClone(this, tx);
            } else {
                AffineTransform t = TRANSFORM.getClone(this);
                t.preConcatenate(tx);
                TRANSFORM.basicSet(this, t);
            }
        } else {
            Point2D.Double anchor = getStartPoint();
            Point2D.Double lead = getEndPoint();
            setBounds(
                    (Point2D.Double) tx.transform(anchor, anchor),
                    (Point2D.Double) tx.transform(lead, lead));
            if (FILL_GRADIENT.get(this) != null &&
                    !FILL_GRADIENT.get(this).isRelativeToFigureBounds()) {
                Gradient g = FILL_GRADIENT.getClone(this);
                g.transform(tx);
                FILL_GRADIENT.basicSet(this, g);
            }
            if (STROKE_GRADIENT.get(this) != null &&
                    !STROKE_GRADIENT.get(this).isRelativeToFigureBounds()) {
                Gradient g = STROKE_GRADIENT.getClone(this);
                g.transform(tx);
                STROKE_GRADIENT.basicSet(this, g);
            }
        }
        invalidate();
    }

    public void restoreTransformTo(Object geometry) {
        Object[] restoreData = (Object[]) geometry;
        roundedRectangle = (RoundRectangle2D.Double) ((RoundRectangle2D.Double) restoreData[0]).clone();
        TRANSFORM.basicSetClone(this, (AffineTransform) restoreData[1]);
        FILL_GRADIENT.basicSetClone(this, (Gradient) restoreData[2]);
        STROKE_GRADIENT.basicSetClone(this, (Gradient) restoreData[3]);
        invalidate();
    }

    public Object getTransformRestoreData() {
        return new Object[]{
                    roundedRectangle.clone(),
                    TRANSFORM.getClone(this),
                    FILL_GRADIENT.getClone(this),
                    STROKE_GRADIENT.getClone(this),};
    }

    // ATTRIBUTES
    // EDITING
    @Override
    public Collection<Handle> createHandles(int detailLevel) {
        LinkedList<Handle> handles = new LinkedList<Handle>();
        switch (detailLevel % 2) {
            case -1: // Mouse hover handles
                handles.add(new BoundsOutlineHandle(this, false, true));
                break;
            case 0:
                ResizeHandleKit.addResizeHandles(this, handles);
                handles.add(new LinkHandle(this));
                break;
            case 1:
                TransformHandleKit.addTransformHandles(this, handles);
                break;
            default:
                break;
        }
        return handles;
    }

    public boolean canConnect() {
        return false; // SVG does not support connecting
    }

    public Connector findConnector(Point2D.Double p, ConnectionFigure prototype) {
        return null; // SVG does not support connectors
    }

    public Connector findCompatibleConnector(Connector c, boolean isStartConnector) {
        return null; // SVG does not support connectors
    }


    public SVGRoundedRectangle clone() {
        SVGRoundedRectangle that = (SVGRoundedRectangle) super.clone();
        that.roundedRectangle = (RoundRectangle2D.Double) this.roundedRectangle.clone();
        that.cachedTransformedShape = null;
        return that;
    }


    public boolean isEmpty() {
        Rectangle2D.Double b = getBounds();
        return b.width <= 0 || b.height <= 0;
    }

    
    public void invalidate() {
        super.invalidate();
        cachedTransformedShape = null;
        cachedHitShape = null;
    }

    @Override
    public int splitFigure(DrawingView view) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
