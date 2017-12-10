/*
 * @(#)DrawToolsPane.java  2.0  2008-04-06
 *
 * Copyright (c) 2007-2008 by the original authors of JHotDraw
 * and all its contributors.
 * All rights reserved.
 *
 * The copyright of this software is owned by the authors and  
 * contributors of the JHotDraw project ("the copyright holders").  
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * the copyright holders. For details see accompanying license terms. 
 */
package org.jhotdraw.samples.svg.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.border.*;
import org.jhotdraw.gui.plaf.palette.*;
import org.jhotdraw.util.*;
import java.util.*;
import javax.swing.*;
import org.jhotdraw.app.action.*;
import org.jhotdraw.draw.*;
import static org.jhotdraw.draw.AnimationToolDefinition.*;
import org.jhotdraw.draw.action.*;
import org.jhotdraw.samples.svg.action.*;
import org.jhotdraw.samples.svg.figures.*;

/**
 * AnimationToolbar
 *
 * @author Antonio Lascari & Alexander Nytofte Markussen
 */
public class AnimationToolBar extends AbstractToolBar {

    private List<AbstractButton> buttons = new ArrayList<>() ;
    
    /**
     * Creates new instance.
     */
    public AnimationToolBar() {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        setName(labels.getString("tools.animationToolbar"));
    }

    @Override
    protected JComponent createDisclosedComponent(int state) {
        JPanel p = null;

        switch (state) {
            case 1: {
                p = new JPanel();
                p.setOpaque(false);
                p.setBorder(new EmptyBorder(5, 5, 5, 8));

                ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org."
                        + "jhotdraw.samples.svg.Labels");

                GridBagLayout layout = new GridBagLayout();
                p.setLayout(layout);
                GridBagConstraints gbc;
                AbstractButton btn;
                AnimationTool animationTool;
                
                
                btn = new JButton(new AnimationTool(ADD_FRAME_TOOL));
                btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                labels.configureToolBarButton(btn, "addFrame");
                gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(0, 0, 0, 0);
                buttons.add(btn);
                p.add(btn, gbc);
                
                btn = new JButton(new AnimationTool(REMOVE_FRAME_TOOL));
                btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                labels.configureToolBarButton(btn, "removeFrame");
                gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.insets = new Insets(5, 0, 0, 0);
                buttons.add(btn);
                p.add(btn, gbc);
                
                btn = new JButton(new AnimationTool(PLAY_TOOL));
                btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                labels.configureToolBarButton(btn, "play");
                gbc = new GridBagConstraints();
                gbc.gridx = 1;
                gbc.gridy = 0;
                gbc.insets = new Insets(0, 5, 0, 0);
                buttons.add(btn);
                p.add(btn, gbc);
                
                btn = new JButton(new AnimationTool(PAUSE_TOOL));
                btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                labels.configureToolBarButton(btn, "pause");
                gbc = new GridBagConstraints();
                gbc.gridx = 1;
                gbc.gridy = 1;
                gbc.insets = new Insets(5, 5, 0, 0);
                buttons.add(btn);
                p.add(btn, gbc);
                
//                HashMap<AttributeKey, Object> attributes = new HashMap<>();
//                Tool frameEditorTool;
//                Tool selectionTool = 
//                        new DelegationSelectionTool(ButtonFactory.createDrawingActions(editor), createSelectionActions(editor));
//                CreationTool creationTool = new CreationTool(new SVGGroupFigure(), attributes);
//                btn = ButtonFactory.addToolTo(this, editor, frameEditorTool = new FrameEditorTool(selectionTool, creationTool, editor), "frameEditor", labels);
//                btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
//                //labels.configureToolBarButton(btn, "pause");
//                gbc = new GridBagConstraints();
//                gbc.gridx = 2;
//                gbc.gridy = 0;
//                gbc.insets = new Insets(10, 5, 0, 0);
//                p.add(btn, gbc);
            }
            break;
        }
        return p;
    }

    public List<AbstractButton> getButtons() {
        return buttons;
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

        return a;
    }

    @Override
    protected String getID() {
        return "tools";
    }

    protected int getDefaultDisclosureState() {
        return 1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setOpaque(false);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
