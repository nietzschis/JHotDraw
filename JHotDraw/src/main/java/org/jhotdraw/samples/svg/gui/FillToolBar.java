/*
 * @(#)FillToolBar.java  1.2  2008-05-23
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

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import org.jhotdraw.text.JavaNumberFormatter;
import javax.swing.border.*;
import org.jhotdraw.gui.*;
import org.jhotdraw.util.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.plaf.SliderUI;
import org.jhotdraw.app.JHotDrawFeatures;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.action.*;
import org.jhotdraw.gui.plaf.palette.*;
import org.jhotdraw.text.ColorFormatter;
import static org.jhotdraw.samples.svg.SVGAttributeKeys.*;

/**
 * FillToolBar.
 *
 * @author Werner Randelshofer
 * @version 1.2 2008-05-23 Hide the toolbar if nothing is selected, and no
 * creation tool is active. 
 * <br>1.1 2008-03-26 Don't draw button borders. 
 * <br>1.0 May 1, 2007 Created.
 */
public class FillToolBar extends AbstractToolBar {
    public static AbstractButton colorPopUpButton;

    private SelectionComponentDisplayer displayer;

    /** Creates new instance. */
    public FillToolBar() {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        setName(labels.getString(getID() + ".toolbar"));
        setDisclosureStateCount(3);
                initComponents();

    }

    @Override
    public void setEditor(DrawingEditor newValue) {
        DrawingEditor oldValue = getEditor();
        if (displayer != null) {
            displayer.dispose();
            displayer = null;
        }
        super.setEditor(newValue);
        if (newValue != null) {
            displayer = new SelectionComponentDisplayer(editor, this);
        }
    }

