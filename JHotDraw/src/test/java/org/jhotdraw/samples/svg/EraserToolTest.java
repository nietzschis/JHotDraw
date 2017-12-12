/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg;

import java.awt.Frame;
import java.awt.Point;
import org.junit.Test;
import static org.assertj.swing.launcher.ApplicationLauncher.*;
import static org.assertj.swing.finder.WindowFinder.findFrame;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import static org.assertj.swing.core.MouseButton.LEFT_BUTTON;
import org.assertj.swing.fixture.JToggleButtonFixture;
import org.jhotdraw.samples.svg.Main;



/**
 *
 * @author Frank Frederiksen-Moeller
 * 
 * This test uses AssertJ for testing the Eraser tool in JHotDraw
 * Unfortunately it doesn't work properly. 
 * It can start JHotDraw GUI and activate the different drawing buttons and also draw a figure. 
 * But it can't remove the figure with the Eraser tool, since the FrameFixture frame
 * can't use the mouse dragged function.
 * This test class is created and executed on MAC OS. It doesn't work on WIN OS.
 */
public class EraserToolTest extends AssertJSwingJUnitTestCase {
    
    private FrameFixture frame;
    
    
   @Override
    protected void onSetUp() {
        
    
        application(Main.class).start();

        frame = findFrame(new GenericTypeMatcher<Frame>(Frame.class) {
        protected boolean isMatching(Frame frame) {
        return frame.isShowing();
        }
    }).using(robot());
        frame.focus();
                        
       robot().settings().delayBetweenEvents(1000);    
     
        
    }
    
       
    @Test
    public void shouldEraseFigureFromCanvasWhenUsingButton() {
                        
        JToggleButtonFixture rect = frame.toggleButton("Rectangle");
        JToggleButtonFixture erase = frame.toggleButton("Eraser");
        JToggleButtonFixture circle = frame.toggleButton("Ellipse");
             
            circle.focus();                      
            circle.requireToolTip("Ellipse");
            circle.click();
            frame.focus();
            frame.moveTo(new Point(10,10)).click(LEFT_BUTTON);
                                    
            erase.focus();
            erase.requireToolTip("Eraser");
            erase.click();
            frame.focus();
            frame.moveTo(new Point(10,10)).click(LEFT_BUTTON);

            rect.focus();
            rect.requireToolTip("Rectangle");
            rect.click();
            frame.focus();
            frame.moveTo(new Point(50,100)).click(LEFT_BUTTON);
            
            erase.focus();
            erase.requireToolTip("Eraser");
            erase.click();
            frame.focus();
            frame.moveTo(new Point(50,100)).click(LEFT_BUTTON);

    }

}
