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
import javax.swing.JFrame;
import org.jhotdraw.draw.Animation;
import org.jhotdraw.draw.AnimationTool;
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
    
    public WhenAnimation i_Add_Three_New_Frames() {
        Animation.getInstance().setCurrentFrame(animationWindow);
        animationTool.actionPerformed(mock(ActionEvent.class));
        animationTool.actionPerformed(mock(ActionEvent.class));
        animationTool.actionPerformed(mock(ActionEvent.class));
        return this;
    }

    public WhenAnimation adding_new_figure_in_frame() {
        return this;
    }

    void play() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void removing_a_frame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
