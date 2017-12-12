/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.tabs.gui;

import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.junit.SimpleScenarioTest;
import java.awt.Dimension;
import javax.swing.JFrame;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.jhotdraw.draw.QuadTreeDrawing;
import org.jhotdraw.tabs.Tab;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author pc4
 */
public class SimpleTabManagerTestAcceptance extends SimpleScenarioTest<SimpleTabManagerTestAcceptance.Steps>
{
    @Test
    public void TabScenario() {
        given().canvas();
        when().cpening_the_canvas();
        then().a_new_tab_opens();
    }
    
    public static class Steps {

        @ProvidedScenarioState
        QuadTreeDrawing drawing;
        
        @ProvidedScenarioState
        TabPanel tabs;
        
        @ProvidedScenarioState
        Tab tab;
        
        @ProvidedScenarioState
        FrameFixture window;

        public void canvas() {
            drawing = new QuadTreeDrawing();
            
            tabs = new TabPanel();
            JFrame temp = new JFrame();
            temp.setPreferredSize(new Dimension(100,100));
            JFrame frame = GuiActionRunner.execute(() -> temp);
            frame.add(tabs);
            window = new FrameFixture(frame);
            window.show();
            
            
        }

        public void cpening_the_canvas() {
            tab = new Tab(new QuadTreeDrawing(), "The Canvas");
        }

        public void a_new_tab_opens() {
            tabs.addTab(tab);
            assertEquals(tabs.getTabs().size(), 1);
        }
        
    }
}
