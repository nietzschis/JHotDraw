/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animationAcceptanceTest;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.junit.Ignore;
import org.junit.Test;
/**
 *
 * @author lasca
 */
public class AcceptenceTest extends ScenarioTest<GivenAnimation, WhenAnimation, ThenAnimation> {
    
    @Test
    public void adding_Frames() {
        given().animationWindow()
                .and()
                .animationAddFrameTool();
        
        when().i_add_three_new_frames();
        
        then().the_Animation_contains_Three_Frames();
    }
    
    @Test
    public void comparing_Frames() {
        given().animationWindow()
                .and()
                .animationAddFrameTool();
        
        when().adding_new_figure_in_frame();
        
        then().the_frames_should_be_different();
    }
    
    @Test
    public void remove_Frame() {
        given().animationWindow()
                .and()
                .animationAddFrameTool();
        
        when().i_add_three_new_frames()
                .and()
                .removing_a_frame();
        
        then().animation_contains_two_frames();
    }
    
    @Test
    public void play_and_pause_Animation() throws InterruptedException {
        given().animationWindow()
                .and()
                .animationAddFrameTool();
        
        when().i_add_three_new_frames()
                .and()
                .play()
                .and()
                .thenPause();
        
        then().the_animation_should_stop();
    }
}
