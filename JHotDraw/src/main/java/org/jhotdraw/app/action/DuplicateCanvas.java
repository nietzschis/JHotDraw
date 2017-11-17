/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import java.awt.event.ActionEvent;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.JHotDrawFeatures;
import org.jhotdraw.app.View;

/**
 *
 * @author ville
 */
public class DuplicateCanvas extends AbstractApplicationAction {

    public DuplicateCanvas(Application app) {
        super(app);
    }

    @Override
    @FeatureEntryPoint(JHotDrawFeatures.MANAGE_DRAWINGS)
    public void actionPerformed(ActionEvent e) {
        Application app = getApplication();
        final View newP = app.createView();
        int multiOpenId = 1;
        
        int i = 0;
        for (View existingP : app.views()) {
            if (existingP.getFile() == null) {
                multiOpenId = Math.max(multiOpenId, existingP.getMultipleOpenId() + 1);
                if (i == 0) {
                    newP.setFile(existingP.getFile());
                }
            }
            i++;
        }
        newP.setMultipleOpenId(multiOpenId);
        app.add(newP);
        newP.execute(new Runnable() {
            public void run() {
                // newP.clear();
            }
        });
        app.show(newP);
    }
    
}
