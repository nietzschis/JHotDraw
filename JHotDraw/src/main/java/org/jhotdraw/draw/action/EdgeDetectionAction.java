package org.jhotdraw.draw.action;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.*;
import javax.swing.undo.*;
import org.jhotdraw.app.JHotDrawFeatures;
import org.jhotdraw.draw.*;
import org.jhotdraw.samples.svg.figures.SVGImageFigure;

/**
 *
 * @author Matic-ProBook
 */
public class EdgeDetectionAction extends AbstractSelectedAction {

    public static String ID = "edit.edgeDetection";

    /**
     * Creates a new instance.
     */
    public EdgeDetectionAction(DrawingEditor editor) {
        super(editor);
        labels.configureAction(this, ID);
    }

    @FeatureEntryPoint(JHotDrawFeatures.IMAGE_TOOL)
    public void actionPerformed(java.awt.event.ActionEvent e) {
        final DrawingView view = getView();
        Collection figures = new LinkedList<Figure>(view.getSelectedFigures());

        edgeDetection(view, figures);
        fireUndoableEditHappened(new AbstractUndoableEdit() {
            @Override
            public String getPresentationName() {
                return labels.getTextProperty(ID);
            }

            @Override
            public void redo() throws CannotRedoException {
                super.redo();
                edgeDetection(view, figures);
            }

            @Override
            public void undo() throws CannotUndoException {
                super.undo();
                // Remove all "edge detected images" and show the originals.
                Iterator i = figures.iterator();
                Drawing drawing = view.getDrawing();

                List<Figure> figuresToAdd = new ArrayList<Figure>();
                while (i.hasNext()) {
                    SVGImageFigure figure = (SVGImageFigure) i.next();
                    if (figure.getEdgeDetectorApplied()) {
                        SVGImageFigure normalImage = new SVGImageFigure(figure.getStartPoint().x, figure.getStartPoint().y, figure.getWidth(), figure.getHeight());
                        normalImage.setBufferedImage(figure.getOriginalBufferedImage());

                        drawing.remove(figure);
                        drawing.add(normalImage);

                        //figures.remove(figure);
                        //figures.add(normalImage);
                        i.remove();
                        figuresToAdd.add(normalImage);
                    }
                }
                for (Figure f : figuresToAdd) {
                    figures.add(f);
                }
            }
        }
        );
    }

    public static void edgeDetection(DrawingView view, Collection figures) {
        EdgeDetector edgeDetector = new EdgeDetector();

        List<Figure> figuresToAdd = new ArrayList<Figure>();
        Iterator i = figures.iterator();
        Drawing drawing = view.getDrawing();
        while (i.hasNext()) {
            SVGImageFigure figure = (SVGImageFigure) i.next();
            BufferedImage bi = figure.getBufferedImage();

            BufferedImage edges = deepCopy(bi);
            edgeDetector.detect(edges);

            SVGImageFigure edgeImage = new SVGImageFigure(figure.getStartPoint().x, figure.getStartPoint().y, figure.getWidth(), figure.getHeight());
            edgeImage.setBufferedImage(edges);
            edgeImage.setOriginalBufferedImage(bi);

            drawing.remove(figure);
            drawing.add(edgeImage);

            //figures.remove(figure);
            //figures.add(edgeImage);
            i.remove();
            figuresToAdd.add(edgeImage);
        }
        for (Figure f : figuresToAdd) {
            figures.add(f);
        }
    }

    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
