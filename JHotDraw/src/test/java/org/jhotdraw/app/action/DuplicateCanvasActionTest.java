/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.jhotdraw.app.AbstractApplication;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.app.View;
import org.jhotdraw.samples.svg.SVGApplicationModel;
import org.jhotdraw.services.ApplicationSPI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openide.util.Lookup;

/**
 *
 * @author ville
 */
public class DuplicateCanvasActionTest {
    private Application app;

    @Before
    public void setUp() throws InterruptedException {
        app = (AbstractApplication) Lookup.getDefault().lookup(ApplicationSPI.class).getApplicationInstance();
        SVGApplicationModel model = new SVGApplicationModel();
        model.setViewClassName("org.jhotdraw.samples.svg.SVGView");
        app.setModel(model);
        app.launch(null);
    }
    
    @After
    public void tearDown() {
        app = null;
    }
    
    @Test
    public void testDuplicateWindow() throws URISyntaxException {
        Assert.assertNotNull(app);
        DuplicateCanvasAction dca = new DuplicateCanvasAction();
        
        URL url = DuplicateCanvasActionTest.class.getResource("duplicatecanvas/test21.svg");
        URI uri = url.toURI(); // convert URL to URI to make the filepath work with spaces.
        
        File f = new File(uri.getPath());
        
        View testView = app.createView();
        testView.setFile(f);
        
        app.add(testView);
        
        dca.actionPerformed(null);
        
        View viewToCheck = null;
        
        for (View existingView : app.views()) {
            if (existingView.getFile() != null) {
                if (viewToCheck == null) {
                    viewToCheck = existingView;
                } else {
                    Assert.assertEquals(viewToCheck.getFile(), existingView.getFile());
                }
            }
        }
        
    }
}
