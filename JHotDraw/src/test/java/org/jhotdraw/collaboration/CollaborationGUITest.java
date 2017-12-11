package org.jhotdraw.collaboration;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ExportException;
import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.fixture.FrameFixture;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.collaboration.client.CollaborationConnection;
import org.jhotdraw.collaboration.server.CollaborationServer;
import org.jhotdraw.samples.svg.SVGApplicationModel;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Niels & Niels
 */
public class CollaborationGUITest {

    private FrameFixture window;
    private Application app;

    @Before
    public void setUp() throws InterruptedException {
        app = new DefaultSDIApplication();
        SVGApplicationModel model = new SVGApplicationModel();
        model.setViewClassName("org.jhotdraw.samples.svg.SVGView");
        app.setModel(model);
        app.launch(null);
        while (app.getFrame() == null) {
            Thread.sleep(1000);
        }
        window = new FrameFixture(app.getFrame());
    }

    @After
    public void tearDown() {
        try {
            app.stopServer();
        }
        catch (RemoteException | NotBoundException e) {
        }
        app = null;
        window.cleanUp();
    }

    //@Test(expected = ExportException.class)
    public void GUIStartServerTest() throws RemoteException, AlreadyBoundException, InterruptedException, UnsupportedFlavorException, UnknownHostException, IOException {
        assertNotNull(app.getFrame());
        assertNotNull(window);

        window.show();

        window.menuItem("collaboration").click();
        window.menuItem("collaboration.start").click();
        window.optionPane().yesButton().click(); //Start server? popup

        findIPJOptionPane(); //Copy IP? popup

        assertEquals(InetAddress.getLocalHost().getHostAddress(), Toolkit.getDefaultToolkit()
                .getSystemClipboard().getData(DataFlavor.stringFlavor));

        CollaborationServer.getInstance().startServer();
    }

    private void findIPJOptionPane() {
        try {
            window.optionPane().yesButton().click(); //Copy IP? popup
        }
        catch (ComponentLookupException e) {
            if (e.getMessage().contains("Starting server...")) {
                findIPJOptionPane();
            }
        }
    }

    @Test(expected = ExportException.class)
    public void GUIStartServerWhenServerIsAlreadyRunningTest() throws RemoteException, AlreadyBoundException {
        assertNotNull(app.getFrame());
        assertNotNull(window);

        CollaborationServer.getInstance().startServer();
        
        window.show();

        window.menuItem("collaboration").click();
        window.menuItem("collaboration.start").click();
        window.optionPane().yesButton().click(); //Start server? popup

        findErrorJOptionPane();
        
        CollaborationServer.getInstance().startServer();
    }

    private void findErrorJOptionPane() {
        try {
            window.optionPane().button().click(); //Error popup
        }
        catch (ComponentLookupException e) {
            if (e.getMessage().contains("Starting server...")) {
                findErrorJOptionPane();
            }
        }
    }

    //@Test
    public void GUIConnectAndDisconnectToServerTest() throws InterruptedException, AlreadyBoundException, AccessException, RemoteException, UnknownHostException {
        assertNotNull(app.getFrame());

        CollaborationServer.getInstance().startServer();

        window.show();
        assertFalse(CollaborationConnection.getInstance().isConnected());
        assertNull(CollaborationConnection.getInstance().getName());

        window.menuItem("collaboration").click();
        window.menuItem("collaboration.connect").click();
        window.optionPane().textBox().enterText(InetAddress.getLocalHost().getHostAddress());
        window.optionPane().okButton().click();
        window.optionPane().yesButton().click();
        window.optionPane().textBox().enterText("TestUser");
        window.optionPane().okButton().click();
        window.optionPane().okButton().click();

        assertTrue(CollaborationConnection.getInstance().isConnected());
        assertEquals("TestUser", CollaborationConnection.getInstance().getName());

        window.menuItem("collaboration").click();
        window.menuItem("collaboration.disConnect").click();

        assertFalse(CollaborationConnection.getInstance().isConnected());
        assertNull(CollaborationConnection.getInstance().getName());

    }
}
