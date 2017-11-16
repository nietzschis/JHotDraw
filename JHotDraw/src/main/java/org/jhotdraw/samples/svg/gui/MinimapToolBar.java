/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author Jakob Andersen
 */
public class MinimapToolBar extends AbstractToolBar {
    
    DrawingView drawingView;
    
    /** Creates new instance. */
    public MinimapToolBar() {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        setName(labels.getString(getID() + ".toolbar"));
    }
    
    @Override
    protected String getID() {
        return "minimap";
    }
    
    @Override
    protected JComponent createDisclosedComponent(int state) {
        JPanel p = null;
        
        if(state == 1){
            p = new JPanel(){
                
                //@Override
                //public Dimension getPreferredSize() {
                //    return new Dimension(80, 80);
                //}
                
                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(Color.white);
                    g.fillRect(0, 0, 800, 800);
                    
                    g.setColor(Color.black);
                    g.drawString("Dummy panel",10,20);
                }  
            };
            
            p.setPreferredSize(new Dimension(80,80));
            //p.invalidate();
        }
        
        return p;
    }
    
    @Override
    protected int getDefaultDisclosureState() {
        return 1;
    }

    public void setDrawingView(DrawingView d) {
        drawingView = d;
    }
}
