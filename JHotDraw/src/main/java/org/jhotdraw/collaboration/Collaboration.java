package org.jhotdraw.collaboration;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import org.jhotdraw.draw.Figure;

public interface Collaboration extends Remote {

    void addCollaborator(Collaborator collaborator) throws RemoteException;

    void removeCollaborator(Collaborator collaborator) throws RemoteException;

    void sendFigures(ArrayList<Figure> figures) throws RemoteException;

    ArrayList<Figure> getFigures() throws RemoteException;
}
