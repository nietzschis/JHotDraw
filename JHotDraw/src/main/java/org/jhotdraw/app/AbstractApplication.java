/*
 * @(#)AbstractApplication.java  1.3  2007-12-24
 *
 * Copyright (c) 1996-2007 by the original authors of JHotDraw
 * and all its contributors.
 * All rights reserved.
 *
 * The copyright of this software is owned by the authors and  
 * contributors of the JHotDraw project ("the copyright holders").  
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * the copyright holders. For details see accompanying license terms. 
 */
package org.jhotdraw.app;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import java.awt.*;
import org.jhotdraw.beans.*;
import org.jhotdraw.gui.Worker;
import org.jhotdraw.util.*;
import java.util.*;
import java.util.prefs.*;
import javax.swing.*;
import java.io.*;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import org.jhotdraw.collaboration.client.CollaborationConnection;
import org.jhotdraw.collaboration.server.CollaborationServer;
import org.jhotdraw.collaboration.server.RemoteObservable;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.samples.svg.SVGView;

/**
 * AbstractApplication.
 *
 *
 * @author Werner Randelshofer
 * @version 1.3 2007-12-24 Added support for active view.
 * <br>1.2 2007-11-25 Method View.clear is now invoked on a worker thread.
 * <br>1.1 2006-05-01 System.exit(0) explicitly in method stop().
 * <br>1.0 October 4, 2005 Created.
 */
public abstract class AbstractApplication extends AbstractBean implements Application {

    private LinkedList<View> views = new LinkedList<View>();
    private Collection<View> unmodifiableDocuments;
    private boolean isEnabled = true;
    protected ResourceBundleUtil labels;
    private ApplicationModel model;
    private LinkedList<File> recentFiles = new LinkedList<File>();
    private final static int maxRecentFilesCount = 10;
    private Preferences prefs;
    private View activeView;
    public final static String VIEW_COUNT_PROPERTY = "viewCount";
    protected final static int DEFAULT_BACKUP_INTERVAL = 300;
    protected final static String DEFAULT_BACKUP_LOCATION = System.getProperty("user.home");

    /**
     * Creates a new instance.
     */
    public AbstractApplication() {
    }

    public void init() {
        prefs = Preferences.userNodeForPackage((getModel() == null) ? getClass() : getModel().getClass());

        int count = prefs.getInt("recentFileCount", 0);
        for (int i = 0; i < count; i++) {
            String path = prefs.get("recentFile." + i, null);
            if (path != null) {
                recentFiles.add(new File(path));
            }
        }

        if (model != null) {
            model.initApplication(this);
        }
    }

    public void start() {
        final View p = createView();
        add(p);
        p.setEnabled(false);
        show(p);
        p.execute(new Worker() {

            public Object construct() {
                p.clear();
                return null;
            }

            public void finished(Object result) {
                p.setEnabled(true);
            }
        });
    }
    
    @FeatureEntryPoint(JHotDrawFeatures.MANAGE_DRAWINGS)
    public final View createView() {
        View p = basicCreateView();
        p.init();
        if (getModel() != null) {
            getModel().initView(this, p);
        }
        initViewActions(p);
        return p;
    }

    public void setModel(ApplicationModel newValue) {
        ApplicationModel oldValue = model;
        model = newValue;
        firePropertyChange("model", oldValue, newValue);
    }

    public ApplicationModel getModel() {
        return model;
    }

    protected View basicCreateView() {
        return model.createView();
    }

    /**
     * Sets the active view. Calls deactivate on the previously active view, and
     * then calls activate on the given view.
     *
     * @param newValue Active view, can be null.
     */
    public void setActiveView(View newValue) {
        View oldValue = activeView;
        if (activeView != null) {
            activeView.deactivate();
        }
        activeView = newValue;
        if (activeView != null) {
            activeView.activate();
        }
        firePropertyChange(ACTIVE_VIEW_PROPERTY, oldValue, newValue);
    }

    /**
     * Gets the active view.
     *
     * @return The active view, can be null.
     */
    public View getActiveView() {
        if (activeView == null && views.size() > 0) {
            return views.getLast();
        }
        return activeView;
    }

    public String getName() {
        return model.getName();
    }

    public String getVersion() {
        return model.getVersion();
    }

