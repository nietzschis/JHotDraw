package org.jhotdraw.app.action;

import static org.junit.Assert.assertTrue;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.collaboration.common.CollaborationConfig;
import org.junit.Test;

/**
 *
 * @author Niels
 */
public class CollaborationStartServerActionTest {
    
    @Test(expected = ExportException.class)
    public void testChangeListener() throws RemoteException {
            Application app = new DefaultSDIApplication();
            AbstractApplicationAction startServerAction = new CollaborationStartServerAction(app);
            
            //startServerAction.actionPerformed(null);
            
            app.startServer();
            
            Registry reg = LocateRegistry.createRegistry(CollaborationConfig.PORT);
            
            //app.stopServer();
            assertTrue(startServerAction.isEnabled());
        
    }
    
}
