/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.net.URL;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.GeometryEdit;
import org.jhotdraw.draw.action.MoveAction;

/**
 *
 * @author Gyhuji
 */
public class SelectionShadowsIcon extends javax.swing.ImageIcon{
    
    private DrawingEditor editor;
    private AttributeKey<Double> shadowKey;
    private AttributeKey<Color> fillColorKey;
    private AttributeKey<Color> strokeColorKey;
    private Shape fillShape;
    private Shape strokeShape;

    /** Creates a new instance.
     * @param editor The drawing editor.
     * @param opacityKey The opacityKey of the default attribute
     * @param imageLocation the icon image
     * @param fillShape The shape to be drawn with the fillColor of the default
     * attribute.
     */
    public SelectionShadowsIcon(
            DrawingEditor editor,
            AttributeKey<Double> shadowKey,
            AttributeKey<Color> fillColorKey,
            AttributeKey<Color> strokeColorKey,
            URL imageLocation,
            Shape fillShape,
            Shape strokeShape) {
        super(imageLocation);
        this.editor = editor;
        this.shadowKey = shadowKey;
        this.fillColorKey = fillColorKey;
        this.strokeColorKey = strokeColorKey;
        this.fillShape = fillShape;
        this.strokeShape = strokeShape;
    }

    public SelectionShadowsIcon(
            DrawingEditor editor,
            AttributeKey<Double> shadowKey,
            AttributeKey<Color> fillColorKey,
            AttributeKey<Color> strokeColorKey,
            Image image,
            Shape fillShape,
            Shape strokeShape) {
        super(image);
        this.editor = editor;
        this.shadowKey = shadowKey;
        this.fillColorKey = fillColorKey;
        this.strokeColorKey = strokeColorKey;
        this.fillShape = fillShape;
        this.strokeShape = strokeShape;
    }

    @Override
    public void paintIcon(java.awt.Component c, java.awt.Graphics gr, int x, int y) {
        Graphics2D g = (Graphics2D) gr;
        super.paintIcon(c, g, x, y);
        Double shadow;
        Color fillColor;
        Color strokeColor;
        DrawingView view = editor.getActiveView();
        
        if(view != null && view.getSelectedFigures().size() == 1){
            
            shadow = shadowKey.get(view.getSelectedFigures().iterator().next());
            fillColor = (fillColorKey == null) ? null : fillColorKey.get(view.getSelectedFigures().iterator().next());
            strokeColor = (strokeColorKey == null) ? null : strokeColorKey.get(view.getSelectedFigures().iterator().next());
            //AttributeKeys.SHADOWS.set(view.getSelectedFigures().iterator().next(), new Color((((int) 
                        //(shadow * 255)) << 24) | (fillColor.getRGB() & 0xffffff), true));
                
        } else {
            shadow = shadowKey.get(editor.getDefaultAttributes());
            fillColor = (fillColorKey == null) ? null : fillColorKey.get(editor.getDefaultAttributes());
            strokeColor = (strokeColorKey == null) ? null : strokeColorKey.get(editor.getDefaultAttributes());
        }
        
        
        if(!shadow.equals(0d)){
                System.out.println("ShadowValue = " + shadow);
                //Figure afigure = (Figure) view.getSelectedFigures().iterator().next().clone();
                //g.draw3DRect(c.getX()+2, c.getY()+2, c.getWidth(), c.getHeight(), true);
                
                MoveAction move;
                
                g.translate(x+29, y+29);
                fillColor = Color.BLACK;
                g.setColor(Color.BLACK);
                g.fill(fillShape);
                g.draw(strokeShape);
                g.translate(-x, -y);
                
                
                
                
               
                //new GeometryEdit(afigure, geom, geom);
                //Figure clone = (Figure) view.getSelectedFigures().iterator().next().clone();
                //Figure bfigure = (Figure) afigure.clone();
                
                //g.draw3DRect(c.getX()+2, c.getY()+2, c.getWidth(), c.getHeight(), true);        
        }
        
//            if (view != null && view.getSelectedFigures().size() == 1) {
//                g.draw3DRect(c.getX()+2, c.getY()+2, c.getWidth(), c.getHeight(), true);
//                shadow = shadowKey.get(view.getSelectedFigures().iterator().next());
//                fillColor = (fillColorKey == null) ? null : fillColorKey.get(view.getSelectedFigures().iterator().next());
//                strokeColor = (strokeColorKey == null) ? null : strokeColorKey.get(view.getSelectedFigures().iterator().next());
//            } else {
//                shadow = shadowKey.get(editor.getDefaultAttributes());
//                fillColor = (fillColorKey == null) ? null : fillColorKey.get(editor.getDefaultAttributes());
//                strokeColor = (strokeColorKey == null) ? null : strokeColorKey.get(editor.getDefaultAttributes());
//            }
//
//        if (fillColorKey != null && fillShape != null) {
//            if (shadow != null) {
//                if (fillColor == null) {
//                    fillColor = Color.BLACK;
//                }
//                g.setColor(new Color((((int) (shadow * 255)) << 24) | (fillColor.getRGB() & 0xffffff), true));
//                g.translate(x, y);
//                g.fill(fillShape);
//                g.translate(-x, -y);
//            }
//            }
//        if (strokeColorKey != null && strokeShape != null) {
//            if (shadow != null) {
//                if (strokeColor == null) {
//                    strokeColor = Color.BLACK;
//                }
//                g.setColor(new Color((((int) (shadow * 255)) << 24) | (strokeColor.getRGB() & 0xffffff), true));
//                g.translate(x, y);
//                g.draw(strokeShape);
//                g.translate(-x, -y);
//            }
//        }
    }
    
}
