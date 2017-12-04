package org.jhotdraw.app.menu;

import org.jhotdraw.app.ApplicationModel;
import org.jhotdraw.app.action.CollaborationConnectAction;
import org.jhotdraw.app.action.CollaborationDisconnectAction;
import org.jhotdraw.app.action.CollaborationListConnectionsAction;
import org.jhotdraw.app.action.CollaborationStartServerAction;
import org.jhotdraw.app.action.CollaborationStopServerAction;

/**
 *
 * @author Niels
 */
public class CollaborationMenu extends AbstractMenu {

    public CollaborationMenu(ApplicationModel model) {
        super("collaboration", model);

        addAction(CollaborationStartServerAction.ID);
        addAction(CollaborationStopServerAction.ID);
        addAction(CollaborationListConnectionsAction.ID);
        addSeparator();
        addAction(CollaborationConnectAction.ID);
        addAction(CollaborationDisconnectAction.ID);
    }

}
