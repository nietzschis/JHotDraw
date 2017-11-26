/*
 * @(#)CreationTool.java  2.5  2008-05-24
 *
 * Copyright (c) 1996-2008 by the original authors of JHotDraw
 * and all its contributors.
 * All rights reserved.
 *
 * The copyright of this software is owned by the authors and  
 * contributors of the JHotDraw project ("the copyright holders").  
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * the copyright holders. For details see accompanying license terms. 
 */
package org.jhotdraw.draw;

import org.jhotdraw.draw.*;
import org.jhotdraw.graph.Graph;
import org.jhotdraw.graph.LinearGraph;
import org.jhotdraw.util.ResourceBundleUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Map;
import org.jhotdraw.gui.AbstractFunctionPanel;
import org.jhotdraw.gui.AbstractGraphPanel;
import org.jhotdraw.gui.LinearGraphPanel;
import org.jhotdraw.gui.QuadraticGraphPanel;

/**
 * A tool to create new figures. The figure to be created is specified by a
 * prototype.
 * <p>
 * To create a figure using the CreationTool, the user does the following mouse
 * gestures on a DrawingView:
 * <ol>
 * <li>Press the mouse button over the DrawingView. This defines the
 * start point of the Figure bounds.</li>
 * <li>Drag the mouse while keeping the mouse button pressed, and then release
 * the mouse button. This defines the end point of the Figure bounds.</li>
 * </ol>
 * The CreationTool works well with most figures that fit into a rectangular
 * shape or that concist of a single straight line. For figures that need
 * additional editing after these mouse gestures, the use of a specialized
 * creation tool is recommended. For example the TextTool allows to enter the
 * text into a TextFigure after the user has performed the mouse gestures.
 * <p>
 * Alltough the mouse gestures might be fitting for the creation of a connection,
 * the CreationTool is not suited for the creation of a ConnectionFigure. Use
 * the ConnectionTool for this type of figures instead.
 * <p>
 * Design pattern:<br>
 * Name: Prototype.<br>
 * Role: Client.<br>
 * Partners: {@link Figure} as Prototype.
 *
 * @author Werner Randelshofer
 * @version 2.4 2008-05-24 Made all private variables protected. Use crosshair
 * cursor for creation tool.
 * <br>2.2 2007-08-22 Added property 'toolDoneAfterCreation'.
 * <br>2.1.1 2006-07-20 Minimal size treshold was enforced too eagerly.
 * <br>2.1 2006-07-15 Changed to create prototype creation from class presentationName.
 * <br>2.0 2006-01-14 Changed to support double precision coordinates.
 * <br>1.0 2003-12-01 Derived from JHotDraw 5.4b1.
 */
public class PredefinedFunctionTool extends AbstractTool {

    /**
     * Attributes to be applied to the created ConnectionFigure.
     * These attributes override the default attributes of the
     * DrawingEditor.
     */
    protected Map<AttributeKey, Object> prototypeAttributes;
    /**
     * A localized name for this tool. The presentationName is displayed by the
     * UndoableEdit.
     */
    protected String presentationName;
    /**
     * Treshold for which we create a larger shape of a minimal size.
     */
    protected Dimension minimalSizeTreshold = new Dimension(2, 2);
    /**
     * We set the figure to this minimal size, if it is smaller than the
     * minimal size treshold.
     */
    protected Dimension minimalSize = new Dimension(40, 40);
    /**
     * The prototype for new figures.
     */
    protected Figure prototype;
    /**
     * The created figure.
     */
    protected PredefinedBezierFigure createdFigure;
    /**
     * If this is set to false, the CreationTool does not fire toolDone
     * after a new Figure has been created. This allows to create multiple
     * figures consecutively.
     */
    private boolean isToolDoneAfterCreation = true;

    /** Creates a new instance. */
    public PredefinedFunctionTool(String prototypeClassName) {
        this(prototypeClassName, null, null);
    }

    public PredefinedFunctionTool(String prototypeClassName, Map<AttributeKey, Object> attributes) {
        this(prototypeClassName, attributes, null);
    }

    public PredefinedFunctionTool(String prototypeClassName, Map<AttributeKey, Object> attributes, String name) {
        try {
            this.prototype = (Figure) Class.forName(prototypeClassName).newInstance();
        } catch (Exception e) {
            InternalError error = new InternalError("Unable to create Figure from " + prototypeClassName);
            error.initCause(e);
            throw error;
        }
        this.prototypeAttributes = attributes;
        if (name == null) {
            ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
            name = labels.getString("edit.createFigure.text");
        }
        this.presentationName = name;
    }

    /** Creates a new instance with the specified prototype but without an
     * attribute set. The CreationTool clones this prototype each time a new
     *  Figure needs to be created. When a new Figure is created, the
     * CreationTool applies the default attributes from the DrawingEditor to it.
     *
     * @param prototype The prototype used to create a new Figure.
     */
    public PredefinedFunctionTool(Figure prototype) {
        this(prototype, null, null);
    }

