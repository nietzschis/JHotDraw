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
        addActionDynamically(RedoAction.class).setIcon(null);
        
        addSeparator();
        
        addActionDynamically(CutAction.class).setIcon(null);
        addActionDynamically(CopyAction.class).setIcon(null);
        addActionDynamically(PasteAction.class).setIcon(null);
        addActionDynamically(DuplicateAction.class).setIcon(null);
        addActionDynamically(DeleteAction.class).setIcon(null);
        
        addSeparator();
        
        addActionDynamically(SelectAllAction.class).setIcon(null);
        if (model.getAction(FindAction.ID) != null) {
            addSeparator();
            addAction(FindAction.ID);
        }
        
        JMenuItem mi = addActionDynamically(ClearSelectionAction.class);
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
