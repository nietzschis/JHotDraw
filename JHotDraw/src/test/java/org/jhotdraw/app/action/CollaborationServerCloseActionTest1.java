package org.jhotdraw.app.action;

import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultSDIApplication;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Niels
 */
public class CollaborationServerCloseActionTest1 {
    
    @Test
    public void testChangeListener() {
        Application app = new DefaultSDIApplication();
        AbstractApplicationAction closeServerAction = new CollaborationServerCloseAction(app);
        
        assertFalse(closeServerAction.isEnabled());
        app.exposeServer();
        assertTrue(closeServerAction.isEnabled());
    }
    
}
