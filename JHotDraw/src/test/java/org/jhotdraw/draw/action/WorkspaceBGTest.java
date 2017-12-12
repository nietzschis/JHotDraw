package org.jhotdraw.draw.action;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractButton;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.DrawingView;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lasse
 */
public class WorkspaceBGTest {
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    DrawingView view;
    AbstractButton button;
    String[] colors;
    ActionEvent ae;
    
    @Before
    public void setUp() {
        view = new DefaultDrawingView();
        button = ButtonFactory.createWorkspaceBGButton(view);
        ae = new ActionEvent(button, 0, "click");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of methods, of class WorkspaceBG.
     */
    @Test
    public void testBlack() {
        System.out.println("Test Black");
        
        WorkspaceBGAction black = new WorkspaceBGAction(view, "Black", button);
        black.actionPerformed(ae);
        assertEquals(Color.BLACK, view.getWorkspaceBG());
    }
    
    @Test
    public void testGray() {
        System.out.println("Test Gray");
        
        WorkspaceBGAction gray = new WorkspaceBGAction(view, "Gray", button);
        gray.actionPerformed(ae);
        assertEquals(Color.GRAY, view.getWorkspaceBG());
    }
    
    @Test
    public void testWhite() {
        System.out.println("Test White");
        
        WorkspaceBGAction white = new WorkspaceBGAction(view, "White", button);
        white.actionPerformed(ae);
        assertEquals(Color.WHITE, view.getWorkspaceBG());
    }
}