    /** Creates a new instance with the specified prototype but without an
     * attribute set. The CreationTool clones this prototype each time a new
     * Figure needs to be created. When a new Figure is created, the
     * CreationTool applies the default attributes from the DrawingEditor to it,
     * and then it applies the attributes to it, that have been supplied in
     * this constructor.
     *
     * @param prototype The prototype used to create a new Figure.
     * @param attributes The CreationTool applies these attributes to the
     * prototype after having applied the default attributes from the DrawingEditor.
     */
    public PredefinedFunctionTool(Figure prototype, Map<AttributeKey, Object> attributes) {
        this(prototype, attributes, null);
    }

    /**
     * Creates a new instance with the specified prototype and attribute set.
     *
     * @param prototype The prototype used to create a new Figure.
     * @param attributes The CreationTool applies these attributes to the
     * prototype after having applied the default attributes from the DrawingEditor.
     * @param name The name parameter is currently not used.
     * @deprecated This constructor might go away, because the name parameter
     * is not used.
     */
    public PredefinedFunctionTool(Figure prototype, Map<AttributeKey, Object> attributes, String name) {
        this.prototype = prototype;
        this.prototypeAttributes = attributes;
        if (name == null) {
            ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
            name = labels.getString("edit.createFigure.text");
        }
        this.presentationName = name;
    }

    public Figure getPrototype() {
        return prototype;
    }

    private PredefinedFunction createFunction(AbstractFunctionPanel[] jPanel) {
        boolean notdone = true;
        AbstractFunctionPanel panel = null;
        JDialog f = null;
        while (notdone) {
            f = null;
            f = new JDialog();
            if (panel == null) {
                panel = new LinearGraphPanel();
            } else {
                panel = panel.changeFrame();
            }      
            panel.setList(jPanel);
            panel.setDialog(f);
            f.setUndecorated(true);
            f.setTitle("");
            f.setType(javax.swing.JFrame.Type.UTILITY);
            f.setModal(true);
            f.setSize(400, 300);
            f.setLocationRelativeTo(null);
            f.add(panel);
            f.pack();
            f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            f.setVisible(true);
            notdone = panel.isDone();
        }
        return panel.getGraph();
    }

    private AbstractFunctionPanel[] jPanel = {new LinearGraphPanel(), new QuadraticGraphPanel()};
    
    @Override
    public void activate(DrawingEditor editor) {
        super.activate(editor);
        graf = (Graph) createFunction(jPanel);
        //getView().clearSelection();
        getView().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

    private Graph graf;

    private void setGraph() {
        graf = new LinearGraph(2,2,0,300,300);

    }

    @Override
    public void deactivate(DrawingEditor editor) {
        super.deactivate(editor);
        if (getView() != null) {
            getView().setCursor(Cursor.getDefaultCursor());
        }
        if (createdFigure != null) {
            if (createdFigure instanceof CompositeFigure) {
                ((CompositeFigure) createdFigure).layout();
            }
            createdFigure = null;
        }
    }

    public void mousePressed(MouseEvent evt) {
        super.mousePressed(evt);
        getView().clearSelection();
        createdFigure = createFigure();
        createdFigure.setFunction(graf);
        Point2D.Double p = constrainPoint(viewToDrawing(anchor));
        anchor.x = evt.getX();
        anchor.y = evt.getY();
        createdFigure.setBounds(p, p);
        getDrawing().add(createdFigure);
    }

    public void mouseDragged(MouseEvent evt) {


    }

    @SuppressWarnings("unchecked")
    protected PredefinedBezierFigure createFigure() {
        PredefinedBezierFigure f = (PredefinedBezierFigure) prototype.clone();
        getEditor().applyDefaultAttributesTo(f);
        if (prototypeAttributes != null) {
            for (Map.Entry<AttributeKey, Object> entry : prototypeAttributes.entrySet()) {
                entry.getKey().basicSet(f, entry.getValue());
            }
        }
        return f;
    }

    protected Figure getCreatedFigure() {
        return createdFigure;
    }

    protected Figure getAddedFigure() {
        return createdFigure;
    }

    /**
     * This method allows subclasses to do perform additonal user interactions
     * after the new figure has been created.
     * The implementation of this class just invokes fireToolDone.
     */
    protected void creationFinished(Figure createdFigure) {
        if (createdFigure.isSelectable()) {
            getView().addToSelection(createdFigure);
        }
        if (isToolDoneAfterCreation()) {
            fireToolDone();
        }
    }

    /**
     * If this is set to false, the CreationTool does not fire toolDone
     * after a new Figure has been created. This allows to create multiple
     * figures consecutively.
     */
    public void setToolDoneAfterCreation(boolean newValue) {
        boolean oldValue = isToolDoneAfterCreation;
        isToolDoneAfterCreation = newValue;
    }

    /**
     * Returns true, if this tool fires toolDone immediately after a new
     * figure has been created.
     */
    public boolean isToolDoneAfterCreation() {
        return isToolDoneAfterCreation;
    }

    @Override
    public void updateCursor(DrawingView view, Point p) {
        if (view.isEnabled()) {
            view.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        } else {
            view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        }
    }
}
