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
public class CollaborationServerCloseAction extends AbstractApplicationAction {

    public final static String ID = "collaboration.close";
    
    private PropertyChangeListener applicationListener;
    
    public CollaborationServerCloseAction(Application app) {
        super(app);
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        labels.configureAction(this, ID);
        setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        Application app = getApplication();
        int answer = JOptionPane.showConfirmDialog(app.getComponent(),
                "If you close yourself down as a server, people currently" + 
                "\nconnected to you will get disconnected." + 
                "\n\nAre you sure?",
                "Collaboration", JOptionPane.YES_NO_OPTION);
        if(answer == JOptionPane.YES_OPTION) {
            System.out.println("Pressed yes");
            setEnabled(false);
            app.closeServer();
        }
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
                if (evt.getPropertyName() == "exposeServer") { // Strings get interned
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
