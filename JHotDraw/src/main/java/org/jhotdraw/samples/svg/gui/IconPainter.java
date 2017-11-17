/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import org.jhotdraw.draw.DrawingView;

/**
 *
 * @author Bruger
 */
public class IconPainter {

    private DrawingOpacityIcon doi;
    private Component c;
    private Graphics2D gr;
    private int x;
    private int y;
    private Color fillColor;
    private Color strokeColor;
    private Double opacity;

    public IconPainter(DrawingOpacityIcon doi, Component c, Graphics gr, int x, int y, Double opacity, Color fillColor, Color strokeColor) {
        this.doi = doi;
        this.c = c;
        this.gr = (Graphics2D) gr;
        this.x = x;
        this.y = y;
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.opacity = opacity;
    }

    private void viewDrawer() {
        DrawingView view = doi.getEditor().getActiveView();
        if (view != null && view.getDrawing() != null) {
            this.opacity = doi.getOpacityKey().get(view.getDrawing());
            this.fillColor = (doi.getFillColorKey() == null) ? null : doi.getFillColorKey().get(view.getDrawing());
            this.strokeColor = (doi.getStrokeColorKey() == null) ? null : doi.getStrokeColorKey().get(view.getDrawing());
        } else {
            this.opacity = doi.getOpacityKey().get(doi.getEditor().getDefaultAttributes());
            this.fillColor = (doi.getFillColorKey() == null) ? null : doi.getFillColorKey().get(doi.getEditor().getDefaultAttributes());
            this.strokeColor = (doi.getStrokeColorKey() == null) ? null : doi.getStrokeColorKey().get(doi.getEditor().getDefaultAttributes());
        }
    }

    private void handleFillShape() {
        if (this.opacity != null) {
            if (this.fillColor == null) {
                this.fillColor = Color.BLACK;
            }
            this.gr.setColor(new Color((((int) (this.opacity * 255)) << 24) | (this.fillColor.getRGB() & 0xffffff), true));
            this.gr.translate(this.x, this.y);
            this.gr.fill(this.doi.getFillShape());
            this.gr.translate(-this.x, -this.y);
        }
    }

    private void handleStrokeShape() {
        if (this.opacity != null) {
            if (this.strokeColor == null) {
                this.strokeColor = Color.BLACK;
            }
            this.gr.setColor(new Color((((int) (this.opacity * 255)) << 24) | (this.strokeColor.getRGB() & 0xffffff), true));
            this.gr.translate(this.x, this.y);
            this.gr.draw(this.doi.getStrokeShape());
            this.gr.translate(-this.x, -this.y);
        }
    }

    public void process() {
        if (doi.editorNotNull()) {
            viewDrawer();
        }
        if (doi.fillColorKeyNotNull() && doi.fillShapeNotNull()) {
            handleFillShape();
        }
        if (doi.strokeColorKeyNotNull() && doi.strokeShapeNotNull()) {
            handleStrokeShape();
        }
    }
}
