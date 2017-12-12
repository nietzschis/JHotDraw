/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.text;

import java.awt.geom.*;
import java.awt.Font;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
/**
 *
 * @author sebastian
 */
public class StartLineTextStrategy implements ShapeRelatedText{
    private Font font;
    private String text;
    
    private  AffineTransform transform = new AffineTransform();
    
    private float flatness = 1;
    
    public StartLineTextStrategy(){
        this.text="q";
        this.font= new Font ("Garamond", Font.BOLD | Font.ITALIC , 30);
    }
    
    public StartLineTextStrategy(String text, Font font){
        this.font = font == null ? new Font ("Garamond", Font.BOLD | Font.ITALIC , 30) : font;
        this.text = text== null ? "" : text;
    }
    
    public Shape textShape(Shape shape){
        GeneralPath textPath = new GeneralPath();
        if (text == null || font==null || shape== null){
            return textPath;
        }
        FontRenderContext fontrc = new FontRenderContext(null,true,true);
        GlyphVector textVector = font.createGlyphVector(fontrc, text);
        PathIterator iterator = new FlatteningPathIterator( shape.getPathIterator(null), flatness );
        pathTextVars vars = new pathTextVars();
        vars.strLen = textVector.getNumGlyphs();
        if (vars.strLen == 0){
            return textPath;
        }
        while(vars.currentChr < vars.strLen && !iterator.isDone()){
            vars.segType = iterator.currentSegment(vars.coords);
            switch(vars.segType){
                case PathIterator.SEG_MOVETO:
                    moveTo(vars, textVector, textPath);
                    break;
                    
                case PathIterator.SEG_LINETO:
                    lineTo(vars, textVector, textPath);
                    break;
            }
            iterator.next();
        }
        return textPath;
    }
    
    
    private void moveTo(pathTextVars vars, GlyphVector textVector, GeneralPath textPath){
        vars.moveX = vars.lastX = vars.coords[0];
        vars.moveY = vars.lastY = vars.coords[1];
        textPath.moveTo(vars.moveX, vars.moveY);
        vars.nextAdv=textVector.getGlyphMetrics(vars.currentChr).getAdvance()*0.5f;
        vars.next = vars.nextAdv;
    }
    
    private void lineTo(pathTextVars vars, GlyphVector textVector, GeneralPath textPath){
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

}
