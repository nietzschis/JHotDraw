package org.jhotdraw.samples.svg.gui;

import java.awt.Color;
import static org.jhotdraw.draw.AttributeKeys.CANVAS_HEIGHT;
import static org.jhotdraw.draw.AttributeKeys.CANVAS_WIDTH;
import static org.jhotdraw.draw.AttributeKeys.FILL_COLOR;
import static org.jhotdraw.draw.AttributeKeys.STROKE_COLOR;
import static org.jhotdraw.draw.AttributeKeys.STROKE_WIDTH;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.samples.svg.figures.SVGFigure;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;

/**
 *
 * @author Karol Zdunek
 */
public class TemplateFactory  {
    
    private static final int pause = 10;
    
    public TemplateFactory() {
    }
    
    public interface TemplateDescriptor {
        void apply(Drawing d);
    }
    /**
    * Returning the given method by using string,
    * which is called in actionlisteners in ComicsToolBar.
    */
    public static TemplateDescriptor createTemplate(String temp) {
        if (temp == null) {
            return null;
        }
        switch (temp) {
            case "temp1": return d -> template1(d);
            case "temp2": return d -> template2(d);
            case "temp3": return d -> template3(d);
            case "temp4": return d -> template4(d);
            case "temp5": return d -> template5(d);
            case "temp6": return d -> template6(d);
            default: return null;
        }
    }
    
    private static void template1(Drawing d) {
        setup(d);
        int height = (int) ((CANVAS_HEIGHT.get(d) / 2) - ((pause * 3) /2 ));
        int width = (int) ((CANVAS_WIDTH.get(d) / 2) - ((pause * 3) / 2));
        int pointA = pause * 2 + height;
        int pointB = pause * 2 + width;
        newRectangle(pause, pause, width, height,d);
        newRectangle(pointB, pause, width, height,d);
        newRectangle(pause, pointA, width, height,d);
        newRectangle(pointB, pointA, width, height,d);
    }
    
    private static void template2(Drawing d) {
        setup(d);
        int height = (int) ((CANVAS_HEIGHT.get(d) / 3) - ((pause * 4) / 3));
        int width = (int) ((CANVAS_WIDTH.get(d) / 2) - ((pause * 3) / 2));
        int height1 = (int) (CANVAS_HEIGHT.get(d) - pause * 2);
        int pointA = pause * 2 + height;
        int pointB = pause * 3 + height * 2;
        int pointC = width + pause * 2;
        newRectangle(pause, pause, width, height,d);
        newRectangle(pause, pointA, width, height,d);
        newRectangle(pause, pointB, width, height,d);
        newRectangle(pointC, pause, width, height1,d);
    }
    
    private static void template3(Drawing d) {
        setup(d);
        int height = (int) ((CANVAS_HEIGHT.get(d) / 3) - ((pause * 4) / 3));
        int width = (int) (CANVAS_WIDTH.get(d) - (pause * 2));
        int pointA = pause * 2 + height;
        int pointB = pause * 3 + height * 2;
        newRectangle(pause, pause, width, height,d);
        newRectangle(pause, pointA, width, height,d);
        newRectangle(pause, pointB, width, height,d);
    }
    
    private static void template4(Drawing d) {
        setup(d);
        int height = (int) (CANVAS_HEIGHT.get(d) - (pause * 2));
        int width = (int) ((CANVAS_WIDTH.get(d) / 3) - ((pause * 4) / 3));
        int pointA = pause * 2 + width;
        int pointB = pause * 3 + width * 2;
        newRectangle(pause, pause, width, height,d);
        newRectangle(pointA, pause, width, height,d);
        newRectangle(pointB, pause, width, height,d);
    }
    
    private static void template5(Drawing d) {
        setup(d);
        int height = (int) ((CANVAS_HEIGHT.get(d) / 3) - ((pause * 4) / 3));
        int width = (int) (CANVAS_WIDTH.get(d) - (pause * 2));
        int width1 = (int) ((CANVAS_WIDTH.get(d) / 2) - (pause * 3) / 2);
        int pointA = pause * 2 + height;
        int pointB = pause * 2 + width1;
        int pointC = pause * 2 + height;
        int pointD = pause * 3 + height * 2;
        newRectangle(pause, pause, width, height,d);
        newRectangle(pause, pointA, width1, height,d);
        newRectangle(pointB, pointC, width1, height,d);
        newRectangle(pause, pointD, width, height,d);
    }
    
    private static void template6(Drawing d) {
        setup(d);
        int height = (int) ((CANVAS_HEIGHT.get(d) / 3) - ((pause * 4) / 3));
        int height1 = (int) ((CANVAS_HEIGHT.get(d) - height) - ((pause * 3)));
        int width = (int) (CANVAS_WIDTH.get(d) - (pause * 2));
        int width1 = (int) ((CANVAS_WIDTH.get(d) / 3) - ((pause * 4) / 3));
        int pointA = pause * 2 + height;
        int pointB =pause * 2 + width1;
        int pointC = pause * 3 + width1 * 2 ;
        newRectangle(pause, pause, width, height,d);
        newRectangle(pause,pointA,width1,height1,d);
        newRectangle(pointB,pointA,width1,height1,d);
        newRectangle(pointC,pointA,width1,height1,d);
    }
    
    private static SVGFigure newRectangle(int x, int y, double w, double h, Drawing d) {
        SVGFigure r = new SVGRectFigure(x, y, w, h);
        d.add(r);
        FILL_COLOR.set(r, Color.white);
        STROKE_COLOR.set(r, Color.black);
        STROKE_WIDTH.set(r, 2.0);
        
        return r;
    }  
    
   /**
    * Making the drawing place ready to draw the templates, 
    * first it clears the panel, then draws the 'frame'.
    */
    private static void setup(Drawing d) {
        d.removeAllChildren();
        newRectangle(0, 0, CANVAS_WIDTH.get(d),CANVAS_HEIGHT.get(d),d);
    }
}
