/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.tabs.gui;

import java.awt.Dimension;
import javax.swing.JFrame;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import static org.hamcrest.CoreMatchers.containsString;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.QuadTreeDrawing;
import org.jhotdraw.tabs.Tab;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pc4
 */
public class TabPanelTest
{
    private TabPanel tabs;
    private FrameFixture window;
    
    @Before
    public void setUp() {
        tabs = new TabPanel();

        
        JFrame temp = new JFrame();
        temp.setPreferredSize(new Dimension(100,100));
        JFrame frame = GuiActionRunner.execute(() -> temp);
        frame.add(tabs);
        
        window = new FrameFixture(frame);
        window.show();
    }
    
    @Test
    public void ChangeTabTest()
    {
        Tab firstTab = new Tab(new QuadTreeDrawing(), "First");
        Tab secondTab = new Tab(new QuadTreeDrawing(), "Second");
        
        tabs.addTab(firstTab);
        tabs.addTab(secondTab);
        
        assertTrue(tabs.getCurrentDrawing().equals(secondTab.getDrawing()));
        
        window.toggleButton(firstTab.getId()+"Label").click();
        
        assertTrue(tabs.getCurrentDrawing().equals(firstTab.getDrawing()));
    }
    
    @Test 
    public void CloseTabTest()
    {
        Tab firstTab = new Tab(new QuadTreeDrawing(), "First");
        Tab secondTab = new Tab(new QuadTreeDrawing(), "Second");
        
        tabs.addTab(firstTab);
        tabs.addTab(secondTab);
        
        assertEquals(tabs.getTabs().size(), 2);
        
        window.button(secondTab.getId()+"Close").click();
        
        assertEquals(tabs.getTabs().size(), 1);
        
        window.button(firstTab.getId()+"Close").click();
        
        assertEquals(tabs.getTabs().size(), 0);
    }
    
    @Test 
    public void ChangeTabName()
    {
        Tab firstTab = new Tab(new QuadTreeDrawing(), "First");
        Tab secondTab = new Tab(new QuadTreeDrawing(), "Second");
        
        tabs.addTab(firstTab);
        tabs.addTab(secondTab);
        
        String newName = "Changed!";
        tabs.setTabName(newName);
        
        assertEquals(secondTab.getName(), newName);
        assertThat(window.toggleButton(secondTab.getId()+"Label").text(), containsString(newName));
    }
    
    

    @After
    public void tearDown() {
        window.cleanUp();
    }
}
