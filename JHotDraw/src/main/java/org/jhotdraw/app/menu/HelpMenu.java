package org.jhotdraw.app.menu;

import org.jhotdraw.app.ApplicationModel;
import org.jhotdraw.app.action.AboutAction;

/**
 *
 * @author Niels
 */
public class HelpMenu extends AbstractMenu {
    
    public HelpMenu(ApplicationModel model) {
        super("help", model);
        addActionDynamically(AboutAction.class);
        //addAction(AboutAction.ID);
    }
    
}
