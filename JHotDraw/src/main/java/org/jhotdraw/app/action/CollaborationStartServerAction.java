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
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.jhotdraw.app.*;
import org.jhotdraw.collaboration.client.CollaborationConnection;
import org.jhotdraw.samples.svg.SVGView;

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
        if (shouldStartServer()) {
            JDialog startingServerDialog = createMessage("Starting server...", JOptionPane.PLAIN_MESSAGE, false);
            single.execute(() -> {
                try {
                    app.startServer();
                    CollaborationConnection.getInstance().setDrawing(((SVGView) app.getActiveView()).getDrawing());
                    String ip = getPrivateIp();
                    startingServerDialog.dispose();
                    if (shouldCopyIpToClipboard(ip)) {
                        copyIpToClipboard(ip);
                    }
                }
                catch (RemoteException | AlreadyBoundException e) {
                    startingServerDialog.dispose();
                    createMessage("Error starting server."
                            + "\n\n" + e, JOptionPane.ERROR_MESSAGE, true, "     OK     ");
                }
            });
            startingServerDialog.setVisible(true);
        }
    }

    private boolean shouldStartServer() {
        return showYesNoDialog("Are you sure want to start being a server, "
                + "\nallowing other people to connect to you?") == JOptionPane.YES_OPTION;
    }

    private boolean shouldCopyIpToClipboard(String ip) {
        return showYesNoDialog("Server started."
                + "\n\nYour IP is " + ip
                + "\nDo you want to copy it to your clipboard?") == JOptionPane.YES_OPTION;
    }

    private int showYesNoDialog(String text) {
        return JOptionPane.showConfirmDialog(app.getComponent(),
                text,
                "Collaboration", JOptionPane.YES_NO_OPTION);
    }

    private JDialog createMessage(String text, int messageType, boolean show, String... options) {
        JOptionPane jop = new JOptionPane();
        jop.setMessage(text);
        jop.setMessageType(messageType);
        jop.setOptions(options);
        JDialog dialog = jop.createDialog(app.getComponent(), "Collaboration");
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setVisible(show);
        return dialog;

        /*JOptionPane.showOptionDialog(app.getComponent(), text, "Collaboration",
                JOptionPane.DEFAULT_OPTION, messageType,
                null, options, null);*/
    }

    private void copyIpToClipboard(String ip) {
        StringSelection selection = new StringSelection(ip);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
    }

    private String getPublicIp() {
        String ip = "";
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                new URL("http://checkip.amazonaws.com").openStream()))) {
            ip = in.readLine();
        }
        catch (IOException e) {
            ip = "[unable to get IP]";
        }
        return ip;
    }

    private String getPrivateIp() {
        String ip = "";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException e) {
            System.err.println("Unable to get private ip: " + e);
        }
        return ip;
    }

    private PropertyChangeListener createApplicationListener() {
        return (PropertyChangeEvent evt) -> {
            if (evt.getPropertyName().equals("stopServer")) {
                setEnabled(true);
            }
            if (evt.getPropertyName().equals("startServer")) {
                setEnabled(false);
            }

            if (evt.getPropertyName().equals("connect")) {
                setEnabled(false);
            }
            if (evt.getPropertyName().equals("disconnect")) {
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
