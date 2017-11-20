package org.jhotdraw.collaboration.server;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 *
 * @author Niels
 */
public interface IServer {
    
    void startServer() throws RemoteException, AlreadyBoundException;
    
    void stopServer() throws RemoteException, NotBoundException;
    
}
