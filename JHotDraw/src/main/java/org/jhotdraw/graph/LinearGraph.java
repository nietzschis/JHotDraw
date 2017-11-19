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
public class LinearGraph implements Graph {

    private double a;
    private double b;
    
    public LinearGraph (double a, double b) {
        this.a = a;
        this.b = b;
    }
    
    @Override
    public double calcYCoordinate(double x) {
        return a*x+b;
    }

    @Override
    public double calcXCoordinate(double y) {
        return y;
    }
    
}
