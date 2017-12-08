/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg;

import java.awt.Frame;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.assertj.swing.launcher.ApplicationLauncher.*;
import static org.assertj.swing.finder.WindowFinder.findFrame;
import org.assertj.swing.finder.FrameFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.core.AbstractComponentMatcher;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.Robot;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import static org.assertj.swing.core.MouseButton.LEFT_BUTTON;
import static org.assertj.swing.core.MouseClickInfo.leftButton;
import org.assertj.swing.fixture.JToggleButtonFixture;
import static org.assertj.swing.timing.Timeout.timeout;
import org.jhotdraw.samples.svg.EraserTool;
import org.jhotdraw.samples.svg.Erasing;
import org.jhotdraw.samples.svg.Main;
import org.jhotdraw.gui.*;
import org.jhotdraw.app.*;



/**
 *
 * @author f_mol
 */
public class EraserToolTest extends AssertJSwingJUnitTestCase {
    
    private FrameFixture frame;
    private JButtonFixture button;
    private AbstractApplication app;
    
       
    
    
   @Override
    protected void onSetUp() {
        
            //        app = new DefaultSDIApplication();
//        Main main = GuiActionRunner.execute(() -> new Main());
        application(Main.class).start();

        frame = findFrame(new GenericTypeMatcher<Frame>(Frame.class) {
        protected boolean isMatching(Frame frame) {
        return frame.isShowing();
        }
    }).using(robot());
                
       robot().settings().delayBetweenEvents(1000);
//         frame.button("Rectangle").click(LEFT_BUTTON);
//         frame.button().requireToolTip("Rectangle").click(LEFT_BUTTON);
//          SVGApplicationModel model = GuiActionRunner.execute(() -> new SVGApplicationModel());
//          SVGView model = GuiActionRunner.execute(() -> new SVGView());
        



//        EraserToolTest frame = GuiActionRunner.execute(() -> new EraserToolTest());
//        window = new FrameFixture();
//        window.show(); 
        
    }
    
       
    @Test
    public void shouldEraseFigureFromCanvasWhenUsingButton() {
//        robot().settings().delayBetweenEvents(1000);
//          final JButtonFixture button1 = frame.toolBar("Tools").button();
//          GuiActionRunner.execute(() -> button1.setText("Name:"));
//          frame.button().click();
        JToggleButtonFixture tb = frame.toggleButton("Rectangle");
            tb.focus();
           
                
            tb.requireToolTip("Rectangle");
            //tb.requireNotSelected();
            tb.click(leftButton().times(1));            
            frame.moveTo(new Point(10,100)).click().moveTo(new Point(100,200));
//        GenericTypeMatcher<Frame> matcher = new GenericTypeMatcher<Frame>() {
//        protected boolean isMatching(Frame frame) {
//        return "JHotDraw - SVG".equals(frame.getTitle());
//        }
//    };
//        FrameFixture window = WindowFinder.findFrame(matcher).using(robot);
//    window.show();
//          app.createContainer();
//        System.out.println(window);
//        window.textBox("textToCopy").enterText("Some random text");
//        window.button("eraserButton").click();
//        window.label("copiedText").requireText("Some random text");
}

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
