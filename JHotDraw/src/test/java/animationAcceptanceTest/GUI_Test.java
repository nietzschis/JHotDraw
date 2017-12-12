/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animationAcceptanceTest;

import javax.swing.JFrame;
import org.assertj.swing.fixture.FrameFixture;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.draw.Animation;
import org.jhotdraw.samples.svg.SVGApplicationModel;
import static org.assertj.core.api.Assertions.assertThat;
import org.jhotdraw.draw.AnimationTool;
import org.junit.*;

/**
 *
 * @author lasca
 */
public class GUI_Test {
    private FrameFixture window;
    private Application app;
    
    @Before
    public void setUp() throws InterruptedException {
        app = new DefaultSDIApplication();
        SVGApplicationModel model = new SVGApplicationModel();
        model.setViewClassName("org.jhotdraw.samples.svg.SVGView");
        app.setModel(model);
        app.launch(null);
        while(app.getFrame() == null) {
            Thread.sleep(1000);
        }
        window = new FrameFixture(app.getFrame());
    }
    
    @After
    public void closeDown() throws InterruptedException {
        window.cleanUp();
    }
    
    @Test
    public void addAndRemoveFrames() throws InterruptedException {
        window.show();
        
        JFrame testFrame = new JFrame();
        Animation.getInstance().setCurrentFrame(testFrame);
        window.button("addFrameButton").click();
        Thread.sleep(1000);
        window.button("addFrameButton").click();
        Thread.sleep(1000);
        window.button("addFrameButton").click();
        Thread.sleep(1000);
        assertThat(Animation.getInstance().getFrames().size()).isEqualTo(3);
        
        Thread.sleep(1000);
        JFrame frameToRemove = Animation.getInstance().getFrames().get(1);
        Animation.getInstance().setCurrentFrame(frameToRemove);
        window.button("removeFrameButton").click();
        
        assertThat(Animation.getInstance().getFrames().size()).isEqualTo(2);
    }
    
    @Test
    public void createAnimationAndPlay() throws InterruptedException {
        window.show();
        
        JFrame testFrame = new JFrame();
        Animation.getInstance().setCurrentFrame(testFrame);
        window.button("addFrameButton").click();
        Thread.sleep(1000);
        window.button("addFrameButton").click();
        Thread.sleep(1000);
        window.button("addFrameButton").click();
        Thread.sleep(1000);
        assertThat(Animation.getInstance().getFrames().size()).isEqualTo(3);
        
        window.button("playButton").click();
        Thread.sleep(500);
        assertThat(AnimationTool.getFramesPlayed()).isGreaterThan(0);
    }
    
    @Ignore
    @Test
    public void playAndPause() throws InterruptedException {
        
    }
}
