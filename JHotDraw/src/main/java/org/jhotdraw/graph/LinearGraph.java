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
    private double length;
    
    public LinearGraph (double a, double b, double length) {
        this.a = a;
        this.b = b;
        this.length = length;
    }

    LinearGraph() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public double calcYCoordinate(double x) {
        return a*x+b;
    }

    @Override
    public double getLength() {
        return length;
    }
    
}
