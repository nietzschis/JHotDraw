package org.jhotdraw.collaboration;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.Figure;

public class CollaborationConnection {
    // TOOD: Opret forbindelse
    private static CollaborationConnection singleton;
    private Drawing drawing;
    private Collaboration collaborationProxy;
    private Collaborator collaborator;
    
    private CollaborationConnection() {
        collaborator = new CollaboratorImpl();
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
    
    public void getFiguresFromServer() {
        try {
            drawing.addAll(collaborationProxy.getFigures());
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }
    
    private void addCollaborator() {
        try {
            collaborationProxy.addCollaborator(collaborator);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }
    
    public void removeCollaborator() {
        try {
            collaborationProxy.removeCollaborator(collaborator);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }
}
