package org.jhotdraw.samples.svg.action;

import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.samples.svg.figures.SVGBezierFigure;

/**
 *
 * @author k0ngen
 */

interface SplitOptions {

    boolean isClosed(SVGBezierFigure svgBezierFigure);

    boolean isALine(SVGBezierFigure svgBezierFigure);

    int line(SVGBezierFigure svgBezierFigure, DrawingView view);

    int fromCenter(SVGBezierFigure svgBezierFigure, DrawingView view);
}
