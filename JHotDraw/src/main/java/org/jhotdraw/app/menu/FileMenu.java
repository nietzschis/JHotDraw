package org.jhotdraw.app.menu;

import javax.swing.JMenu;
import org.jhotdraw.app.ApplicationModel;
import org.jhotdraw.app.action.ClearAction;
import org.jhotdraw.app.action.DuplicateCanvasAction;
import org.jhotdraw.app.action.ExitAction;
import org.jhotdraw.app.action.ExportAction;
import org.jhotdraw.app.action.ImportWatermarkAction;
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

        addActionDynamically(ClearAction.class);
        addActionDynamically(NewAction.class);
        addActionDynamically(DuplicateCanvasAction.class);
        addAction(LoadAction.ID);
        if (model.getAction(LoadDirectoryAction.ID) != null) {
            addAction(LoadDirectoryAction.ID);
        }
        add(openRecentMenu);
        addSeparator();
        addAction(SaveAction.ID);
        addAction(SaveAsAction.ID);

        if (model.getActionDynamicly(ExportAction.class) != null) {
            addActionDynamically(ExportAction.class);
        }
        if (model.getAction(ImportWatermarkAction.ID) != null) {
            addAction(ImportWatermarkAction.ID);
        }
        if (model.getActionDynamicly(PrintAction.class) != null) {
            addSeparator();
            addActionDynamically(PrintAction.class);
        }
        addSeparator();
        addActionDynamically(ExitAction.class);
    }

}
