/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.figures;

/**
 *
 * @author Mathias
 */

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import org.jhotdraw.app.JHotDrawFeatures;
import org.jhotdraw.draw.*;
import static org.jhotdraw.samples.svg.SVGAttributeKeys.*;
import org.jhotdraw.samples.svg.*;
import org.jhotdraw.geom.*;


public class SVGTriangleFigure extends SVGAttributedFigure implements SVGFigure {

    /** Creates a new instance. */
    public SVGTriangleFigure() {

    }

    protected void drawFill(Graphics2D g) {

    }
    
    protected void drawStroke(Graphics2D g) {

    }

    public Rectangle2D.Double getBounds() {
        return null;
    }

    public boolean contains(Point2D.Double p) {
        return true;
    }

    public void transform(AffineTransform tx) {

    }
 
    public void restoreTransformTo(Object geometry) {

    }

    public Object getTransformRestoreData() {
        return null;
    }

    public boolean isEmpty() {
        return true;
    }


}
