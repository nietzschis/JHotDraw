package org.jhotdraw.collaboration.client;

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
import org.jhotdraw.samples.svg.figures.SVGRectFigure;

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
    public void update(List<Figure> figures) throws RemoteException {
        System.out.println("update på klient");
        for (Figure serverFigure : figures) {
            boolean found = false;
            for (Figure workingFigure : drawing.getChildren()) {
                if (workingFigure.getCollaborationId() == serverFigure.getCollaborationId()) {
                    //drawing.add((SVGRectFigure) serverFigure.clone());
                    System.out.println("Found same fig");
                    found = true;
                }
                System.out.println("Working figure: " + workingFigure.getCollaborationId() + "; Server figure: " + serverFigure.getCollaborationId());
            }
            
            if(!found) {
                System.out.println("add");
                SVGRectFigure newFig = (SVGRectFigure) serverFigure.clone();
                newFig.setCollaborationId(serverFigure.getCollaborationId());
                drawing.add((SVGRectFigure) serverFigure.clone());
            }
        }
        System.out.println(drawing.getChildCount());

    }

    /*public void update(List<SVGRectDTO> figures) throws RemoteException {

        List<Figure> serverList = new ArrayList<Figure>();
        for(SVGRectDTO dto : figures) {
            SVGRectFigure newRect = new SVGRectFigure(dto.x, dto.y, dto.width, dto.height, dto.rx, dto.ry);
            newRect.setAttributes(dto.attributes);
            drawing.add(newRect);
        }
        for (Figure workingFigure : drawing.getChildren()) {
            for (Figure serverFigure : serverList) {

                // A figure exists on the client
                if (serverFigure.equals(workingFigure)) {
                    if (workingFigure.getAttributes().equals(serverFigure.getAttributes())) {
                        System.out.println("No difference");
                    }
                    else {
                        System.out.println("Some difference");
                        drawing.remove(workingFigure);
                        drawing.add(serverFigure);
                    }
                }

                // A figure from the server does not exist in the client
                if (!drawing.getChildren().contains(serverFigure)) {
                    drawing.add(serverFigure);
                }

            }

            // A figure exists on client but not on server
            if (!serverList.contains(workingFigure)) {
                drawing.remove(workingFigure);
            }
        }
    }*/
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
