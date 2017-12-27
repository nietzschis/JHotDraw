/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Action;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.ConnectionFigure;
import org.jhotdraw.draw.Connector;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Handle;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tim
 */
public class SVGImageFigureTest {
    
    public SVGImageFigureTest() {
    }
   
    @Before
    public void setUp() {
        
    }


    /**
     * Test of clipToShape method, of class SVGImageFigure.
     */
    @Test
    public void testClipToShape() {
        System.out.println("clipToShape");
        
        int x = 0;
        int y = 0;
        int width = 100;
        int height = 50;
        
        BufferedImage buffImg = generateImg(width, height);

        Graphics2D g = buffImg.createGraphics();
        Rectangle2D.Double rectangle = new Rectangle2D.Double(x, y, width, height);
        Shape clipShape = new Ellipse2D.Double(x, y, width, height);
        
        SVGImageFigure instance = new SVGImageFigure();
        instance.clipToShape(g, clipShape);
        
        //check if pixel is NOT within boundaries of the shape
        //x and y should be in the upper left corner of the image
        Point2D.Double p = new Point2D.Double(x, y);
        boolean exists = instance.contains(p);
        assertFalse(exists);
    }
    
    public BufferedImage generateImg(int w, int h){
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        return image;
    }


    /**
     * Test of drawStroke method, of class SVGImageFigure.
     
    @Test
    public void testDrawStroke() {
        System.out.println("drawStroke");
        Graphics2D g = null;
        SVGImageFigure instance = new SVGImageFigure();
        instance.drawStroke(g);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    * 
    */

 
    
}
