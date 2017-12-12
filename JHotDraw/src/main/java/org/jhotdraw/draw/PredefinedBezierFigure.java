/*
 * @(#)BezierFigure.java 3.2  2008-07-06
 *
 * Copyright (c) 1996-2008 by the original authors of JHotDraw
 * and all its contributors.
 * All rights reserved.
 *
 * The copyright of this software is owned by the authors and  
 * contributors of the JHotDraw project ("the copyright holders").  
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * the copyright holders. For details see accompanying license terms. 
 */

package org.jhotdraw.draw;



/**
 * A BezierFigure can be used to draw arbitrary shapes using a <code>BezierPath</code>.
 */
public abstract class PredefinedBezierFigure extends BezierFigure {

    public abstract void setFunction(PredefinedFunction path);

}