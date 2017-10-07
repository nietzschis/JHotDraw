package org.jhotdraw.draw.action;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import java.awt.image.BufferedImage;
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
        final LinkedList<Figure> figures = new LinkedList<Figure>(view.getSelectedFigures());

        edgeDetection(view, figures);
        // TODO: You can not undo changes when edge detection is applied.
        fireUndoableEditHappened(new AbstractUndoableEdit() {
            @Override
            public String getPresentationName() {
                return labels.getTextProperty(ID);
            }

            @Override
            public void redo() throws CannotRedoException {
                super.redo();
                SendToBackAction.sendToBack(view, figures);
            }

            @Override
            public void undo() throws CannotUndoException {
                super.undo();
                BringToFrontAction.bringToFront(view, figures);
            }
        }
        );
    }

    public static void edgeDetection(DrawingView view, Collection figures) {
        //create the detector and adjust its parameters as desired
//        CannyEdgeDetector detector = new CannyEdgeDetector();
//        detector.setLowThreshold(0.5f);
//        detector.setHighThreshold(1f);
        EdgeDetector edgeDetector = new EdgeDetector();
        
        Iterator i = figures.iterator();
        Drawing drawing = view.getDrawing();
        while (i.hasNext()) {
            SVGImageFigure figure = (SVGImageFigure) i.next();
            BufferedImage bi = figure.getBufferedImage();

            bi.getGraphics().drawImage(bi, 0, 0, null);
            BufferedImage edges = edgeDetector.detect(bi);
           
            //apply edge detector to an image
//            detector.setSourceImage(bi);
//            detector.process();
//            BufferedImage edges = detector.getEdgesImage();

            SVGImageFigure edgeImage = new SVGImageFigure(figure.getStartPoint().x, figure.getStartPoint().y, figure.getWidth(), figure.getHeight());
            edgeImage.setBufferedImage(edges);

            drawing.basicRemove(figure);
            drawing.add(edgeImage);
        }
    }
}
