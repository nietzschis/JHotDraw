package org.jhotdraw;

import java.awt.Point;
import java.util.Random;
import javafx.scene.input.KeyCode;
import org.assertj.swing.core.MouseButton;
import org.assertj.swing.fixture.FrameFixture;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.samples.svg.SVGApplicationModel;
import org.junit.After;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Daniel Holst
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RecordingToolGUITest {
    
    private FrameFixture window;
    private Application app;
    
    //setup borrowed from CollaborationGUITest
    @Before
    public void setUp() throws InterruptedException {
        app = new DefaultSDIApplication();
        SVGApplicationModel model = new SVGApplicationModel();
        model.setViewClassName("org.jhotdraw.samples.svg.SVGView");
        app.setModel(model);
        app.launch(null);
        while (app.getFrame() == null) {
            Thread.sleep(500);
            window = new FrameFixture(app.getFrame());
        }
    }
    
    
    @Test
    public void TestA_PlaybackWithoutRecordingTest() throws InterruptedException {
        assertNotNull(app.getFrame());
        assertNotNull(window);

        window.button("btnPlayRecording").click();
        Thread.sleep(100);

        //no exceptions or crashes, things went well.

    }
    
    @Test
    public void TestB_RecordingTest() throws InterruptedException {
        assertNotNull(app.getFrame());
        assertNotNull(window);
        Random r = new Random();
        window.button("btnStartRecording").click();

        window.toggleButton("btnScribbleTool").click();

        Point center = app.getFrame().getLocation();
        center.x += app.getFrame().getSize().width/2;
        center.y += app.getFrame().getSize().height/2;

        
        for(int i = 0; i < 10; i++){
            Point random = new Point((int)(r.nextDouble()*100), (int)(r.nextDouble()*100));
            window.robot().click(new Point((center.x-random.x/2)+random.x, (center.y-random.y/2)+random.y), MouseButton.LEFT_BUTTON, 0);
        }
        
        window.pressKey(KeyCode.ESCAPE.ordinal());

        window.button("btnPlayRecording").click();
        Thread.sleep(1000);

        //no exceptions or crashes, things went well.

    }
    
    
    @After
    public void tearDown() {
        window.cleanUp();
    }
    
    
    
}
