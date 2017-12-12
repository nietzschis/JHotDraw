package org.jhotdraw.util;

import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.junit.SimpleScenarioTest;
import java.io.File;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.app.View;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Nick
 */
public class FileBackupSaverAcceptanceTest extends SimpleScenarioTest<FileBackupSaverAcceptanceTest.Stages> {
    
    @Test
    public void backupFilesShouldBeSavedAfterInterval() {
        given().application();
        when().timeHasElapsed();
        then().backupFilesHaveBeenSaved();
    }
    
    @Test
    public void backupFilesShouldBeSavedOnClose() {
        given().application();
        when().applicationStop();
        then().backupFilesHaveBeenSaved();
    }
    
    public class Stages {
        @ProvidedScenarioState
        DefaultSDIApplication app;
        @ProvidedScenarioState
        View view1;
        @ProvidedScenarioState
        View view2;
        
        public void application() {
            app = new LowIntervalBackupApplication();
            app.init();
            view1 = app.createView();
            view2 = app.createView();
            app.add(view1);
            app.add(view2);
        }
        
        public void timeHasElapsed() {
            try {
                Thread.sleep(1500);
            } catch (Exception ex) {
                fail(ex.toString());
            }
        }
        
        public void applicationStop() {
            app.stop();
            try {
                Thread.sleep(100);
            } catch (Exception ex) {
                fail(ex.toString());
            }
        }
        
        public void backupFilesHaveBeenSaved() {
            File backup = view1.getBackupFile();
            assertNotNull(backup);
            assertTrue(backup.exists());
            backup = view2.getBackupFile();
            assertNotNull(backup);
            assertTrue(backup.exists());
        }
    }
    
    public class LowIntervalBackupApplication extends DefaultSDIApplication {
        
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
