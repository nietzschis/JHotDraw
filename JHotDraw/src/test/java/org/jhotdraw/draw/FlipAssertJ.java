/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw;

import java.lang.reflect.InvocationTargetException;
import javax.swing.Action;
import javax.swing.JFrame;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.jhotdraw.samples.svg.gui.ActionsToolBar;
import org.jhotdraw.app.action.HorizontalFlipAction;
import org.jhotdraw.app.action.VerticalFlipAction;
import org.junit.Test;
import org.jhotdraw.gui.JPopupButton;
import org.junit.Assert;

/**
 *
 * @author Sadik
 */
public class FlipAssertJ extends AssertJSwingJUnitTestCase{
    private Action v;
    private Action h;

    private ActionsToolBar toolBar;
    private JPopupButton buttonV;
    private JPopupButton buttonH;
    
    private FrameFixture window;

    @Override
    protected void onSetUp() {
        
        //We run everything from a single event-dispatcher-thread "gui action runner"
        JFrame frame = GuiActionRunner.execute(() -> new JFrame());
        
        GuiActionRunner.execute(() -> toolBar = new ActionsToolBar());
        
        GuiActionRunner.execute(() -> v = new VerticalFlipAction());
        GuiActionRunner.execute(() -> buttonV = new JPopupButton());
        GuiActionRunner.execute(() -> buttonV.setName("VerticalButton"));
        GuiActionRunner.execute(() -> buttonV.setAction(v));

        GuiActionRunner.execute(() -> h = new HorizontalFlipAction());
        GuiActionRunner.execute(() -> buttonH = new JPopupButton());
        GuiActionRunner.execute(() -> buttonH.setName("HorizontalButton"));
        GuiActionRunner.execute(() -> buttonH.setAction(h));

        toolBar.add(buttonV);
        toolBar.add(buttonH);
        
        frame.add(toolBar);
        
        window = new FrameFixture(robot(), frame);
        window.show();
        
    }

    @Test
    public void testComponentLookup() throws InvocationTargetException, InterruptedException {
            window.button(buttonV.getName()).click();            
            Assert.assertTrue(buttonV.getActionCommand().equals("Vertical")); //command fired is "vertical"
            
            window.button(buttonH.getName()).click();
            Assert.assertTrue(buttonH.getActionCommand().equals("Horizontal")); //command fired is "vertical"                           
    }
    
}
