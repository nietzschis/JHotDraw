/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw.action;

import java.awt.Container;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.CreationTool;
import org.jhotdraw.draw.DefaultDrawingEditor;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.QuadTreeDrawing;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.jhotdraw.util.ResourceBundleUtil;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alexander
 */
public class ButtonFactoryIT {
    
    @Test
    public void createRectangleTool() {
        JToolBar toolbar = new JToolBar("TestingToolbar");
        DrawingEditor editor = new DefaultDrawingEditor();
        
        
        AbstractButton button;
        Map<AttributeKey, Object> toolAttributes = new HashMap<>();
        CreationTool rectangleTool = 
                new CreationTool(new SVGRectFigure(), toolAttributes);
        ResourceBundleUtil labels = 
                ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        
        ButtonGroup group = new ButtonGroup();
        toolbar.putClientProperty("toolButtonGroup", group);
        
        button = ButtonFactory.addToolTo(toolbar, editor, rectangleTool, "createRectangle", labels);
        
        
        
        toolbar.add(button);
        
        assertEquals(1, toolbar.getComponentCount());
    }
    
    @Test
    public void testUseRectangleTool() {
        JToolBar toolbar = new JToolBar("TestingToolbar");
        DrawingEditor editor = new DefaultDrawingEditor();
        DrawingView view = new DefaultDrawingView();
        Drawing drawing = new QuadTreeDrawing();
        
        editor.setActiveView(view);
        editor.add(view);
        view.setDrawing(drawing);
        
        
        
        
        AbstractButton button;
        Map<AttributeKey, Object> toolAttributes = new HashMap<>();
        CreationTool rectangleTool = 
                new CreationTool(new SVGRectFigure(), toolAttributes);
        ResourceBundleUtil labels = 
                ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        
        ButtonGroup group = new ButtonGroup();
        toolbar.putClientProperty("toolButtonGroup", group);
        
        button = ButtonFactory.addToolTo(toolbar, editor, rectangleTool, "createRectangle", labels);
        
        
        
        toolbar.add(button);
        view.getComponent().add(button);
        
        rectangleTool.activate(editor);
        MouseEvent event = new MouseEvent(view.getComponent(), 
                MouseEvent.MOUSE_CLICKED, 
                1L, 
                MouseEvent.BUTTON1_MASK, 10, 10, 1, true);
        rectangleTool.mousePressed(event);
        //button.doClick();
        
        System.out.println("Rectangle tool activated: " + rectangleTool.isActive());
        for (DrawingView v : editor.getDrawingViews()) {
            List<Figure> figuresInDrawing = v.getDrawing().getChildren();
            assertTrue(!figuresInDrawing.isEmpty());
            assertEquals(new SVGRectFigure().getClass(), figuresInDrawing.get(0).getClass());
        }
    }
}
