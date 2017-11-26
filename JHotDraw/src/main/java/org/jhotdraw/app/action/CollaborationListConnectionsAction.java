package org.jhotdraw.app.action;

import org.jhotdraw.util.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
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
            StringBuilder names = new StringBuilder();
            ((RemoteObservable) RemoteObservable.getInstance()).getCollaboratorNames().forEach(name -> names.append(name).append("\n"));
            JOptionPane.showMessageDialog(app.getComponent(),
                    "All currently connected clients:"
                    + "\n\n" + names.toString(),
                    "Collaboration", JOptionPane.INFORMATION_MESSAGE);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
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
