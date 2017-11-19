package org.jhotdraw.app.action;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import org.jhotdraw.util.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JOptionPane;
import org.jhotdraw.app.*;

/**
 *
 * @author Niels
 */
public class CollaborationStartServerAction extends AbstractApplicationAction {

    public final static String ID = "collaboration.start";

    private PropertyChangeListener applicationListener;
    private Application app;
    private ExecutorService single;

    public CollaborationStartServerAction(Application app) {
        super(app);
        this.app = app;
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        labels.configureAction(this, ID);
        single = Executors.newSingleThreadExecutor();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        single.execute(() -> {
            if (shouldStartServer()) {
                startServer();
                String ip = getPublicIp();
                if (shouldCopyIpToClipboard(ip)) {
                    copyIpToClipboard(ip);
                }
            }
        });
    }

    private boolean shouldStartServer() {
        return JOptionPane.showConfirmDialog(app.getComponent(),
                "Are you sure want to start being a server, "
                + "\nallowing other people to connect to you?",
                "Collaboration", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    private boolean shouldCopyIpToClipboard(String ip) {
        return JOptionPane.showConfirmDialog(app.getComponent(),
                "Server started."
                + "\n\nYour IP is " + ip
                + "\nDo you want to copy it to your clipboard?",
                "Collaboration", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    private void copyIpToClipboard(String ip) {
        StringSelection selection = new StringSelection(ip);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
    }

    private void startServer() {
        setEnabled(false);
        app.startServer();
    }

    private String getPublicIp() {
        String ip = "";
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                new URL("http://checkip.amazonaws.com").openStream()))) {
            ip = in.readLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return ip;
    }

    private String getPrivateIp() {
        String ip = "";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }

    private PropertyChangeListener createApplicationListener() {
        return (PropertyChangeEvent evt) -> {
            if (evt.getPropertyName() == "stopServer") {
                setEnabled(true);
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
