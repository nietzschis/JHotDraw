/*
 * @(#)FigureToolBar.java  2.0  2009-04-17
 *
 * Copyright (c) 2007-2009 by the original authors of JHotDraw
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
import javax.swing.*;
import static javax.swing.SwingConstants.SOUTH_EAST;
import javax.swing.plaf.LabelUI;
import javax.swing.plaf.SliderUI;
import javax.swing.plaf.TextUI;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;
import org.jhotdraw.app.JHotDrawFeatures;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.action.*;
import org.jhotdraw.gui.plaf.palette.*;
import static org.jhotdraw.samples.svg.SVGAttributeKeys.*;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;

/**
 * FigureToolBar.
 *
 * @author Werner Randelshofer
 * @version 2.0 2009-04-17 Moved hyperlink attributes out into LinkToolBar.
 * <br>1.2 2008-05-23 Hide the toolbar if nothing is selected, and no creation
 * tool is active.
 * <br>1.1 2008-03-26 Don't draw border.
 * <br>1.0 May 1, 2007 Created.
 */
public class FigureToolBar extends AbstractToolBar {

    private SelectionComponentDisplayer displayer;
    private ResourceBundleUtil labels;
    private GridBagConstraints gbc;
    private JPanel p = null;

    /**
     * Creates new instance.
     */
    public FigureToolBar() {
        labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        setName(labels.getString(getID() + ".toolbar"));
        setDisclosureStateCount(3);
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
    @FeatureEntryPoint(JHotDrawFeatures.FIGURE_PALETTE)
    protected JComponent createDisclosedComponent(int state) {
        p = null;
        switch (state) {
            case 1: {
                p = new JPanel();
                p.setOpaque(false);
                p.setLayout(new GridBagLayout());
                p.setBorder(new EmptyBorder(5, 5, 5, 8));

                // Opacity slider
                createOpacitySlider(state);

                //Shadow slider
                createShadowSlider(state);

                //contrast slider
                createContrastSlider();

                // Width and height fields
                createWidthField(state);
                createHeightField(state);

                break;
            }
            case 2: {
                p = new JPanel();
                p.setOpaque(false);
                p.setLayout(new GridBagLayout());
                p.setBorder(new EmptyBorder(5, 5, 5, 8));

                // Opacity field with slider
                createOpacityField();
                createOpacitySlider(state);

                //Shadow field with slider
                createShadowField();
                createShadowSlider(state);

                // Width and height fields
                createWidthField(state);
                createHeightField(state);
            }
            break;

        }
        return p;
    }

    private void createShadowSlider(int state) {
        JPopupButton shadowsPopupButton = new JPopupButton();
        JAttributeSlider shadowsSlider = new JAttributeSlider(JSlider.HORIZONTAL, 0, 25, 0);
        shadowsPopupButton.add(shadowsSlider);
        labels.configureToolBarButton(shadowsPopupButton, "attribute.addShadow");
        shadowsPopupButton.setUI((PaletteButtonUI) PaletteButtonUI.createUI(shadowsPopupButton));
        shadowsPopupButton.setIcon(
                new SelectionShadowsIcon(editor, SHADOWS, FILL_COLOR, STROKE_COLOR, getClass().getResource(labels.getString("attribute.addShadow.icon")),
                        new Rectangle(5, 5, 6, 6), new Rectangle(4, 4, 7, 7)));
        shadowsPopupButton.setPopupAnchor(SOUTH_EAST);
        new SelectionComponentRepainter(editor, shadowsPopupButton);
        gbc = new GridBagConstraints();
        if(state == 1){
                    gbc.gridx = 2;
                    gbc.gridy = 0;
                    gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                    gbc.insets = new Insets(0, 3, 0, 0);
        } 
        p.add(shadowsPopupButton, gbc);
        shadowsSlider.setUI((SliderUI) PaletteSliderUI.createUI(shadowsSlider));
        shadowsSlider.setScaleFactor(1d);
        new FigureAttributeEditorHandler<Double>(SHADOWS, shadowsSlider, editor);
    }

    private void createShadowField() {
        JAttributeTextField<Double> shadowsField = new JAttributeTextField<Double>();
        shadowsField.setColumns(3);
        shadowsField.setToolTipText(labels.getString("attribute.addShadow.toolTipText"));
        shadowsField.setHorizontalAlignment(JAttributeTextField.RIGHT);
        shadowsField.putClientProperty("Palette.Component.segmentPosition", "first");
        shadowsField.setUI((PaletteFormattedTextFieldUI) PaletteFormattedTextFieldUI.createUI(shadowsField));
        shadowsField.setFormatterFactory(JavaNumberFormatter.createFormatterFactory(0d, 1d, 100d));
        shadowsField.setHorizontalAlignment(JTextField.LEADING);
        new FigureAttributeEditorHandler<Double>(SHADOWS, shadowsField, editor);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.weightx = 1d;
        p.add(shadowsField, gbc);
    }

    private void createContrastSlider() {
        JPopupButton contrastPopupButton = new JPopupButton();
        contrastPopupButton.setName("abc");
        JAttributeSlider contrastSlider = new JAttributeSlider(JSlider.VERTICAL, 0, 100, 100);

        contrastPopupButton.add(contrastSlider);
        labels.configureToolBarButton(contrastPopupButton, "attribute.figureContrast");
        contrastPopupButton.setUI((PaletteButtonUI) PaletteButtonUI.createUI(contrastPopupButton));
        contrastPopupButton.setIcon(
                new SelectionOpacityIcon(editor, CONTRAST, FILL_COLOR, STROKE_COLOR, getClass().getResource(labels.getString("attribute.figureContrast.icon")),
                        new Rectangle(5, 5, 6, 6), new Rectangle(4, 4, 7, 7)));
        contrastPopupButton.setPopupAnchor(SOUTH_EAST);
    }

    private void createOpacitySlider(int state) {
        JPopupButton opacityPopupButton = new JPopupButton();
        JAttributeSlider opacitySlider = new JAttributeSlider(JSlider.VERTICAL, 0, 100, 100);
        opacityPopupButton.add(opacitySlider);
        labels.configureToolBarButton(opacityPopupButton, "attribute.figureOpacity");
        opacityPopupButton.setUI((PaletteButtonUI) PaletteButtonUI.createUI(opacityPopupButton));
        opacityPopupButton.setIcon(
                new SelectionOpacityIcon(editor, OPACITY, FILL_COLOR, STROKE_COLOR, getClass().getResource(labels.getString("attribute.figureOpacity.icon")),
                        new Rectangle(5, 5, 6, 6), new Rectangle(4, 4, 7, 7)));
        opacityPopupButton.setPopupAnchor(SOUTH_EAST);
        new SelectionComponentRepainter(editor, opacityPopupButton);

        gbc = new GridBagConstraints();
        if (state == 1) {
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        } 

        p.add(opacityPopupButton, gbc);
        opacitySlider.setUI((SliderUI) PaletteSliderUI.createUI(opacitySlider));
        opacitySlider.setScaleFactor(100d);
        new FigureAttributeEditorHandler<Double>(OPACITY, opacitySlider, editor);

    }

    private void createOpacityField() {
        JAttributeTextField<Double> opacityField = new JAttributeTextField<Double>();
        opacityField.setColumns(3);
        opacityField.setToolTipText(labels.getString("attribute.figureOpacity.toolTipText"));
        opacityField.setHorizontalAlignment(JAttributeTextField.RIGHT);
        opacityField.putClientProperty("Palette.Component.segmentPosition", "first");
        opacityField.setUI((PaletteFormattedTextFieldUI) PaletteFormattedTextFieldUI.createUI(opacityField));
        opacityField.setFormatterFactory(JavaNumberFormatter.createFormatterFactory(0d, 1d, 100d));
        opacityField.setHorizontalAlignment(JTextField.LEADING);
        new FigureAttributeEditorHandler<Double>(OPACITY, opacityField, editor);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.weightx = 1d;
        p.add(opacityField, gbc);
    }

    private void createWidthField(int state) {
        JLabel widthLabel;
        widthLabel = new javax.swing.JLabel();
        JAttributeTextField<Double> widthField;
        widthField = new JAttributeTextField<Double>();
        widthLabel.setUI((LabelUI) PaletteLabelUI.createUI(widthLabel));
        widthLabel.setLabelFor(widthField);
        widthLabel.setToolTipText(labels.getString("attribute.figureWidth.toolTipText"));
        if (state == 1) {
            widthLabel.setText(labels.getString("attribute.figureWidth.shorttext"));
        } else {
            widthLabel.setText(labels.getString("attribute.figureWidth.longtext"));
        }
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        p.add(widthLabel, gbc);

        widthField.setUI((TextUI) PaletteFormattedTextFieldUI.createUI(widthField));
        widthField.setColumns(3);
        widthField.setToolTipText(labels.getString("attribute.figureWidth.toolTipText"));
        widthField.setFormatterFactory(JavaNumberFormatter.createFormatterFactory(1d, 4096d, 1d, true, false));
        widthField.setHorizontalAlignment(JTextField.LEADING);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        p.add(widthField, gbc);
        new FigureAttributeEditorHandler<Double>(FIGURE_WIDTH, widthField, editor);
    }

    private void createHeightField(int state) {
        JLabel heightLabel;
        JAttributeTextField<Double> heightField;
        heightLabel = new javax.swing.JLabel();
        heightField = new JAttributeTextField<Double>();
        heightLabel.setUI((LabelUI) PaletteLabelUI.createUI(heightLabel));
        heightLabel.setLabelFor(heightField);
        heightLabel.setToolTipText(labels.getString("attribute.figureHeight.toolTipText"));
        if (state == 1) {
            heightLabel.setText(labels.getString("attribute.figureHeight.shorttext"));
        } else {
            heightLabel.setText(labels.getString("attribute.figureHeight.longtext"));
        }
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3, 0, 0, 0);
        p.add(heightLabel, gbc);

        heightField.setUI((TextUI) PaletteFormattedTextFieldUI.createUI(heightField));
        heightField.setColumns(3);
        heightField.setToolTipText(labels.getString("attribute.figureHeight.toolTipText"));
        heightField.setFormatterFactory(JavaNumberFormatter.createFormatterFactory(1d, 4096d, 1d, true, false));
        heightField.setHorizontalAlignment(JTextField.LEADING);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridwidth = 2;
        p.add(heightField, gbc);
        new FigureAttributeEditorHandler<Double>(FIGURE_HEIGHT, heightField, editor);
    }

    @Override
    protected String getID() {
        return "figure";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
