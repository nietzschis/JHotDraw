package org.jhotdraw.app.action;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import org.jhotdraw.app.AbstractApplication;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.samples.svg.SVGView;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 *
 * @author Niels
 */
public class CollaborationStopServerActionTest {

    @Test
    public void testChangeListener() throws RemoteException, AlreadyBoundException, NotBoundException {
        AbstractApplication app = new DefaultSDIApplication();
        app.setActiveView(mock(SVGView.class));
        AbstractApplicationAction stopServerAction = new CollaborationStopServerAction(app);

        assertFalse(stopServerAction.isEnabled());
        app.startServer();
        assertTrue(stopServerAction.isEnabled());
        app.stopServer();
        assertFalse(stopServerAction.isEnabled());
    }

}
