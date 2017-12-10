/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw;

import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.junit.SimpleScenarioTest;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.junit.Assert;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author Sadik
 */
public class FlipJGivenTest  extends SimpleScenarioTest<FlipJGivenTest>{
    
    @Test
    public void scenario_one(){
        given().a_figure();
        when().vertical_flip_chosen();
        then().figure_is_flipped_vertically();
    }
    
    @Test
    public void scenario_two(){
        given().a_figure();
        when().horizontal_flip_chosen();
        then().figure_is_flipped_horizontally();
    }

    @ProvidedScenarioState
    DefaultDrawingView view;
    
    @ProvidedScenarioState
    DefaultDrawing drawing;
    
    public void a_figure() {
        view = new DefaultDrawingView();
        drawing = new DefaultDrawing();
        Figure figure = new SVGRectFigure(100, 100, 1, 1);
        
        drawing.add(figure);
        view.setDrawing(drawing);
        
    }
    
    public void vertical_flip_chosen() {
        String choice = "Vertical";
        view.toggleSelection(drawing.getChild(0));
        view.flip(choice);
    }
    
    public void horizontal_flip_chosen() {
        String choice = "Horizontal";
        view.toggleSelection(drawing.getChild(0));
        view.flip(choice);
    }
    
    public void figure_is_flipped_vertically() {
        
        Assert.assertSame((int)drawing.getChild(0).getDrawingArea().getCenterX(), (int)drawing.getChild(1).getDrawingArea().getCenterX()); //same center x-coordinates
        Assert.assertNotSame(drawing.getChild(0).getDrawingArea().getCenterY(), drawing.getChild(1).getDrawingArea().getCenterY()); //different center y-coordinates
        Assert.assertNotSame(drawing.getChild(0), drawing.getChild(1)); //just to make sure they are not the same
        assertNotNull(drawing);
        assertNotNull(view);
    }
    
    public void figure_is_flipped_horizontally() {
                
        Assert.assertSame((int)drawing.getChild(0).getDrawingArea().getCenterY(), (int)drawing.getChild(1).getDrawingArea().getCenterY()); //different center y-coordinates
        Assert.assertNotSame((int)drawing.getChild(0).getDrawingArea().getCenterX(), (int)drawing.getChild(1).getDrawingArea().getCenterX()); //same center x-coordinates
        Assert.assertNotSame(drawing.getChild(0), drawing.getChild(1)); //just to make sure they are not the same
        assertNotNull(drawing);
        assertNotNull(view);
    }
    
}
