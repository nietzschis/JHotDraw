package org.jhotdraw.collaboration.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import org.jhotdraw.draw.Figure;

public interface IRemoteObserver extends Remote {

    //void update(List<SVGRectDTO> figures) throws RemoteException;
    void update(List<Figure> figures) throws RemoteException;
    
    String getName() throws RemoteException;
    
}
