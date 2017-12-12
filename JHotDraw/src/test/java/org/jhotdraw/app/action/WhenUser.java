/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import javax.swing.Action;
import org.jhotdraw.app.Application;
import org.openide.util.Lookup;

/**
 *
 * @author Daniel
 */
public class WhenUser extends Stage<WhenUser>{
    @ExpectedScenarioState
    Application app;

    public WhenUser the_user_clicks_on_the_searchbar() {
        return self();
    }

    public WhenUser the_user_inserts_the_string_abo_in_the_searchbar() {
        app.getTextField().setText("abo");
        return self();
    }

    public WhenUser the_user_presses_enter() {
        String text = app.getSearchText();
        SearchAction searchAction = Lookup.getDefault().lookup(SearchAction.class);
        searchAction.getAllTools();
        for(Action action : searchAction.setList(text)){
            app.getSearchMenu().add(action);
        }
        return self();
    }
    
}
