/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JOptionPane;
import org.jhotdraw.app.Application;
import org.jhotdraw.collaboration.client.CollaborationConnection;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.samples.svg.SVGView;
import org.jhotdraw.util.ResourceBundleUtil;

public class CollaborationConnectAction extends AbstractApplicationAction {

    public final static String ID = "collaboration.connect";
    private Application app;

    public CollaborationConnectAction(Application app) {
        super(app);
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        labels.configureAction(this, ID);
        app.addPropertyChangeListener(createApplicationListener());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        app = getApplication();

        showInputDialog("Type the IP of the server:");
    }

    private boolean showConfirmDialog(String message) {
        return JOptionPane.showConfirmDialog(app.getComponent(), message, "Connect to server", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    private void showInputDialog(String message) {

        String inputText = JOptionPane.showInputDialog(app.getComponent(), message);
        if (inputText != null) {
            if (showConfirmDialog("Are you sure? Your current work will be deleted")) {
                verifyIP(inputText);
            }
        }
    }

    private void verifyIP(String IP) {
        String IPRegExpr = "\\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
                + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
                + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
                + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b";
        if (IP.matches(IPRegExpr));
        if (IP.length() >= 7) {
            // TODO: Implement Server connection
            connectToServer(IP);
            JOptionPane.showMessageDialog(app.getComponent(), "Connected to server! ");
        } else {
            showInputDialog("Wrong IP, try again:");
        }
    }

    private void connectToServer(String IP) {
        if (CollaborationConnection.getInstance().connectToServer(IP)) {

            Drawing drawing = ((SVGView) app.getActiveView()).getDrawing();
            CollaborationConnection.getInstance().setDrawing(drawing);
            app.firePropertyEvent("connect", null, null);
            setEnabled(false);
            
            // Clear own canvas
            if(drawing.getChildCount() > 0) {
                drawing.removeAllChildren();
            }
        }
    }

    private PropertyChangeListener createApplicationListener() {
        return (PropertyChangeEvent evt) -> {
            if (evt.getPropertyName() == "disconnect") {
                setEnabled(true);
            }
            if (evt.getPropertyName() == "startServer") {
                setEnabled(false);
            }
            if (evt.getPropertyName() == "stopServer") {
                setEnabled(true);
            }
        };
    }

}