    @Override
    @FeatureEntryPoint(JHotDrawFeatures.FILL_PALETTE)
    protected JComponent createDisclosedComponent(int state) {
        JPanel p = null;

        switch (state) {
            case 1:
                 {
                    p = new JPanel();
                    p.setOpaque(false);
                    p.setBorder(new EmptyBorder(5, 5, 5, 8));
                    ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
                    GridBagLayout layout = new GridBagLayout();
                    p.setLayout(layout);
                    GridBagConstraints gbc;
                    AbstractButton btn;

                    // Fill color
                    //Right mouse
                    Map<AttributeKey, Object> defaultAttributesRight = new HashMap<AttributeKey, Object>();
                    FILL_GRADIENT.set(defaultAttributesRight, null);
                    btn = ButtonFactory.createSelectionColorButton(editor,
                            FILL_COLOR_RIGHT_MOUSE, ButtonFactory.HSV_COLORS, ButtonFactory.HSV_COLORS_COLUMN_COUNT,
                            "attribute.fillColor", labels, defaultAttributesRight, new Rectangle(3, 3, 10, 10));
                    btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                    ((JPopupButton) btn).setAction(null, null);
                    gbc = new GridBagConstraints();
                    gbc.gridx = 0;
                    gbc.gridwidth = 2;
                    gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                    p.add(btn, gbc);
                    
                    //Left mouse
                    Map<AttributeKey, Object> defaultAttributesLeft = new HashMap<AttributeKey, Object>();
                    FILL_GRADIENT.set(defaultAttributesLeft, null);
                    btn = ButtonFactory.createSelectionColorButton(editor,
                            FILL_COLOR_LEFT_MOUSE, ButtonFactory.HSV_COLORS, ButtonFactory.HSV_COLORS_COLUMN_COUNT,
                            "attribute.fillColor", labels, defaultAttributesLeft, new Rectangle(3, 3, 10, 10));
                    btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                    ((JPopupButton) btn).setAction(null, null);
                    gbc = new GridBagConstraints();
                    gbc.gridx = 0;
                    gbc.gridwidth = 2;
                    gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                    p.add(btn, gbc);
                    
                    

                    // Opacity slider
                    JPopupButton opacityPopupButton = new JPopupButton();
                    JAttributeSlider opacitySlider = new JAttributeSlider(JSlider.VERTICAL, 0, 100, 100);
                    opacityPopupButton.add(opacitySlider);
                    labels.configureToolBarButton(opacityPopupButton, "attribute.fillOpacity");
                    opacityPopupButton.setUI((PaletteButtonUI) PaletteButtonUI.createUI(opacityPopupButton));
                    opacityPopupButton.setIcon(
                            new SelectionOpacityIcon(editor, FILL_OPACITY, FILL_COLOR, null, getClass().getResource(labels.getString("attribute.fillOpacity.icon")),
                            new Rectangle(5, 5, 6, 6), new Rectangle(4, 4, 7, 7)));
                    opacityPopupButton.setPopupAnchor(SOUTH_EAST);
                    new SelectionComponentRepainter(editor, opacityPopupButton);
                    gbc = new GridBagConstraints();
                    gbc.gridx = 0;
                    gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                    gbc.weighty = 1f;
                    gbc.insets = new Insets(3, 0, 0, 0);
                    p.add(opacityPopupButton, gbc);
                    opacitySlider.setUI((SliderUI) PaletteSliderUI.createUI(opacitySlider));
                    opacitySlider.setScaleFactor(100d);
                    new FigureAttributeEditorHandler<Double>(FILL_OPACITY, opacitySlider, editor);
                    
                    
                                     
                   // Color pop-up button
                Map<AttributeKey, Object> defaultAttributes = new HashMap<AttributeKey, Object>();
                 defaultAttributes = new HashMap<AttributeKey, Object>();
                 FILL_GRADIENT.set(defaultAttributes, null);        
                 btn = ButtonFactory.createCustomizedColorButton(editor,
                         FILL_COLOR, ButtonFactory.eyedropped, ButtonFactory.HSV_COLORS_COLUMN_COUNT,
                         "attribute.fillColor", labels, defaultAttributes, new Rectangle(3, 3, 10, 10));
                 EyedropperAction.popup = btn;
                 btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                 ((JPopupButton) btn).setAction(null, null);
 
                 gbc = new GridBagConstraints();
                 gbc.gridx = 1;
                 gbc.gridy = 3;
                 gbc.insets = new Insets(0, 3, 0, 0);
                 p.add(btn, gbc);                
                 
                    
                }
                break;

            case 2:
                 {
                    p = new JPanel();
                    p.setOpaque(false);

                    JPanel p1 = new JPanel(new GridBagLayout());
                    JPanel p2 = new JPanel(new GridBagLayout());
                    JPanel p3 = new JPanel(new GridBagLayout());
                    p1.setOpaque(false);
                    p2.setOpaque(false);
                    p3.setOpaque(false);

                    p.setBorder(new EmptyBorder(5, 5, 5, 8));
                    p.removeAll();

                    ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
                    GridBagLayout layout = new GridBagLayout();
                    p.setLayout(layout);
                    GridBagConstraints gbc;
                    AbstractButton btn;

                    // Fill color field and button
                    // Right Mouse button
                    Map<AttributeKey, Object> defaultAttributesRight = new HashMap<AttributeKey, Object>();
                    FILL_GRADIENT.set(defaultAttributesRight, null);
                    JAttributeTextField<Color> colorFieldRight = new JAttributeTextField<Color>();
                    colorFieldRight.setColumns(7);
                    colorFieldRight.setToolTipText(labels.getString("attribute.fillColor.toolTipText"));
                    colorFieldRight.putClientProperty("Palette.Component.segmentPosition", "first");
                    colorFieldRight.setUI((PaletteFormattedTextFieldUI) PaletteFormattedTextFieldUI.createUI(colorFieldRight));
                    colorFieldRight.setFormatterFactory(ColorFormatter.createFormatterFactory());
                    colorFieldRight.setHorizontalAlignment(JTextField.LEFT);
                    new FigureAttributeEditorHandler<Color>(FILL_COLOR_RIGHT_MOUSE, defaultAttributesRight, colorFieldRight, editor, true);
                    gbc = new GridBagConstraints();
                    gbc.gridx = 0;
                    gbc.fill = GridBagConstraints.HORIZONTAL;
                    gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                    p1.add(colorFieldRight, gbc);
                    btn = ButtonFactory.createSelectionColorButton(editor,
                            FILL_COLOR_RIGHT_MOUSE, ButtonFactory.HSV_COLORS, ButtonFactory.HSV_COLORS_COLUMN_COUNT,
                            "attribute.fillColor", labels, defaultAttributesRight, new Rectangle(3, 3, 10, 10));
                    btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                    ((JPopupButton) btn).setAction(null, null);
                    gbc = new GridBagConstraints();
                    gbc.gridx = 1;
                    gbc.gridwidth = 2;
                    gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                    p1.add(btn, gbc);
                    
                    //Left mouse button
                    Map<AttributeKey, Object> defaultAttributesLeft = new HashMap<AttributeKey, Object>();
                    FILL_GRADIENT.set(defaultAttributesLeft, null);
                    JAttributeTextField<Color> colorFieldLeft = new JAttributeTextField<Color>();
                    colorFieldLeft.setColumns(7);
                    colorFieldLeft.setToolTipText(labels.getString("attribute.fillColor.toolTipText"));
                    colorFieldLeft.putClientProperty("Palette.Component.segmentPosition", "first");
                    colorFieldLeft.setUI((PaletteFormattedTextFieldUI) PaletteFormattedTextFieldUI.createUI(colorFieldLeft));
                    colorFieldLeft.setFormatterFactory(ColorFormatter.createFormatterFactory());
                    colorFieldLeft.setHorizontalAlignment(JTextField.LEFT);
                    new FigureAttributeEditorHandler<Color>(FILL_COLOR_LEFT_MOUSE, defaultAttributesLeft, colorFieldLeft, editor, true);
                    gbc = new GridBagConstraints();
                    gbc.gridx = 0;
                    gbc.fill = GridBagConstraints.HORIZONTAL;
                    gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                    p1.add(colorFieldLeft, gbc);
                    btn = ButtonFactory.createSelectionColorButton(editor,
                            FILL_COLOR_LEFT_MOUSE, ButtonFactory.HSV_COLORS, ButtonFactory.HSV_COLORS_COLUMN_COUNT,
                            "attribute.fillColor", labels, defaultAttributesLeft, new Rectangle(3, 3, 10, 10));
                    btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                    ((JPopupButton) btn).setAction(null, null);
                    gbc = new GridBagConstraints();
                    gbc.gridx = 1;
                    gbc.gridwidth = 2;
                    gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                    p1.add(btn, gbc);
                    
                    // Opacity field with slider
                    JAttributeTextField<Double> opacityField = new JAttributeTextField<Double>();
                    opacityField.setColumns(3);
                    opacityField.setToolTipText(labels.getString("attribute.fillOpacity.toolTipText"));
                    opacityField.putClientProperty("Palette.Component.segmentPosition", "first");
                    opacityField.setUI((PaletteFormattedTextFieldUI) PaletteFormattedTextFieldUI.createUI(opacityField));
                    opacityField.setFormatterFactory(JavaNumberFormatter.createFormatterFactory(0d, 1d, 100d));
                    opacityField.setHorizontalAlignment(JTextField.LEFT);
                    new FigureAttributeEditorHandler<Double>(FILL_OPACITY, opacityField, editor);
                    gbc = new GridBagConstraints();
                    gbc.gridx = 0;
                    gbc.insets = new Insets(3, 0, 0, 0);
                    gbc.fill = GridBagConstraints.HORIZONTAL;
                    gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                    p2.add(opacityField, gbc);
                    JPopupButton opacityPopupButton = new JPopupButton();
                    JAttributeSlider opacitySlider = new JAttributeSlider(JSlider.VERTICAL, 0, 100, 100);
                    opacityPopupButton.add(opacitySlider);
                    labels.configureToolBarButton(opacityPopupButton, "attribute.fillOpacity");
                    opacityPopupButton.setUI((PaletteButtonUI) PaletteButtonUI.createUI(opacityPopupButton));
                    opacityPopupButton.setPopupAnchor(SOUTH_EAST);
                    opacityPopupButton.setIcon(
                            new SelectionOpacityIcon(editor, FILL_OPACITY, FILL_COLOR, null, getClass().getResource(labels.getString("attribute.fillOpacity.icon")),
                            new Rectangle(5, 5, 6, 6), new Rectangle(4, 4, 7, 7)));
                    opacityPopupButton.setPopupAnchor(SOUTH_EAST);
                    new SelectionComponentRepainter(editor, opacityPopupButton);
                    gbc = new GridBagConstraints();
                    gbc.gridx = 1;
                    gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                    gbc.weighty = 1f;
                    gbc.insets = new Insets(3, 0, 0, 0);
                    p2.add(opacityPopupButton, gbc);
                    opacitySlider.setUI((SliderUI) PaletteSliderUI.createUI(opacitySlider));
                    opacitySlider.setScaleFactor(100d);
                    new FigureAttributeEditorHandler<Double>(FILL_OPACITY, opacitySlider, editor);

                    // Add horizontal strips
                    gbc = new GridBagConstraints();
                    gbc.gridy = 0;
                    gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                    p.add(p1, gbc);
                    gbc = new GridBagConstraints();
                    gbc.gridy = 1;
                    gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                    p.add(p2, gbc);
                    gbc = new GridBagConstraints();
                    gbc.gridy = 2;
                    gbc.weighty = 1f;
                    gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                    p.add(p3, gbc);
                }
                break;
        }
        return p;
    }

    @Override
    protected String getID() {
        return "fill";
    }
    @Override
    protected int getDefaultDisclosureState() {
        return 1;
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setOpaque(false);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
