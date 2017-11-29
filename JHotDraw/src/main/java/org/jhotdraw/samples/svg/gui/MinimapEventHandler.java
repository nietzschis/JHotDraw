package org.jhotdraw.samples.svg.gui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import org.jhotdraw.samples.svg.ViewportModifier;

/**
 * Controller for {@link MinimapView}.
 * This implemtation listens for clicks on the minimap, and moves the viewport on the cnavas to the matchin place.
 * This is done throught a {@link ViewportModifier}.
 */
public class MinimapEventHandler {
    
    private final JPanel view;
    private final ViewportModifier viewportModifier;

    /**
     * Creates a null object for this class, used then events should not be handled.
     */
    public MinimapEventHandler() {
        view = null;
        viewportModifier = null;
    }
    
    public MinimapEventHandler(JPanel view, ViewportModifier viewportModifier) {
        assert view != null;
        assert viewportModifier != null;
        this.view = view;
        this.viewportModifier = viewportModifier;
        view.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //ignored
            }

            @Override
            public void mousePressed(MouseEvent e) {
                handleEvent(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //ignored
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //ignored
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //ignored
            }
        });
        
        view.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                handleEvent(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                //ignored
            }
        });
    }
    
    private void handleEvent(MouseEvent e){
        Dimension minimapSize = e.getComponent().getPreferredSize();
        
        Point.Double p = new Point.Double(e.getPoint().getX(), e.getPoint().getY());
        p.setLocation(constrain(p.x, 0, minimapSize.width), constrain(p.y, 0, minimapSize.height)); // constrain the values to be within the container.
        p.setLocation(p.getX()/minimapSize.width, p.getY()/minimapSize.height); // Center the point relative to the full canvas.
        assert p.getX() >= 0 && p.getX() <= 1 && p.getY() >= 0 && p.getY() <= 1;
        viewportModifier.centerPointOnCanvas(p);
    }
    
    private double constrain(double value, double min, double max) {
        return Math.min(Math.max(value, min), max);
    }
    
    
}
