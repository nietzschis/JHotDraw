package org.jhotdraw.samples.svg.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;
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
    
    private Drawing drawing;
    private final List<MinimapViewListener> listeners = new LinkedList<>();

    /**
     * Creates a new view to draw a {@link Drawing} onto the minimap.
     */
    public MinimapView() {
        setName("minimapView");
        
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //ignored
            }

            @Override
            public void mousePressed(MouseEvent e) {
                handleEvent(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //ignored
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //ignored
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //ignored
            }
        });
        
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                handleEvent(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                //ignored
            }
        });
    }
    
    /**
     * Handels mouse events.
     * @param e describes the event
     */
    private void handleEvent(MouseEvent e){    
        Dimension minimapSize = e.getComponent().getPreferredSize();
        Rectangle2D.Double canvasSize = getCanvasSize();
        double largerSide = Math.max(canvasSize.width, canvasSize.height);
        
        Point.Double p = new Point.Double(e.getPoint().getX(), e.getPoint().getY());
        p.setLocation((p.getX()*largerSide)/(minimapSize.width*canvasSize.width), (p.getY()*largerSide)/(minimapSize.height*canvasSize.height)); // Center the point relative.
        p.setLocation(constrain(p.x, 0, 1), constrain(p.y, 0, 1)); // constrain the values to be within the container.
        
        notifyListeners(p);
    }
    
    private double constrain(double value, double min, double max) {
        return Math.min(Math.max(value, min), max);
    }
    
    /**
     * Notify listeners that a click occured.
     * @param p The relative coordinates describing where the click occured.
     */
    private void notifyListeners(Point2D.Double p) {
        for(MinimapViewListener listener: listeners){
            listener.relativeOnClick(p);
        }
    }
    
    /**
     * Add a new listener to be invoked, whenever an event occurs.
     * @param listener The event listener.
     */
    public void addListener(MinimapViewListener listener){
        listeners.add(listener);
    }
    
    /**
     * Removes a listener, or noop if the listner is not registered on this instance.
     * @param listener The event listener.
     * @return True, if the listener was registered, false otherwise.
     */
    public boolean removeListener(MinimapViewListener listener){
        return listeners.remove(listener);
    }
    
    @Override
    public void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        
        Graphics2D g = (Graphics2D) gr.create();
        
        Rectangle2D.Double size = getCanvasSize();
        
        if(getDrawing() == null || size.width == 0 || size.height == 0){ // if drawing is not available or have a size of 0, paint the minimap white and return.
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
     * Returns the rectangle of the canvas, wich the minimap should draw.
     * For fixed size canvas, this is full canvas located at (0,0).
     * For dynamic this is the smallest possible rectangle located so the most left-top figure is just within.
     * @return the pratical size of the canvas.
     */
    private Rectangle2D.Double getCanvasSize(){
        if(getCanvasWidth() != null && getCanvasHeight() != null){
            return new Rectangle2D.Double(0, 0, getCanvasWidth(), getCanvasHeight());
        }else{
            return getSmallestSize();
        }
    }
    
    /**
     * Get the current {@link Drawing} to display on the minimap.
     * @return The {@link Drawing}, or null if not available.
     */
    private Drawing getDrawing(){
        return drawing;
    }
    
    void setDrawing(Drawing drawing){
        this.drawing = drawing;
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
        if(getDrawing() == null)
            return null;
        return CANVAS_WIDTH.get(getDrawing());
    }
    
    /**
     * 
     * @return The height of {@link Drawing}.
     */
    private Double getCanvasHeight(){
        if(getDrawing() == null)
            return null;
        return CANVAS_HEIGHT.get(getDrawing());
    }
    
    /**
     * If the canvas does not have a fixed size, this function is used to calculate the size and position of smallest rectange able to contain all figures on the {@link Drawing}.
     * @return The rectangle that can fit no more than all figures.
     */
    private Rectangle2D.Double getSmallestSize(){
        
        if (getDrawing() == null || getDrawing().getChildren().isEmpty()){
            return new Rectangle2D.Double(1,1,1,1);
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
        
        // add 10% to border
        smallestContainer.x -= smallestContainer.width*0.1;
        smallestContainer.y -= smallestContainer.height*0.1;
        smallestContainer.width *= 1.2;
        smallestContainer.height *= 1.2;
        
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
