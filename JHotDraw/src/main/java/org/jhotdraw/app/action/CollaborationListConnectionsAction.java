package org.jhotdraw.app.action;

import org.jhotdraw.util.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import org.jhotdraw.app.*;
import org.jhotdraw.collaboration.server.RemoteObservable;

/**
 *
 * @author Niels
 */
public class CollaborationListConnectionsAction extends AbstractApplicationAction {

    public final static String ID = "collaboration.list";

    private PropertyChangeListener applicationListener;
    private Application app;

    public CollaborationListConnectionsAction(Application app) {
        super(app);
        this.app = app;
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        labels.configureAction(this, ID);
        setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        try {
            ((RemoteObservable) RemoteObservable.getInstance()).getCollaboratorNames().forEach(name -> System.out.println(name));
            
            /*if (shouldStopServer()) {
            try {
            app.stopServer();
            }
            catch (RemoteException | NotBoundException e) {
            JOptionPane.showMessageDialog(app.getComponent(),
            "Error shutting down server."
            + "\n\n" + e,
            "Collaboration error", JOptionPane.ERROR_MESSAGE);
            }
            }*/
        }
        catch (RemoteException ex) {
            Logger.getLogger(CollaborationListConnectionsAction.class.getName()).log(Level.SEVERE, null, ex);
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
            if (evt.getPropertyName() == "startServer") {
                setEnabled(true);
            }
            if (evt.getPropertyName() == "stopServer") {
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
