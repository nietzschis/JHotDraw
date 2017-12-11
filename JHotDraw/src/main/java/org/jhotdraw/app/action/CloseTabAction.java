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
import static org.jhotdraw.app.action.CloseAction.ID;
import org.jhotdraw.samples.svg.SVGView;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author pc4
 */
public class CloseTabAction extends AbstractSaveBeforeAction
{

    public CloseTabAction(Application app)
    {
        super(app);
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        labels.configureAction(this, ID);
    }

    @FeatureEntryPoint(JHotDrawFeatures.MANAGE_DRAWINGS)
    @Override protected void doIt(View view) {
        if (view instanceof SVGView)
        {
            SVGView sv = (SVGView) view;
            sv.getTabs().ClosePressedTab();
            sv.setFile(null);
        }
    }
    
}
