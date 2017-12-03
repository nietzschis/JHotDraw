/*
 * @(#)SVGApplicationModel.java  2.0  2009-04-10
 *
 * Copyright (c) 1996-2009 by the original authors of JHotDraw
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

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import org.jhotdraw.app.action.*;
import org.jhotdraw.samples.svg.action.*;
import org.jhotdraw.samples.svg.figures.*;
import org.jhotdraw.util.*;
import java.util.*;
import javax.swing.*;
import org.jhotdraw.app.*;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.action.*;

/**
 * SVGApplicationModel.
 *
 * @author Werner Randelshofer.
 * @version 2.0 2009-04-10 Moved all drawing related toolbars into SVGDrawingPanel.
 * <br>1.0 June 10, 2006 Created.
 */
public class SVGApplicationModel extends DefaultApplicationModel {

    private final static double[] scaleFactors = {5, 4, 3, 2, 1.5, 1.25, 1, 0.75, 0.5, 0.25, 0.10};
    private GridConstrainer gridConstrainer;
    /**
     * This editor is shared by all views.
     */
    private DefaultDrawingEditor sharedEditor;

    /** Creates a new instance. */
    @FeatureEntryPoint(JHotDrawFeatures.APPLICATION_STARTUP)
    public SVGApplicationModel() {
    }

    public DefaultDrawingEditor getSharedEditor() {
        if (sharedEditor == null) {
            sharedEditor = new DefaultDrawingEditor();
        }
        return sharedEditor;
    }

    @Override
    public void initView(Application a, View p) {
        SVGView v = (SVGView) p;
        if (a.isSharingToolsAmongViews()) {
            v.setEditor(getSharedEditor());
        }

        p.putAction(SelectSameAction.ID, new SelectSameAction(v.getEditor()));
    }

    @Override
    public void initApplication(Application a) {
        ResourceBundleUtil drawLabels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        AbstractAction aa;

        gridConstrainer = new GridConstrainer(12, 12);

        putAction(ClearSelectionAction.ID, new ClearSelectionAction());
        putAction(ViewSourceAction.ID, new ViewSourceAction(a));
        putAction(ExportAction.ID, new ExportAction(a));
    }

    public Collection<Action> createDrawingActions(Application app, DrawingEditor editor) {
        LinkedList<Action> a = new LinkedList<Action>();
        a.add(new CutAction());
        a.add(new CopyAction());
        a.add(new PasteAction());
        a.add(new SelectAllAction());
        a.add(new ClearSelectionAction());
        a.add(new SelectSameAction(editor));
        return a;
    }

    public static Collection<Action> createSelectionActions(DrawingEditor editor) {
        LinkedList<Action> a = new LinkedList<Action>();
        a.add(new DuplicateAction());

        a.add(null); // separator
        a.add(new GroupAction(editor, new SVGGroupFigure()));
        a.add(new UngroupAction(editor, new SVGGroupFigure()));
        a.add(new CombineAction(editor));
        a.add(new SplitAction(editor));

        a.add(null); // separator
        a.add(new BringToFrontAction(editor));
        a.add(new SendToBackAction(editor));
        
        a.add(null); // separator
        a.add(new EdgeDetectionAction(editor));

        return a;
    }

    /**
     * Where all the menu points are created.
     * To create a new menu, just add it to the mb list
     * @return the menus to be drawn
     */
    @Override
    public java.util.List<JMenu> createMenus(Application a, View pr) {
        LinkedList<JMenu> mb = new LinkedList<>();
        mb.add(createFileMenu(a, pr));
        mb.add(createEditMenu(a, pr));
        mb.add(createCollaborationMenu());
        mb.add(createViewMenu());
        mb.add(createHelpMenu());
        
        return mb;
    }
    
