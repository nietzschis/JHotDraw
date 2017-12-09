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
public class QuadraticGraph implements Graph {

    private double a;
    private double b;
    private double c;
    
    public QuadraticGraph (double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    @Override
    public double calcYCoordinate(double x) {
        return a*Math.pow(x, 2)+b*x+c;
    }

    @Override
    public double getLength() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
