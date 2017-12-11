/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author Christian
 */
public class ResizeHandle extends LocatorHandle {
        private final static boolean DEBUG = false;
        
        private int dx,  dy;
        Object geometry;

        ResizeHandle(Figure owner, Locator loc) {
            super(owner, loc);
        }

        @Override
        public String getToolTipText(Point p) {
            ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
            return labels.getString("handle.resize.toolTipText");
        }

        /**
         * Draws this handle.
         * <p>
         * If the figure is transformable, the handle is drawn as a filled rectangle.
         * If the figure is not transformable, the handle is drawn as an unfilled
         * rectangle.
         */
        @Override
        public void draw(Graphics2D g) {
            if (getEditor().getTool().supportsHandleInteraction()) {
                if (getOwner().isTransformable()) {
                    drawRectangle(g,
                            (Color) getEditor().getHandleAttribute(HandleAttributeKeys.RESIZE_HANDLE_FILL_COLOR),
                            (Color) getEditor().getHandleAttribute(HandleAttributeKeys.RESIZE_HANDLE_STROKE_COLOR));
                } else {
                    drawRectangle(g,
                            (Color) getEditor().getHandleAttribute(HandleAttributeKeys.NULL_HANDLE_FILL_COLOR),
                            (Color) getEditor().getHandleAttribute(HandleAttributeKeys.NULL_HANDLE_STROKE_COLOR));
                }
            } else {
                drawRectangle(g,
                        (Color) getEditor().getHandleAttribute(HandleAttributeKeys.HANDLE_FILL_COLOR_DISABLED),
                        (Color) getEditor().getHandleAttribute(HandleAttributeKeys.HANDLE_STROKE_COLOR_DISABLED));
            }
        }

        @Override
        public void trackStart(Point anchor, int modifiersEx) {
            geometry = getOwner().getTransformRestoreData();
            Point location = getLocation();
            dx = -anchor.x + location.x;
            dy = -anchor.y + location.y;
        }

        @Override
        public void trackStep(Point anchor, Point lead, int modifiersEx) {
            if (getOwner().isTransformable()) {
                Point2D.Double p = view.viewToDrawing(new Point(lead.x + dx, lead.y + dy));
                view.getConstrainer().constrainPoint(p);

                if (AttributeKeys.TRANSFORM.get(getOwner()) != null) {
                    try {
                        AttributeKeys.TRANSFORM.get(getOwner()).inverseTransform(p, p);
                    } catch (NoninvertibleTransformException ex) {
                        if (DEBUG) {
                            ex.printStackTrace();
                        }
                    }
                }

                trackStepNormalized(p);
            }
        }

        @Override
        public void trackEnd(Point anchor, Point lead, int modifiersEx) {
            if (getOwner().isTransformable()) {
                fireUndoableEditHappened(
                        new GeometryEdit(getOwner(), geometry, getOwner().getTransformRestoreData()));
            }
        }

        protected void trackStepNormalized(Point2D.Double p) {
        }

        protected void setBounds(Point2D.Double anchor, Point2D.Double lead) {
            Figure f = getOwner();
            f.willChange();
            f.setBounds(anchor, lead);
            f.changed();
        }
    }
