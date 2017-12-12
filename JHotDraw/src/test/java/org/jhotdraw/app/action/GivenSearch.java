/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.app.Application;

/**
 *
 * @author Daniel
 */
public class GivenSearch extends Stage<GivenSearch>{
    @ProvidedScenarioState
    Application app;

    public GivenSearch an_application_with_an_empty_searchbar() {
        return null;
    }
    
}
