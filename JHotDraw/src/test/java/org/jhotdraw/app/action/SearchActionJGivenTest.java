/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import org.junit.Test;
import com.tngtech.jgiven.junit.ScenarioTest;

/**
 *
 * @author Daniel
 */
public class SearchActionJGivenTest extends ScenarioTest<GivenSearch, WhenUser, ThenGetResults>{
    
    @Test
    public void actions_should_be_listed_when_user_search_in_searchbar() {
        given().an_application_with_an_empty_searchbar();
        
        when().the_user_clicks_on_the_searchbar().
                and().the_user_inserts_the_string_abo_in_the_searchbar().
                and().the_user_presses_enter();
        
        then().the_searchresult_list_should_contain_exactly_one_action().
                and().that_one_action_should_be_about();
    }
    
}
