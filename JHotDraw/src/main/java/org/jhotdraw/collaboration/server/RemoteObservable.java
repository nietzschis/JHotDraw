package org.jhotdraw.collaboration.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.jhotdraw.collaboration.client.CollaborationConnection;
import org.jhotdraw.collaboration.common.IRemoteObservable;
import org.jhotdraw.collaboration.common.IRemoteObserver;
import org.jhotdraw.draw.Figure;

/**
 *
 * @author Niels
 */
public class RemoteObservable extends UnicastRemoteObject implements IRemoteObservable {

    private final Set<IRemoteObserver> collaborators;
    private static IRemoteObservable instance;

    public static IRemoteObservable getInstance() throws RemoteException {
        if (instance == null) {
            instance = new RemoteObservable();
        }
        return instance;
    }

    private RemoteObservable() throws RemoteException {
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

    public Collection<String> getCollaboratorNames() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue();
        collaborators.parallelStream()
                .filter(collaborator -> collaborator != CollaborationConnection.getInstance())
                .forEach(collaborator -> {
                    try {
                        queue.add(collaborator.getName());
                    }
                    catch (RemoteException e) {
                        System.err.println("Exception while getting name from client " + collaborator + ": " + e);
                    }
                });
        return queue;
    }

    public void clearAllCollaborators() {
        collaborators.clear();
    }

}
