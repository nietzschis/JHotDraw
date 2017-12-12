package org.jhotdraw.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.View;
import org.jhotdraw.gui.Worker;

/**
 * Used for auto saving all files in Application to a specified location,
 * at a specific interval.
 * @author Nick
 */
public class FileBackupSaver {

    private final DateFormat dateFormat = new SimpleDateFormat("yyMMdd_HHmmss");

    private ScheduledExecutorService executorService;
    private final Application application;
    private final int interval;
    private final String backupLocation;

    /**
     * Creates new FileBackupSaver. Will save backup files found 
     * in each View contained in specified Application, every interval.
     * Need to be started by calling start().
     * @param application
     * @param interval in seconds.
     * @param backupLocation default location of saved backup files. Used
     * whenever a new backup file is created.
     */
    public FileBackupSaver(Application application, int interval, String backupLocation) {
        this.application = application;
        this.interval = interval;
        this.backupLocation = backupLocation;
    }

    /**
     * Starts this FileBackupSaver such that all open files will be saved 
     * repeatedly every time interval has elapsed. If already started, will be 
     * stopped and started again. Elapsed time will start from 0.
     */
    public void start() {
        startExecutorService();
    }
    
    /**
     * Stops this FileBackupSaver, such that no files will be saved. Elapsed
     * time since last file-save will be forgotten.
     */
    public void stop() {
        if (executorService != null) {
            executorService.shutdown();
            executorService = null;
        }
    }
    
    private void startExecutorService() {
        stop();
        executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(
                () -> saveFiles(), interval, interval, TimeUnit.SECONDS);
    }
    
    /**
     * Saves backup file for each View contained in Application. If a backup
     * file has not been set for a View, a new backup file will be created.
     * This method is called automatically internally at every interval, but 
     * can be be used manually if needed.
     */
    public synchronized void saveFiles() {
        if (application != null) {
            for (View view : application.views()) {
                File backupFile = view.getBackupFile();
                if (backupFile == null) {
                    backupFile = createBackupFile(view.getFile());
                    view.setBackupFile(backupFile);
                    application.addRecentFile(backupFile);
                }
                saveFile(view, backupFile);
            }
        }
    }

    /**
     * Attempts to write specified File by calling write(File) on specified 
     * View.
     * @param view
     * @param file 
     */
    private void saveFile(final View view, final File file) {
        view.execute(new Worker() {
            public Object construct() {
                try {
                    view.setEnabled(false);
                    view.write(file);
                    return null;
                } catch (IOException e) {
                    return e;
                }
            }
            public void finished(Object value) {
                view.setEnabled(true);
            }
        });
    }

    /**
     * Creates a new file object based on specified backup folder location. 
     * The file's name will contain the current time and the name of the
     * original file, unless {@code originalFile} is null.
     * @param originalFile
     * @return 
     */
    private File createBackupFile(File originalFile) {
        StringBuilder backupPath = new StringBuilder();
        backupPath.append(backupLocation).append(File.separator);
        backupPath.append(dateFormat.format(new Date()));
        if (originalFile != null) {
            backupPath.append(originalFile.getName());
        }
        backupPath.append(".backup");
        File backupFile = resolveFileName(new File(backupPath.toString()));
        return backupFile;
    }

    /**
     * Resolves a filename so that the name will be unique, by appending a 
     * number enclosed in parentheses to the end of the filename.
     * @param file
     * @return a file which when written to, will not overwrite a previous
     * existing file.
     */
    private File resolveFileName(File file) {
        String path = file.getAbsolutePath();
        String resolvedPath = path;
        int extIndex = path.lastIndexOf(".");
        if (extIndex < 0) {
            extIndex = path.length() - 1;
        }
        for (int i = 1; new File(resolvedPath).exists(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(path.substring(0, extIndex));
            sb.append("(").append(i).append(")");
            sb.append(path.substring(extIndex));
            resolvedPath = sb.toString();
        }
        return new File(resolvedPath);
    }
}
