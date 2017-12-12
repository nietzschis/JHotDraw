/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.action;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.samples.svg.SVGView;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;

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
    
    public ThenSomeOutcome that_one_figure_should_be_a_rectangle() {
        Drawing drawing = view.getDrawing();
        List<Figure> children = drawing.getChildren();
        assertThat(children.get(0)).isInstanceOf(SVGRectFigure.class);
        
        return self();
    }
}
