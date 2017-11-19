package org.jhotdraw.collaboration;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Collaborator extends Remote {

    void notifyCollaborator() throws RemoteException;
}
