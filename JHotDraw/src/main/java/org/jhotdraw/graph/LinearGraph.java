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
    private double startX;
    private double endX;
    private double length;
    
    public LinearGraph (double a, double b, double startX, double endX, double length) {
        this.a = a;
        this.b = b;
        this.startX = startX;
        this.endX = endX;
        this.length = length;
    }
    
    @Override
    public double calcYCoordinate(double x) {
        return a*x+b;
    }

    @Override
    public double calcXCoordinate(double y) {
        return y;
    }

    @Override
    public double getStartX() {
        return startX;
    }

    @Override
    public double getEndX() {
        return endX;
    }

    @Override
    public double getLength() {
        return length;
    }
    
}
