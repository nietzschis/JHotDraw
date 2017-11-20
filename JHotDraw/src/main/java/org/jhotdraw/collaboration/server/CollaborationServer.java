package org.jhotdraw.collaboration.server;

import java.rmi.AlreadyBoundException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;
import org.jhotdraw.collaboration.common.*;

/**
 *
 * @author Niels
 */
public class CollaborationServer implements IServer {
    
    private static IServer instance;
    
    private CollaborationServer() {}
    
    public static IServer getInstance() {
        if(instance == null) {
            instance = new CollaborationServer();
        }
        return instance;
    }

    @Override
    public void startServer() throws RemoteException, AlreadyBoundException {
        try {
            LocateRegistry.getRegistry(CollaborationConfig.PORT).lookup(CollaborationConfig.NAME);
            System.err.println("Server not started.");
            throw new ExportException("Port " + CollaborationConfig.PORT + " already in use");
        }
        catch (NotBoundException e) {
            LocateRegistry.getRegistry(
                    CollaborationConfig.PORT).bind(CollaborationConfig.NAME, RemoteObservable.getInstance());
            System.out.println("Server started (rebound).");
        }
        catch (ConnectException e) {
            LocateRegistry.createRegistry(
                    CollaborationConfig.PORT).bind(CollaborationConfig.NAME, RemoteObservable.getInstance());
            System.out.println("Server started.");
        }
    }

    @Override
    public void stopServer() throws RemoteException, NotBoundException {
        LocateRegistry.getRegistry(CollaborationConfig.PORT).unbind(CollaborationConfig.NAME);
        ((RemoteObservable) RemoteObservable.getInstance()).clearAllCollaborators();
        System.out.println("Server stopped.");
    }

}
