/*
 * @(#)Main.java  1.0  July 8, 2006
 *
 * Copyright (c) 1996-2006 by the original authors of JHotDraw
 * and all its contributors.
 * All rights reserved.
 *
 * The copyright of this software is owned by the authors and  
 * contributors of the JHotDraw project ("the copyright holders").  
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * the copyright holders. For details see accompanying license terms. 
 */

package org.jhotdraw.samples.svg;

import org.jhotdraw.app.*;
import org.jhotdraw.services.ApplicationSPI;
import org.jhotdraw.util.ResourceBundleUtil;
import org.openide.util.lookup.ServiceProvider;
/**
 * Main.
 *
 * @author Werner Randelshofer.
 * @version 1.0 July 8, 2006 Created.
 */
@ServiceProvider(service = ApplicationSPI.class)
public class Main implements ApplicationSPI{
    public static Application app;
    
    /** Creates a new instance. */
    public static void main(String[] args) {
        // Debug resource bundle
        ResourceBundleUtil.setVerbose(true);

        //Application app;
        String os = System.getProperty("os.name").toLowerCase();
        if (os.startsWith("win")) {
          //  app = new DefaultMDIApplication();
            createApplication();
        } else {
            createApplication();
        }
        SVGApplicationModel model = new SVGApplicationModel();
        model.setName("JHotDraw SVG");
        model.setVersion(Main.class.getPackage().getImplementationVersion());
        model.setCopyright("Copyright 2006-2009 (c) by the authors of JHotDraw\n" +
                "This software is licensed under LGPL or Creative Commons 3.0 BY");
        model.setViewClassName("org.jhotdraw.samples.svg.SVGView");
        app.setModel(model);
        app.launch(args);
    }
    
    private static void createApplication(){
        if(app == null){
            app = new DefaultSDIApplication();
        }
    }

    @Override
    public Application getApplicationInstance() {
        createApplication();
        return app;
    }
    
}
