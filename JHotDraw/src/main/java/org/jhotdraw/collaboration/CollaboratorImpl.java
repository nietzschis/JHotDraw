package org.jhotdraw.collaboration;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

// TODO: Skal nok hedde noget bedre
public class CollaboratorImpl extends UnicastRemoteObject implements Collaborator {

    public CollaboratorImpl() throws RemoteException {
    }
    @Override
    public void notifyCollaborator() throws RemoteException {
        // TODO: Skal pulle fra server
    }
    
}
