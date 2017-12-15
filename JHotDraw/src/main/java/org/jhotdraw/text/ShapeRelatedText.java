/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.text;

import java.awt.Shape;

/**
 *
 * @author sebastian
 */

/*
Interface for implementing different strategies for drawing drawing text along a shape.
*/
interface ShapeRelatedText {
    Shape textShape(Shape shape);
}
