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
public class WhenUser extends Stage<WhenUser>{

    public WhenUser the_user_clicks_on_the_searchbar() {
        return self();
    }

    public WhenUser the_user_inserts_the_string_abo_in_the_searchbar() {
        return self();
    }

    public WhenUser the_user_presses_enter() {
        return self();
    }
    
}
