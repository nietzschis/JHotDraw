package org.jhotdraw.app.action;

import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultSDIApplication;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Niels
 */
public class CollaborationStartServerActionTest {

    @Test
    public void testChangeListener() {
        Application app = new DefaultSDIApplication();
        AbstractApplicationAction startServerAction = new CollaborationStartServerAction(app);

        assertTrue(startServerAction.isEnabled());
        app.startServer();
        assertFalse(startServerAction.isEnabled());
        app.stopServer();
        assertTrue(startServerAction.isEnabled());
    }

}
