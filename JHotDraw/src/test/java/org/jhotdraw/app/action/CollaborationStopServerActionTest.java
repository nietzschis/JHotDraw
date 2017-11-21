package org.jhotdraw.app.action;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultSDIApplication;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Niels
 */
public class CollaborationStopServerActionTest {

    @Test
    public void testChangeListener() throws RemoteException, AlreadyBoundException, NotBoundException {
        Application app = new DefaultSDIApplication();
        AbstractApplicationAction stopServerAction = new CollaborationStopServerAction(app);

        assertFalse(stopServerAction.isEnabled());
        app.startServer();
        assertTrue(stopServerAction.isEnabled());
        app.stopServer();
        assertFalse(stopServerAction.isEnabled());
    }

}
