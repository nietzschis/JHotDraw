/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.graph;

import org.jhotdraw.draw.AbstractDrawing;

/**
 *
 * @author joach
 */
public class GraphMath {
    
    private static GraphMath instance = null;

    private GraphMath() {}

    public static GraphMath getInstance() {
        if (instance == null) {
            synchronized(GraphMath.class) {
                    instance = new GraphMath();
            }
        }
        return instance;
    }

    double calcYCoordinate(double x, Graph graph, double y) {
        return y - graph.calcYCoordinate(x);
    }
}
