package org.jhotdraw.collaboration.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.collaboration.common.IRemoteObservable;
import org.jhotdraw.collaboration.common.IRemoteObserver;

public class CollaborationConnection extends UnicastRemoteObject implements IRemoteObserver {

    private static CollaborationConnection singleton;
    private Drawing drawing;
    private IRemoteObservable collaborationProxy;
    private List<Figure> list;

    private CollaborationConnection() throws RemoteException {
        super();
    }

    public static CollaborationConnection getInstance() {
        if (singleton == null) {
            try {
                singleton = new CollaborationConnection();
            } catch (RemoteException ex) {
            }
        }
        return singleton;
    }

    public boolean connectToServer(String IP) {
        // TOOD: Opret forbindelse

        this.addCollaborator();

        return true;
    }

    public void disconnectFromServer() {
        this.removeCollaborator();
        this.drawing = null;
        this.collaborationProxy = null;
    }

    public void setDrawing(Drawing drawing) {
        this.drawing = drawing;
    }

    public void notifyUpdate(String source) {
        if (drawing != null) {
            System.out.println("Collaboration Notified");
            //collaborationProxy.notifyAllCollaborators(drawing.getChildren());
        }
    }

    private void sendFiguresToServer() {
        /*try {
            list = drawing.getChildren();
            collaborationProxy.sendFigures(list);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }*/
        list = cloneList();
        System.out.println("List saved");
        System.out.println("Set List lenght " + list.size());
        for (Figure f : list) {
            System.out.println(f);
        }
        System.out.println("");
    }

    private List<Figure> cloneList() {
        List<Figure> newList = new ArrayList<Figure>();
        System.out.println("Children size: " + drawing.getChildren().size());
        for (Figure f : drawing.getChildren()) {
            newList.add((Figure) f.clone());
        }
        return newList;
    }

    // Server kalder denne på clienten
    @Override
    public void update(List<Figure> figures) throws RemoteException {

        List<Figure> serverList = null;
        for (Figure workingFigure : drawing.getChildren()) {
            for (Figure serverFigure : serverList) {

                // A figure exists on the client
                if (serverFigure.equals(workingFigure)) {
                    drawing.remove(workingFigure);
                    drawing.add(serverFigure);
                }

                // A figure from the server does not exist in the client
                if (!drawing.getChildren().contains(serverFigure)) {
                    drawing.add(serverFigure);
                }

                // A figure exists on client but not on server
                if (!serverList.contains(workingFigure)) {
                    drawing.remove(workingFigure);
                }
            }
        }
    }

    private void addCollaborator() {
        try {
            collaborationProxy.addCollaborator(this);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    private void removeCollaborator() {
        try {
            collaborationProxy.removeCollaborator(this);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }
}
