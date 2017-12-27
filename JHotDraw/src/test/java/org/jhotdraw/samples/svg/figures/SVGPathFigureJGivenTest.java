/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.figures;

import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.junit.SimpleScenarioTest;
import java.awt.geom.Point2D;
import java.util.HashMap;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.samples.svg.io.DefaultSVGFigureFactory;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;



/**
 *
 * @author sebastian
 */
public class SVGPathFigureJGivenTest extends SimpleScenarioTest<SVGPathFigureJGivenTest.Stages> {
    
    public SVGPathFigureJGivenTest() {
    }
        @Test
        public void test_scanario() {
        given().a_non_empty_SVGPathFigure();
        when().updating_text();
        then().text_is_in_figure_drawingarea();
    }
    
        public static class Stages {

        
        @ProvidedScenarioState
        SVGPathFigure figure;
        
        public void a_non_empty_SVGPathFigure() {
        Point2D.Double a = new Point2D.Double(0, 0);
        Point2D.Double b = new Point2D.Double(10, 0);
        Point2D.Double c = new Point2D.Double(10, 10);
        Point2D.Double d = new Point2D.Double(0, 10);
        Point2D.Double[] points = new Point2D.Double[]{a,b,c,d};
        DefaultSVGFigureFactory f = new DefaultSVGFigureFactory();
        HashMap<AttributeKey, Object> attributes;
        attributes = new HashMap<AttributeKey, Object>();
        figure = (SVGPathFigure) f.createPolygon(points,attributes);
        
        }

        public void updating_text() {
            figure.setText("JGivenTest");
        }

        public void text_is_in_figure_drawingarea() {
            assertNotNull(figure);
            assertTrue(figure.getDrawingArea().contains(figure.getTextPath().getBounds2D()));
        }
    }

}
