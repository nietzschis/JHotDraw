package org.jhotdraw.collaboration.client;

import java.awt.geom.Point2D;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jhotdraw.collaboration.common.CollaborationConfig;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.collaboration.common.IRemoteObservable;
import org.jhotdraw.collaboration.common.IRemoteObserver;
import org.jhotdraw.collaboration.common.SVGRectDTO;
import org.jhotdraw.samples.svg.figures.SVGEllipseFigure;
import org.jhotdraw.samples.svg.figures.SVGPathFigure;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.jhotdraw.samples.svg.figures.SVGTextFigure;

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
            List<SVGRectDTO> listToSend = new ArrayList<>();

            for (Figure f : drawing.getChildren()) {
                if (f instanceof SVGRectFigure) {
                    SVGRectDTO fig = new SVGRectDTO();
                    fig.x = ((SVGRectFigure) f).getX();
                    fig.y = ((SVGRectFigure) f).getY();
                    fig.height = ((SVGRectFigure) f).getHeight();
                    fig.width = ((SVGRectFigure) f).getWidth();
                    fig.rx = ((SVGRectFigure) f).getArcWidth();
                    fig.ry = ((SVGRectFigure) f).getArcHeight();
                    fig.attributes = f.getAttributes();
                    listToSend.add(fig);
                }

            }

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
        //System.out.println("update på klient");
        for (Figure serverFigure : figures) {
            boolean found = false;
            for (Figure workingFigure : drawing.getChildren()) {
                if (workingFigure.getCollaborationId() == serverFigure.getCollaborationId()) {

                    // Same figure check bounds
                    if ((workingFigure.getBounds().x != serverFigure.getBounds().x) || (workingFigure.getBounds().y != serverFigure.getBounds().y)
                            || (workingFigure.getBounds().height != serverFigure.getBounds().height) || (workingFigure.getBounds().width != serverFigure.getBounds().width)) {
                        System.out.println("Moved");
                        //System.out.println(serverFigure.getBounds() + " " + workingFigure.getBounds());
                        Point2D.Double start = new Point2D.Double(serverFigure.getBounds().x, serverFigure.getBounds().y);
                        Point2D.Double end = new Point2D.Double(serverFigure.getBounds().x + serverFigure.getBounds().width, serverFigure.getBounds().y + serverFigure.getBounds().height);

                        workingFigure.willChange();
                        workingFigure.setBounds(start, end);
                        workingFigure.changed();
                    }

                    // Same figure check attributes
                    if (!workingFigure.getAttributes().equals(serverFigure.getAttributes())) {
                        System.out.println("Changed attribute");
                        workingFigure.willChange();
                        workingFigure.restoreAttributesTo(serverFigure.getAttributesRestoreData());
                        workingFigure.changed();
                    }

                    found = true;
                }
                //System.out.println("Working figure: " + workingFigure.getCollaborationId() + "; Server figure: " + serverFigure.getCollaborationId());
            }

            if (!found) {
                
                if (serverFigure instanceof SVGRectFigure) {
                    System.out.println("add square");
                    SVGRectFigure newFig = (SVGRectFigure) serverFigure.clone();
                    newFig.setCollaborationId(serverFigure.getCollaborationId());
                    drawing.add(newFig);
                }
                else if(serverFigure instanceof SVGEllipseFigure) {
                    System.out.println("Added circle");
                    SVGEllipseFigure newFig = (SVGEllipseFigure) serverFigure.clone();
                    newFig.setCollaborationId(serverFigure.getCollaborationId());
                    drawing.add(newFig);
                }
                else if(serverFigure instanceof SVGPathFigure) {
                    System.out.println("Added path");
                    SVGPathFigure newFig = (SVGPathFigure) serverFigure.clone();
                    newFig.setCollaborationId(serverFigure.getCollaborationId());
                    drawing.add(newFig);
                }
                else if(serverFigure instanceof SVGTextFigure) {
                    System.out.println("Added text");
                    SVGTextFigure newFig = (SVGTextFigure) serverFigure.clone();
                    newFig.setCollaborationId(serverFigure.getCollaborationId());
                    drawing.add(newFig);
                }
                else {
                    System.out.println("Unknown figure");
                }
            }
        }
        System.out.println(drawing.getChildCount());

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

    @Override
    public String getName() throws RemoteException {
        return "Anton" + (Math.random() * 100);
    }
}
