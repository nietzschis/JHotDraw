package org.jhotdraw.collaboration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;
import org.assertj.swing.fixture.FrameFixture;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.collaboration.client.CollaborationConnection;
import org.jhotdraw.collaboration.common.CollaborationConfig;
import org.jhotdraw.collaboration.server.RemoteObservable;
import org.jhotdraw.samples.svg.SVGApplicationModel;
import org.junit.After;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
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
        System.out.println("hwedjsiak");
        app = new DefaultSDIApplication();
        SVGApplicationModel model = new SVGApplicationModel();
        model.setViewClassName("org.jhotdraw.samples.svg.SVGView");
        app.setModel(model);
        app.launch(null);
        Thread.sleep(5000);
        window = new FrameFixture(app.getFrame());
    }

    @BeforeClass
    public static void setUpClass() {
        //FailOnThreadViolationRepaintManager.install();
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

    @Test(expected = ExportException.class)
    public void GUIStartServerTest() throws RemoteException, AlreadyBoundException, InterruptedException {
        System.out.println("TestStartGUI");
        
        assertNotNull(app.getFrame());
        
        window.show();
        
        window.menuItem("collaboration").click();
        window.menuItem("collaboration.start").click();
        window.optionPane().yesButton().click();

        Thread.sleep(10000);
        
        LocateRegistry.createRegistry(CollaborationConfig.PORT).bind(CollaborationConfig.NAME, RemoteObservable.getInstance());
    }
    
    @Test
    public void GUIConnectToServerTest() throws InterruptedException, AlreadyBoundException, AccessException, RemoteException, UnknownHostException {
        System.out.println("TestConnectGUI");
        
        assertNotNull(app.getFrame());
        
        LocateRegistry.createRegistry(
                    CollaborationConfig.PORT).bind(CollaborationConfig.NAME, RemoteObservable.getInstance());
        
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
        assertNotNull(CollaborationConnection.getInstance().getName());
        
        window.menuItem("collaboration").click();
        window.menuItem("collaboration.disConnect").click();
        
        assertFalse(CollaborationConnection.getInstance().isConnected());
        assertNull(CollaborationConnection.getInstance().getName());
        
    }
}