    public String getCopyright() {
        return model.getCopyright();
    }

    protected abstract void initViewActions(View p);

    @FeatureEntryPoint(JHotDrawFeatures.MANAGE_DRAWINGS)
    public void stop() {
        for (View p : new LinkedList<View>(views())) {
            dispose(p);
        }
        System.exit(0);
    }

    public void remove(View p) {
        hide(p);
        int oldCount = views.size();
        views.remove(p);
        p.setApplication(null);
        firePropertyChange(VIEW_COUNT_PROPERTY, oldCount, views.size());
    }

    public void add(View p) {
        if (p.getApplication() != this) {
            int oldCount = views.size();
            views.add(p);
            p.setApplication(this);
            firePropertyChange(VIEW_COUNT_PROPERTY, oldCount, views.size());
        }
    }

    public void dispose(View p) {
        remove(p);
        p.dispose();
    }

    public Collection<View> views() {
        if (unmodifiableDocuments == null) {
            unmodifiableDocuments = Collections.unmodifiableCollection(views);
        }
        return unmodifiableDocuments;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean newValue) {
        boolean oldValue = isEnabled;
        isEnabled = newValue;
        firePropertyChange("enabled", oldValue, newValue);
    }

    public Container createContainer() {
        return new JFrame();
    }

    public void launch(String[] args) {
        configure(args);
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                init();
                start();
            }
        });
    }

    protected void initLabels() {
        labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
    }

    public void configure(String[] args) {
    }

    public void startServer() throws RemoteException, AlreadyBoundException {
        CollaborationServer.getInstance().startServer();
        CollaborationConnection.getInstance().setCollaborationProxy(RemoteObservable.getInstance());
        CollaborationConnection.getInstance().setDrawing(((SVGView) getActiveView()).getDrawing());
        RemoteObservable.getInstance().addCollaborator(CollaborationConnection.getInstance());
        firePropertyChange("startServer", null, null);
    }

    public void stopServer() throws RemoteException, NotBoundException {
        CollaborationServer.getInstance().stopServer();
        firePropertyChange("stopServer", null, null);
    }

    public boolean connectToServer(String IP) {
        if (CollaborationConnection.getInstance().connectToServer(IP)) {

            Drawing drawing = ((SVGView) getActiveView()).getDrawing();
            CollaborationConnection.getInstance().setDrawing(drawing);
            firePropertyChange("connect", null, null);

            // Clear own canvas
            if (drawing.getChildCount() > 0) {
                drawing.removeAllChildren();
            }
            return true;
        }
        return false;
    }
    
    public void disconnectFromServer() {
        CollaborationConnection.getInstance().disconnectFromServer();
        firePropertyChange("disconnect", null, null);
    }

    public java.util.List<File> recentFiles() {
        return Collections.unmodifiableList(recentFiles);
    }

    public void clearRecentFiles() {
        @SuppressWarnings("unchecked")
        java.util.List<File> oldValue = (java.util.List<File>) recentFiles.clone();
        recentFiles.clear();
        prefs.putInt("recentFileCount", recentFiles.size());
        firePropertyChange("recentFiles",
                Collections.unmodifiableList(oldValue),
                Collections.unmodifiableList(recentFiles));
    }

    public void addRecentFile(File file) {
        @SuppressWarnings("unchecked")
        java.util.List<File> oldValue = (java.util.List<File>) recentFiles.clone();
        if (recentFiles.contains(file)) {
            recentFiles.remove(file);
        }
        recentFiles.addFirst(file);
        if (recentFiles.size() > maxRecentFilesCount) {
            recentFiles.removeLast();
        }

        prefs.putInt("recentFileCount", recentFiles.size());
        int i = 0;
        for (File f : recentFiles) {
            prefs.put("recentFile." + i, f.getPath());
            i++;
        }

        firePropertyChange("recentFiles", oldValue, 0);
        firePropertyChange("recentFiles",
                Collections.unmodifiableList(oldValue),
                Collections.unmodifiableList(recentFiles));
    }

    public void removePalette(Window palette) {
    }

    public void addPalette(Window palette) {
    }

    public void removeWindow(Window window) {
    }

    public void addWindow(Window window, View p) {
    }
}
