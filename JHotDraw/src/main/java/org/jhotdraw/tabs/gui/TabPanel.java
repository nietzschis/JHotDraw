/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.tabs.gui;

import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
    ItemListener itemHandle;



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
    
    
    public Drawing getCurrentDrawing()
    {
        return tabs.getCurrentDrawing();
    }
    
    private JPanel createTab(ButtonGroup group, Tab tab)
    {
        FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
        layout.setVgap(0);
        layout.setHgap(0);
        
        JPanel temp = new JPanel(layout);
        temp.add(createLabelButton(group, tab));
        temp.add(createCloseTabButton(tab));
        return temp;
    }
    
    private JToggleButton createLabelButton(ButtonGroup group, Tab tab) 
    {
        JToggleButton button = new JToggleButton("\t" + tab.getName() + "\t");
        button.setUI((PaletteButtonUI) PaletteButtonUI.createUI(button));
        button.setFocusable(false);
        button.addItemListener((e) ->
        {
            if(e.getStateChange() == ItemEvent.SELECTED)
            {
                tabs.setCurrentTab(tab);
            }
            itemHandle.itemStateChanged(e);
        }
        );
        group.add(button);
        button.setSelected(true);
        
        return button;
    }
    
    private JButton createCloseTabButton(Tab tab)
     {
        JButton button = new JButton("x");
        button.setUI((PaletteButtonUI) PaletteButtonUI.createUI(button));
        button.setFocusable(false);
        button.addActionListener((e) ->
        {
            //remove();
        });
        
        return button;
     }
    
    
    public ItemListener getItemHandle()
    {
        return itemHandle;
    }

    public void setItemHandle(ItemListener itemHandle)
    {
        this.itemHandle = itemHandle;
    }
    
}


