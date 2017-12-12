/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animationAcceptanceTest;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import static org.assertj.core.api.Assertions.assertThat;
import org.jhotdraw.draw.Animation;
import org.jhotdraw.draw.AnimationTool;
import org.jhotdraw.samples.svg.SVGView;

/**
 *
 * @author lasca
 */
public class ThenAnimation extends Stage<ThenAnimation> {
    
    @ExpectedScenarioState
    JFrame animationWindow;
    
    @ExpectedScenarioState
    AnimationTool animationTool;
    
    public void the_Animation_contains_Three_Frames() {
        assertThat(Animation.getInstance()).isNotNull();
        assertThat(Animation.getInstance().getFrames()).isNotNull();
        
        assertThat(Animation.getInstance().getFrames().size()).isEqualTo(3);
    }

    public void the_frames_should_be_different() {
        assertThat(Animation.getInstance()).isNotNull();
        assertThat(Animation.getInstance().getFrames()).isNotNull();
        
        SVGView frame1 = getView(0);
        SVGView frame2 = getView(1);
        
        assertThat(frame1.getDrawing().getChildren().size()).isNotEqualTo(frame2.getDrawing().getChildren().size());
    }
    
    public SVGView getView(int indexInFrameArray) {
        JRootPane pane = (JRootPane) Animation.getInstance().getFrames().get(indexInFrameArray).getComponent(0);
        JLayeredPane pane1 = (JLayeredPane) pane.getComponent(1);
        JPanel panel = (JPanel) pane1.getComponent(0);
        return (SVGView) panel.getComponent(0);
    }

    public void the_animation_plays() throws InterruptedException {
        assertThat(Animation.getInstance()).isNotNull();
        assertThat(Animation.getInstance().getFrames()).isNotNull();
        Thread.sleep(500);
        assertThat(animationTool.getTimesPlayed()).isGreaterThan(0);
    }

    public void animation_contains_two_frames() {
        
    }
}
