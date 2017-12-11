/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.QuadTreeDrawing;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Marc
 */
public class SVGInputFormatTest {
    
    private QuadTreeDrawing drawing;
    private SVGInputFormat inputFormat;
    private InputStream in;
    
    public SVGInputFormatTest() {
    }
    
    @Before
    public void setUp() {
        drawing = new QuadTreeDrawing();
        inputFormat = new SVGInputFormat();
    }
    
    @After
    public void tearDown() {
        drawing = null;
        inputFormat = null;
        in = null;
    }

    
    @Test
    public void testRectangle() throws IOException{
        String xml =    "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" version=\"1.2\" baseProfile=\"tiny\">\n" +
                        "    <defs/>\n" +
                        "    <rect x=\"708\" y=\"408\" width=\"303\" height=\"230\" fill=\"#fff\" stroke=\"#000\"/>\n" +
                        "</svg>";
        in = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        inputFormat.read(in, drawing, true);
        
        assertTrue(drawing.getChildCount() == 1);
        Figure figure = drawing.getChild(0);
        assertTrue(figure instanceof SVGRectFigure);
        SVGRectFigure rect = (SVGRectFigure) figure;
        
        assertTrue(rect.getX() == 708);
        assertTrue(rect.getY() == 408);
        assertTrue(rect.getWidth() == 303);
        assertTrue(rect.getHeight() == 230);
    }
    
    @Test(expected = IOException.class)
    public void testInvalidXML() throws IOException{
        String xml = "Definitely not XML";
        in = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        inputFormat.read(in, drawing, true);
    }
    
    
}
