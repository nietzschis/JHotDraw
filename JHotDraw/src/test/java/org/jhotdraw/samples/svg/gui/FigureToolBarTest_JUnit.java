/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.gui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.JSlider;
import org.jhotdraw.gui.JAttributeSlider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Nam
 */
public class FigureToolBarTest_JUnit {

    JAttributeSlider contrastSlider;
    Image image;
    Image image2;
    BufferedImage bufferedImage1;
    BufferedImage bufferedImage2;

    RescaleOp op;
    float contrastValue; // this value is used to set the contrastValue of the image. 100f is for 100% contrastValue and 0f for 0% contrasrtValue

    public FigureToolBarTest_JUnit() {
    }

    @Before
    public void setUp() throws IOException {

        contrastSlider = new JAttributeSlider(JSlider.VERTICAL, 0, 100, 100);
        File f = new File("src\\test\\java\\org\\jhotdraw\\samples\\svg\\gui\\A.jpg");
        String s = f.getAbsolutePath();
        image = ImageIO.read(new File(s));

        bufferedImage1 = (BufferedImage) image;
        bufferedImage2 = (BufferedImage) image;

    }

    @After
    public void tearDown() {
        System.out.println("Tear Down the FigureToolBarTest");
        contrastSlider = null;
        image = null;
        op = null;

        assertNull(contrastSlider);
        assertNull(image);
        assertNull(op);

    }

    @Test
    public void testSetValue() {
        contrastSlider.setValue(66);
        assertSame(66, contrastSlider.getValue());
    }

    @Test
    public void testContratValue100() {

        contrastValue = 100f;
        op = new RescaleOp((contrastValue / 100), 0, null);
        //before Rescale
        byte[] pixelsArrayBefore = ((DataBufferByte) bufferedImage1.getRaster().getDataBuffer()).getData();

        //Rescale       
        bufferedImage1 = op.filter(bufferedImage1, null);

        //After Rescale
        byte[] pixelsArrayAfter = ((DataBufferByte) bufferedImage1.getRaster().getDataBuffer()).getData();

        for (int i = 0; i < pixelsArrayBefore.length; i++) {

            assertSame(pixelsArrayBefore[i], pixelsArrayAfter[i]);
        }

    }

    @Test(expected = AssertionError.class)
    public void testContratValue50() {

        contrastValue = 50f;
        op = new RescaleOp((contrastValue / 100), 0, null);
        //before Rescale
        byte[] pixelsArrayBefore = ((DataBufferByte) bufferedImage2.getRaster().getDataBuffer()).getData();

        //Rescale       
        bufferedImage2 = op.filter(bufferedImage2, null);

        //After Rescale
        byte[] pixelsArrayAfter = ((DataBufferByte) bufferedImage2.getRaster().getDataBuffer()).getData();

        for (int i = 0; i < pixelsArrayBefore.length; i++) {

            assertSame(pixelsArrayBefore[i], pixelsArrayAfter[i]);
        }

    }

    @Test(expected = AssertionError.class)
    public void testDiv() {
        assertSame(20, contrastSlider.getValue());

    }

}
