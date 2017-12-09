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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import org.jhotdraw.gui.plaf.palette.PaletteButtonUI;
import org.jhotdraw.tabs.Tab;

/**
 *
 * @author pc4
 */
public class TabElement extends JPanel{

    private JToggleButton labelButton;
    private JButton closeButton;
    
    private ItemListener tabButtonPressed;
    private ActionListener closeButtonPressed;
    
    
    
    public TabElement(ButtonGroup group, Tab tab, ItemListener tabPressed, ActionListener closePressed)
    {
        tabButtonPressed = tabPressed;
        closeButtonPressed = closePressed;
        
        
        FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
        layout.setVgap(0);
        layout.setHgap(0);
        setLayout(layout);
        
        putClientProperty("ID", tab.getId());
        
        labelButton = createLabelButton(group, tab);
        add(labelButton);
        
        closeButton = createCloseTabButton(tab);
        add(closeButton);
        
    }
    
    public void ChangeName(String name)
    {
        labelButton.setText("\t" + name + "\t");
    }
    
    public void SetActive()
    {
        labelButton.setSelected(true);
    }
    
    
    private JToggleButton createLabelButton(ButtonGroup group, Tab tab) 
    {
        JToggleButton button = new JToggleButton("\t" + tab.getName() + "\t");
        button.setUI((PaletteButtonUI) PaletteButtonUI.createUI(button));
        button.setFocusable(false);
        button.addItemListener(tabButtonPressed);
        group.add(button);
        button.setSelected(true);
        
        return button;
    }
    
    private JButton createCloseTabButton(Tab tab)
     {
        JButton button = new JButton("x");
        button.setUI((PaletteButtonUI) PaletteButtonUI.createUI(button));
        button.setFocusable(false);
        button.addActionListener(closeButtonPressed);
        
        return button;
     }
    
    
}
