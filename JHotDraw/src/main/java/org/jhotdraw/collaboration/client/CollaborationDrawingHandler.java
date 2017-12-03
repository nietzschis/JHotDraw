package org.jhotdraw.collaboration.client;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.samples.svg.figures.SVGPathFigure;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;

/**
 *
 * @author Niclas
 */
public class CollaborationDrawingHandler {
    
    private Drawing drawing;
     
    public void setDrawing(Drawing drawing) {
        this.drawing = drawing;
    }

    public void addFigure(Figure figure) {
        Figure newFig = (Figure) figure.clone();
        newFig.setCollaborationId(figure.getCollaborationId());
        drawing.add(newFig);
    }
    
    public void removeFigures(List<Figure> figures) {
        drawing.removeAll(figures);
    }
    
    public void changeBounds(Figure oldFigure, Figure newFigure) {
       // Paths are a little funny, needs to be readded
        if (oldFigure instanceof SVGPathFigure) {
            removeFigures(Arrays.asList(oldFigure));
            addFigure(newFigure);
        } else {
            Point2D.Double start = new Point2D.Double(newFigure.getBounds().x, newFigure.getBounds().y);
            Point2D.Double end = new Point2D.Double(newFigure.getBounds().x + newFigure.getBounds().width, newFigure.getBounds().y + newFigure.getBounds().height);

            oldFigure.willChange();
            oldFigure.setBounds(start, end);
            oldFigure.changed();
        } 
    }
    
    public void changeArc(SVGRectFigure oldFigure, SVGRectFigure newFigure) {
        oldFigure.willChange();
            oldFigure.setArc(newFigure.getArc());
        oldFigure.changed();
    }
    
    public void changeAttributes(Figure oldFigure, Figure newFigure) {
        oldFigure.willChange();
        oldFigure.restoreAttributesTo(newFigure.getAttributesRestoreData());
        oldFigure.changed();
    }
}
