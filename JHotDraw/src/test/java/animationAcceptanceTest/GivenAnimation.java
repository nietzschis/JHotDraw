
package animationAcceptanceTest;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import java.awt.Component;
import javax.swing.JFrame;
import org.jhotdraw.draw.AnimationTool;
import org.jhotdraw.draw.AnimationToolDefinition;
import static org.jhotdraw.draw.AnimationToolDefinition.*;
import org.jhotdraw.samples.svg.SVGView;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;
/**
 *
 * @author lasca
 */
public class GivenAnimation extends Stage<GivenAnimation> {
    
    @ProvidedScenarioState
    AnimationTool animationTool;
    
    @ProvidedScenarioState
    JFrame animationWindow;
    
    public GivenAnimation animationWindow() {
        animationWindow = new JFrame();
        SVGView view = new SVGView();
        view.init();
        view.getDrawing().add(new SVGRectFigure());
        animationWindow.add(view);
        return this;
    }
    
    public GivenAnimation animationAddFrameTool() {
        animationTool = new AnimationTool(ADD_FRAME_TOOL);
        return this;
    }
    
    public GivenAnimation animationRemoveFrameTool() {
        animationTool = new AnimationTool(REMOVE_FRAME_TOOL);
        return this;
    }
    
    public GivenAnimation animationPlayTool() {
        animationTool = new AnimationTool(PLAY_TOOL);
        return this;
    }
    
    public GivenAnimation animationPauseTool() {
        animationTool = new AnimationTool(PAUSE_TOOL);
        return this;
    }

    public GivenAnimation animationWindow_with_three_frames() {
        return this;
    }
}
