/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.figures.svgtrianglefigure;

import static org.assertj.core.api.Java6Assertions.assertThat;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.QuadTreeDrawing;
import org.jhotdraw.samples.svg.figures.SVGTriangleFigure;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Mathias
 */
public class BDDTriangleTest {
    
    public BDDTriangleTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    private QuadTreeDrawing drawing;
    private Figure triangle;
    
    @Before
    public void setUp() {
        drawing = new QuadTreeDrawing();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void given_a_drawing() {
        assertNotNull(drawing);
    }
    
    @Test
    public void when_a_triangle_is_drawn() {
        triangle = new SVGTriangleFigure();
        drawing.add(triangle);
        assertNotNull(triangle);
    }
    
    @Test
    public void then_drawing_contains_triangle() {
        assertThat(drawing.contains(triangle));
    }
    
}
