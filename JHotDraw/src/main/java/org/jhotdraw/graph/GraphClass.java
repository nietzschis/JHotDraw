/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.graph;

import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JDialog;
import javax.swing.JFrame;
import static org.jhotdraw.draw.AttributeKeys.CLOSED;
import org.jhotdraw.draw.BezierFigure;
import org.jhotdraw.geom.*;

/**
 *
 * @author joach
 */
public class GraphClass extends BezierFigure {

    //private BezierPath path;
    private double graphLength;
    private double graphHeight;
    private Point2D.Double graphStartPos;
    
    
    public GraphClass() {
        path = new BezierPath();
        this.setClosed(false);
    }
     
    public BezierPath generatePath(Graph graph) {
        double x = graphStartPos.getX();
        double y = graphStartPos.getY();
        path.clear();
        GraphMath graphCalc = GraphMath.getInstance();
        for (double i = 0+x; i < 300+x; i =i+5) {
            double yPoint = graphCalc.calcYCoordinate(i-x, graph, y);
            if(yPoint < 0) {
                return path;
            }
            path.addPoint(i, yPoint);
        }
        return path;
    }
    
    @Override
    public boolean canConnect() {
        return false;
    }
    
    @Override
    public void setBounds(Point2D.Double anchor, Point2D.Double lead) {
        graphStartPos = anchor;
        
        //generatePath(graphChooser());
        generatePath(new LinearGraph(1,2, 0,300, 300));
        invalidate();
    }   
    
    @Override
    protected void drawFill(Graphics2D g) {
        super.drawFill(g);
    }

    @Override
    protected void drawStroke(Graphics2D g) {
        super.drawStroke(g);
    }
    
    public boolean isClosed() {
        return (Boolean) getAttribute(CLOSED);
    }
    public void setClosed(boolean newValue) {
        CLOSED.set(this, newValue);
    }

    @Override
    public Rectangle2D.Double getBounds() {
        Rectangle2D.Double bounds =path.getBounds2D();
        return bounds;
    }

    @Override
    public boolean contains(Point2D.Double p) {
        return super.contains(p);
    }

    @Override
    public Object getTransformRestoreData() {
        return super.getAttributesRestoreData();
    }

    @Override
    public void restoreTransformTo(Object restoreData) {
        super.restoreTransformTo(restoreData);
    }

    @Override
    public void transform(AffineTransform tx) {
        super.transform(tx);
    }    

    private Graph graphChooser() {
//        JDialog f = new JDialog();
//        f.setUndecorated(true);
//        f.setTitle("");
//        f.setType(javax.swing.JFrame.Type.UTILITY);
//        f.setModal(true);
//        f.setSize(400, 300);
//        f.setLocationRelativeTo(null);
//        //f.add(jvpc);
//        f.pack();
//        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//        f.addWindowListener(new WindowListener() {
//            //I skipped unused callbacks for readability
//            @Override
//            public void windowClosing(WindowEvent e) {
//                f.dispose();
//            }
//
//            @Override
//            public void windowOpened(WindowEvent we) {
//                //jvpc.setFocus();
//            }
//
//            @Override
//            public void windowClosed(WindowEvent we) {
//            }
//
//            @Override
//            public void windowIconified(WindowEvent we) {
//            }
//
//            @Override
//            public void windowDeiconified(WindowEvent we) {
//            }
//
//            @Override
//            public void windowActivated(WindowEvent we) {
//                f.requestFocusInWindow();
//            }
//
//            @Override
//            public void windowDeactivated(WindowEvent we) {
//               
//            }
//        });
//        f.setVisible(true);
        return null;
    }

   
}

   
