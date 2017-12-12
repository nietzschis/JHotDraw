/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import com.tngtech.jgiven.Stage;

/**
 *
 * @author Daniel
 */
public class ThenGetResults extends Stage<ThenGetResults>{

    public ThenGetResults the_searchresult_list_should_contain_exactly_one_action() {
        return self();
    }

    public ThenGetResults that_one_action_should_be_about() {
        return self();
    }
    
}
