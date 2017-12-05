/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.figures;

/**
 *
 * @author Mathias
 */

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import org.jhotdraw.app.JHotDrawFeatures;
import org.jhotdraw.draw.*;
import static org.jhotdraw.samples.svg.SVGAttributeKeys.*;
import org.jhotdraw.samples.svg.*;
import org.jhotdraw.geom.*;


public class SVGTriangleFigure extends SVGAttributedFigure implements SVGFigure {

    // Surrounding box which the
    // triangle is drawn inside
    private Rectangle2D.Double triangle;
    
    private transient Shape cachedTransformedShape;
    
    private transient Shape cachedHitShape;

    public SVGTriangleFigure() {
        this(0, 0, 0, 0);
    }

    @FeatureEntryPoint(JHotDrawFeatures.TRIANGLE_TOOL)
    public SVGTriangleFigure(double x, double y, double width, double height) {
        triangle = new Rectangle2D.Double(x, y, width, height);
        SVGAttributeKeys.setDefaults(this);
    }
    
    //drawing method for filling the triangle
    protected void drawFill(Graphics2D g) {
        GeneralPath p = new GeneralPath();
        p.moveTo((float) triangle.x, (float) (triangle.y + triangle.height));
        p.lineTo((float) (triangle.x + (triangle.width / 2.0)), (float) triangle.y);
        p.lineTo((float) (triangle.x + triangle.width), (float) (triangle.y + triangle.height));
        p.closePath();
        g.fill(p);
    }
    
    //drawing the lines of the triangle
    @FeatureEntryPoint(JHotDrawFeatures.TRIANGLE_TOOL)
    protected void drawStroke(Graphics2D g) {
        GeneralPath p = new GeneralPath();
        p.moveTo((float) triangle.x, (float) (triangle.y + triangle.height));
        p.lineTo((float) (triangle.x + (triangle.width / 2.0)), (float) triangle.y);
        p.lineTo((float) (triangle.x + triangle.width), (float) (triangle.y + triangle.height));
        p.closePath();
        g.draw(p);
    }

    public double getX() {
          return triangle.x;
    }

    public double getY() {
        return triangle.y;
    }

    public double getWidth() {
          return triangle.width;
    }

    public double getHeight() {
          return triangle.height;
    }
    
    //Surrounding rectangular box
    public Rectangle2D.Double getBounds() {
        return (Rectangle2D.Double) triangle.getBounds2D();
    }

    public Rectangle2D.Double getDrawingArea() {
        Rectangle2D figure = getTransformedShape().getBounds2D();
        Rectangle2D.Double tri = (figure instanceof Rectangle2D.Double) ? 
                (Rectangle2D.Double) figure : 
                new Rectangle2D.Double(figure.getX(), figure.getY(), figure.getWidth(), figure.getHeight());
        if (TRANSFORM.get(this) == null) {
            double g = SVGAttributeKeys.getPerpendicularHitGrowth(this) * 2d + 1d;
            Geom.grow(tri, g, g);
        } else {
            double strokeTotalWidth = AttributeKeys.getStrokeTotalWidth(this);
            double width = strokeTotalWidth / 2d;
            if (STROKE_JOIN.get(this) == BasicStroke.JOIN_MITER) {
                width *= STROKE_MITER_LIMIT.get(this);
            }
            if (STROKE_CAP.get(this) != BasicStroke.CAP_BUTT) {
                width += strokeTotalWidth * 2;
            }
            width++;
            Geom.grow(tri, width, width);
        }
        return tri;
    }

    public boolean contains(Point2D.Double p) {
        return getHitShape().contains(p);
    }

    public void setBounds(Point2D.Double start, Point2D.Double end) {
        invalidateTransformedShape();
        triangle.x = Math.min(start.x, end.x);
        triangle.y = Math.min(start.y, end.y);
        triangle.width = Math.max(0.1, Math.abs(end.x - start.x));
        triangle.height = Math.max(0.1, Math.abs(end.y - start.y));
        invalidate();
    }

    private void invalidateTransformedShape() {
        cachedTransformedShape = null;
        cachedHitShape = null;
    }

    private Shape getTransformedShape() {
        if (cachedTransformedShape == null) {
            cachedTransformedShape = triangle.getBounds2D();
            if (TRANSFORM.get(this) != null) {
                cachedTransformedShape = TRANSFORM.get(this).createTransformedShape(cachedTransformedShape);
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
            } 
            else {
                cachedHitShape = SVGAttributeKeys.getHitStroke(this).createStrokedShape(getTransformedShape());
            }
        }
        return cachedHitShape;
    }

    public void transform(AffineTransform tx) {
        invalidateTransformedShape();
        if (TRANSFORM.get(this) != null ||
                (tx.getType() & (AffineTransform.TYPE_TRANSLATION)) != tx.getType()) {
            if (TRANSFORM.get(this) == null) {
                TRANSFORM.basicSet(this, (AffineTransform) tx.clone());
            } 
            else {
                AffineTransform t = TRANSFORM.getClone(this);
                t.preConcatenate(tx);
                TRANSFORM.basicSet(this, t);
            }
        } 
        else {
            Point2D.Double start = getStartPoint();
            Point2D.Double end = getEndPoint();
            setBounds(
                    (Point2D.Double) tx.transform(start, start),
                    (Point2D.Double) tx.transform(end, end));
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
    }

    public void restoreTransformTo(Object geometry) {
        invalidateTransformedShape();
        Object[] restoreData = (Object[]) geometry;
        triangle = (Rectangle2D.Double) ((Rectangle2D.Double) restoreData[0]).clone();
        TRANSFORM.basicSetClone(this, (AffineTransform) restoreData[1]);
        FILL_GRADIENT.basicSetClone(this, (Gradient) restoreData[2]);
        STROKE_GRADIENT.basicSetClone(this, (Gradient) restoreData[3]);
    }

    public Object getTransformRestoreData() {
        return new Object[]{
                    triangle.clone(),
                    TRANSFORM.getClone(this),
                    FILL_GRADIENT.getClone(this),
                    STROKE_GRADIENT.getClone(this),};
    }

    public Collection<Handle> createHandles(int detailLevel) {
        LinkedList<Handle> handles = new LinkedList<Handle>();
        switch (detailLevel % 2) {
            case -1: // Mouse hover handles
                handles.add(new BoundsOutlineHandle(this, false, true));
                break;
            case 0:
                ResizeHandleKit.addResizeHandles(this, handles);
                handles.add(new RotateHandle(this));
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
        return false;
    }

    public Connector findConnector(Point2D.Double p, ConnectionFigure prototype) {
        return null;
    }

    public Connector findCompatibleConnector(Connector c, boolean isStartConnector) {
        return null;
    }

    public SVGTriangleFigure clone() {
        SVGTriangleFigure triClone = (SVGTriangleFigure) super.clone();
        triClone.triangle = (Rectangle2D.Double) this.triangle.clone();
        triClone.cachedTransformedShape = null;
        triClone.cachedHitShape = null;
        return triClone;
    }

    public boolean isEmpty() {
        Rectangle2D.Double box = getBounds();
        return box.width <= 0 || box.height <= 0;
    }

    public void invalidate() {
        super.invalidate();
        invalidateTransformedShape();
    }
}
