package org.jhotdraw.collaboration.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import org.jhotdraw.collaboration.common.CollaborationConfig;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.collaboration.common.IRemoteObservable;
import org.jhotdraw.collaboration.common.IRemoteObserver;

public class CollaborationConnection extends UnicastRemoteObject implements IRemoteObserver {

    private static CollaborationConnection singleton;
    private Drawing drawing;
    private IRemoteObservable collaborationProxy;
    private String name;
    private final transient CollaborationDrawingHandler drawingHandler;
    private boolean connected = false;

    private CollaborationConnection() throws RemoteException {
        super();
        drawingHandler = new CollaborationDrawingHandler();
    }

    public static CollaborationConnection getInstance() {
        if (singleton == null) {
            try {
                singleton = new CollaborationConnection();
            }
            catch (RemoteException ex) {
            }
        }
        return singleton;
    }

    public boolean connectToServer(String IP) {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(IP, CollaborationConfig.PORT);
            collaborationProxy = (IRemoteObservable) registry.lookup(CollaborationConfig.NAME);
            addCollaborator();
            connected = true;

            return true;
        }
        catch (RemoteException | NotBoundException e) {
            return false;
        }

        //return true;
    }

    public void setCollaborationProxy(IRemoteObservable server) {
        collaborationProxy = server;
    }

    public void disconnectFromServer() {
        this.removeCollaborator();
        this.drawing = null;
        this.collaborationProxy = null;
        this.name = null;
        connected = false;
    }

    public void setDrawing(Drawing drawing) {
        this.drawing = drawing;
        drawingHandler.setDrawing(drawing);
    }

    public void notifyUpdate(String source) {
        if (drawing != null) {
            System.out.println("Collaboration Notified, action: " + source);
            try {
                collaborationProxy.notifyAllCollaborators(drawing.getChildren());
            }
            catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Server kalder denne på clienten
    @Override
    public synchronized void update(List<Figure> figures) throws RemoteException {
        try {
            drawingHandler.mergeFigures(figures);
        }
        catch (NullPointerException e) {
        }
    }

    private void addCollaborator() {
        try {
            collaborationProxy.addCollaborator(this);
        }
        catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    private void removeCollaborator() {
        System.out.println("starting to remove as collab");
        try {
            collaborationProxy.removeCollaborator(this);
            System.out.println("removed as collab");
        }
        catch (RemoteException ex) {
            System.out.println("collab error");
            ex.printStackTrace();
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() throws RemoteException {
        return name;
    }

    public boolean isConnected() {
        return connected;
    }

}
