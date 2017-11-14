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
public class CollaborationConnectAction extends AbstractApplicationAction {

    public final static String ID = "collaboration.connect";
    private Application app;

    public CollaborationConnectAction(Application app) {
        super(app);
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        labels.configureAction(this, ID);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        app = getApplication();
        showInputDialog("Type the IP of the server:");
    }
    
    private void showInputDialog(String message) {
        
        String inputText = JOptionPane.showInputDialog(app.getComponent(), message);
        if (inputText != null) {
            verifyIP(inputText);
            
        }
    }
    
    private void verifyIP(String IP) {
        if(IP.length() >= 7) {
            // TODO: Implement Server connection
            JOptionPane.showMessageDialog(app.getComponent(), "Connected to server!");
        } else {
            showInputDialog("Wrong IP, try again:");
        }
    }

}
