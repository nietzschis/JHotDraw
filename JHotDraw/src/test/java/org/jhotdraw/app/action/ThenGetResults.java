/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import static org.assertj.core.api.Assertions.assertThat;
import org.jhotdraw.app.Application;

/**
 *
 * @author Daniel
 */
public class ThenGetResults extends Stage<ThenGetResults>{
    @ExpectedScenarioState
    Application app;

    public ThenGetResults the_searchresult_list_should_contain_exactly_one_action() {
        assertThat(app.getSearchMenu().getItemCount() == 1);
        return self();
    }

    public ThenGetResults that_one_action_should_be_about() {
        assertThat(app.getSearchMenu().getItem(0).getClass().equals(AboutAction.class));
        return self();
    }
    
}
