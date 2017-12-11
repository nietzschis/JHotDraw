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
    
    @Ignore
    @Test
    public void adding_Frames() {
        given().animationWindow()
                .and()
                .toolbar();
        
        when().i_Add_Three_New_Frames();
        
        then().the_Animation_contains_Four_Frames();
    }
    
    @Ignore
    @Test
    public void comparing_Frames() {
        given().animationWindow()
                .and()
                .toolbar();
        
        when().adding_new_figure_in_frame();
        
        then().the_frames_should_be_different();
    }
    
    @Ignore
    @Test
    public void play_Animation() {
        given().animationWindow()
                .and()
                .toolbar();
        
        when().i_Add_Three_New_Frames()
                .and()
                .play();
        
        then().the_animation_plays();
    }
    
    @Ignore
    @Test
    public void remove_Frame() {
        given().animationWindow_with_three_frames()
                .and()
                .toolbar();
        
        when().removing_a_frame();
        
        then().animation_contains_two_frames();
    }
}
