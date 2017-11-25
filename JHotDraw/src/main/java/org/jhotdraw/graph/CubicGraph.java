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
public class CubicGraph implements Graph  {

    private double a;
    private double b;
    private double c;
    private double d;
    
    public CubicGraph (double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
    
    @Override
    public double calcYCoordinate(double x) {
        return a*Math.pow(x, 3)+b*Math.pow(x, 2)+c*x+d;
    }

    @Override
    public double calcXCoordinate(double y) {
        return 1;
    }

    @Override
    public double getStartX() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getEndX() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getLength() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
