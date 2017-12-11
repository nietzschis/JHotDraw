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
    public void something_should_happen() {
        given().some_state();
        when().some_action();
        then().some_outcome();
    }
    
    @Ignore
    @Test
    public void add_Frames_And_Play() {
        given().animationWindow();
    }
}
