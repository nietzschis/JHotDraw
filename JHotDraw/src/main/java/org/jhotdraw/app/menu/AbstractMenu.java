package org.jhotdraw.app.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import org.jhotdraw.app.ApplicationModel;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author Niels
 */
public abstract class AbstractMenu extends JMenu {

    private ApplicationModel model;

    public AbstractMenu(String id, ApplicationModel model) {
        ResourceBundleUtil appLabels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        appLabels.configureMenu(this, id);
        this.model = model;
    }

    public final JMenuItem addAction(String id) {
        return super.add(model.getAction(id));
    }

}
