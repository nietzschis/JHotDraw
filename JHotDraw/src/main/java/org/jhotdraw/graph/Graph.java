/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.graph;

import org.jhotdraw.draw.PredefinedFunction;

/**
 *
 * @author Joachim
 */
public interface Graph extends PredefinedFunction {

    double calcYCoordinate(double x);
}
