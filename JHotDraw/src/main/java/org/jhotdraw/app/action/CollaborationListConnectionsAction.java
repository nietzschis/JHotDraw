package org.jhotdraw.app.action;

import org.jhotdraw.util.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.*;
import org.jhotdraw.app.*;
import org.jhotdraw.collaboration.server.RemoteObservable;

/**
 *
 * @author Niels
 */

public class CollaborationListConnectionsAction extends AbstractApplicationAction{

    public final static String ID = "collaboration.list";

    private PropertyChangeListener applicationListener;
    private Application app;
    private ExecutorService single;

    public CollaborationListConnectionsAction(Application app) {
        super(app);
        this.app = app;
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        labels.configureAction(this, ID);
        single = Executors.newSingleThreadExecutor();
        setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        single.execute(() -> {
            try {
                JOptionPane.showMessageDialog(app.getComponent(),
                        "All currently connected clients:"
                        + "\n\n" + ((RemoteObservable) RemoteObservable.getInstance()).getCollaboratorNames(),
                        "Collaboration", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
        });
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
