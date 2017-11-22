/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import org.jhotdraw.draw.AttributeKeys;
import static org.jhotdraw.draw.AttributeKeys.CANVAS_FILL_COLOR;
import static org.jhotdraw.draw.AttributeKeys.CANVAS_FILL_OPACITY;
import static org.jhotdraw.draw.AttributeKeys.CANVAS_HEIGHT;
import static org.jhotdraw.draw.AttributeKeys.CANVAS_WIDTH;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.Figure;

/**
 *
 * @author Jakob Andersen
 * 
 * A view to draw a {@link Drawing} onto the minimap.
 */
public class MinimapView extends JPanel{
    
    AbstractToolBar toolBar;

    /**
     * Creates a new view to draw a {@link Drawing} onto the minimap.
     * @param toolBar The {@link AbstractToolBar} used to disply the drawing. A reference to {@link Drawing} is obatained througt the toolbar.
     */
    public MinimapView(AbstractToolBar toolBar) {
        assert toolBar != null;
        this.toolBar = toolBar;
    }
    
    @Override
    public void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        
        Graphics2D g = (Graphics2D) gr.create();
        
        Rectangle2D.Double size;
        if(getCanvasWidth() != null && getCanvasHeight() != null){
            size = new Rectangle2D.Double(0, 0, getCanvasWidth(), getCanvasHeight());
        }else{
            size = getSmallestSize();
        }
        
        if(getDrawing() == null || size.width == 0 || size.height == 0){ // if drawing is not available or it doesn't have a size, paint the minimap white and return.
            g.setColor(Color.white);
            g.fillRect(0, 0, getPreferredSize().width, getPreferredSize().height);
            
            g.dispose();
            return;
        }
        
        // background
        double scale = getDownScale(size, new Rectangle2D.Double(0,0,getPreferredSize().width, getPreferredSize().height));
        g.setColor(getBackgroundColor());
        g.fillRect(0, 0, (int) (size.width*scale), (int) (size.height*scale));
        
        g.translate(-size.x*scale, -size.y*scale);
        g.scale(scale, scale);
        getDrawing().setFontRenderContext(g.getFontRenderContext());
        getDrawing().draw(g);
                
        g.dispose();
    }
    
    /**
     * Get the current {@link Drawing} to display on the minimap.
     * @return The {@link Drawing}, or null if not available.
     */
    private Drawing getDrawing(){
        if(toolBar.getEditor() != null && toolBar.getEditor().getActiveView() != null){
            return toolBar.getEditor().getActiveView().getDrawing();
        }
        
        return null;
    }
    
    /**
     * Get the background color of the current {@link Drawing}.
     * @return The background color.
     */
    private Color getBackgroundColor(){
        Color canvasColor = CANVAS_FILL_COLOR.get(getDrawing());
        if (canvasColor != null) {
            canvasColor = new Color((canvasColor.getRGB() & 0xffffff) | ((int) (CANVAS_FILL_OPACITY.get(getDrawing()) * 255) << 24), true);
        }
        
        return canvasColor;
    }
    
    /**
     * 
     * @return The width of {@link Drawing}.
     */
    private Double getCanvasWidth(){
        return CANVAS_WIDTH.get(getDrawing());
    }
    
    /**
     * 
     * @return The height of {@link Drawing}.
     */
    private Double getCanvasHeight(){
        return CANVAS_HEIGHT.get(getDrawing());
    }
    
    /**
     * If the canvas does not have a fixed size, this function is used to calculate the size and position of smallest rectange able to contain all figures on the {@link Drawing}.
     * @return The rectangle that can fit no more than all figures.
     */
    private Rectangle2D.Double getSmallestSize(){
        
        if (getDrawing() != null && getDrawing().getChildren().isEmpty()){
            return new Rectangle2D.Double();
        }
        
        Rectangle2D.Double smallestContainer = new Rectangle2D.Double();
        Double minX = Double.MAX_VALUE;
        Double minY = Double.MAX_VALUE;
        for(Figure f: getDrawing().getChildren()){
            Rectangle2D.Double r = f.getBounds();
            if (AttributeKeys.TRANSFORM.get(f) != null) {
                Rectangle2D rt = AttributeKeys.TRANSFORM.get(f).createTransformedShape(r).getBounds2D();
                r = (rt instanceof Rectangle2D.Double) ? (Rectangle2D.Double) rt : new Rectangle2D.Double(rt.getX(), rt.getY(), rt.getWidth(), rt.getHeight());
            }
            if (f.isVisible()) {
                if(smallestContainer.getWidth() < r.getX() + r.getWidth()){
                    smallestContainer.width = r.getX() + r.getWidth();
                }
                
                if(smallestContainer.getHeight()< r.getY() + r.getHeight()){
                    smallestContainer.height = r.getY() + r.getHeight();
                }
                
                if(minX > r.getX()){
                    minX = r.getX();
                }
                
                if(minY > r.getY()){
                    minY = r.getY();
                }
            }
        }
        
        minX = Math.max(0, minX); // skip area outside of canvas
        minY = Math.max(0, minY);
        
        smallestContainer.x = minX;
        smallestContainer.y = minY;
        smallestContainer.width -= minX;
        smallestContainer.height -= minY;
        return smallestContainer;
    }
    
    /**
     * Calcualtes a factor, this can be multiplied onto elements of the {@link Drawing}, to get the size fitting the minimap.
     * @param large The size of the canvas.
     * @param small The size of the minimap.
     * @return A factor.
     */
    private double getDownScale(Rectangle2D.Double large, Rectangle2D.Double small){
        if(large.width/small.width > large.height/small.height){ // decide what side is largerst
            return small.width/large.width;
        }else{
            return small.height/large.height;
        }
    }
}
