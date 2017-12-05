/*
 * @(#)BoxHandleKit.java  2.0  2008-05-11
 *
 * Copyright (c) 1996-2008 by the original authors of JHotDraw
 * and all its contributors.
 * All rights reserved.
 *
 * The copyright of this software is owned by the authors and  
 * contributors of the JHotDraw project ("the copyright holders").  
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * the copyright holders. For details see accompanying license terms. 
 */
package org.jhotdraw.draw;

import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 * A set of utility methods to create handles which resize a Figure by
 * using its <code>setBounds</code> method, if the Figure is transformable.
 * 
 * @author Werner Randelshofer
 * @version 2.0 2008-05-11 Added keyboard support. 
 * Handle attributes are now read from DrawingEditor.
 * <br>1.1 2008-02-28 Only resize a figure, if it is transformable. 
 * <br>1.0 2007-04-14 Created.
 */
public class ResizeHandleKit {

    private final static boolean DEBUG = false;

    static final int DIR_S = 1;
    static final int DIR_N = 2;
    static final int DIR_E = 4;
    static final int DIR_W = 8;
    static final int DIR_SE = DIR_S|DIR_E;
    static final int DIR_SW = DIR_S|DIR_W;
    static final int DIR_NE = DIR_N|DIR_E;
    static final int DIR_NW = DIR_N|DIR_W;

    /** Creates a new instance. */
    public ResizeHandleKit() {
    }

    /**
     * Creates handles for each corner of a
     * figure and adds them to the provided collection.
     */
    static public void addCornerResizeHandles(Figure f, Collection<Handle> handles) {
        handles.add(southEast(f));
        handles.add(southWest(f));
        handles.add(northEast(f));
        handles.add(northWest(f));
    }

    /**
     * Fills the given Vector with handles at each
     * the north, south, east, and west of the figure.
     */
    static public void addEdgeResizeHandles(Figure f, Collection<Handle> handles) {
        handles.add(south(f));
        handles.add(north(f));
        handles.add(east(f));
        handles.add(west(f));
    }

    /**
     * Fills the given Vector with handles at each
     * the north, south, east, and west of the figure.
     */
    static public void addResizeHandles(Figure f, Collection<Handle> handles) {
        handles.add(new BoundsOutlineHandle(f));
        addCornerResizeHandles(f, handles);
        addEdgeResizeHandles(f, handles);
    }

    static public Handle south(Figure owner) {
        return new ResizeHandle(owner, DIR_S);
    }

    static public Handle southEast(Figure owner) {
        return new ResizeHandle(owner, DIR_SE);
    }

    static public Handle southWest(Figure owner) {
        return new ResizeHandle(owner, DIR_SW);
    }

    static public Handle north(Figure owner) {
        return new ResizeHandle(owner, DIR_N);
    }

    static public Handle northEast(Figure owner) {
        return new ResizeHandle(owner, DIR_NE);
    }

    static public Handle northWest(Figure owner) {
        return new ResizeHandle(owner, DIR_NW);
    }

    static public Handle east(Figure owner) {
        return new ResizeHandle(owner, DIR_E);
    }

    static public Handle west(Figure owner) {
        return new ResizeHandle(owner, DIR_W);
    }

    private static class ResizeHandle extends LocatorHandle {

        private int dx,  dy, direction;
        Object geometry;

        @Nullable
        private Point2D.Double aspectRatio;
        private boolean shiftPressed;

        ResizeHandle(Figure owner, int direction)
        {
            super(owner, chooseLocator(direction));
            this.direction = direction;
            this.aspectRatio = null;
            this.shiftPressed = false;
        }

