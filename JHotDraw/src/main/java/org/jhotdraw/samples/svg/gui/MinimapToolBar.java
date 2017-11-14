/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.gui;

import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author Jakob Andersen
 */
public class MinimapToolBar extends AbstractToolBar {
    /** Creates new instance. */
    public MinimapToolBar() {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        setName(labels.getString(getID() + ".toolbar"));
        setDisclosureStateCount(2);
    }
    
    @Override
    protected String getID() {
        return "minimap";
    }
    
    
}
