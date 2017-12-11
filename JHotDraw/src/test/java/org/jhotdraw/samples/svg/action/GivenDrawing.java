/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.action;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.samples.svg.SVGView;

/**
 *
 * @author Marc
 */
public class GivenDrawing extends Stage<GivenDrawing>{
    @ProvidedScenarioState
    SVGView view;
    
    public GivenDrawing a_view_with_an_empty_drawing() {
        view = new SVGView();
        view.init();
        return self();
    }
}
