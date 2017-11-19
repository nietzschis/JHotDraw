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
    // TOOD: Opret forbindelse
    private static CollaborationConnection singleton;
    private Drawing drawing;
    private IRemoteObservable collaborationProxy;
    
    private CollaborationConnection() {
    }
    
    public static CollaborationConnection getInstance() {
        if(singleton == null) {
            singleton = new CollaborationConnection();
        }
        return singleton;
    }
    
    public void setDrawing(Drawing drawing) {
        this.drawing = drawing;
//        System.out.println(this.drawing.getChildren());
//        Figure f = new SVGRectFigure();
//        f.setBounds(new Point2D.Double(10, 10), new Point2D.Double(50, 50));
//        this.drawing.add(f);
        
    }
    
    public void sendFiguresToServer(ArrayList<Figure> figureList) {
        try {
            collaborationProxy.sendFigures(figureList);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }
    
    /*public void getFiguresFromServer() {
        try {
            drawing.addAll(collaborationProxy.getFigures());
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }*/
    
    private void addCollaborator() {
        try {
            collaborationProxy.addCollaborator(this);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }
    
    public void removeCollaborator() {
        try {
            collaborationProxy.removeCollaborator(this);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(List<Figure> figures) {
        drawing.addAll(figures);
    }
}
