/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.gui;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Arrays;
import java.util.Collection;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.jhotdraw.draw.AnimationTool;
import static org.jhotdraw.draw.AnimationToolDefinition.*;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.gui.plaf.palette.PaletteButtonUI;
import org.jhotdraw.util.ResourceBundleUtil;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lasca
 */
public class AnimationToolBarTest {
    
    AnimationToolBar toolbar;
    final int STATE = 1;
    
    public AnimationToolBarTest() {
        toolbar = new AnimationToolBar();
    }
    
    /**
     * Test that components are inserted in the toolbar
     */
    @Test
    public void testComponentsAreInserted() {
        toolbar.createDisclosedComponent(STATE);
        assertTrue(toolbar.getComponentCount() > 0);
    }
    
    /**
     * Test that checks if the first tool match, with the other toolbar created in the test
     */
    @Test
    public void testIfFirstToolMatch() {
        JComponent toolbarPanel = toolbar.createDisclosedComponent(STATE);
        JPanel panel = new JPanel();
        
        // Insert a new button with a animation action
        JButton addButton = new JButton(new AnimationTool(ADD_FRAME_TOOL));
        panel.add(addButton);
        
        // Cast to JButton, because it returns Component, which we're not interested in. 
        JButton createdToolToTest = (JButton) panel.getComponent(0);
        JButton existingToolToTest = (JButton) toolbarPanel.getComponent(0);
        assertEquals(createdToolToTest.getAction().getClass(), existingToolToTest.getAction().getClass());
    }
}
