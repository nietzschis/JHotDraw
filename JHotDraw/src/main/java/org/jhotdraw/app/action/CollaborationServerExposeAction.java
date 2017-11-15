package org.jhotdraw.app.action;

import org.jhotdraw.util.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import org.jhotdraw.app.*;

/**
 *
 * @author Niels Heltner
 */
public class CollaborationServerExposeAction extends AbstractApplicationAction {

    public final static String ID = "collaboration.expose";
    
    private PropertyChangeListener applicationListener;
    
    public CollaborationServerExposeAction(Application app) {
        super(app);
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        labels.configureAction(this, ID);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        Application app = getApplication();
        int answer = JOptionPane.showConfirmDialog(app.getComponent(),
                "Are you sure want to expose yourself as a server, " + 
                "\nallowing other people to connect to you?"
                + "\n\nYour IP is " + getIP(),
                "Collaboration", JOptionPane.YES_NO_OPTION);
        if(answer == JOptionPane.YES_OPTION) {
            System.out.println("Pressed yes");
            setEnabled(false);
            app.exposeServer();
        }
    }
    
    private String getIP() {
        return "192.168.101.202";
    }
    
    @Override
    protected void installApplicationListeners(Application app) {
        super.installApplicationListeners(app);
        if (applicationListener == null) {
            applicationListener = createApplicationListener();
        }
        app.addPropertyChangeListener(applicationListener);
    }
    
    private PropertyChangeListener createApplicationListener() {
        return new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName() == "closeServer") { // Strings get interned
                    setEnabled(true);
                }
            }
        };
    }
    
    /**
     * Installs listeners on the application object.
     */
    @Override
    protected void uninstallApplicationListeners(Application app) {
        super.uninstallApplicationListeners(app);
        app.removePropertyChangeListener(applicationListener);
    }
}
