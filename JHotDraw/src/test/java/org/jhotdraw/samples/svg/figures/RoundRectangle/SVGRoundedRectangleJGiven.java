/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.figures.RoundRectangle;

import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.junit.*;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.QuadTreeDrawing;
import org.jhotdraw.samples.svg.figures.SVGRoundedRectangle;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Jonas
 */
public class SVGRoundedRectangleJGiven extends SimpleScenarioTest<SVGRoundedRectangleJGiven.Stages>{
    
    @Test
    public void test(){
        given().a_new_drawing_canvas();
        when().a_new_round_rectangle_drawn();
        then().canvas_contains_round_rectangle();
    }
    
    public static class Stages {
        
        @ProvidedScenarioState
        SVGRoundedRectangle roundRectangle;
        
        @ProvidedScenarioState
        QuadTreeDrawing canvas;
        
        //creates a new drawing canvas. 
        public void a_new_drawing_canvas(){
            canvas = new QuadTreeDrawing();
        }
        //draws a new round rectangle and adds it to the canvas.
        public void a_new_round_rectangle_drawn(){
            roundRectangle = new SVGRoundedRectangle();
            canvas.add(roundRectangle);
        }
        //check to see if canvas has a round rectangle, and if the objects are not null. 
        public void canvas_contains_round_rectangle(){
            assertTrue(canvas.contains(roundRectangle));
            assertNotNull(canvas);
            assertNotNull(roundRectangle);
        }
        
    }
}
