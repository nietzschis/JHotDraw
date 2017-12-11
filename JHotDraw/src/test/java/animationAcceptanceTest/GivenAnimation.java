
package animationAcceptanceTest;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import javax.swing.JFrame;
import org.jhotdraw.app.AbstractApplication;
import org.jhotdraw.app.DefaultSDIApplication;
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
    
    public GivenAnimation animationWindow() {
        AbstractApplication app = new DefaultSDIApplication();
        SVGView view = new SVGView();
        view.init();
        view.getDrawing().add(new SVGRectFigure());
        animationWindow.add(view);
        return this;
    }
    
    public GivenAnimation toolbar() {
        toolbar = new AnimationToolBar();
        toolbar.createDisclosedComponent(0);
        return this;
    }

    public GivenAnimation animationWindow_with_three_frames() {
        return this;
    }
}
