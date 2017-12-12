
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.JHotDrawFeatures;
import org.jhotdraw.app.View;
import org.jhotdraw.gui.JSheet;
import org.jhotdraw.gui.Worker;
import org.jhotdraw.services.ActionSPI;
import org.jhotdraw.services.ApplicationSPI;
import org.jhotdraw.util.ResourceBundleUtil;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author ville
 */
@ServiceProvider(service = ActionSPI.class)
public class DuplicateCanvasAction extends AbstractApplicationAction implements ActionSPI{

    public final static String ID = "window.duplicate";
    
    /** Creates a new instance. */
    public DuplicateCanvasAction() {
        super(Lookup.getDefault().lookup(ApplicationSPI.class).getApplicationInstance());
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
                openFile(fileChooser, view);
            } else {
                if (removeMe) {
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
}