/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import org.jhotdraw.app.Application;
import static org.jhotdraw.app.action.AboutAction.ID;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author niclasmolby
 */
public class CollaborationConnectAction extends AbstractApplicationAction {
    public final static String ID = "collaboration.connect";

    public CollaborationConnectAction(Application app) {
        super(app);
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        labels.configureAction(this, ID);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Application app = getApplication();
        JOptionPane.showMessageDialog(app.getComponent(),
                "TODO: Put text felt til ip herind");
    }
    
}
