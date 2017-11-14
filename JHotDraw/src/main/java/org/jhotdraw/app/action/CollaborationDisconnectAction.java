/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.jhotdraw.app.Application;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author niclasmolby
 */
public class CollaborationDisconnectAction extends AbstractApplicationAction {

    public final static String ID = "collaboration.disConnect";
    private Application app;

    public CollaborationDisconnectAction(Application app) {
        super(app);
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        labels.configureAction(this, ID);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        app = getApplication();
    }
    
    

}
