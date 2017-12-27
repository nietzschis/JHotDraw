package org.jhotdraw.app.action;

import org.jhotdraw.util.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.*;
import org.jhotdraw.app.*;
import org.jhotdraw.services.ApplicationSPI;
import org.openide.util.Lookup;

/**
 *
 * @author Niels
 */

public class CollaborationStopServerAction extends AbstractApplicationAction{

    public final static String ID = "collaboration.stop";

    private PropertyChangeListener applicationListener;
    private Application app;

    public CollaborationStopServerAction(Application app) {
        super(app);
        this.app = Lookup.getDefault().lookup(ApplicationSPI.class).getApplicationInstance();
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        labels.configureAction(this, ID);
        setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (shouldStopServer()) {
            try {
                app.stopServer();
            }
            catch (RemoteException | NotBoundException e) {
                JOptionPane.showMessageDialog(app.getComponent(),
                        "Error shutting down server."
                        + "\n\n" + e,
                        "Collaboration error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean shouldStopServer() {
        return JOptionPane.showConfirmDialog(app.getComponent(),
                "If you stop being a server, people currently"
                + "\nconnected to you will get disconnected."
                + "\n\nAre you sure?",
                "Collaboration", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    private PropertyChangeListener createApplicationListener() {
        return (PropertyChangeEvent evt) -> {
            if (evt.getPropertyName().equals("startServer")) {
                setEnabled(true);
            }
            if (evt.getPropertyName().equals("stopServer")) {
                setEnabled(false);
            }
        };
    }

    @Override
    protected void installApplicationListeners(Application app) {
        super.installApplicationListeners(app);
        if (applicationListener == null) {
            applicationListener = createApplicationListener();
        }
        app.addPropertyChangeListener(applicationListener);
    }

    @Override
    protected void uninstallApplicationListeners(Application app) {
        super.uninstallApplicationListeners(app);
        app.removePropertyChangeListener(applicationListener);
    }
}
