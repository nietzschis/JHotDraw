/*
 * @(#)DefaultApplicationModel.java  1.1  2007-01-11
 *
 * Copyright (c) 1996-2007 by the original authors of JHotDraw
 * and all its contributors.
 * All rights reserved.
 *
 * The copyright of this software is owned by the authors and  
 * contributors of the JHotDraw project ("the copyright holders").  
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * the copyright holders. For details see accompanying license terms. 
 */
package org.jhotdraw.app;

import org.jhotdraw.services.ActionSPI;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.jhotdraw.app.action.*;
import org.jhotdraw.beans.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import org.jhotdraw.util.ResourceBundleUtil;
import org.openide.util.Lookup;

/**
 * DefaultApplicationModel.
 *
 * @author Werner Randelshofer.
 * @version 1.1 2007-01-11 Changed method createToolBars.
 * <br>1.0 June 10, 2006 Created.
 */
public abstract class DefaultApplicationModel
        extends AbstractBean
        implements ApplicationModel {

    private HashMap<String, Action> actions;
    private String name;
    private String version;
    private String copyright;
    private Class viewClass;
    private String viewClassName;

    public final static String NAME_PROPERTY = "name";
    public final static String VERSION_PROPERTY = "version";
    public final static String COPYRIGHT_PROPERTY = "copyright";
    public final static String VIEW_CLASS_NAME_PROPERTY = "viewClassName";
    public final static String VIEW_CLASS_PROPERTY = "viewClass";

    private final Lookup lookup = Lookup.getDefault();
    private Lookup.Result<ActionSPI> ActionResult;

    /**
     * Creates a new instance.
     */
    public DefaultApplicationModel() {
    }

    public void setName(String newValue) {
        String oldValue = name;
        name = newValue;
        firePropertyChange(NAME_PROPERTY, oldValue, newValue);
    }

    public String getName() {
        return name;
    }

    public void setVersion(String newValue) {
        String oldValue = version;
        version = newValue;
        firePropertyChange(VERSION_PROPERTY, oldValue, newValue);
    }

    public String getVersion() {
        return version;
    }

    public void setCopyright(String newValue) {
        String oldValue = copyright;
        copyright = newValue;
        firePropertyChange(COPYRIGHT_PROPERTY, oldValue, newValue);
    }

    public String getCopyright() {
        return copyright;
    }

    /**
     * Use this method for best application startup performance.
     */
    public void setViewClassName(String newValue) {
        String oldValue = viewClassName;
        viewClassName = newValue;
        firePropertyChange(VIEW_CLASS_NAME_PROPERTY, oldValue, newValue);
    }

    /**
     * Use this method only, if setViewClassName() does not suit you.
     */
    public void setViewClass(Class newValue) {
        Class oldValue = viewClass;
        viewClass = newValue;
        firePropertyChange(VIEW_CLASS_PROPERTY, oldValue, newValue);
    }

    public Class getViewClass() {
        if (viewClass == null) {
            if (viewClassName != null) {
                try {
                    viewClass = Class.forName(viewClassName);
                } catch (Exception e) {
                    InternalError error = new InternalError("unable to get view class");
                    error.initCause(e);
                    throw error;
                }
            } else {
                InternalError error = new InternalError("unable to get view "
                        + "class as view class name is not set");
                throw error;
            }
        }
        return viewClass;
    }

    public View createView() {
        try {
            return (View) getViewClass().newInstance();
        } catch (Exception e) {
            InternalError error = new InternalError("unable to create view");
            error.initCause(e);
            throw error;
        }
    }

    /**
     * Creates toolbars for the application. This class creates a standard
     * toolbar with the following buttons in it:
     * <ul>
     * <li>File New</li>
     * <li>File Open</li>
     * <li>File Save</li>
     * <li>Undo</li>
     * <li>Redo</li>
     * <li>Cut</li>
     * <li>Copy</li>
     * <li>Paste</li>
     * </ul>
     */
    public List<JToolBar> createToolBars(Application app, View p) {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");

        JToolBar tb = new JToolBar();
        tb.setName(labels.getString("standardToolBarTitle"));

        JButton b;
        Action a;
        if (null != (a = getActionDynamicly(NewAction.class))) {
            b = tb.add(a);
            b.setFocusable(false);
        }
        if (null != (a = getActionDynamicly(DuplicateCanvasAction.class))) {
            b = tb.add(a);
            b.setFocusable(false);
        }
        if (null != (a = getActionDynamicly(OpenAction.class))) {
            b = tb.add(a);
            b.setFocusable(false);
        }
        if (null != (a = getActionDynamicly(LoadAction.class))) {
            b = tb.add(a);
            b.setFocusable(false);
        }
        b = tb.add(getAction(SaveAction.ID));
        tb.addSeparator();
        b = tb.add(getAction(UndoAction.ID));
        b.setFocusable(false);
        b = tb.add(getActionDynamicly(RedoAction.class));
        b.setFocusable(false);
        tb.addSeparator();
        b = tb.add(getActionDynamicly(CutAction.class));
        b.setFocusable(false);
        b = tb.add(getActionDynamicly(CopyAction.class));
        b.setFocusable(false);
        b = tb.add(getActionDynamicly(PasteAction.class));
        b.setFocusable(false);

        LinkedList<JToolBar> list = new LinkedList<JToolBar>();
        list.add(tb);
        return list;
    }

    public List<JMenu> createMenus(Application a, View p) {
        LinkedList<JMenu> list = new LinkedList<JMenu>();
        list.add(createEditMenu(a, p));
        return list;
    }

    protected JMenu createEditMenu(Application a, View p) {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");

        JMenu m;
        JMenuItem mi;

        m = new JMenu();
        labels.configureMenu(m, "edit");
        mi = m.add(getAction(UndoAction.ID));
        mi.setIcon(null);
        mi = m.add(getActionDynamicly(RedoAction.class));
        mi.setIcon(null);
        m.addSeparator();
        mi = m.add(getActionDynamicly(CutAction.class));
        mi.setIcon(null);
        mi = m.add(getActionDynamicly(CopyAction.class));
        mi.setIcon(null);
        mi = m.add(getActionDynamicly(PasteAction.class));
        mi.setIcon(null);
        mi = m.add(getActionDynamicly(DuplicateAction.class));
        mi.setIcon(null);
        mi = m.add(getActionDynamicly(DeleteAction.class));
        mi.setIcon(null);
        m.addSeparator();
        mi = m.add(getActionDynamicly(SelectAllAction.class));
        mi.setIcon(null);
        if (getAction(FindAction.ID) != null) {
            m.addSeparator();
            m.add(getAction(FindAction.ID));
        }
        return m;
    }

    public void initView(Application a, View p) {
    }

    public void initApplication(Application a) {
    }

    @Override
    public Action getActionDynamicly(Class name) {
            return (Action) lookup.lookup(name);
    }
    /**
     * Returns the action with the specified id.
     */
    @Override
    public Action getAction(String id) {
        return (actions == null) ? null : (Action) actions.get(id);
    }

    /**
     * Puts an action with the specified id.
     */
    public void putAction(String id, Action action) {
        if (actions == null) {
            actions = new HashMap<String, Action>();
        }
        if (action == null) {
            actions.remove(id);
        } else {
            actions.put(id, action);
        }
    }
}
