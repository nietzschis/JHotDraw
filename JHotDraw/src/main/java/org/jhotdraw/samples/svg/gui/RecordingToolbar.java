package org.jhotdraw.samples.svg.gui;

import javax.swing.JComponent;
import org.jhotdraw.util.ResourceBundleUtil;

public class RecordingToolbar extends AbstractToolBar {
    public RecordingToolbar() {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        setName(labels.getString("recording.toolbar"));
    }
    
    @Override
    protected JComponent createDisclosedComponent(int state) {
        System.out.println("WORKAAWRAWRAWRAWRAWRAWR -> " + state);

        return null;
    }
    
    @Override
    protected int getDefaultDisclosureState() {
        return 1;
    }
    
    @Override
    protected String getID() {
        return "recording";
    }
}
