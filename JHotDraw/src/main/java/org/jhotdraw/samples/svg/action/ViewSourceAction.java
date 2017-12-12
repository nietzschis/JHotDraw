/*
 * @(#)ViewSourceAction.java  1.1  2009-04-10
 *
 * Copyright (c) 2007-2009 by the original authors of JHotDraw
 * and all its contributors.
 * All rights reserved.
 *
 * The copyright of this software is owned by the authors and  
 * contributors of the JHotDraw project ("the copyright holders").  
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * the copyright holders. For details see accompanying license terms. 
 */
package org.jhotdraw.samples.svg.action;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.prefs.Preferences;
import org.jhotdraw.app.*;
import org.jhotdraw.app.action.*;
import javax.swing.*;
import org.jhotdraw.samples.svg.*;
import org.jhotdraw.samples.svg.io.*;
import org.jhotdraw.util.ResourceBundleUtil;
import org.jhotdraw.util.prefs.PreferencesUtil;

/**
 * ViewSourceAction.
 *
 * @author Werner Randelshofer
 * @version 1.1 2009-04-10 Reuse dialog window.
 * <br>1.0 19. Mai 2007 Created.
 */
public class ViewSourceAction extends AbstractViewAction {

    public final static String ID = "view.viewSource";
    /**
     * We store the dialog as a client property in the view.
     */
    private final static String DIALOG_CLIENT_PROPERTY = "view.viewSource.dialog";

    /** Creates a new instance. */
    public ViewSourceAction(Application app) {
        super(app);
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        labels.configureAction(this, ID);
    }

    @FeatureEntryPoint(JHotDrawFeatures.VIEW_SOURCE)
    public void actionPerformed(ActionEvent e) {
        try {
            SVGView p = (SVGView) getActiveView();
            ViewSourceWindow viewSourceWindow = new ViewSourceWindow(p);
            
            JDialog dialog = viewSourceWindow.getDialog();

            Preferences prefs = Preferences.userNodeForPackage(getClass());
            PreferencesUtil.installFramePrefsHandler(prefs, "viewSource", dialog);

            dialog.addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosed(WindowEvent evt) {
                    getApplication().removeWindow(dialog);
                    p.putClientProperty(DIALOG_CLIENT_PROPERTY, null);
                }
            });

            getApplication().addWindow(dialog, getActiveView());
            dialog.setVisible(true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
