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
import org.jhotdraw.draw.QuadTreeDrawing;
import org.jhotdraw.samples.svg.figures.SVGPathFigure;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;

public class CollaborationConnection extends UnicastRemoteObject implements IRemoteObserver {

    private static CollaborationConnection singleton;
    private QuadTreeDrawing drawing;
    private IRemoteObservable collaborationProxy;
    private String name;
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
        this.drawing = (QuadTreeDrawing) drawing;
    }

    public void notifyUpdate(String source) {
        System.out.println("Call for " + source);
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
        for (Figure serverFigure : figures) {
            boolean found = false;
            for (Figure workingFigure : drawing.getChildren()) {
                if (workingFigure.getCollaborationId() == serverFigure.getCollaborationId()) {

                    // Same figure check bounds
                    if ((workingFigure.getBounds().x != serverFigure.getBounds().x) || (workingFigure.getBounds().y != serverFigure.getBounds().y)
                            || (workingFigure.getBounds().height != serverFigure.getBounds().height) || (workingFigure.getBounds().width != serverFigure.getBounds().width)) {
                        System.out.println("Moved");

                        if (workingFigure instanceof SVGPathFigure) {
                            SVGPathFigure newFig = (SVGPathFigure) serverFigure.clone();
                            newFig.setCollaborationId(serverFigure.getCollaborationId());
                            drawing.remove(workingFigure);
                            drawing.add(newFig);
                        } else {
                            Point2D.Double start = new Point2D.Double(serverFigure.getBounds().x, serverFigure.getBounds().y);
                            Point2D.Double end = new Point2D.Double(serverFigure.getBounds().x + serverFigure.getBounds().width, serverFigure.getBounds().y + serverFigure.getBounds().height);

                            workingFigure.willChange();
                            workingFigure.setBounds(start, end);
                            workingFigure.changed();
                        }
                    }

                    // Same figure check attributes
                    if (!workingFigure.getAttributes().equals(serverFigure.getAttributes())) {
                        System.out.println("Changed attribute");
                        workingFigure.willChange();
                        workingFigure.restoreAttributesTo(serverFigure.getAttributesRestoreData());
                        workingFigure.changed();
                    }
                    System.out.println(serverFigure);
                    found = true;
                }
            }

            if (!found) {

                if (true) {
                    System.out.println("add square");
                    Figure newFig = (Figure) serverFigure.clone();
                    newFig.setCollaborationId(serverFigure.getCollaborationId());
                    drawing.add(newFig);
                } else {
                    System.out.println("Unsupported figure");
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
