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
import java.awt.geom.*;

import com.sun.istack.internal.NotNull;

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

    final static boolean DEBUG = false;

    static final int DIR_S = 1;
    static final int DIR_N = 2;
    static final int DIR_E = 4;
    static final int DIR_W = 8;
    static final int DIR_SE = DIR_S|DIR_E;
    static final int DIR_SW = DIR_S|DIR_W;
    static final int DIR_NE = DIR_N|DIR_E;
    static final int DIR_NW = DIR_N|DIR_W;

    /** Creates a new instance. */
    private ResizeHandleKit() {
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

    private static Handle south(Figure owner) {
        return new ResizeHandle(owner, DIR_S);
    }

    private static Handle southEast(Figure owner) {
        return new ResizeHandle(owner, DIR_SE);
    }

    private static Handle southWest(Figure owner) {
        return new ResizeHandle(owner, DIR_SW);
    }

    private static Handle north(Figure owner) {
        return new ResizeHandle(owner, DIR_N);
    }

    private static Handle northEast(Figure owner) {
        return new ResizeHandle(owner, DIR_NE);
    }

    private static Handle northWest(Figure owner) {
        return new ResizeHandle(owner, DIR_NW);
    }

    private static Handle east(Figure owner) {
        return new ResizeHandle(owner, DIR_E);
    }

    private static Handle west(Figure owner) {
        return new ResizeHandle(owner, DIR_W);
    }

    /**
     * Adjust rect to specified aspect ratio.
     * For West or east direction using aspectRatio.y to calculate height from width
     * For other directions calculates new width from height and aspectRatio.x
     *
     * @param direction Direction of handle
     * @param topLeft Top left point of new rect. Can not be null.
     * @param bottomRight Bottom right point of new rect. Can not be null.
     * @param aspectRatio Aspect ratio modificators. Can not be null.
     */
    static void applyAspectRatio(int direction, @NotNull Point2D.Double topLeft, @NotNull Point2D.Double bottomRight, @NotNull Point2D.Double aspectRatio)
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
}