    private JMenu createFileMenu(Application a, View p) {
        JMenu m = new JMenu();
        final JMenu openRecentMenu;
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        
        labels.configureMenu(m, "file");
        m.add(getAction(ClearAction.ID));
        m.add(getAction(NewAction.ID));
        m.add(getAction(LoadAction.ID));
        if (getAction(LoadDirectoryAction.ID) != null) {
            m.add(getAction(LoadDirectoryAction.ID));
        }
        openRecentMenu = new JMenu();
        labels.configureMenu(openRecentMenu, "file.openRecent");
        openRecentMenu.add(getAction(ClearRecentFilesAction.ID));
        updateOpenRecentMenu(openRecentMenu, a);
        m.add(openRecentMenu);
        m.addSeparator();
        m.add(getAction(SaveAction.ID));
        m.add(getAction(SaveAsAction.ID));
        if (getAction(ExportAction.ID) != null) {
            m.add(getAction(ExportAction.ID));
        }
        if (getAction(PrintAction.ID) != null) {
            m.addSeparator();
            m.add(getAction(PrintAction.ID));
        }
        m.addSeparator();
        m.add(getAction(ExitAction.ID));
        
        
        a.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                String name = evt.getPropertyName();
                if (name == "viewCount") {
                    if (p == null || a.views().contains(p)) {
                    } else {
                        a.removePropertyChangeListener(this);
                    }
                } else if (name == "recentFiles") {
                    updateOpenRecentMenu(openRecentMenu, a);
                }
            }
        });

        return m;
    }
    
    private void updateOpenRecentMenu(JMenu openRecentMenu, Application a) {
        if (openRecentMenu.getItemCount() > 0) {
            JMenuItem clearRecentFilesItem = (JMenuItem) openRecentMenu.getItem(
                    openRecentMenu.getItemCount() - 1);
            openRecentMenu.removeAll();
            for (File f : a.recentFiles()) {
                openRecentMenu.add(new LoadRecentAction(a, f));
            }
            if (a.recentFiles().size() > 0) {
                openRecentMenu.addSeparator();
            }
            openRecentMenu.add(clearRecentFilesItem);
        }
    }

    private JMenu createViewMenu() {
        JMenu m;
        ResourceBundleUtil appLabels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");

        m = new JMenu();
        appLabels.configureMenu(m, "view");
        m.add(getAction(ViewSourceAction.ID));

        return m;
    }
    
    private JMenu createCollaborationMenu() {
        JMenu menu = new JMenu();
        ResourceBundleUtil appLabels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");       
        appLabels.configureMenu(menu, "collaboration");
        
        menu.add(getAction(CollaborationStartServerAction.ID));   
        menu.add(getAction(CollaborationStopServerAction.ID));      
        menu.add(getAction(CollaborationListConnectionsAction.ID));     
        menu.addSeparator();      
        menu.add(getAction(CollaborationConnectAction.ID));       
        menu.add(getAction(CollaborationDisconnectAction.ID));

        return menu;
    }

    @Override
    protected JMenu createEditMenu(Application a, View p) {
        ResourceBundleUtil drawLabels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");

        JMenu m = super.createEditMenu(a, p);
        JMenuItem mi;

        mi = m.add(getAction(ClearSelectionAction.ID));
        mi.setIcon(null);

        if (p != null) {
            mi = m.add(p.getAction(SelectSameAction.ID));
        } else {
            mi = new JMenuItem();
            drawLabels.configureMenu(mi, SelectSameAction.ID);
            mi.setEnabled(false);
            m.add(mi);
        }
        mi.setIcon(null);
        return m;
    }
    
    private JMenu createHelpMenu() {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        JMenu m;

        m = new JMenu();
        labels.configureMenu(m, "help");
        m.add(getAction(AboutAction.ID));

        return m;
    }

    /**
     * Overriden to create no toolbars.
     * 
     * @param app
     * @param p
     * @return An empty list.
     */
    @Override
    public List<JToolBar> createToolBars(Application app, View p) {
        LinkedList<JToolBar> list = new LinkedList<JToolBar>();
        return list;
    }
}
