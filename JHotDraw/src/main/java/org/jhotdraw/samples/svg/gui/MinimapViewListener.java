<<<<<<< HEAD
package org.jhotdraw.samples.svg.gui;

import java.awt.Point;

/**
 * Used to listen for events on the minimap.
 */
public interface MinimapViewListener {
    /**
     * Called when the minimap is clicked, the x and y coordinates are relative values between 0 and 1 inclusive.
     * @param relativePosition 
     */
    void relativeOnClick(Point.Double relativePosition);
}
=======
package org.jhotdraw.samples.svg.gui;

import java.awt.Point;

/**
 * Used to listen for events on the {@link MinimapView}.
 */
public interface MinimapViewListener {
    /**
     * Called when the minimap is clicked, the x and y coordinates are relative values between 0 and 1 inclusive.
     * @param relativePosition 
     */
    void relativeOnClick(Point.Double relativePosition);
}
>>>>>>> refs/remotes/origin/master
