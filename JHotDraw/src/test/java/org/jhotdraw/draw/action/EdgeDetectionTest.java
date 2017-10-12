package org.jhotdraw.draw.action;

/**
 *
 * @author Matic-ProBook
 */
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.EdgeDetector;
import org.jhotdraw.draw.Figure;
import static org.jhotdraw.draw.action.EdgeDetectionAction.deepCopy;
import static org.jhotdraw.draw.action.EdgeDetectionAction.bufferedImagesEqual;
import org.jhotdraw.samples.svg.figures.SVGImageFigure;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class EdgeDetectionTest {

    @Test
    public void edgeDetectionTest() {
        BufferedImage img = loadTestImage();
        
        Collection figures = new LinkedList<Figure>();
        SVGImageFigure f = new SVGImageFigure();
        f.setBufferedImage(img);
        figures.add(f);
        BufferedImage imgCopy = deepCopy(img);

        EdgeDetector edgeDetector = new EdgeDetector();
        edgeDetector.detect(imgCopy);

        //EdgeDetectionAction eda = new EdgeDetectionAction(null);
        EdgeDetectionAction.edgeDetection(new DefaultDrawingView(), figures);
        Iterator i = figures.iterator();
        while (i.hasNext()) {
            SVGImageFigure tempFigure = (SVGImageFigure) i.next();
            BufferedImage edgesDetected = tempFigure.getBufferedImage();
            assertTrue(bufferedImagesEqual(edgesDetected, imgCopy));
        }
    }
    
    public static BufferedImage loadTestImage() {
        //String fileName = "D:\\tree.png";
        String fileName = "/tree.png";
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
}
