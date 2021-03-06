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
    private int length;
    
    public LinearGraph (double a, double b, int length) {
        this.a = a;
        this.b = b;
        this.length = length;
    }
    
    @Override
    public double calcYCoordinate(double x) {
        return a*x+b;
    }

    @Override
    public int getLength() {
        return length;
    }
    
}
