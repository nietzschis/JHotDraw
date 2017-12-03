package org.jhotdraw.collaboration.client;

import java.awt.geom.Point2D;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import org.jhotdraw.collaboration.common.CollaborationConfig;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.collaboration.common.IRemoteObservable;
import org.jhotdraw.collaboration.common.IRemoteObserver;
import org.jhotdraw.samples.svg.figures.SVGPathFigure;

public class CollaborationConnection extends UnicastRemoteObject implements IRemoteObserver {

    private static CollaborationConnection singleton;
    private Drawing drawing;
    private IRemoteObservable collaborationProxy;
    private String name;
    private CollaborationDrawingHandler drawingHandler;

    private CollaborationConnection() throws RemoteException {
        super();
        drawingHandler = new CollaborationDrawingHandler();
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
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(IP, CollaborationConfig.PORT);
            collaborationProxy = (IRemoteObservable) registry.lookup(CollaborationConfig.NAME);
            addCollaborator();

            return true;
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
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
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Server kalder denne på clienten
    @Override
    public synchronized void update(List<Figure> figures) throws RemoteException {
        if (figures.size() > drawing.getChildCount()) {
            serverListLongest(figures);
        } else {
            clientListLongest(figures);
        }
    }

    private void serverListLongest(List<Figure> serverList) {
        for (Figure serverFigure : serverList) {
            boolean found = false;
            for (Figure workingFigure : drawing.getChildren()) {

                // Figures have the same Id, so must be the same figure, check for updates
                if (workingFigure.getCollaborationId() == serverFigure.getCollaborationId()) {

                    // Same figure check bounds
                    if ((workingFigure.getBounds().x != serverFigure.getBounds().x) || (workingFigure.getBounds().y != serverFigure.getBounds().y)
                            || (workingFigure.getBounds().height != serverFigure.getBounds().height) || (workingFigure.getBounds().width != serverFigure.getBounds().width)) {
                        drawingHandler.changeBounds(workingFigure, serverFigure);
                    }

                    // Same figure check attributes
                    if (!workingFigure.getAttributes().equals(serverFigure.getAttributes())) {
                        drawingHandler.changeAttributes(workingFigure, serverFigure);
                    }

                    // Found the same figures so its not new 
                    found = true;
                }
            }

            if (!found) {
                // Add new figure to list
                drawingHandler.addFigure(serverFigure);
            }
        }
    }

    private void clientListLongest(List<Figure> serverList) {
        List<Figure> figuresToBeDeleted = new ArrayList();
        for (Figure workingFigure : drawing.getChildren()) {
            boolean found = false;
            for (Figure serverFigure : serverList) {

                // Figures have the same Id, so must be the same figure, check for updates
                if (workingFigure.getCollaborationId() == serverFigure.getCollaborationId()) {

                    // Same figure check bounds
                    if ((workingFigure.getBounds().x != serverFigure.getBounds().x) || (workingFigure.getBounds().y != serverFigure.getBounds().y)
                            || (workingFigure.getBounds().height != serverFigure.getBounds().height) || (workingFigure.getBounds().width != serverFigure.getBounds().width)) {
                        drawingHandler.changeBounds(workingFigure, serverFigure);
                    }

                    // Same figure check attributes
                    if (!workingFigure.getAttributes().equals(serverFigure.getAttributes())) {
                        drawingHandler.changeAttributes(workingFigure, serverFigure);
                    }

                    // Found the same figures so its not missing 
                    found = true;
                }
            }

            if (!found) {
                // Remove figure from list
                figuresToBeDeleted.add(workingFigure);
            }
        }

        if (!figuresToBeDeleted.isEmpty()) {
            drawingHandler.removeFigures(figuresToBeDeleted);
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
        System.out.println("starting to remove as collab");
        try {
            collaborationProxy.removeCollaborator(this);
            System.out.println("removed as collab");
        } catch (RemoteException ex) {
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
}
