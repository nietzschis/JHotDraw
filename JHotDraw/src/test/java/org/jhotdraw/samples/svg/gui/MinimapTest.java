package org.jhotdraw.samples.svg.gui;

import java.awt.Dimension;
import javax.swing.JFrame;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.samples.svg.ViewportModifier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Used to test the Minimap feature.
 */
public class MinimapTest {
    
    private MinimapView minimapView;
    private MinimapController minimapController;
    private FrameFixture window;
    private Drawing drawing;
    private ViewportModifier viewportModifier;
    private DrawingView dview;
    
    @Before
    public void setUp() {
        AbstractToolBar toolBar = mock(AbstractToolBar.class);
        DrawingEditor editor = mock(DrawingEditor.class);
        dview = mock(DrawingView.class);
        drawing = mock(Drawing.class);
        
        viewportModifier = mock(ViewportModifier.class);
        
        Mockito.when(toolBar.getEditor()).thenReturn(editor);
        Mockito.when(editor.getActiveView()).thenReturn(dview);
        Mockito.when(dview.getDrawing()).thenReturn(drawing);
        
        minimapView = new MinimapView(toolBar);
        minimapView.setPreferredSize(new Dimension(80, 80));
        minimapController = new MinimapController(viewportModifier, minimapView);
        
        JFrame frame = GuiActionRunner.execute(() -> new JFrame());
        frame.add(minimapView);
        
        window = new FrameFixture(frame);
        window.show();
    }
    
    @Test
    public void respondsToUserInput(){
        verify(viewportModifier, never()).centerViewportOnPoint(Matchers.any());
        window.panel("minimapView").click();
        verify(viewportModifier, atLeastOnce()).centerViewportOnPoint(Matchers.any());
    }
    
    @Test
    public void drawingIsDrawnOnMinimap(){
        if (dview.getDrawing() != null) {
            verify(drawing, atLeastOnce()).draw(Matchers.any());
        }
    }
    
    @After
    public void tearDown() {
        window.cleanUp();
    }
}
