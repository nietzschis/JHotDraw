/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw;

/**
 *
 * @author joach
 */
public class GraphMath {
    
    // f(x) = ax + b
    // f(x) = ax^2 + bx + c
    private int a;
    private int b;
    private int c;
    
    public GraphMath(int a, int  b, int  c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    public GraphMath(int a, int  b) {
        this.a = a;
        this.b = b;
        this.c = 0;
    }
    
    public int calcYCoordinate(int x) {
        return a*x+b*x+c;
    }
    
    public int calcXCoordinate(int y) {        
        return 1;
    }
    
    
}
