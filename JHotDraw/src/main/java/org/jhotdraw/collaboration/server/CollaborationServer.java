package org.jhotdraw.collaboration.server;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.jhotdraw.collaboration.common.IRemoteObservable;
import org.jhotdraw.collaboration.common.IRemoteObserver;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.collaboration.common.CollaborationConfig;

/**
 *
 * @author Niels
 */
public class CollaborationServer extends UnicastRemoteObject implements IRemoteObservable, IServer {

    private Set<IRemoteObserver> collaborators;
    private static IServer instance;

    public static IServer getInstance() {
        if (instance == null) {
            try {
                instance = new CollaborationServer();
            }
            catch (RemoteException e) {
                System.err.println("Unable to start client: " + e);
            }
        }
        return instance;
    }

    private CollaborationServer() throws RemoteException {
        super();
        collaborators = new LinkedHashSet<>();
        LocateRegistry.createRegistry(CollaborationConfig.PORT);
    }

    @Override
    public void addCollaborator(IRemoteObserver collaborator) throws RemoteException {
        collaborators.add(collaborator);
    }

    @Override
    public void removeCollaborator(IRemoteObserver collaborator) throws RemoteException {
        collaborators.remove(collaborator);
    }

    @Override
    public void notifyAllCollaborators(List<Figure> figures) throws RemoteException {
        collaborators.parallelStream().forEach(collaborator -> {
            try {
                collaborator.update(figures);
            }
            catch (RemoteException e) {
                System.err.println("Exception while updating client " + collaborator + ": " + e);
            }
        });
    }

    @Override
    public void startServer() { // TODO throws Exception, håndter dem inde i Action-klassen, ved at opdatere GUI med fejlmeddelserne
        try {
            LocateRegistry.getRegistry(
                    CollaborationConfig.PORT).bind(CollaborationConfig.NAME, (Remote) getInstance());
            System.out.println("Server started.");
        }
        catch (RemoteException | AlreadyBoundException e) {
            System.err.println("Server launch failed: " + e);
        }
    }

    @Override
    public void stopServer() {
        try {
            LocateRegistry.getRegistry(CollaborationConfig.PORT).unbind(CollaborationConfig.NAME);
            System.out.println("Server stopped.");
        }
        catch (RemoteException | NotBoundException e) {
            System.err.println("Server shutdown failed: " + e);
        }
    }

}
