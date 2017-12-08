package org.jhotdraw.samples.svg;

import java.awt.Point;

/**
 * Used to add support for other software components to change where the viewport is placed.
 */
public interface ViewportModifier {
    
    /**
     * Moves the viewpot so {@link Point} p is centered.
     * @param p The point to center on, this is a relative value between 0 and 1
     */
    public void centerViewportOnPoint(Point.Double p);
}
