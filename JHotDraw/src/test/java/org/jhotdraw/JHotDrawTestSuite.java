package org.jhotdraw;

import org.jhotdraw.app.action.MyColorsSaveLoadActionTest;
import org.jhotdraw.app.action.DuplicateCanvasAction;
import org.jhotdraw.app.action.TinyPngCompressActionJGivenTest;
import org.jhotdraw.app.action.TinyPngCompressActionTest;
import org.jhotdraw.samples.svg.action.ViewSourceActionJGivenTest;
import org.jhotdraw.draw.action.ButtonFactoryTest;
import org.jhotdraw.draw.action.MagnifyingGlassAcceptanceTest;
import org.jhotdraw.collaboration.CollaborationAcceptanceTest;
import org.jhotdraw.collaboration.CollaborationGUITest;
import org.jhotdraw.collaboration.client.CollaborationDrawingHandlerTest;
import org.jhotdraw.collaboration.server.CollaborationServerTest;
import org.jhotdraw.collaboration.server.RemoteObservableTest;
import org.jhotdraw.draw.DefaultDrawingViewTest;
import org.jhotdraw.draw.SelectionTest;
import org.jhotdraw.draw.SimpleDrawingViewTest;
import org.jhotdraw.opencontaingfolder.CorrectStrategyTest;
import org.jhotdraw.samples.svg.action.SplitTest;
import org.jhotdraw.samples.svg.figures.svgtrianglefigure.SVGTriangleFigureTest;
import org.jhotdraw.samples.svg.figures.svgtrianglefigure.TriangleJGivenTest;
import org.jhotdraw.samples.svg.gui.FigureToolBarTestGui_AssertJ_Swing;
import org.jhotdraw.samples.svg.gui.FigureToolBarTest_JUnit;
import org.jhotdraw.samples.svg.io.DefaultSVGFigureFactoryTest;
import org.jhotdraw.samples.svg.io.SVGInputFormatTest;
import org.jhotdraw.tabs.SimpleTabManagerTest;
import org.jhotdraw.tabs.gui.SimpleTabManagerTestAcceptance;
import org.jhotdraw.tabs.gui.TabPanelTest;
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
                     SVGTriangleFigureTest.class, TriangleJGivenTest.class, DefaultSVGFigureFactoryTest.class, SimpleDrawingViewTest.class, 
                    CollaborationDrawingHandlerTest.class, CollaborationServerTest.class, RemoteObservableTest.class,
                    CollaborationAcceptanceTest.class, CollaborationGUITest.class,
                    SimpleTabManagerTest.class, TabPanelTest.class, SimpleTabManagerTestAcceptance.class,
                    RecordingToolTest.class, RecordingToolGUITest.class,
                    SelectionTest.class, SelectionTest.class, ButtonFactoryTest.class, MagnifyingGlassAcceptanceTest.class,
                    ViewSourceActionJGivenTest.class, SVGInputFormatTest.class, DefaultDrawingViewTest.class, SplitTest.class,
                    DuplicateCanvasAction.class, CorrectStrategyTest.class,
                    SelectionTest.class,
                    FigureToolBarTestGui_AssertJ_Swing.class,
                    FigureToolBarTest_JUnit.class})

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
