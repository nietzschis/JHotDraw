/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.tabs;

import java.util.HashMap;

/**
 *
 * @author pc4
 */
public class SimpleTabManager implements  Tabs
{
    private Tab currentTab;
    private HashMap<String, Tab> tabs;
    
    public SimpleTabManager()
    {
        tabs = new HashMap<>();
    }
    
    @Override
    public Tab currentTab()
    {
        return currentTab;
    }

    @Override
    public void setCurrentTab(Tab tab)
    {
        if(tabs.containsKey(tab.getId()))
            currentTab = tab;
    }

    @Override
    public void add(Tab tab)
    {
        if(currentTab == null)
            currentTab = tab;
        
        tabs.put(tab.getId(), tab);
    }

    @Override
    public void remove(Tab tab)
    {
        tabs.remove(tab.getId());
    }
    
}
