package org.jhotdraw.samples.svg.action;

import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.geom.BezierPath;
import org.jhotdraw.samples.svg.figures.SVGBezierFigure;

import java.awt.geom.Point2D;

/**
 *
 * @author k0ngen
 */

public class Split implements SplitOptions {

    @Override
    public boolean isClosed(SVGBezierFigure svgBezierFigure) {
        return svgBezierFigure.isClosed();
    }

    @Override
    public boolean isALine(SVGBezierFigure svgBezierFigure) {
        return svgBezierFigure.getNodeCount() == 2;
    }

    @Override
    public int line(SVGBezierFigure svgBezierFigure, DrawingView view) {
        if (!isALine(svgBezierFigure))
            return -1;
        
        try {
            notifyViewAndFigureOfChange(svgBezierFigure, view);

            SVGBezierFigure secondBezierFigure = CreateAndInitializeNewBezierFigure(svgBezierFigure);

            svgBezierFigure.setEndPoint(svgBezierFigure.getCenter());

            view.getDrawing().add(secondBezierFigure);

            notifyViewAndFigureThatAChangeHappened(svgBezierFigure, view);

        } catch (Exception e) {
            System.out.println("An error happened in the method splitLine\n");
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    private SVGBezierFigure CreateAndInitializeNewBezierFigure(SVGBezierFigure svgBezierFigure) {
        int moveFactor = 10;

        SVGBezierFigure newSvgBezierFigure = new SVGBezierFigure();

        Point2D.Double startOfNewFigure =
                new Point2D.Double(svgBezierFigure.getCenter().getX() + moveFactor,
                                   svgBezierFigure.getCenter().getY());

        Point2D.Double endPointOfNewFigure =
                new Point2D.Double(svgBezierFigure.getEndPoint().getX() + moveFactor,
                                   svgBezierFigure.getEndPoint().getY());


        newSvgBezierFigure.setStartPoint(startOfNewFigure);
        newSvgBezierFigure.setEndPoint(endPointOfNewFigure);

        return newSvgBezierFigure;
    }

    @Override
    public int fromCenter(SVGBezierFigure svgBezierFigure, DrawingView view) {
        try {
            notifyViewAndFigureOfChange(svgBezierFigure, view);

            int centerIndex = findIndexOfNodeClosestToCenter(svgBezierFigure);

            svgBezierFigure = addCenterNodeToSplitFrom(svgBezierFigure, centerIndex);

            SVGBezierFigure secondBezierFigure = new SVGBezierFigure();
            secondBezierFigure = addNodesAndStartEndPointsToFigure(svgBezierFigure, secondBezierFigure, centerIndex);

            svgBezierFigure = removeNodesAfterCenter(svgBezierFigure, centerIndex);
            svgBezierFigure.setEndPoint(svgBezierFigure.getPoint(svgBezierFigure.getNodeCount() - 1));

            view.getDrawing().add(secondBezierFigure);

            if (figuresTouch(svgBezierFigure, secondBezierFigure))
                moveSecondFigure(secondBezierFigure);

            notifyViewAndFigureThatAChangeHappened(svgBezierFigure, view);

        } catch (Exception e) {
            System.out.println("An error happened in the method splitFromCenter\n");
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    private void notifyViewAndFigureOfChange(SVGBezierFigure svgBezierFigure, DrawingView view) {
        svgBezierFigure.willChange();
        view.getDrawing().willChange();
    }

    private SVGBezierFigure addCenterNodeToSplitFrom(SVGBezierFigure svgBezierFigure, int centerIndex) {
        BezierPath.Node centerNode = new BezierPath.Node(svgBezierFigure.getCenter().getX(),
                svgBezierFigure.getCenter().getY());

        svgBezierFigure.addNode(centerIndex, centerNode);

        return svgBezierFigure;
    }

    private int findIndexOfNodeClosestToCenter(SVGBezierFigure svgBezierFigure) {
        Point2D.Double centerPoint = svgBezierFigure.getCenter();

        for (int i = 0; i < svgBezierFigure.getNodeCount(); i++) {
            Point2D.Double currentPoint = svgBezierFigure.getPoint(i);

            if (isEqualsOrLessThanCenter(currentPoint, centerPoint))
                continue;

            return i;
        }
        return -1;
    }

    private boolean isEqualsOrLessThanCenter(Point2D.Double currentPoint, Point2D.Double centerPoint) {
        return currentPoint == centerPoint || currentPoint.getX() < centerPoint.getX();
    }

    private SVGBezierFigure addNodesAndStartEndPointsToFigure(SVGBezierFigure svgBezierFigure, SVGBezierFigure secondBezierFigure, int start) {
        secondBezierFigure = addNodesFromCenter(svgBezierFigure, secondBezierFigure, start);
        secondBezierFigure = setStartAndEndPoints(svgBezierFigure, secondBezierFigure);

        return secondBezierFigure;
    }

    private SVGBezierFigure addNodesFromCenter(SVGBezierFigure svgBezierFigure, SVGBezierFigure secondBezierFigure, int start) {
        int limit = svgBezierFigure.getNodeCount();

        for(int i = start; i < limit; i++)
            secondBezierFigure.addNode(svgBezierFigure.getNode(i));

        return secondBezierFigure;
    }

    private SVGBezierFigure setStartAndEndPoints(SVGBezierFigure svgBezierFigure, SVGBezierFigure secondBezierFigure) {
        Point2D.Double endPoint = svgBezierFigure.getEndPoint();

        secondBezierFigure.setStartPoint(secondBezierFigure.getPoint(0));
        secondBezierFigure.setEndPoint(endPoint);

        return secondBezierFigure;
    }

    private SVGBezierFigure removeNodesAfterCenter(SVGBezierFigure svgBezierFigure, int limit) {
        int start = svgBezierFigure.getNodeCount() - 1;

        for(int i = start; i > limit; i--)
            svgBezierFigure.removeNode(i);

        return svgBezierFigure;
    }

    private boolean figuresTouch(SVGBezierFigure svgBezierFigure, SVGBezierFigure secondBezierFigure) {
        return svgBezierFigure.getEndPoint().getX() == secondBezierFigure.getStartPoint().getX() &&
                svgBezierFigure.getEndPoint().getY() == secondBezierFigure.getStartPoint().getY();
    }

    private void moveSecondFigure(SVGBezierFigure secondBezierFigure) {
        int moveFactor = 10;

        Point2D.Double newStartPoint = secondBezierFigure.getStartPoint();
        newStartPoint.x = newStartPoint.getX() + moveFactor;

        Point2D.Double newEndPoint = secondBezierFigure.getEndPoint();
        newEndPoint.x = newEndPoint.getX() + moveFactor;

        secondBezierFigure.setPoint(0, newStartPoint);
        secondBezierFigure.setStartPoint(newStartPoint);

        secondBezierFigure.setPoint(secondBezierFigure.getNodeCount() - 1, newEndPoint);
        secondBezierFigure.setEndPoint(newEndPoint);
    }

    private void notifyViewAndFigureThatAChangeHappened(SVGBezierFigure svgBezierFigure, DrawingView view) {
        svgBezierFigure.changed();
        view.getDrawing().changed();
    }
}
