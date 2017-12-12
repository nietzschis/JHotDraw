/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;

import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.junit.SimpleScenarioTest;
import javax.swing.AbstractButton;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.DrawingView;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Christian
 */
public class MagnifyingGlassAcceptanceTest extends SimpleScenarioTest<MagnifyingGlassAcceptanceTest.Stages>{
    
    @Test
    public void screen_should_be_zoomed(){
        given().magnify_button();
        when().button_click();
        then().screen_will_zoom();
    }
    

    public static class Stages{
        @ProvidedScenarioState
        DrawingView view;
        
        @ProvidedScenarioState
        AbstractButton magnifyingButton;
        
        
        public void magnify_button(){
            view = new DefaultDrawingView();
            magnifyingButton = ButtonFactory.createMagnifyButton(view);
        }
        
        public void button_click(){
            magnifyingButton.setSelected(true);
        }
        
        public void screen_will_zoom(){
            assertEquals(2.0, view.getScaleFactor() , 0.0);
            assertNotNull(magnifyingButton);
            
        }
    }
}
    

