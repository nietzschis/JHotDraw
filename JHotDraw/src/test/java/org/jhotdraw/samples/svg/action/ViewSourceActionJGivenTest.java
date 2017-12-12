/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.action;


import com.tngtech.jgiven.junit.ScenarioTest;
import org.junit.Test;

/**
 *
 * @author Marc
 */
public class ViewSourceActionJGivenTest extends ScenarioTest<GivenDrawing, WhenUser, ThenSomeOutcome>{
    
    @Test
    public void drawing_should_be_updated_when_user_edits_source() {
        given().a_view_with_an_empty_drawing();
        
        when().the_user_opens_the_view_source_window().
                and().the_user_inserts_xml_in_the_source_window_to_add_a_rectangle().
                and().the_user_closes_the_view_source_window();
        
        then().the_drawing_should_contain_exactly_one_figure().
                and().that_one_figure_should_be_a_rectangle();
    }
}
