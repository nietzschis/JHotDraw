package org.jhotdraw.collaboration.client;

import java.awt.geom.Rectangle2D;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.collaboration.common.IRemoteObservable;
import org.jhotdraw.collaboration.common.IRemoteObserver;

public class CollaborationConnection extends UnicastRemoteObject implements IRemoteObserver {

    // TOOD: Opret forbindelse
    private static CollaborationConnection singleton;
    private Drawing drawing;
    private IRemoteObservable collaborationProxy;
    private List<Figure> list;

    private CollaborationConnection() {
    }

    public static CollaborationConnection getInstance() {
        if (singleton == null) {
            singleton = new CollaborationConnection();
        }
        return singleton;
    }

    /*private void createCollaboratorObserver() {
        try {
            collaborator = new CollaboratorImpl();
            this.addCollaborator();
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }*/
    public boolean connectToServer(String IP) {
        // TOOD: Opret forbindelse
        //this.createCollaboratorObserver();

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
        }

        System.out.println(source);
    }

    public void sendFiguresToServer() {
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

    /*public void getFiguresFromServer() {
        try {
        
        List<Figure> serverList = null;
        for (Figure workingFigure : drawing.getChildren()) {
            for(Figure serverFigure : serverList) {
                
                // A figure exists on the client
                if( serverFigure.equals(workingFigure)) {
                    /*Rectangle2D.Double serverBounds = serverFigure.getBounds();
                    Rectangle2D.Double clientBounds = workingFigure.getBounds();
                    if( serverBounds.x != clientBounds.x || serverBounds.y != clientBounds.y || 
                        serverBounds.height != clientBounds.height || serverBounds.width != clientBounds.width) {*/
                        
                        drawing.remove(workingFigure);
                        drawing.add(serverFigure);
                    //}
                }
                
                // A figure from the server does not exist in the client
                if( !drawing.getChildren().contains(serverFigure)) {
                    drawing.add(serverFigure);
                }
            }
            
            // A figure exists on client but not on server
                if( !serverList.contains(workingFigure) ) {
                    drawing.remove(workingFigure);
                }
        }
    }*/
 /*drawing.removeAllChildren();
        drawing.addAll(list);
        System.out.println("Get List lenght " + list.size());
        System.out.println("List recieved");*/
    
    private void addCollaborator() {
        try {
            collaborationProxy.addCollaborator(this);
        }
        catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    public void removeCollaborator() {
        try {
            collaborationProxy.removeCollaborator(this);
        }
        catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(List<Figure> figures) {
        drawing.addAll(figures);
    }
}
