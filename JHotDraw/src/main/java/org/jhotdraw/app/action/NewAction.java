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
import java.awt.event.*;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.JHotDrawFeatures;
import org.jhotdraw.app.View;
import org.jhotdraw.services.ActionSPI;
import org.jhotdraw.services.ApplicationSPI;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

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
@ServiceProvider(service = ActionSPI.class)
public class NewAction extends AbstractApplicationAction implements ActionSPI{
    public final static String ID = "file.new";
    
    /** Creates a new instance. */
    public NewAction() {
        super(Lookup.getDefault().lookup(ApplicationSPI.class).getApplicationInstance());
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        labels.configureAction(this, ID);
    }
    
    @FeatureEntryPoint(JHotDrawFeatures.MANAGE_DRAWINGS)
    public void actionPerformed(ActionEvent evt) {
        Application app = getApplication();
        final View newP = app.createView();
        int multiOpenId = 1;
        for (View existingP : app.views()) {
            if (existingP.getFile() == null) {
                multiOpenId = Math.max(multiOpenId, existingP.getMultipleOpenId() + 1);
            }
        }
        newP.setMultipleOpenId(multiOpenId);
        app.add(newP);
        newP.execute(new Runnable() {
            public void run() {
                newP.clear();
            }
        });
        app.show(newP);
    }
}