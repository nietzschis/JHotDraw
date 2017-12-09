/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import java.awt.Frame;
import java.io.File;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.JHotDrawFeatures;
import org.jhotdraw.app.View;
import org.jhotdraw.gui.JSheet;
import org.jhotdraw.gui.Worker;
import org.jhotdraw.gui.event.SheetEvent;
import org.jhotdraw.gui.event.SheetListener;

/**
 *
 * @author ville
 */
public class DuplicateViewAction extends AbstractSaveBeforeAction {
    public final static String ID = "file.duplicateView";
    private File file;
    
    /** Creates a new instance. */
    public DuplicateViewAction(Application app, File file) {
        super(app);
        this.file = file;
        putValue(Action.NAME, file.getName());
    }

    @FeatureEntryPoint(JHotDrawFeatures.DRAWING_PERSITENCE)
    public void doIt(final View view) {
        final Application app = getApplication();
        app.setEnabled(true);
        
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
        
        // Open the file
        view.execute(new Worker() {
            public Object construct() {
                try {
                    view.read(file);
                    return null;
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
            if (app != null) {
                app.setEnabled(true);
            }
        } else {
            if (value instanceof Throwable) {
                ((Throwable) value).printStackTrace();
            }
            JSheet.showMessageSheet(view.getComponent(),
                    "<html>"+UIManager.getString("OptionPane.css")+
                    "<b>Couldn't open the file \""+file+"\".</b><br>"+
                    value,
                    JOptionPane.ERROR_MESSAGE, new SheetListener() {
                public void optionSelected(SheetEvent evt) {
                    // app.dispose(view);
                }
            }
            );
        }
    }
}
