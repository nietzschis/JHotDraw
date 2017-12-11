/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;

import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.junit.SimpleScenarioTest;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import org.jhotdraw.draw.EdgeDetector;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Matic-ProBook
 */
public class EdgeDetectionActionJGivenTest extends SimpleScenarioTest<EdgeDetectionActionJGivenTest.Steps> {

    @Test
    public void example_scenario() {
        given().image();
        when().applying_matrix();
        then().edges_on_image();
    }

    public static class Steps {

        @ProvidedScenarioState
        BufferedImage img;

        public void image() {
            // read an image
            img = EdgeDetectionActionTest.loadTestImage(getClass());
        }

        public void applying_matrix() {
            // apply matrix to image
            EdgeDetector edgeDetector = new EdgeDetector();
            edgeDetector.detect(img);
        }

        public void edges_on_image() {
            // resoult should be black and white image with edges detected
            assertTrue(isImageBlackAndWhite(img));
        }

        public static boolean isImageBlackAndWhite(BufferedImage img) {
            int w = img.getWidth(null);
            int h = img.getHeight(null);
            int[] pixels = new int[w * h];
            PixelGrabber pg = new PixelGrabber(img, 0, 0, w, h, pixels, 0, w);
            try {
                pg.grabPixels();
            } catch (Exception ex) {
                return false;
            }
            boolean isValid = false;
            for (int pixel : pixels) {
                Color color = new Color(pixel);
                if (color.getAlpha() == 0 || color.getRGB() != Color.WHITE.getRGB()) {
                    isValid = true;
                    break;
                }
            }
            return isValid;
        }
    }
    
}
