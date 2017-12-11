/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animationAcceptanceTest;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import javax.swing.JFrame;
import static org.assertj.core.api.Assertions.assertThat;
import org.jhotdraw.draw.Animation;
import org.jhotdraw.draw.AnimationTool;

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
        
    }

    public void the_animation_plays() {
        
    }

    public void animation_contains_two_frames() {
        
    }
}
