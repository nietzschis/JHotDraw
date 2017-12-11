/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import org.jhotdraw.util.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import org.jhotdraw.app.*;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Daniel
 */
@ServiceProvider(service = ActionSPI.class)
public class SearchAction extends AbstractApplicationAction implements ActionSPI {

    /**
     * Displays a dialog showing information about the application.
     *
     * @author Werner Randelshofer
     * @version 1.0 04 January 2005 Created.
     */
    public final static String ID = "application.search";
    private ArrayList<Action> toolList = new ArrayList<>();
    private ArrayList<Action> tmpList = new ArrayList<>();
    private Lookup.Result<ActionSPI> ActionResult;

    /**
     * Creates a new instance.
     */
    public SearchAction() {
        super(Lookup.getDefault().lookup(ApplicationSPI.class).getApplicationInstance());
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        labels.configureAction(this, ID);
    }

    public void actionPerformed(ActionEvent evt) {
        getAllTools();
        if (evt.getSource() != null) {
            Lookup.getDefault().lookup(ApplicationSPI.class).getApplicationInstance().setSearchMenu(setList());
        }
    }

    public ArrayList<Action> setList() {
        tmpList.removeAll(tmpList);
        for (Action action : toolList) {
            String textFieldText = Lookup.getDefault().lookup(ApplicationSPI.class).getApplicationInstance().getSearchText().toLowerCase();
            String actionName = action.getClass().getSimpleName().toLowerCase();
            if (actionName.contains("action")) {
                actionName = actionName.replace("action", "");
            }
            if (actionName.contains(textFieldText)) {
                if (action.getClass() != SearchAction.class) {
                    tmpList.add(action);
                }
            }
        }
        return tmpList;
    }

    public void getAllTools() {
        if (toolList.isEmpty()) {
            ActionResult = Lookup.getDefault().lookupResult(ActionSPI.class);
            ActionResult.allItems();
            for (ActionSPI action : ActionResult.allInstances()) {
                toolList.add((Action) action);
            }
        }
    }
}
