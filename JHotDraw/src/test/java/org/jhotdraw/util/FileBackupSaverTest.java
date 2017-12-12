package org.jhotdraw.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Executors;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.View;
import org.jhotdraw.gui.Worker;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 *
 * @author Nick
 */
public class FileBackupSaverTest {

    public FileBackupSaverTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Tests that FileBackupSaver will save a View's backup file, OR create a
     * new backup file if none exist, and that it does so by using
     * View.execute(..) and View.write(..). The test includes Views with 4
     * different states: - (no file, no backup) - (file, no backup) - (no file,
     * backup) - (file, backup) The interaction with View should be different in
     * each case, which is verified in the test.
     */
    @Test
    public void filesGetAutoSavedTest() throws IOException {
        View viewWithNoFiles = mock(View.class);
        View viewWithOnlyFile = mock(View.class);
        View viewWithOnlyBackup = mock(View.class);
        View viewWithBothFiles = mock(View.class);
        // Define user-defined file for each view
        when(viewWithNoFiles.getFile()).thenReturn(null);
        when(viewWithOnlyFile.getFile()).thenReturn(new File("test.svg"));
        when(viewWithOnlyBackup.getFile()).thenReturn(null);
        when(viewWithBothFiles.getFile()).thenReturn(new File("both.txt"));
        // Define backup file for each view
        when(viewWithNoFiles.getBackupFile()).thenReturn(null);
        when(viewWithOnlyFile.getBackupFile()).thenReturn(null);
        when(viewWithOnlyBackup.getBackupFile()).thenReturn(
                new File("test_location/backup-only.txt"));
        when(viewWithBothFiles.getBackupFile()).thenReturn(
                new File("test_location/backup-both.txt"));
        // It is necessary to actually execute the Worker passed to
        // view.execute(..), so that it is possible to verify method calls that
        // are contained in the Worker
        Answer<Void> executeAnswer = (InvocationOnMock invocation) -> {
            Runnable runnable = invocation.getArgument(0);
            Executors.newSingleThreadExecutor().execute(runnable);
            return null;
        };
        doAnswer(executeAnswer).when(viewWithNoFiles).execute(any(Worker.class));
        doAnswer(executeAnswer).when(viewWithOnlyFile).execute(any(Worker.class));
        doAnswer(executeAnswer).when(viewWithOnlyBackup).execute(any(Worker.class));
        doAnswer(executeAnswer).when(viewWithBothFiles).execute(any(Worker.class));
        // Mock Application.views()
        Application app = mock(Application.class);
        when(app.views()).thenReturn(Arrays.asList(viewWithNoFiles,
                viewWithOnlyFile, viewWithOnlyBackup, viewWithBothFiles));
        // Create FileBackupSaver with interval of 1 second and start it
        FileBackupSaver saver = new FileBackupSaver(app, 1, "test_location");
        saver.start();
        try {
            // Wait for it to save once, and then stop it
            Thread.sleep(1500);
        } catch (Exception ex) {
            fail(ex.toString());
        } finally {
            saver.stop();
        }
        // getBackupFile() should be called for every view
        verify(viewWithNoFiles).getBackupFile();
        verify(viewWithOnlyFile).getBackupFile();
        verify(viewWithOnlyBackup).getBackupFile();
        verify(viewWithBothFiles).getBackupFile();
        // getFile() should be called to determine the name of the backup file
        // on views with no backup file set
        verify(viewWithNoFiles).getFile();
        verify(viewWithOnlyFile).getFile();
        // setBackupFile() should be called on any view with no backup file set
        // and contain the file name of the View's file, if set.
        verify(viewWithNoFiles).setBackupFile(any(File.class));
        verify(viewWithOnlyFile).setBackupFile(argThat(
                (File argument) -> argument.getAbsolutePath().contains("test")));
        // setEnabled(..) should be called exactly twice for each view, as to
        // disable the view while saving, and then re-enable it.
        verify(viewWithNoFiles, times(2)).setEnabled(anyBoolean());
        verify(viewWithOnlyFile, times(2)).setEnabled(anyBoolean());
        verify(viewWithOnlyBackup, times(2)).setEnabled(anyBoolean());
        verify(viewWithBothFiles, times(2)).setEnabled(anyBoolean());
        // write(..) should be called on every view
        verify(viewWithNoFiles).write(any(File.class));
        verify(viewWithOnlyFile).write(argThat(
                (File argument) -> argument.getAbsolutePath().contains("test")));
        verify(viewWithOnlyBackup).write(argThat(
                (File argument) -> argument.equals(
                        new File("test_location/backup-only.txt"))));
        verify(viewWithBothFiles).write(argThat(
                (File argument) -> argument.equals(
                        new File("test_location/backup-both.txt"))));
        // addRecentFile(..) should be called exactly twice, as there are 2
        // Views with no backup file.
        verify(app, times(2)).addRecentFile(any(File.class));
    }

    /**
     * Tests that FileBackupSaver will stop saving files when stop() is called,
     * and that calling start() will start it again.
     */
    @Test
    public void autoSaverStartStopTest() {
        View view = mock(View.class);
        Application app = mock(Application.class);
        when(app.views()).thenReturn(Arrays.asList(view));
        // Create saver
        FileBackupSaver saver = new FileBackupSaver(app, 1, "test_location");
        // Assert that no methods were called on View
        verifyZeroInteractions(view);
        // Start the saver and wait for it to save once
        saver.start();
        try {
            Thread.sleep(1500);
        } catch (Exception ex) {
            fail(ex.toString());
        } finally {
            saver.stop();
        }
        // Verify that these methods were called on View
        verify(view).getBackupFile();
        verify(view).setBackupFile(any(File.class));
        verify(view).execute(any(Worker.class));
    }

    /**
     * Tests that manually calling saveFiles() on FileBackupSaver will save all
     * backup files on Views returned by Application.
     */
    @Test
    public void saveFilesTest() throws IOException {
        View view = mock(View.class);
        Application app = mock(Application.class);
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Runnable runnable = invocation.getArgument(0);
                Executors.newSingleThreadExecutor().execute(runnable);
                return null;
            }
        }).when(view).execute(any(Worker.class));
        when(app.views()).thenReturn(Arrays.asList(view));
        FileBackupSaver saver = new FileBackupSaver(app, 1, "test_location");
        saver.saveFiles();
        // Wait so that any executed workers will have time to execute
        try {
            Thread.sleep(100);
        } catch (Exception ex) {
            fail(ex.toString());
        }
        // Verify that these methods were called on View
        verify(view).getBackupFile();
        verify(view).setBackupFile(any(File.class));
        verify(view).write(any(File.class));
    }
}
