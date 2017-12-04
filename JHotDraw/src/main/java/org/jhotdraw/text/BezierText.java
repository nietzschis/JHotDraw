/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.text;

import java.awt.*;
import java.awt.geom.*;
import java.awt.font.GlyphVector;
import org.jhotdraw.geom.*;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Path2D; 
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jhotdraw.geom.BezierPath.Node;
/**
 *
 * @author sebastian
 */
public class BezierText extends JPanel {
   /* private String text;
    private Font font; */
    private Font font = new Font ("Garamond", Font.BOLD | Font.ITALIC , 30);
    private String text = "Testing jaja";
    
    private  AffineTransform transform = new AffineTransform();
    
    private float flatness = 1;
    
    public Shape pathText(Shape shape){
        FontRenderContext fontrc = new FontRenderContext(null,true,true);
        GlyphVector textVector = font.createGlyphVector(fontrc, text);
        
        GeneralPath textPath = new GeneralPath();
        PathIterator iterator = new FlatteningPathIterator( shape.getPathIterator(null), flatness );
        
        pathTextVars vars = new pathTextVars();
        vars.strLen = textVector.getNumGlyphs();
        
        if (vars.strLen == 0){
            return textPath;
        }
        
        
        while(vars.currentChr < vars.strLen && !iterator.isDone()){
            vars.segType = iterator.currentSegment(vars.coords);
            System.out.print(ui);
            switch(vars.segType){
                case PathIterator.SEG_MOVETO:
                    vars.moveX = vars.lastX = vars.coords[0];
                    vars.moveY = vars.lastY = vars.coords[1];
                    textPath.moveTo(vars.moveX, vars.moveY);
                    vars.nextAdv=textVector.getGlyphMetrics(vars.currentChr).getAdvance()*0.5f;
                    vars.next = vars.nextAdv;
                    break;
                    
                case PathIterator.SEG_LINETO:
                    vars.thisX = vars.coords[0];
                    vars.thisY = vars.coords[1];
                    float difX = vars.thisX - vars.lastX;
                    float difY = vars.thisY - vars.lastY;
                    float dist = (float)Math.sqrt(difY * difY + difX * difX);
                    if (dist>= vars.next){
                        float a = 1.0f/dist;
                        float angle = (float)Math.atan2(difY, difX);
                        while(vars.currentChr<vars.strLen && dist>=vars.next){
                            float adv = vars.nextAdv;
                            vars.nextAdv = vars.currentChr< vars.strLen - 1 ? textVector.getGlyphMetrics(vars.currentChr+1).getAdvance() * 0.5f : 0;
                            transform.setToTranslation(vars.lastX+vars.next*a*difX, vars.lastY+ vars.next*a*difY);
                            transform.rotate(angle);
                            
                            Point2D point = textVector.getGlyphPosition(vars.currentChr);
                            transform.translate((float)-point.getX()-adv, (float)-point.getY());
                            textPath.append(transform.createTransformedShape(textVector.getGlyphOutline(vars.currentChr)), false);
                            vars.next += adv+vars.nextAdv;
                            vars.currentChr++;
                        }
                    }
                    vars.next -=dist;
                    vars.lastX= vars.thisX;
                    vars.lastY= vars.thisY;
            }
            iterator.next();
        }
        return textPath;
    }
    
    private class pathTextVars{
        float moveX =0, moveY = 0;
        float lastX = 0, lastY = 0;
        float thisX = 0, thisY = 0;
        float coords[] = new float[6];
        int segType = 0;
        float next = 0;
        int currentChr = 0;
        int strLen = 0;
        float nextAdv = 0;
    }

    
    //below is for testing
    
    
    public void paint(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
 
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
 
    String s = "M";
    Font font = new Font("Serif", Font.PLAIN, 50);
    FontRenderContext frc = g2.getFontRenderContext();
    g2.translate(40, 80);
 
    GlyphVector gv = font.createGlyphVector(frc, s);
    int length = gv.getNumGlyphs();
    for (int i = 0; i < length; i++) {
      Point2D p = gv.getGlyphPosition(i);
      double theta = (double) i / (double) (length - 1) * Math.PI / 4;
      AffineTransform at = AffineTransform.getTranslateInstance(p.getX(),
          p.getY());
      at.rotate(theta);
      Shape glyph = gv.getGlyphOutline(i);
      g2.fill(glyph);
      Shape transformedGlyph = pathText(glyph);
      g2.fill(transformedGlyph);
      Path2D a = new Path2D.Float();
      a.moveTo(50, 50);
      a.curveTo(100, 50, 125, 100, 50, 100);
      g2.draw(a);
      g2.fill(pathText(a));
    }
  }
 
  public static void main(String[] args) {
    JFrame f = new JFrame("RollingText v1.0");
    f.getContentPane().add(new BezierText());
    f.setSize(600, 300);
    f.setVisible(true);
  }
}
