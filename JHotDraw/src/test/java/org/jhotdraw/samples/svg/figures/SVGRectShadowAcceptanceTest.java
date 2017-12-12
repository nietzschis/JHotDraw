/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.figures;

import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.junit.SimpleScenarioTest;
import org.jhotdraw.draw.QuadTreeDrawing;
import org.jhotdraw.samples.svg.SVGAttributeKeys;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/**
 *
 * @author Gyhujification
 */
public class SVGRectShadowAcceptanceTest  extends SimpleScenarioTest<SVGRectShadowAcceptanceTest.Stages>{
    
    @Test
    public void shadow_should_be_had(){
        given().rect_setup();
        when().rect_drawn();
        then().shadow_rect_added();
    }
    
    public static class Stages{

        @ProvidedScenarioState
        QuadTreeDrawing drawing;
        
        @ProvidedScenarioState
        SVGRectFigure rect;
        
        
        public void rect_setup(){
            drawing = new QuadTreeDrawing();
            rect = new SVGRectFigure(20,20,20,20,0,0);
            SVGAttributeKeys.SHADOWS.set(rect, 10d);
        }
        
        public void rect_drawn(){
            drawing.add(rect);
        }
        
        public void shadow_rect_added(){
            assertTrue(drawing.contains(rect));
            assertTrue(SVGAttributeKeys.SHADOWS.get(rect)>0d);
            
        }
    }
}
