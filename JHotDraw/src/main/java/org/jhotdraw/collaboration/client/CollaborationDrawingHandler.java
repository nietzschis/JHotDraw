package org.jhotdraw.collaboration.client;

import java.awt.geom.Point2D;
import java.util.ArrayList;
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

    void clientListLongest(List<Figure> serverList, CollaborationConnection collaborationConnection) {
        List<Figure> figuresToBeDeleted = new ArrayList();
        for (Figure workingFigure : drawing.getChildren()) {
            boolean found = false;
            for (Figure serverFigure : serverList) {
                // Figures have the same Id, so must be the same figure, check for updates
                if (workingFigure.getCollaborationId() == serverFigure.getCollaborationId()) {
                    // Same figure check bounds
                    if ((workingFigure.getBounds().x != serverFigure.getBounds().x) || (workingFigure.getBounds().y != serverFigure.getBounds().y) || (workingFigure.getBounds().height != serverFigure.getBounds().height) || (workingFigure.getBounds().width != serverFigure.getBounds().width)) {
                        changeBounds(workingFigure, serverFigure);
                    }
                    // Same figure check attributes
                    if (!workingFigure.getAttributes().equals(serverFigure.getAttributes())) {
                        changeAttributes(workingFigure, serverFigure);
                    }
                    // If rect, check for arc
                    if (workingFigure instanceof SVGRectFigure) {
                        SVGRectFigure rectWorkingFig = (SVGRectFigure) workingFigure;
                        SVGRectFigure rectServerFig = (SVGRectFigure) serverFigure;
                        if (!rectWorkingFig.getArc().equals(rectServerFig.getArc())) {
                            changeArc((SVGRectFigure) workingFigure, (SVGRectFigure) serverFigure);
                        }
                    }
                    // Found the same figures so its not missing
                    found = true;
                }
            }
            if (!found) {
                // Remove figure from list
                figuresToBeDeleted.add(workingFigure);
            }
        }
        if (!figuresToBeDeleted.isEmpty()) {
            removeFigures(figuresToBeDeleted);
        }
    }

    void serverListLongest(List<Figure> serverList, CollaborationConnection collaborationConnection) {
        for (Figure serverFigure : serverList) {
            boolean found = false;
            for (Figure workingFigure : drawing.getChildren()) {
                // Figures have the same Id, so must be the same figure, check for updates
                if (workingFigure.getCollaborationId() == serverFigure.getCollaborationId()) {
                    // Same figure check bounds
                    if ((workingFigure.getBounds().x != serverFigure.getBounds().x) || (workingFigure.getBounds().y != serverFigure.getBounds().y) || (workingFigure.getBounds().height != serverFigure.getBounds().height) || (workingFigure.getBounds().width != serverFigure.getBounds().width)) {
                        changeBounds(workingFigure, serverFigure);
                    }
                    // Same figure check attributes
                    if (!workingFigure.getAttributes().equals(serverFigure.getAttributes())) {
                        changeAttributes(workingFigure, serverFigure);
                    }
                    // If rect, check for arc
                    if (workingFigure instanceof SVGRectFigure) {
                        SVGRectFigure rectWorkingFig = (SVGRectFigure) workingFigure;
                        SVGRectFigure rectServerFig = (SVGRectFigure) serverFigure;
                        if (!rectWorkingFig.getArc().equals(rectServerFig.getArc())) {
                            changeArc((SVGRectFigure) workingFigure, (SVGRectFigure) serverFigure);
                        }
                    }
                    // Found the same figures so its not new
                    found = true;
                }
            }
            if (!found) {
                // Add new figure to list
                addFigure(serverFigure);
            }
        }
    }
}
