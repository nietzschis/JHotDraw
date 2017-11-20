package org.jhotdraw.collaboration.server;

import java.rmi.AlreadyBoundException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.jhotdraw.collaboration.client.CollaborationConnection;
import org.jhotdraw.collaboration.common.*;
import org.jhotdraw.draw.Figure;

/**
 *
 * @author Niels
 */
public class CollaborationServer extends UnicastRemoteObject implements IRemoteObservable, IServer {

    private final Set<IRemoteObserver> collaborators;
    private static IServer instance;

    public static IServer getInstance() throws RemoteException {
        if (instance == null) {
            instance = new CollaborationServer();
        }
        return instance;
    }

    private CollaborationServer() throws RemoteException {
        super();
        collaborators = new LinkedHashSet<>();
    }

    @Override
    public void addCollaborator(IRemoteObserver collaborator) throws RemoteException {
        collaborators.add(collaborator);
        System.out.println("Added new collaborator: " + collaborator);
    }

    @Override
    public void removeCollaborator(IRemoteObserver collaborator) throws RemoteException {
        collaborators.remove(collaborator);
        System.out.println("Removed collaborator: " + collaborator);
    }

    @Override
    public void notifyAllCollaborators(List<Figure> figures) throws RemoteException {
        collaborators.parallelStream().forEach(collaborator -> {
            try {
                collaborator.update(figures);
            }
            catch (RemoteException e) {
                collaborators.remove(collaborator);
                System.err.println("Exception while updating client " + collaborator + ": " + e);
            }
        });
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
                    CollaborationConfig.PORT).bind(CollaborationConfig.NAME, (Remote) getInstance());
            System.out.println("Server started.");
        }
        catch (ConnectException e) {
            LocateRegistry.createRegistry(
                    CollaborationConfig.PORT).bind(CollaborationConfig.NAME, (Remote) getInstance());
            System.out.println("Server started.");
        }
    }

    @Override
    public void stopServer() throws RemoteException, NotBoundException {
        LocateRegistry.getRegistry(CollaborationConfig.PORT).unbind(CollaborationConfig.NAME);
        collaborators.clear();
        System.out.println("Server stopped.");
    }

}