        private static Locator chooseLocator(int direction)
        {
            switch (direction)
            {
                case DIR_S: return RelativeLocator.south(true);
                case DIR_N: return RelativeLocator.north(true);
                case DIR_E: return RelativeLocator.east(true);
                case DIR_W: return RelativeLocator.west(true);
                case DIR_SE: return RelativeLocator.southEast(true);
                case DIR_SW: return RelativeLocator.southWest(true);
                case DIR_NE: return RelativeLocator.northEast(true);
                case DIR_NW: return RelativeLocator.northWest(true);
                default: return null;
            }
        }
        private static int chooseCursor(int direction)
        {
            switch (direction)
            {
                case DIR_S: return Cursor.S_RESIZE_CURSOR;
                case DIR_N: return Cursor.N_RESIZE_CURSOR;
                case DIR_E: return Cursor.E_RESIZE_CURSOR;
                case DIR_W: return Cursor.W_RESIZE_CURSOR;
                case DIR_SE: return Cursor.SE_RESIZE_CURSOR;
                case DIR_SW: return Cursor.SW_RESIZE_CURSOR;
                case DIR_NE: return Cursor.NE_RESIZE_CURSOR;
                case DIR_NW: return Cursor.NW_RESIZE_CURSOR;
                default: return Cursor.DEFAULT_CURSOR;
            }
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

        public void trackStart(Point anchor, int modifiersEx) {
            geometry = getOwner().getTransformRestoreData();
            Point location = getLocation();
            dx = -anchor.x + location.x;
            dy = -anchor.y + location.y;
            saveAspectRatio();
        }

        private void saveAspectRatio()
        {
            Rectangle.Double r = getOwner().getBounds();
            aspectRatio = new Point2D.Double(r.width / r.height, r.height / r.width);
        }

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

        public void trackEnd(Point anchor, Point lead, int modifiersEx) {
            if (getOwner().isTransformable()) {
                fireUndoableEditHappened(
                        new GeometryEdit(getOwner(), geometry, getOwner().getTransformRestoreData()));
            }
            aspectRatio = null;
            shiftPressed = false;
        }

        void trackStepNormalized(Point2D.Double p) {
            Rectangle2D.Double r = getOwner().getBounds();
            double left = (direction & DIR_W) != 0 ? Math.min(r.x + r.width - 1, p.x) : r.x;
            double right = (direction & DIR_E) != 0 ? Math.max(r.x + 1, p.x) : r.x + r.width;
            double bottom = (direction & DIR_S) != 0 ? Math.max(r.y + 1, p.y) : r.y + r.height;
            double top = (direction & DIR_N) != 0 ? Math.min(r.y + r.height - 1, p.y) : r.y;

            setBounds(
                    new Point2D.Double(left, top),
                    new Point2D.Double(right, bottom),
                    shiftPressed ? aspectRatio : null);
        }

        void applyAspectRatio(@NotNull Point2D.Double topLeft, @NotNull Point2D.Double bottomRight, @NotNull Point2D.Double aspectRatio)
        {
            double height = bottomRight.y -  topLeft.y;
            double width = bottomRight.x -  topLeft.x;

            if ((direction & (DIR_W | DIR_E)) == 0)
            {
                width = height*aspectRatio.x;
            }
            else
            {
                height = width*aspectRatio.y;
            }

            switch (direction)
            {
                case DIR_N:
                case DIR_NW:
                    bottomRight.x = topLeft.x + width;
                    topLeft.y = bottomRight.y - height;
                    break;
                case DIR_W:
                    bottomRight.x = topLeft.x + width;
                    bottomRight.y = topLeft.y + height;
                    break;
                case DIR_E:
                case DIR_SW:
                    topLeft.x = bottomRight.x - width;
                    bottomRight.y = topLeft.y + height;
                    break;
                case DIR_NE:
                    topLeft.x = bottomRight.x - width;
                    topLeft.y = bottomRight.y - height;
                    break;
                case DIR_SE:
                case DIR_S:
                    bottomRight.x = topLeft.x + width;
                    bottomRight.y = topLeft.y + height;
                    break;
            }
        }

        void setBounds(Point2D.Double anchor, Point2D.Double lead, @Nullable Point2D.Double aspectRatio) {
            //if (aspectRatio != null)
            {
                applyAspectRatio(anchor, lead, aspectRatio);
            }

            Figure f = getOwner();
            f.willChange();
            f.setBounds(anchor, lead);
            f.changed();
        }

        // ugly java alternative of !!
        private static int nn(int x)
        {
            return x > 0 ? 1 : 0;
        }

        @Override
        public void keyPressed(KeyEvent evt) {
            Rectangle2D.Double r = getOwner().getBounds();

            int up = 0;
            int down = 0;
            int left = 0;
            int right = 0;

            switch (evt.getKeyCode())
            {
                case KeyEvent.VK_UP:
                    if (r.height <= 1 && (direction & DIR_S) != 0)
                        break;
                    down = -nn(direction & DIR_S);
                    up = -nn(direction & DIR_N);
                    break;
                case KeyEvent.VK_DOWN:
                    if (r.height <= 1 && (direction & DIR_N) != 0)
                        break;
                    up = nn(direction & DIR_N);
                    down = nn(direction & DIR_S);
                    break;
                case KeyEvent.VK_LEFT:
                    if (r.width <= 1 && (direction & DIR_E) != 0)
                        break;
                    left = -nn(direction & DIR_W);
                    right = -nn(direction & DIR_E);
                    break;
                case KeyEvent.VK_RIGHT:
                    if (r.width <= 1 && (direction & DIR_W) != 0)
                        break;
                    left = nn(direction & DIR_W);
                    right = nn(direction & DIR_E);
                    break;
                case KeyEvent.VK_SHIFT:
                    shiftPressed = true;
                    saveAspectRatio();
                    if (aspectRatio != null)
                    {
                        setBounds(new Point2D.Double(r.x,r.y), new Point2D.Double(r.x + r.width, r.y + r.height), aspectRatio);
                    }
                    break;
            }

            setBounds(
                    new Point2D.Double(r.x + left, r.y + up),
                    new Point2D.Double(r.x + r.width + right, r.y + r.height + down),
                    null);

            evt.consume();
        }

        @Override
        public void keyReleased(KeyEvent evt) {
            super.keyReleased(evt);

            if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
                shiftPressed = false;
                if (aspectRatio != null)
                {
                    Rectangle2D.Double r = getOwner().getBounds();
                    setBounds(new Point2D.Double(r.x,r.y), new Point2D.Double(r.x + r.width, r.y + r.height), null);
                }
            }
        }
        public Cursor getCursor() {
            return Cursor.getPredefinedCursor(
                    getOwner().isTransformable() ? chooseCursor(direction) : Cursor.DEFAULT_CURSOR);
        }
    }
}
