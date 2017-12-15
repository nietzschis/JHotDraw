/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import org.jhotdraw.services.ApplicationSPI;
import org.jhotdraw.services.ActionSPI;
import java.util.ArrayList;
import javax.swing.Action;
import org.jhotdraw.app.AbstractApplication;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.samples.svg.Main;
import org.jhotdraw.samples.svg.SVGApplicationModel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.openide.util.Lookup;

/**
 *
 * @author Daniel
 */
public class SearchActionTest {
    AbstractApplication app;
    SVGApplicationModel model;
    ArrayList<Action> actions = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        app = (AbstractApplication) Lookup.getDefault().lookup(ApplicationSPI.class).getApplicationInstance();
        model = new SVGApplicationModel();
        app.setModel(model);
        actions.add(app.getModel().getActionDynamicly(RedoAction.class));
        actions.add(app.getModel().getActionDynamicly(AboutAction.class));
    }
    
    @Test
    public void testLookup(){
        assertTrue(app != null);
        assertTrue(Lookup.getDefault().lookupAll(ActionSPI.class).size() >= 3);
    }
    
    @Test
    public void testClassLookup(){
        app.getModel().putAction(AboutAction.ID, new AboutAction());
        
        assertTrue(app.getModel().getActionDynamicly(AboutAction.class) != null);
        assertTrue(app.getModel().getAction(AboutAction.ID).getClass().equals(app.getModel().getActionDynamicly(AboutAction.class).getClass()));
    }
    
}
