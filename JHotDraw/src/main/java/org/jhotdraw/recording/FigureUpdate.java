/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.recording;

import org.jhotdraw.draw.Figure;

public class FigureUpdate {
    private final Figure figure;
    private final int hash;
    
    public FigureUpdate(Figure figure, int hash) {
        this.hash = hash;
        this.figure = figure;
    }
    
    public Figure getFigure() {
        return figure;
    }
    
    public int getHash() {
        return hash;
    }
}