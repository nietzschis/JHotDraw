package org.jhotdraw.draw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import com.sun.istack.internal.Nullable;
import org.jhotdraw.util.ResourceBundleUtil;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import org.jhotdraw.util.ResourceBundleUtil;
import java.awt.geom.Rectangle2D;

class ResizeHandle extends LocatorHandle {

    private int dx,  dy;
    private final int direction;
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

    /**
     * Select specific locator according to handle direction
     * @param direction  Direction of handle
     * @return Relative locator
     */
    private static Locator chooseLocator(int direction)
    {
        switch (direction)
        {
            case ResizeHandleKit.DIR_S: return RelativeLocator.south(true);
            case ResizeHandleKit.DIR_N: return RelativeLocator.north(true);
            case ResizeHandleKit.DIR_E: return RelativeLocator.east(true);
            case ResizeHandleKit.DIR_W: return RelativeLocator.west(true);
            case ResizeHandleKit.DIR_SE: return RelativeLocator.southEast(true);
            case ResizeHandleKit.DIR_SW: return RelativeLocator.southWest(true);
            case ResizeHandleKit.DIR_NE: return RelativeLocator.northEast(true);
            case ResizeHandleKit.DIR_NW: return RelativeLocator.northWest(true);
            default: return null;
        }
    }

    /**
     * Select specific predefined cursor according to handle direction
     *
     * @param direction Direction of handle
     * @return Predefined cursor
     */
    private static Cursor chooseCursor(int direction)
    {
        int cur;
        switch (direction)
        {
            case ResizeHandleKit.DIR_S: cur = Cursor.S_RESIZE_CURSOR; break;
            case ResizeHandleKit.DIR_N: cur = Cursor.N_RESIZE_CURSOR; break;
            case ResizeHandleKit.DIR_E: cur = Cursor.E_RESIZE_CURSOR; break;
            case ResizeHandleKit.DIR_W: cur = Cursor.W_RESIZE_CURSOR; break;
            case ResizeHandleKit.DIR_SE: cur = Cursor.SE_RESIZE_CURSOR; break;
            case ResizeHandleKit.DIR_SW: cur = Cursor.SW_RESIZE_CURSOR; break;
            case ResizeHandleKit.DIR_NE: cur = Cursor.NE_RESIZE_CURSOR; break;
            case ResizeHandleKit.DIR_NW: cur = Cursor.NW_RESIZE_CURSOR; break;
            default: cur = Cursor.DEFAULT_CURSOR; break;
        }

        return Cursor.getPredefinedCursor(cur);
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
                        getEditor().getHandleAttribute(HandleAttributeKeys.RESIZE_HANDLE_FILL_COLOR),
                        getEditor().getHandleAttribute(HandleAttributeKeys.RESIZE_HANDLE_STROKE_COLOR));
            } else {
                drawRectangle(g,
                        getEditor().getHandleAttribute(HandleAttributeKeys.NULL_HANDLE_FILL_COLOR),
                        getEditor().getHandleAttribute(HandleAttributeKeys.NULL_HANDLE_STROKE_COLOR));
            }
        } else {
            drawRectangle(g,
                    getEditor().getHandleAttribute(HandleAttributeKeys.HANDLE_FILL_COLOR_DISABLED),
                    getEditor().getHandleAttribute(HandleAttributeKeys.HANDLE_STROKE_COLOR_DISABLED));
        }
    }

    public void trackStart(Point anchor, int modifiersEx) {
        geometry = getOwner().getTransformRestoreData();
        Point location = getLocation();
        dx = -anchor.x + location.x;
        dy = -anchor.y + location.y;
        saveAspectRatio();
    }

    /**
     * Updates aspectRatio according to current bounds
     */
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
                    if (ResizeHandleKit.DEBUG) {
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

    /**
     * Normalize new rectangle to prevent its size to go lower thank 1x1 point
     * @param p New point where current handle was dragged into
     */
    void trackStepNormalized(Point2D.Double p) {
        Rectangle2D.Double r = getOwner().getBounds();
        double left = (direction & ResizeHandleKit.DIR_W) != 0 ? Math.min(r.x + r.width - 1, p.x) : r.x;
        double right = (direction & ResizeHandleKit.DIR_E) != 0 ? Math.max(r.x + 1, p.x) : r.x + r.width;
        double bottom = (direction & ResizeHandleKit.DIR_S) != 0 ? Math.max(r.y + 1, p.y) : r.y + r.height;
        double top = (direction & ResizeHandleKit.DIR_N) != 0 ? Math.min(r.y + r.height - 1, p.y) : r.y;

        setBounds(
                new Point2D.Double(left, top),
                new Point2D.Double(right, bottom),
                shiftPressed ? aspectRatio : null);
    }

    void setBounds(Point2D.Double anchor, Point2D.Double lead, @Nullable Point2D.Double aspectRatio) {
        if (aspectRatio != null)
        {
            ResizeHandleKit.applyAspectRatio(direction, anchor, lead, aspectRatio);
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
                if (r.height <= 1 && (direction & ResizeHandleKit.DIR_S) != 0)
                    break;
                down = -nn(direction & ResizeHandleKit.DIR_S);
                up = -nn(direction & ResizeHandleKit.DIR_N);
                break;
            case KeyEvent.VK_DOWN:
                if (r.height <= 1 && (direction & ResizeHandleKit.DIR_N) != 0)
                    break;
                up = nn(direction & ResizeHandleKit.DIR_N);
                down = nn(direction & ResizeHandleKit.DIR_S);
                break;
            case KeyEvent.VK_LEFT:
                if (r.width <= 1 && (direction & ResizeHandleKit.DIR_E) != 0)
                    break;
                left = -nn(direction & ResizeHandleKit.DIR_W);
                right = -nn(direction & ResizeHandleKit.DIR_E);
                break;
            case KeyEvent.VK_RIGHT:
                if (r.width <= 1 && (direction & ResizeHandleKit.DIR_W) != 0)
                    break;
                left = nn(direction & ResizeHandleKit.DIR_W);
                right = nn(direction & ResizeHandleKit.DIR_E);
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

    @Override
    public Cursor getCursor() {
        return getOwner().isTransformable() ? chooseCursor(direction) : Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    }
}
