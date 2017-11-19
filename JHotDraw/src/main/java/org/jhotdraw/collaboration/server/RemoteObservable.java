package org.jhotdraw.collaboration.server;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jhotdraw.collaboration.common.IRemoteObservable;
import org.jhotdraw.collaboration.common.IRemoteObserver;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.collaboration.common.CollaborationConfig;

/**
 *
 * @author Niels
 */
public class RemoteObservable extends UnicastRemoteObject implements IRemoteObservable {
    
    private Set<IRemoteObserver> collaborators;
    
    public RemoteObservable() throws RemoteException {
        super();
        collaborators = new LinkedHashSet<>();
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
            catch(RemoteException e) {
                System.out.println("Exception while updating client " + collaborator + ": " + e);
            }
        });
    }
    
    public void run() {
        try {
            LocateRegistry.createRegistry(
                    CollaborationConfig.PORT).bind(CollaborationConfig.NAME, new RemoteObservable());
        }
        catch (RemoteException ex) {
            Logger.getLogger(RemoteObservable.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (AlreadyBoundException ex) {
            Logger.getLogger(RemoteObservable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
