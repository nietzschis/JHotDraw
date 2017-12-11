package org.jhotdraw.app.menu;

import javax.swing.JMenu;
import org.jhotdraw.app.ApplicationModel;
import org.jhotdraw.app.action.ClearAction;
import org.jhotdraw.app.action.DuplicateCanvasAction;
import org.jhotdraw.app.action.ExitAction;
import org.jhotdraw.app.action.ExportAction;
import org.jhotdraw.app.action.LoadAction;
import org.jhotdraw.app.action.LoadDirectoryAction;
import org.jhotdraw.app.action.NewAction;
import org.jhotdraw.app.action.PrintAction;
import org.jhotdraw.app.action.SaveAction;
import org.jhotdraw.app.action.SaveAsAction;

/**
 *
 * @author Niels
 */
public class FileMenu extends AbstractMenu {

    public FileMenu(ApplicationModel model, JMenu openRecentMenu) {
        super("file", model);
        
        addAction(ClearAction.ID);
        addAction(NewAction.ID);
        addAction(DuplicateCanvasAction.ID);
        addAction(LoadAction.ID);
        if (model.getAction(LoadDirectoryAction.ID) != null) {
            addAction(LoadDirectoryAction.ID);
        }
        add(openRecentMenu);
        addSeparator();
        addAction(SaveAction.ID);
        addAction(SaveAsAction.ID);
        if (model.getAction(ExportAction.ID) != null) {
            addAction(ExportAction.ID);
        }
        if (model.getAction(PrintAction.ID) != null) {
            addSeparator();
            addAction(PrintAction.ID);
        }
        addSeparator();
        addAction(ExitAction.ID);
    }

}
