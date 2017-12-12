/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animationAcceptanceTest;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import java.awt.event.ActionEvent;
import java.util.concurrent.Executors;
import javax.swing.JFrame;
import org.jhotdraw.draw.Animation;
import org.jhotdraw.draw.AnimationTool;
import static org.jhotdraw.draw.AnimationToolDefinition.*;
import org.jhotdraw.samples.svg.SVGView;
import org.jhotdraw.samples.svg.figures.SVGEllipseFigure;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import static org.mockito.Mockito.mock;

/**
 *
 * @author lasca
 */
public class WhenAnimation extends Stage<WhenAnimation> {
    
    @ExpectedScenarioState
    @ProvidedScenarioState
    JFrame animationWindow;
    
    @ExpectedScenarioState
    @ProvidedScenarioState
    AnimationTool animationTool;
    
    public WhenAnimation i_add_three_new_frames() {
        Animation.getInstance().getFrames().clear();
        Animation.getInstance().setCurrentFrame(animationWindow);
        animationTool.actionPerformed(mock(ActionEvent.class));
        animationTool.actionPerformed(mock(ActionEvent.class));
        animationTool.actionPerformed(mock(ActionEvent.class));
        return this;
    }

    public WhenAnimation adding_new_figure_in_frame() {
        
        // Add to animation what there's already in the drawing
        Animation.getInstance().setCurrentFrame(animationWindow);
        animationTool.actionPerformed(mock(ActionEvent.class));
        
        // Create new frame with new drawing where 2 figures are added
        JFrame newFrame = new JFrame();
        SVGView newView = new SVGView();
        newView.init();
        newView.getDrawing().add(new SVGRectFigure());
        newView.getDrawing().add(new SVGEllipseFigure());
        newFrame.add(newView);
        
        Animation.getInstance().setCurrentFrame(newFrame);
        animationTool.actionPerformed(mock(ActionEvent.class));
        
        return this;
    }

    public WhenAnimation play() {
        animationTool.changeTool(ADD_FRAME_TOOL);
        Animation.getInstance().setCurrentFrame(animationWindow);
        animationTool.actionPerformed(mock(ActionEvent.class));
        animationTool.actionPerformed(mock(ActionEvent.class));
        animationTool.actionPerformed(mock(ActionEvent.class));
        
        animationTool.changeTool(PLAY_TOOL);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                animationTool.actionPerformed(mock(ActionEvent.class));
            }
        });
        return this; 
    }

    public WhenAnimation removing_a_frame() {
        animationTool.changeTool(REMOVE_FRAME_TOOL);
        animationTool.actionPerformed(mock(ActionEvent.class));
        return this;
    }

    public WhenAnimation thenPause() throws InterruptedException {
        Thread.sleep(500);
        animationTool.changeTool(PAUSE_TOOL);
        animationTool.actionPerformed(mock(ActionEvent.class));
        return this;
    }
    
    
}
