/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.gui;

import java.util.Collection;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.jhotdraw.draw.DefaultDrawingEditor;
import org.jhotdraw.draw.DrawingEditor;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alexander
 */
public class ToolsToolBarTest {

    private int TOOLBAR_OPENED_IN_GUI = 1;
    
    @Test
    public void testCreateDisclosedComponent() {
        JPanel frameWithToolbar = new JPanel();
        AbstractToolBar toolsToolbar = new ToolsToolBar();
        frameWithToolbar.add(toolsToolbar);
        
        toolsToolbar.setEditor(new DefaultDrawingEditor());
        JComponent componentsInToolbar =
                toolsToolbar.createDisclosedComponent(TOOLBAR_OPENED_IN_GUI);
        int toolsInToolbar = componentsInToolbar.getComponents().length;
        
        assertTrue(toolsInToolbar > 0);    
    }
}
