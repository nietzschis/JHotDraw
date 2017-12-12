package org.jhotdraw.app.action.watermark;





/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Mads
 */
public class WatermarkTest {

    public WatermarkTest() {

    }


    @Test
    public void testWatermarkOnExportedFile() {
        BufferedImage watermarkImage = loadWatermark();
        File sourceFile = loadTestFile();
        BufferedImage sourceFileImage = null;
        
        File finalFile = new File(sourceFile.getPath().substring(0, sourceFile.getPath().lastIndexOf('.')) + "_watermarked.png");
        try {
            sourceFileImage = ImageIO.read(sourceFile);
        

        Graphics2D g2d = (Graphics2D) sourceFileImage.getGraphics();
        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
        g2d.setComposite(alpha);

        g2d.drawImage(watermarkImage, 0, 0, sourceFileImage.getWidth(), sourceFileImage.getHeight(), null);

        ImageIO.write(sourceFileImage, "png", finalFile);
        g2d.dispose();
        assertThat(pixels(finalFile), not(equalTo(pixels(sourceFile))));
        Files.deleteIfExists(finalFile.toPath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @Test
    public void testBMPFileFormat(){
        File testFile = new File("src/test/resources/org/jhotdraw/app/action/images/testfilebmp.bmp");
        try {
            BufferedImage sourceFileImage = ImageIO.read(testFile);
            assertNotNull(ImageIO.read(testFile));
            assertNull(sourceFileImage.getAlphaRaster());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @Test
    public void testPNGFileFormat(){
        File testFile = new File("src/test/resources/org/jhotdraw/app/action/images/testfilepng.png");
        try {
            BufferedImage sourceFileImage = ImageIO.read(testFile);
            assertNotNull(ImageIO.read(testFile));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @Test
    public void testJPGFileFormat(){
        File testFile = new File("src/test/resources/org/jhotdraw/app/action/images/testfilejpg.jpg");
        try {
            BufferedImage sourceFileImage = ImageIO.read(testFile);
            assertNotNull(ImageIO.read(testFile));
            assertNull(sourceFileImage.getAlphaRaster());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }    
    
    @Test
    public void testGIFFileFormat(){
        File testFile = new File("src/test/resources/org/jhotdraw/app/action/images/testfilepng.png");
        try {          
            BufferedImage sourceFileImage = ImageIO.read(testFile);
            assertNotNull(ImageIO.read(testFile));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
       
    protected byte[] pixels(File file){
        BufferedImage image = null;
        byte[] pixels = null;
        try{
            image = ImageIO.read(file);
            pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        }catch(IOException e){
            e.printStackTrace();
        } return pixels;
    }

    protected BufferedImage loadWatermark() {
        File watermarkFile = new File("src/test/resources/org/jhotdraw/app/action/images/sample.png");
        BufferedImage watermarkImage = null;
        try{
        watermarkImage = ImageIO.read(watermarkFile);
        }
         catch(IOException e){
            e.printStackTrace();
        }
        return watermarkImage;
    }

    protected File loadTestFile(){
        File testFile = new File("src/test/resources/org/jhotdraw/app/action/images/testImage.png");
        return testFile;
    }
}
