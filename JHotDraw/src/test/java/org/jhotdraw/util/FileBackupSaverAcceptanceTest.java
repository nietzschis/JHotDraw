package org.jhotdraw.util;

import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.junit.SimpleScenarioTest;
import java.io.File;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.samples.svg.SVGApplicationModel;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Nick
 */
public class FileBackupSaverAcceptanceTest extends SimpleScenarioTest<FileBackupSaverAcceptanceTest.Stages> {
    
    /**
     * Tests that DefaultSDIApplication will use FileBackupSaver to save files
     * aften a given interval.
     */
    @Test
    public void backupFilesShouldBeSavedAfterInterval() {
        given().application();
        when().timeHasElapsed();
        then().backupFilesHaveBeenSaved();
    }
    
    public static class Stages {
        @ProvidedScenarioState
        DefaultSDIApplication app;
        
        
        public void application() {
            app = new LowIntervalBackupApplication();
            SVGApplicationModel model = new SVGApplicationModel();
            model.setViewClassName("org.jhotdraw.samples.svg.SVGView");
            app.setModel(model);
            app.launch(new String[0]);
        }
        
        public void timeHasElapsed() {
            try {
                Thread.sleep(1500);
            } catch (Exception ex) {
                fail(ex.toString());
            }
        }
        
        public void backupFilesHaveBeenSaved() {
            if (app.views().size() > 0) {
                app.views().forEach((v) -> {
                File backup = v.getBackupFile();
                assertNotNull(backup);
                assertTrue(backup.exists());
                }); 
            } else {
                fail("No views contained in application (test invalid)");
            }
        }
        
        private class LowIntervalBackupApplication extends DefaultSDIApplication {
            /**
             * Overriden so that interval is lower, for testing purposes.
             * @param interval
             * @param backupLocation 
             */
            @Override
            protected void createAutosaver(int interval, String backupLocation) {
                super.createAutosaver(1, backupLocation);
            }
        }
    }
}
