package org.jhotdraw.collaboration.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.n3.nanoxml.IXMLElement;
import net.n3.nanoxml.IXMLParser;
import net.n3.nanoxml.IXMLReader;
import net.n3.nanoxml.StdXMLReader;
import net.n3.nanoxml.XMLException;
import net.n3.nanoxml.XMLParserFactory;
import org.jhotdraw.collaboration.common.IRemoteObservable;
import org.jhotdraw.collaboration.common.IRemoteObserver;
import org.jhotdraw.draw.Figure;
import static org.jhotdraw.samples.svg.SVGConstants.SVG_NAMESPACE;

/**
 *
 * @author Niels
 */
public class RemoteObservable extends UnicastRemoteObject implements IRemoteObservable {
    
    private Set<IRemoteObserver> collaborators;
    private String path = "org.jhotdraw.collaboration.config";
    
    public RemoteObservable() throws RemoteException {
        super();
        collaborators = new LinkedHashSet<>();
        try {
            readConfig();
        }
        catch (IOException ex) {
            Logger.getLogger(RemoteObservable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void readConfig() throws FileNotFoundException, IOException {
        IXMLParser parser;
        try {
            parser = XMLParserFactory.createDefaultXMLParser();
        } catch (Exception ex) {
            InternalError e = new InternalError("Unable to instantiate NanoXML Parser");
            e.initCause(ex);
            throw e;
        }
        IXMLReader reader = new StdXMLReader(new FileInputStream(path));
        parser.setReader(reader);
        IXMLElement document;
        try {
            document = (IXMLElement) parser.parse();
        } catch (XMLException ex) {
            IOException e = new IOException(ex.getMessage());
            e.initCause(ex);
            throw e;
        }
        
        IXMLElement svg = document;
        Stack<Iterator<IXMLElement>> stack = new Stack<Iterator<IXMLElement>>();
        LinkedList<IXMLElement> ll = new LinkedList<IXMLElement>();
        ll.add(document);
        stack.push(ll.iterator());
        while (!stack.empty() && stack.peek().hasNext()) {
            Iterator<IXMLElement> iter = stack.peek();
            IXMLElement node = iter.next();
            
            System.out.println(node.getAttributeCount());

            Iterator<IXMLElement> children = (node.getChildren() == null) ? null : node.getChildren().iterator();

            if (!iter.hasNext()) {
                stack.pop();
            }
            if (children != null && children.hasNext()) {
                stack.push(children);
            }
            if (node.getName() != null &&
                    node.getName().equals("svg") &&
                    (node.getNamespace() == null ||
                    node.getNamespace().equals(SVG_NAMESPACE))) {
                svg = node;
                break;
            }
        }
    }

    @Override
    public void addCollaborator(IRemoteObserver collaborator) throws RemoteException {
        collaborators.add(collaborator);
    }

    @Override
    public void removeCollaborator(IRemoteObserver collaborator) throws RemoteException {
        collaborators.remove(collaborator);
    }

    @Override
    public void notifyAllCollaborators(List<Figure> figures) throws RemoteException {
        collaborators.parallelStream().forEach(collaborator -> {
            try {
                collaborator.update(figures);
            }
            catch(RemoteException e) {
                System.out.println("Exception while updating client " + collaborator + ": " + e);
            }
        });
    }
    
    public void run() {
        int PORT = 0;
        String NAME = "";
        try {
            LocateRegistry.createRegistry(PORT).bind(NAME, new RemoteObservable());
        }
        catch (RemoteException ex) {
            Logger.getLogger(RemoteObservable.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (AlreadyBoundException ex) {
            Logger.getLogger(RemoteObservable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
