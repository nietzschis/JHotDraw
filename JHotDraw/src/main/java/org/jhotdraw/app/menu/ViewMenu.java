package org.jhotdraw.app.menu;

import org.jhotdraw.app.ApplicationModel;
import org.jhotdraw.samples.svg.action.ViewSourceAction;

/**
 *
 * @author Niels
 */
public class ViewMenu extends AbstractMenu {
    
    public ViewMenu(ApplicationModel model) {
        super("view", model);
        
        addAction(ViewSourceAction.ID);
    }
    
}
