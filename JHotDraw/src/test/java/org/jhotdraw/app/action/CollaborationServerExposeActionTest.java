package org.jhotdraw.app.action;

import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultSDIApplication;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Niels
 */
public class CollaborationServerExposeActionTest {
    
    @Test
    public void testChangeListener() {
        Application app = new DefaultSDIApplication();
        AbstractApplicationAction exposeServerAction = new CollaborationStartServerAction(app);
        
        app.stopServer();
        assertTrue(exposeServerAction.isEnabled());
    }
    
}
