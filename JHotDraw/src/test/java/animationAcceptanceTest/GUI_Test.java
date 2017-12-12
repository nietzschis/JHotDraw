/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animationAcceptanceTest;

import java.awt.event.ActionEvent;
import java.util.concurrent.Executors;
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
    public void tearDown() throws InterruptedException {
        Animation.getInstance().getFrames().clear();
        app = null;
        window.cleanUp();
    }
    
    @Test
    public void addAndRemoveFrames() throws InterruptedException {
        window.show();
        Animation.getInstance().getFrames().clear();
        JFrame testFrame = new JFrame();
        Animation.getInstance().setCurrentFrame(testFrame);
        
        window.button("addFrameButton").click();
        window.button("addFrameButton").click();
        window.button("addFrameButton").click();
        assertThat(Animation.getInstance().getFrames().size()).isEqualTo(3);
        
        JFrame frameToRemove = Animation.getInstance().getFrames().get(1);
        Animation.getInstance().setCurrentFrame(frameToRemove);
        window.button("removeFrameButton").click();
        
        assertThat(Animation.getInstance().getFrames().size()).isEqualTo(2);
    }
    /*
    @Ignore
    @Test
    public void createAnimation_and_play_and_pause() throws InterruptedException {
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
        
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                window.button("playButton").click();
            }
        });
        assertThat(Animation.getInstance().getTimesPlayed()).isGreaterThan(0);
        window.button("pauseButton").click();
        Thread.sleep(500);
        assertThat(Animation.getInstance().getTimesPlayed()).isEqualTo(0);
    }*/
}