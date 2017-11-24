package org.jhotdraw.draw.action;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.EdgeDetector;
import org.jhotdraw.draw.Figure;
import static org.jhotdraw.draw.action.EdgeDetectionAction.bufferedImagesEqual;
import static org.jhotdraw.draw.action.EdgeDetectionAction.deepCopy;
import org.jhotdraw.samples.svg.figures.SVGImageFigure;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Matic-ProBook
 */
public class EdgeDetectionActionTest {
    
    public EdgeDetectionActionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of actionPerformed method, of class EdgeDetectionAction.
     */
    /*
    @Test
    public void testActionPerformed() {
        System.out.println("actionPerformed");
        
        ActionEvent e = null;
        EdgeDetectionAction instance = null;
        instance.actionPerformed(e);
        
        instance.fireUndoableEditHappened(null);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    */

    @Ignore
    @Test
    public void testEdgeDetection() {
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
    
    public BufferedImage loadTestImage() {
        URL url = getClass().getClassLoader().getResource("org/jhotdraw/draw/action/images/tree.png");
        String fileName = url.getPath();
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
}
