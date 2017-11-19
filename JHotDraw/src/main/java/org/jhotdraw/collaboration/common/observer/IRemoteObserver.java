package org.jhotdraw.collaboration.common.observer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import org.jhotdraw.draw.Figure;

public interface IRemoteObserver extends Remote {

    void update(List<Figure> figures) throws RemoteException;
    
}
