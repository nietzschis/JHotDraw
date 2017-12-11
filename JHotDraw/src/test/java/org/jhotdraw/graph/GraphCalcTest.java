package org.jhotdraw.graph;


import java.awt.geom.Point2D;
import org.jhotdraw.geom.BezierPath;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Joachim
 */
public class GraphCalcTest {
    
    public GraphCalcTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }   
    
    /**
     * Tests the calcYCoordinate for a linear graphs with 5 different functions
     */
    @Test
    public void linearTest() {
        LinearGraph[] lineareGraphs = {new LinearGraph(-5, 10,  0), new LinearGraph(7, 5,  0), new LinearGraph(1.55, -0.84,  0), new LinearGraph(50, -50,  0), new LinearGraph(0, 0,  0)};
        double[] linearInputX = {5, 0.45, -8, 555, -645};
        double[] linearResults = {-15, 8.15, -13.24, 27700, 0};
        for (int i = 0; i < lineareGraphs.length; i++) {
            testLinearGraph(lineareGraphs[i], linearInputX[i], linearResults[i]);
        }
    }
    
    public void testLinearGraph(LinearGraph lineareGraphs, double linearInputX, double linearResults) {
        double y = lineareGraphs.calcYCoordinate(linearInputX);
        assertEquals(y, linearResults, 0);
    }
    
    /**
     * Tests the calcYCoordinate for a quadratic graphs with 5 different functions
     */
    @Test
    public void quadraticTest() {
        QuadraticGraph[] quadraticGraph = {new QuadraticGraph(-5, 10,  8, 500), new QuadraticGraph(7, 5,  45, 500), new QuadraticGraph(1.55, -0.84,  33.47, 500), new QuadraticGraph(5, -50,  -0.5, 500), new QuadraticGraph(0, 0,  0, 500)};
        double[] quadraticInputX = {5, 0.45, -8, 100, -645};
        double[] quadraticResults = {-67, 48.6675, 139.39, 44999.5, 0};
        for (int i = 0; i < quadraticGraph.length; i++) {
            testQuadraticGraph(quadraticGraph[i], quadraticInputX[i], quadraticResults[i]);
        }
    }

    public void testQuadraticGraph(QuadraticGraph quadraticGraphs, double quadraticInputX, double quadraticResults) {
        double y = quadraticGraphs.calcYCoordinate(quadraticInputX);
        assertEquals(y, quadraticResults, 0);
    }
    
    @Test
    public void testQuadraticWithMouseInput() {
        GraphBezierFigure graphBezier = new GraphBezierFigure();
        QuadraticGraph gr = new QuadraticGraph(-5, 10,  0, 50);
        double y1 = 500 - gr.calcYCoordinate(15);
        graphBezier.setFunction(gr);
        graphBezier.setBounds(new Point2D.Double(500,500), new Point2D.Double(600,600));
        BezierPath path = graphBezier.generatePath();
        
        double y2 = path.get(3).y[0];
        assertEquals(y2, y1, 0);
    }
    
    @Test
    public void testLinearWithMouseInput() {
        GraphBezierFigure graphBezier = new GraphBezierFigure();
        LinearGraph gr = new LinearGraph(-5, 10,  0);
        double y1 = 500 - gr.calcYCoordinate(15);
        graphBezier.setFunction(gr);
        graphBezier.setBounds(new Point2D.Double(500,500), new Point2D.Double(600,600));
        BezierPath path = graphBezier.generatePath();
        
        double y2 = path.get(3).y[0];
        assertEquals(y2, y1, 0);
    }
    
    @Test
    public void testBound() {
        GraphBezierFigure graphBezier = new GraphBezierFigure();
        LinearGraph gr = new LinearGraph(3, 0,  0);
        graphBezier.setFunction(gr);
        graphBezier.setBounds(new Point2D.Double(15,15), new Point2D.Double(600,600));
        BezierPath path = graphBezier.generatePath();
        
        int pathLenth = path.size();
        assertEquals(2, pathLenth);
    }
    
    
    
}
