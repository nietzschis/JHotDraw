/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.samples.svg.Main;
import org.jhotdraw.samples.svg.SVGApplicationModel;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author Daniel
 */
public class GivenSearch extends Stage<GivenSearch>{
    @ProvidedScenarioState
    Application app;

    public GivenSearch an_application_with_an_empty_searchbar() {
        ResourceBundleUtil.setVerbose(true);

        //Application app;
        String os = System.getProperty("os.name").toLowerCase();
        if (os.startsWith("win")) {
          //  app = new DefaultMDIApplication();
            app = new DefaultSDIApplication();
        } else {
            app = new DefaultSDIApplication();
        }
        SVGApplicationModel model = new SVGApplicationModel();
        model.setName("JHotDraw SVG");
        model.setVersion(Main.class.getPackage().getImplementationVersion());
        model.setCopyright("Copyright 2006-2009 (c) by the authors of JHotDraw\n" +
                "This software is licensed under LGPL or Creative Commons 3.0 BY");
        model.setViewClassName("org.jhotdraw.samples.svg.SVGView");
        app.setModel(model);
        app.launch(null);
        app.getTextField().setText("");
        return self();
    }
    
}
