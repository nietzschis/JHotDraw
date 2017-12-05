package org.jhotdraw.collaboration;

import java.lang.reflect.InvocationTargetException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.collaboration.common.CollaborationConfig;
import org.jhotdraw.collaboration.server.CollaborationServer;
import org.jhotdraw.samples.svg.SVGApplicationModel;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Niels & Niels
 */
public class CollaborationGUITest {

    private FrameFixture window;
    
    @BeforeClass
    public static void setUp() {
        FailOnThreadViolationRepaintManager.install();
    }

    @Test(expected = ExportException.class)
    public void test() throws InterruptedException, InvocationTargetException {
        Application app = new DefaultSDIApplication();
        SVGApplicationModel model = new SVGApplicationModel();
        model.setViewClassName("org.jhotdraw.samples.svg.SVGView");
        app.setModel(model);
        app.launch(null);
        Thread.sleep(3000);
        assertNotNull(app.getFrame());
        
        //SwingUtilities.invokeLater(() -> {
        
        window = new FrameFixture(app.getFrame());
        window.show();
        window.focus();
        window.menuItem("collaboration").click();
        //Thread.sleep(1000);
        //window.menuItem("collaboration").click().click();
        //Thread.sleep(1000);
        window.menuItem("collaboration.start").click();
        //Thread.sleep(1000);
        window.optionPane().buttonWithText("Yes").click();

        Thread.sleep(5000);

        try {
            LocateRegistry.createRegistry(CollaborationConfig.PORT).bind(CollaborationConfig.NAME, (Remote) CollaborationServer.getInstance());
            fail();
        }
        catch (RemoteException | AlreadyBoundException ex) {
        }
        //});

    }

}
