/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.action;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import static org.assertj.core.api.Assertions.*;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.samples.svg.SVGView;

/**
 *
 * @author Marc
 */
public class ThenSomeOutcome extends Stage<ThenSomeOutcome> {
    @ExpectedScenarioState
    SVGView view;
    
    public ThenSomeOutcome the_drawing_should_contain_exactly_one_figure() {
        Drawing drawing = view.getDrawing();
        assertThat(drawing.getChildCount()).isEqualTo(1);
        return self();
    }
}
