/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw;

import java.awt.event.MouseEvent;

/**
 *
 * @author karim
 */
public class FigurePainter {
    
    public Figure paint(Figure figure, MouseEvent event, DrawingEditor editor){
        
       switch(event.getButton()){
           case 1:
               System.out.println("im pressed");
           case 2:
               System.out.println("im pressed");
       }
          return null;
    }
    
}
