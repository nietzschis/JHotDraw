package org.jhotdraw;

import org.jhotdraw.app.action.TinyPngCompressActionJGivenTest;
import org.jhotdraw.app.action.TinyPngCompressActionTest;
import org.jhotdraw.app.action.watermark.WatermarkTest;
import org.jhotdraw.samples.svg.figures.svgtrianglefigure.SVGTriangleFigureTest;
import org.jhotdraw.samples.svg.figures.svgtrianglefigure.TriangleJGivenTest;
import org.jhotdraw.samples.svg.io.DefaultSVGFigureFactoryTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * TODO: Dear co-developers. Please add your test to this suite.
 *
 * @author corfixen
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({TinyPngCompressActionJGivenTest.class, TinyPngCompressActionTest.class,
                     SVGTriangleFigureTest.class, TriangleJGivenTest.class, DefaultSVGFigureFactoryTest.class, WatermarkTest.class})
public class JHotDrawTestSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

}
