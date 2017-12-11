package org.jhotdraw.collaboration.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import org.jhotdraw.draw.Figure;

public interface IRemoteObservable extends Remote {

    void addCollaborator(IRemoteObserver collaborator) throws RemoteException;

    void removeCollaborator(IRemoteObserver collaborator) throws RemoteException;
    
    void notifyAllCollaborators(List<Figure> figures) throws RemoteException;
    
}
