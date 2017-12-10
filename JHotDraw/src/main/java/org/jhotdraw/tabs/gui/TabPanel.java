/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.tabs.gui;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.temporal.Temporal;
import java.util.Collection;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import org.jhotdraw.app.action.CloseAction;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.gui.plaf.palette.PaletteButtonUI;
import org.jhotdraw.tabs.SimpleTabManager;
import org.jhotdraw.tabs.Tab;
import org.jhotdraw.tabs.Tabs;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.action.CloseTabAction;

/**
 *
 * @author pc4
 */
public class TabPanel extends JPanel
{
    private Tabs tabs;
    private ButtonGroup group;
    private TabListener selectedTabChanged = new TabListener()
    {
        @Override
        public void ChangeTab(){ }
        
        @Override
        public void CloseTab(){ ClosePressedTab();}
       
    };
    
    
    private Tab tabToClose;
    

    public TabPanel()
    {
        tabs = new SimpleTabManager();
        
        FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
        layout.setVgap(0);
        layout.setHgap(5);
        setLayout(layout);
        
        group = new ButtonGroup();
    }
    
    
    public void addTab(Drawing d, String title)
    {
        addTab(new Tab(d,title));
    }
    
    public void addTab(Tab tab)
    {
        tabs.add(tab);
        tabs.setCurrentTab(tab);
        add(createTab(group, tab));

        revalidate();
        repaint();
    }

    public boolean tabExist()
    {
        return tabExist(tabs.getCurrentTab());
    }
    
    public boolean tabExist(Tab tab)
    {
        return tab != null || !tabs.getAllTabs().isEmpty();
    }
    
    public Drawing getCurrentDrawing()
    {
        return tabs.getCurrentDrawing();
    }
    
    private void ValidateTab()
    {
        if(tabExist() && tabs.getCurrentTab() == null)
        {
            for(Tab t : tabs.getAllTabs())
            {
                tabs.setCurrentTab(t);
                getTabElement(t).SetActive();
                break;
            }
        }
    }
    
    private JPanel createTab(ButtonGroup group, Tab tab)
    {
        return new TabElement(group, tab,
        (ItemEvent e) ->
        {
            if(e.getStateChange() == ItemEvent.SELECTED)
            {
                tabs.setCurrentTab(tab);
                selectedTabChanged.ChangeTab();
            }  
        }, 
        (ActionEvent e) ->
        {
            tabToClose = tab;
            selectedTabChanged.CloseTab();
        });
         
    }
    
    public void CloseTab()
    {
        if(tabs.getAllTabs().contains(tabs.getCurrentTab()))
        {
            tabToClose = tabs.getCurrentTab();
            ClosePressedTab();
        }
    }
    
    public void ClosePressedTab()
    {
        TabElement component = getTabElement(tabToClose);
        tabs.remove(tabToClose);
        this.remove(component);
        
        ValidateTab();
       
        selectedTabChanged.ChangeTab();
        
        revalidate();
        repaint();
    }
    
    private TabElement getTabElement(Tab tab)
    {
         for(Component c : this.getComponents())
            if(c instanceof TabElement)
               if(((JPanel)c).getClientProperty("ID") == tab.getId())
                    return (TabElement)c;

         return null;
    }
    
    
    public void setTabName(String name)
    {
        tabs.getCurrentTab().setName(name);
        getTabElement(tabs.getCurrentTab()).ChangeName(name);
    }
    
    public Collection<Tab> getTabs()
    {
        return tabs.getAllTabs();
    }

    
    
    
    
    public TabListener getSelectedTabChanged()
    {
        return selectedTabChanged;
    }

    public void setSelectedTabChanged(TabListener tabHandle)
    {
        this.selectedTabChanged = tabHandle;
    }

    
}


