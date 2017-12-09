/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.tabs;

import org.jhotdraw.draw.QuadTreeDrawing;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pc4
 */
public class SimpleTabManagerTest
{

    @Test
    public void TestAddAndGetCurrentTab()
    {
        Tabs tabs = new SimpleTabManager();
        assertNull(tabs.getCurrentTab());

        Tab firstTab = new Tab(new QuadTreeDrawing(), "First");
        tabs.add(firstTab);
        assertTrue(firstTab.equals(tabs.getCurrentTab()));

        Tab secondTab = new Tab(new QuadTreeDrawing(), "Second");
        tabs.add(secondTab);
        assertFalse(secondTab.equals(tabs.getCurrentTab()));
    }

    @Test
    public void TestGetAll()
    {
        Tabs tabs = new SimpleTabManager();
        assertTrue(tabs.getAllTabs().isEmpty());

        Tab firstTab = new Tab(new QuadTreeDrawing(), "First");
        tabs.add(firstTab);
        assertTrue(tabs.getAllTabs().size() == 1);

        Tab secondTab = new Tab(new QuadTreeDrawing(), "Second");
        tabs.add(secondTab);
        assertTrue(tabs.getAllTabs().size() == 2);
    }
    
    @Test
    public void TestSetCurrentTab()
    {
        Tabs tabs = new SimpleTabManager();
        Tab firstTab = new Tab(new QuadTreeDrawing(), "First");
        tabs.add(firstTab);
        assertTrue(firstTab.equals(tabs.getCurrentTab()));
        
        Tab secondTab = new Tab(new QuadTreeDrawing(), "Second");
        tabs.add(secondTab);
        assertTrue(firstTab.equals(tabs.getCurrentTab()));
        
        tabs.setCurrentTab(secondTab);
        assertTrue(secondTab.equals(tabs.getCurrentTab()));
        
        tabs.setCurrentTab(firstTab);
        assertTrue(firstTab.equals(tabs.getCurrentTab()));
    }
    
    @Test
    public void TestGetCurrentDrawing()
    {
        Tabs tabs = new SimpleTabManager();
        assertNull(tabs.getCurrentDrawing());

        Tab firstTab = new Tab(new QuadTreeDrawing(), "First");
        tabs.add(firstTab);
        assertTrue(firstTab.getDrawing().equals(tabs.getCurrentDrawing()));

        Tab secondTab = new Tab(new QuadTreeDrawing(), "Second");
        tabs.add(secondTab);
        assertTrue(firstTab.getDrawing().equals(tabs.getCurrentDrawing()));
    }
    
    @Test
    public void TestRemove()
    {
        Tabs tabs = new SimpleTabManager();
        assertTrue(tabs.getAllTabs().isEmpty());

        Tab firstTab = new Tab(new QuadTreeDrawing(), "First");
        tabs.add(firstTab);
        assertTrue(tabs.getAllTabs().size() == 1);

        Tab secondTab = new Tab(new QuadTreeDrawing(), "Second");
        tabs.add(secondTab);
        assertTrue(tabs.getAllTabs().size() == 2);
        
        tabs.remove(secondTab);
        assertFalse(tabs.getAllTabs().contains(secondTab));
        
        tabs.remove(firstTab);
        assertTrue(tabs.getAllTabs().isEmpty());
        assertNull(tabs.getCurrentTab());
    }
}
