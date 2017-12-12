package org.jhotdraw.app.menu;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.JMenuItem;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.ApplicationModel;
import org.jhotdraw.app.View;
import org.jhotdraw.app.action.ClearRecentFilesAction;
import org.jhotdraw.app.action.LoadRecentAction;

/**
 *
 * @author Niels
 */
public class OpenRecentMenu extends AbstractMenu {

    public OpenRecentMenu(ApplicationModel model, Application a, View p) {
        super("file.openRecent", model);
        
        addAction(ClearRecentFilesAction.ID);
        updateOpenRecentMenu(a);

        a.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                String name = evt.getPropertyName();
                if (name.equals("viewCount")) {
                    if (p == null || a.views().contains(p)) {
                    }
                    else {
                        a.removePropertyChangeListener(this);
                    }
                }
                else if (name.equals("recentFiles")) {
                    updateOpenRecentMenu(a);
                }
            }
        });
    }

    private void updateOpenRecentMenu(Application a) {
        if (getItemCount() > 0) {
            JMenuItem clearRecentFilesItem = (JMenuItem) getItem(
                    getItemCount() - 1);
            removeAll();
            for (File f : a.recentFiles()) {
                add(new LoadRecentAction(a, f));
            }
            if (a.recentFiles().size() > 0) {
                addSeparator();
            }
            add(clearRecentFilesItem);
        }
    }

}
