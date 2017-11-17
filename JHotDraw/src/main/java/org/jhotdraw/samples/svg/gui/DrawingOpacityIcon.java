/*
 * @(#)DrawingOpacityIcon.java  1.0  2008-06-08
 *
 * Copyright (c) 2008 by the original authors of JHotDraw
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

import java.awt.*;
import java.net.*;
import org.jhotdraw.draw.*;

/**
 * {@code DrawingOpacityIcon} visualizes an opacity attribute of the
 * {@code Drawing} object which is in the active {@code DrawingView} of a
 * {@code DrawingEditor}.
 *
 * @author Werner Randelshofer
 * @version 1.0 2008-06-08 Created.
 */
public class DrawingOpacityIcon extends javax.swing.ImageIcon {

    private DrawingEditor editor;
    private AttributeKey<Double> opacityKey;
    private AttributeKey<Color> fillColorKey;
    private AttributeKey<Color> strokeColorKey;
    private Shape fillShape;
    private Shape strokeShape;

    /**
     * Creates a new instance.
     *
     * @param editor The drawing editor.
     * @param opacityKey The opacityKey of the default attribute
     * @param imageLocation the icon image
     * @param fillShape The shape to be drawn with the fillColor of the default
     * attribute.
     */
    public DrawingOpacityIcon(
            DrawingEditor editor,
            AttributeKey<Double> opacityKey,
            AttributeKey<Color> fillColorKey,
            AttributeKey<Color> strokeColorKey,
            URL imageLocation,
            Shape fillShape,
            Shape strokeShape) {
        super(imageLocation);
        this.editor = editor;
        this.opacityKey = opacityKey;
        this.fillColorKey = fillColorKey;
        this.strokeColorKey = strokeColorKey;
        this.fillShape = fillShape;
        this.strokeShape = strokeShape;
    }

    public DrawingOpacityIcon(
            DrawingEditor editor,
            AttributeKey<Double> opacityKey,
            AttributeKey<Color> fillColorKey,
            AttributeKey<Color> strokeColorKey,
            Image image,
            Shape fillShape,
            Shape strokeShape) {
        super(image);
        this.editor = editor;
        this.opacityKey = opacityKey;
        this.fillColorKey = fillColorKey;
        this.strokeColorKey = strokeColorKey;
        this.fillShape = fillShape;
        this.strokeShape = strokeShape;
    }

    public DrawingEditor getEditor() {
        return editor;
    }

    public AttributeKey<Double> getOpacityKey() {
        return opacityKey;
    }

    public AttributeKey<Color> getFillColorKey() {
        return fillColorKey;
    }

    public AttributeKey<Color> getStrokeColorKey() {
        return strokeColorKey;
    }

    public Shape getFillShape() {
        return fillShape;
    }

    public Shape getStrokeShape() {
        return strokeShape;
    }

    private boolean editorNotNull() {
        return this.editor != null;
    }

    private boolean fillColorKeyNotNull() {
        return this.fillColorKey != null;
    }

    private boolean fillShapeNotNull() {
        return this.fillShape != null;
    }

    private boolean strokeColorKeyNotNull() {
        return this.strokeColorKey != null;
    }

    private boolean strokeShapeNotNull() {
        return this.strokeShape != null;
    }

    private void viewDrawer(DrawingOpacityIcon doi, Double opacity, Color fillColor, Color strokeColoer) {
        DrawingView view = doi.getEditor().getActiveView();
        if (view != null && view.getDrawing() != null) {
            opacity = doi.getOpacityKey().get(view.getDrawing());
            fillColor = (doi.getFillColorKey() == null) ? null : doi.getFillColorKey().get(view.getDrawing());
            strokeColoer = (doi.getStrokeColorKey() == null) ? null : doi.getStrokeColorKey().get(view.getDrawing());
        } else {
            opacity = doi.getOpacityKey().get(doi.getEditor().getDefaultAttributes());
            fillColor = (doi.getFillColorKey() == null) ? null : doi.getFillColorKey().get(doi.getEditor().getDefaultAttributes());
            strokeColoer = (doi.getStrokeColorKey() == null) ? null : doi.getStrokeColorKey().get(doi.getEditor().getDefaultAttributes());
        }
    }

    private void handleFillShape(DrawingOpacityIcon doi, Color fillColor, Double opacity, Graphics2D g, int x, int y) {
        if (opacity != null) {
            if (fillColor == null) {
                fillColor = Color.BLACK;
            }
            g.setColor(new Color((((int) (opacity * 255)) << 24) | (fillColor.getRGB() & 0xffffff), true));
            g.translate(x, y);
            g.fill(doi.getFillShape());
            g.translate(-x, -y);
        }
    }

    private void handleStrokeShape(DrawingOpacityIcon doi, Color strokeColor, Double opacity, Graphics2D g, int x, int y) {
        if (opacity != null) {
            if (strokeColor == null) {
                strokeColor = Color.BLACK;
            }
            g.setColor(new Color((((int) (opacity * 255)) << 24) | (strokeColor.getRGB() & 0xffffff), true));
            g.translate(x, y);
            g.draw(doi.getStrokeShape());
            g.translate(-x, -y);
        }
    }

    @Override
    public void paintIcon(java.awt.Component c, java.awt.Graphics gr, int x, int y) {
        Graphics2D g = (Graphics2D) gr;
        Double opacity = 0d;
        Color fillColor = null;
        Color strokeColor = null;
        if (editorNotNull()) {
            viewDrawer(this, opacity, fillColor, strokeColor);
        }
        if (fillColorKeyNotNull() && fillShapeNotNull()) {
            handleFillShape(this, fillColor, opacity, g, x, y);
        }
        if (strokeColorKeyNotNull() && strokeShapeNotNull()) {
            handleStrokeShape(this, strokeColor, opacity, g, x, y);
        }
    }
}
