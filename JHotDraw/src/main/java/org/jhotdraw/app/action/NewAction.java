/*
 * @(#)NewAction.java  1.3  2007-11-30
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

package org.jhotdraw.app.action;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import org.jhotdraw.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.JHotDrawFeatures;
import org.jhotdraw.app.View;
import org.jhotdraw.gui.JSheet;
import org.jhotdraw.gui.Worker;

/**
 * Creates a new view.
 *
 * @author Werner Randelshofer
 * @version 1.3 2007-11-30 Call method clear on a worker thread. 
 * <br>1.2 2006-02-22 Support for multiple open id added.
 * <br>1.1.1 2005-07-14 Make view explicitly visible after creating it.
 * <br>1.1 2005-07-09 Place new view relative to current one.
 * <br>1.0  04 January 2005  Created.
 */
public class NewAction extends AbstractApplicationAction {
    public final static String ID = "file.new";
    
    /** Creates a new instance. */
    public NewAction(Application app) {
        super(app);
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        labels.configureAction(this, ID);
    }
    
    protected JFileChooser getFileChooser(View view) {
        return view.getOpenChooser();
    }

    public void actionPerformed(ActionEvent evt) {
        final Application app = getApplication();
        if (app.isEnabled()) {
            app.setEnabled(false);
            // Search for an empty view
            View emptyView = app.getActiveView();
            if (emptyView == null ||
                    emptyView.getFile() != null ||
                    emptyView.hasUnsavedChanges()) {
                emptyView = null;
            }

            final View view;
            boolean removeMe;
            if (emptyView == null) {
                view = app.createView();
                app.add(view);
                removeMe = true;
            } else {
                view = emptyView;
                removeMe = false;
            }
            JFileChooser fileChooser = getFileChooser(view);
            
            for (View existingP : app.views()) {
                if (existingP.getFile() != null) {
                    fileChooser.setSelectedFile(existingP.getFile());
                    fileChooser.approveSelection();
                }
            }
            
            if (fileChooser.getSelectedFile() != null) {
                app.show(view);
                System.out.println("Calling openFile");
                openFile(fileChooser, view);
            } else {
                if (removeMe) {
                    System.out.println("Calling remove(view)");
                    app.remove(view);
                }
                app.setEnabled(true);
            }
        }
    }

    protected void openFile(JFileChooser fileChooser, final View view) {
        final Application app = getApplication();
        final File file = fileChooser.getSelectedFile();
        app.setEnabled(true);
        view.setEnabled(false);

        // If there is another view with we set the multiple open
        // id of our view to max(multiple open id) + 1.
        int multipleOpenId = 1;
        for (View aView : app.views()) {
            if (aView != view &&
                    aView.getFile() != null &&
                    aView.getFile().equals(file)) {
                multipleOpenId = Math.max(multipleOpenId, aView.getMultipleOpenId() + 1);
            }
        }
        view.setMultipleOpenId(multipleOpenId);
        view.setEnabled(false);

        // Open the file
        view.execute(new Worker() {

            public Object construct() {
                try {
                    if (file.exists()) {
                        view.read(file);
                        return null;
                    } else {
                        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
                        return new IOException(labels.getFormatted("file.open.fileDoesNotExist.message", file.getName()));
                    }
                } catch (Throwable e) {
                    return e;
                }
            }

            public void finished(Object value) {
                fileOpened(view, file, value);
            }
        });
    }

    protected void fileOpened(final View view, File file, Object value) {
        final Application app = getApplication();
        if (value == null) {
            view.setFile(file);
            view.setEnabled(true);
            Frame w = (Frame) SwingUtilities.getWindowAncestor(view.getComponent());
            if (w != null) {
                w.setExtendedState(w.getExtendedState() & ~Frame.ICONIFIED);
                w.toFront();
            }
            view.getComponent().requestFocus();
            app.addRecentFile(file);
            app.setEnabled(true);
        } else {
            view.setEnabled(true);
            app.setEnabled(true);
            String message;
            if ((value instanceof Throwable) && ((Throwable) value).getMessage() != null) {
                message = ((Throwable) value).getMessage();
                ((Throwable) value).printStackTrace();
            } else if ((value instanceof Throwable)) {
                message = value.toString();
                ((Throwable) value).printStackTrace();
            } else {
                message = value.toString();
            }
            ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
            JSheet.showMessageSheet(view.getComponent(),
                    "<html>" + UIManager.getString("OptionPane.css") +
                    "<b>" + labels.getFormatted("file.open.couldntOpen.message", file.getName()) + "</b><br>" +
                    ((message == null) ? "" : message),
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
// Original ---
//    @FeatureEntryPoint(JHotDrawFeatures.MANAGE_DRAWINGS)
//    public void actionPerformed(ActionEvent evt) {
//        Application app = getApplication();
//        final View newP = app.createView();
//        int multiOpenId = 1;
//        for (View existingP : app.views()) {
//            if (existingP.getFile() == null) {
//                multiOpenId = Math.max(multiOpenId, existingP.getMultipleOpenId() + 1);
//            }
//        }
//        newP.setMultipleOpenId(multiOpenId);
//        app.add(newP);
//        newP.execute(new Runnable() {
//            public void run() {
//                newP.clear();
//            }
//        });
//        app.show(newP);
//    }
    
    
    //New try:
//    @FeatureEntryPoint(JHotDrawFeatures.MANAGE_DRAWINGS)
//    public void actionPerformed(ActionEvent evt) {
//    Application app = getApplication();
//        final View newP = app.createView();
//        int multiOpenId = 1;
//        
//        int i = 0;
//        for (View existingP : app.views()) {
//            if (existingP.getFile() == null) {
//                multiOpenId = Math.max(multiOpenId, existingP.getMultipleOpenId() + 1);
//                // if (i == 0) {
//                    try {
//                        System.out.println("try-read");
//                        newP.read(existingP.getFile());
//                        newP.setFile(existingP.getFile());
//                    } catch (IOException ex) {
//                        Logger.getLogger(NewAction.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                // }
//            }
//            i++;
//        }
//        newP.setMultipleOpenId(multiOpenId);
//        app.add(newP);
//        newP.execute(new Runnable() {
//            public void run() {
//                // newP.clear();
//            }
//        });
//        app.show(newP);
//    }
}
