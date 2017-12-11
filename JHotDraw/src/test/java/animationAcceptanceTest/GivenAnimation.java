/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animationAcceptanceTest;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import javax.swing.JFrame;
import org.jhotdraw.app.AbstractApplication;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.app.View;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.samples.svg.SVGView;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.jhotdraw.samples.svg.gui.AnimationToolBar;
/**
 *
 * @author lasca
 */
public class GivenAnimation extends Stage<GivenAnimation> {
    
    @ProvidedScenarioState
    AnimationToolBar toolbar;
    
    @ProvidedScenarioState
    JFrame animationWindow;
    
    public GivenAnimation some_state() {
        return this;
    }
    
    public GivenAnimation animationWindow() {
        AbstractApplication app = new DefaultSDIApplication();
        SVGView view = new SVGView();
        view.init();
        animationWindow.add(view);
        view.getDrawing().add(new SVGRectFigure());
        return this;
    }
    
    public GivenAnimation toolbar() {
        toolbar = new AnimationToolBar();
        toolbar.createDisclosedComponent(0);
        return this;
    }
}
