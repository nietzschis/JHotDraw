package org.jhotdraw.app.menu;

import javax.swing.JMenuItem;
import org.jhotdraw.app.ApplicationModel;
import org.jhotdraw.app.View;
import org.jhotdraw.app.action.ClearSelectionAction;
import org.jhotdraw.app.action.CopyAction;
import org.jhotdraw.app.action.CutAction;
import org.jhotdraw.app.action.DeleteAction;
import org.jhotdraw.app.action.DuplicateAction;
import org.jhotdraw.app.action.FindAction;
import org.jhotdraw.app.action.PasteAction;
import org.jhotdraw.app.action.RedoAction;
import org.jhotdraw.app.action.SelectAllAction;
import org.jhotdraw.app.action.UndoAction;
import org.jhotdraw.draw.action.SelectSameAction;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author Niels
 */
public class EditMenu extends AbstractMenu {
    
    public EditMenu(ApplicationModel model, View p) {
        super("edit", model);
        ResourceBundleUtil drawLabels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
        
        addAction(UndoAction.ID).setIcon(null);
        addAction(RedoAction.ID).setIcon(null);
        
        addSeparator();
        
        addAction(CutAction.ID).setIcon(null);
        addAction(CopyAction.ID).setIcon(null);
        addAction(PasteAction.ID).setIcon(null);
        addAction(DuplicateAction.ID).setIcon(null);
        addAction(DeleteAction.ID).setIcon(null);
        
        addSeparator();
        
        addAction(SelectAllAction.ID).setIcon(null);
        if (model.getAction(FindAction.ID) != null) {
            addSeparator();
            addAction(FindAction.ID);
        }
        
        JMenuItem mi = addAction(ClearSelectionAction.ID);
        mi.setIcon(null);

        if (p != null) {
            mi = add(p.getAction(SelectSameAction.ID));
        } else {
            mi = new JMenuItem();
            drawLabels.configureMenu(mi, SelectSameAction.ID);
            mi.setEnabled(false);
            add(mi);
        }
        mi.setIcon(null);
    }
    
}
