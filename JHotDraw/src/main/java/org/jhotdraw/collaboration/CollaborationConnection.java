package org.jhotdraw.collaboration;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
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
    private List<Figure> list;
    
    private CollaborationConnection() {
        //collaborator = new CollaboratorImpl();
    }
    
    public static CollaborationConnection getInstance() {
        if(singleton == null) {
            singleton = new CollaborationConnection();
        }
        return singleton;
    }
    
    public void setDrawing(Drawing drawing) {
        this.drawing = drawing;
        
    }
    
    public void notifyUpdate(String source) {
        if(drawing != null) {
            System.out.println("Collaboration Notified");
        }
        
        System.out.println(source);
    }
    
    public void sendFiguresToServer() {
        /*try {
            collaborationProxy.sendFigures(figureList);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }*/
        list = cloneList();
        System.out.println("List saved");
        System.out.println("Set List lenght " + list.size());
        for(Figure f : list) {
            System.out.println(f);
        }
        System.out.println("");
    }
    
    private List<Figure> cloneList() {
        List<Figure> newList = new ArrayList<Figure>();
        System.out.println("Children size: " + drawing.getChildren().size());
        for(Figure f : drawing.getChildren()) {
            newList.add((Figure) f.clone());
        }
        return newList;
    }
    
    public void getFiguresFromServer() {
        /*try {
            drawing.addAll(collaborationProxy.getFigures());
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }*/
        drawing.removeAllChildren();
        drawing.addAll(list);
        System.out.println("Get List lenght " + list.size());
        System.out.println("List recieved");
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
