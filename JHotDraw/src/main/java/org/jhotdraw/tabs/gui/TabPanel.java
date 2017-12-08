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
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.gui.plaf.palette.PaletteButtonUI;
import org.jhotdraw.tabs.SimpleTabManager;
import org.jhotdraw.tabs.Tab;
import org.jhotdraw.tabs.Tabs;

/**
 *
 * @author pc4
 */
public class TabPanel extends JPanel
{
    Tabs tabs;
    ButtonGroup group;
    TabListener tabHandle;

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
        Tab t = new Tab(d, title);
        tabs.add(t);
        tabs.setCurrentTab(t);
        System.out.println(title);
        add(createTab(group, t));
        
        System.out.println(this.getComponentCount());
        revalidate();
        repaint();
    }

    public boolean tabExist()
    {
        return tabs.getCurrentTab() != null || !tabs.getAllTabs().isEmpty();
    }
    
    public Drawing getCurrentDrawing()
    {
        if(tabExist() && tabs.getCurrentTab() == null)
        {
            for(Tab t : tabs.getAllTabs())
            {
                tabs.setCurrentTab(t);
                break;
            }
        }
        
        return tabs.getCurrentDrawing();
    }
    
    private JPanel createTab(ButtonGroup group, Tab tab)
    {
        return new TabElement(group, tab,
        (ItemEvent e) ->
        {
            if(e.getStateChange() == ItemEvent.SELECTED)
            {
                tabs.setCurrentTab(tab);
                tabHandle.ChangeTab();
            }  
        }, 
        (ActionEvent e) ->
        {
            Component component = getTabElement(tabs.getCurrentTab());
            tabs.remove(tab);
            this.remove(component);
            tabHandle.ChangeTab();
            revalidate();
            repaint();
            
        });
        
        
        
            
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
        getTabElement(tabs.getCurrentTab()).ChangeName(name);
    }
    

    
    
    
    
    public TabListener getItemHandle()
    {
        return tabHandle;
    }

    public void setItemHandle(TabListener tabHandle)
    {
        this.tabHandle = tabHandle;
    }
    
}


