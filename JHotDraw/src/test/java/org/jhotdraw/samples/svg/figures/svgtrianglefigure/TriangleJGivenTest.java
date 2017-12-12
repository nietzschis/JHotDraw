/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.figures.svgtrianglefigure;

import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.junit.SimpleScenarioTest;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.QuadTreeDrawing;
import org.jhotdraw.samples.svg.figures.SVGTriangleFigure;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Mathias
 */
public class TriangleJGivenTest extends SimpleScenarioTest<TriangleJGivenTest.Stages> {
    
    // Adding a triangle to a drawing.
    // If there's no triangle in the drawing,
    // the behavior is wrong.
    @Test
    public void test_scanario() {
        given().a_drawing();
        when().triangle_drawn();
        then().drawing_contains_triangle();
    }

    public static class Stages {

        @ProvidedScenarioState
        QuadTreeDrawing drawing;
        
        @ProvidedScenarioState
        Figure triangle;
        
        public void a_drawing() {
            drawing = new QuadTreeDrawing();
        }

        public void triangle_drawn() {
            triangle = new SVGTriangleFigure();
            drawing.add(triangle);
        }

        public void drawing_contains_triangle() {
            assertNotNull(drawing);
            assertNotNull(triangle);
            assertTrue(drawing.contains(triangle));
        }
    }
}
