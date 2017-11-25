/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.graph;

/**
 *
 * @author Joachim
 */
public interface Graph {
    
    public double getStartX();
    
    public double getEndX();
    
    public double getLength();
    
    public double calcYCoordinate(double x);      
      
    
    public double calcXCoordinate(double y);
}
