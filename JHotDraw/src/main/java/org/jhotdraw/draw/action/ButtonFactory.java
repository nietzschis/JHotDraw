/*
 * @(#)ButtonFactory.java  2.4  2009-04-17
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
package org.jhotdraw.draw.action;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import org.jhotdraw.gui.JPopupButton;
import org.jhotdraw.util.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.*;
import org.jhotdraw.app.ApplicationModel;
import org.jhotdraw.app.JHotDrawFeatures;
import org.jhotdraw.app.action.*;
import static org.jhotdraw.draw.AttributeKeys.*;
import org.jhotdraw.geom.*;
import org.jhotdraw.draw.*;
import org.jhotdraw.gui.JFontChooser;
import org.jhotdraw.gui.plaf.palette.PaletteButtonUI;
import java.util.List;

/**
 * ButtonFactory.
 * <p>
 * Design pattern:<br>
 * Name: Abstract Factory.<br>
 * Role: Abstract Factory.<br>
 * Partners: {@link org.jhotdraw.samples.draw.DrawApplicationModel} as Client,
 * {@link org.jhotdraw.samples.draw.DrawView} as Client,
 * {@link org.jhotdraw.samples.draw.DrawingPanel} as Client.
 *
 * FIXME - All buttons created using the ButtonFactory must automatically become
 * disabled/enabled, when the DrawingEditor is disabled/enabled.
 *
 * @author Werner Randelshofer
 * @version 2.4 2009-04-17 Added HSV_COLORS palette.
 * <br>2.3 2008-05-23 Factured SelectionComponentRepainter out.
 * <br>2.2 2008-05-18 Added method createDrawingColorButton.
 * <br>2.1 2008-03-25 Made method signature of createSelectionColorButton
 * compatible with createEditorColorButton.
 * <br>2.0.1 2007-12-17 Fixed createToggleGridButton method.
 * <br>2.0 2007-03-31 Renamed from ToolBarButtonFactory to ButtonFactory.
 * Replaced most add...ButtonTo methods by create...Button methods.
 * <br>1.3 2006-12-29 Split methods even more up. Added additional buttons.
 * <br>1.2 2006-07-16 Split some methods up for better reuse.
 * <br>1.1 2006-03-27 Font exclusion list updated.
 * <br>1.0 13. Februar 2006 Created.
 */
public class ButtonFactory {

    private static ResourceBundleUtil SVGlabels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
    private static ResourceBundleUtil DrawLabels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
    /**
     * Mac OS X 'Apple Color Palette'. This palette has 8 columns.
     */
    public final static List<ColorIcon> DEFAULT_COLORS;
    public final static List<ColorIcon> EYEDROPPED_COLORS;
    public static LinkedList<ColorIcon>  eyedropped;
  		  
     static {
         eyedropped = new LinkedList<ColorIcon>();
         EYEDROPPED_COLORS = Collections.unmodifiableList(eyedropped);       
     }
    
        

    static {
        LinkedList<ColorIcon> m = new LinkedList<ColorIcon>();
        m.add(new ColorIcon(0x800000, "Cayenne"));
        m.add(new ColorIcon(0x808000, "Asparagus"));
        m.add(new ColorIcon(0x008000, "Clover"));
        m.add(new ColorIcon(0x008080, "Teal"));
        m.add(new ColorIcon(0x000080, "Midnight"));
        m.add(new ColorIcon(0x800080, "Plum"));
        m.add(new ColorIcon(0x7f7f7f, "Tin"));
        m.add(new ColorIcon(0x808080, "Nickel"));
        m.add(new ColorIcon(0xff0000, "Maraschino"));
        m.add(new ColorIcon(0xffff00, "Lemon"));
        m.add(new ColorIcon(0x00ff00, "Spring"));
        m.add(new ColorIcon(0x00ffff, "Turquoise"));
        m.add(new ColorIcon(0x0000ff, "Blueberry"));
        m.add(new ColorIcon(0xff00ff, "Magenta"));
        m.add(new ColorIcon(0x666666, "Steel"));
        m.add(new ColorIcon(0x999999, "Aluminium"));
        m.add(new ColorIcon(0xff6666, "Salmon"));
        m.add(new ColorIcon(0xffff66, "Banana"));
        m.add(new ColorIcon(0x66ff66, "Flora"));
        m.add(new ColorIcon(0x66ffff, "Ice"));
        m.add(new ColorIcon(0x6666ff, "Orchid"));
        m.add(new ColorIcon(0xff66ff, "Bubblegum"));
        m.add(new ColorIcon(0x4c4c4c, "Iron"));
        m.add(new ColorIcon(0xb3b3b3, "Magnesium"));
        m.add(new ColorIcon(0x804000, "Mocha"));
        m.add(new ColorIcon(0x408000, "Fern"));
        m.add(new ColorIcon(0x008040, "Moss"));
        m.add(new ColorIcon(0x004080, "Ocean"));
        m.add(new ColorIcon(0x400080, "Eggplant"));
        m.add(new ColorIcon(0x800040, "Maroon"));
        m.add(new ColorIcon(0x333333, "Tungsten"));
        m.add(new ColorIcon(0xcccccc, "Silver"));
        m.add(new ColorIcon(0xff8000, "Tangerine"));
        m.add(new ColorIcon(0x80ff00, "Lime"));
        m.add(new ColorIcon(0x00ff80, "Sea Foam"));
        m.add(new ColorIcon(0x0080ff, "Aqua"));
        m.add(new ColorIcon(0x8000ff, "Grape"));
        m.add(new ColorIcon(0xff0080, "Strawberry"));
        m.add(new ColorIcon(0x191919, "Lead"));
        m.add(new ColorIcon(0xe6e6e6, "Mercury"));
        m.add(new ColorIcon(0xffcc66, "Cantaloupe"));
        m.add(new ColorIcon(0xccff66, "Honeydew"));
        m.add(new ColorIcon(0x66ffcc, "Spindrift"));
        m.add(new ColorIcon(0x66ccff, "Sky"));
        m.add(new ColorIcon(0xcc66ff, "Lavender"));
        m.add(new ColorIcon(0xff6fcf, "Carnation"));
        m.add(new ColorIcon(0x000000, "Licorice"));
        m.add(new ColorIcon(0xffffff, "Snow"));
        DEFAULT_COLORS = Collections.unmodifiableList(m);
    }
    public final static int DEFAULT_COLORS_COLUMN_COUNT = 8;
    /**
     * Websave color palette as used by Macromedia Fireworks. This palette has
     * 19 columns. The leftmost column contains a redundant set of color icons
     * to make selection of gray scales and of the primary colors easier.
     */
    public final static java.util.List<ColorIcon> WEBSAVE_COLORS;

    static {
        LinkedList<ColorIcon> m = new LinkedList<ColorIcon>();
        for (int b = 0; b <= 0xff; b += 0x33) {
            int rgb = (b << 16) | (b << 8) | b;
            m.add(new ColorIcon(rgb));
            for (int r = 0; r <= 0x66; r += 0x33) {
                for (int g = 0; g <= 0xff; g += 0x33) {
                    rgb = (r << 16) | (g << 8) | b;
                    m.add(new ColorIcon(rgb));
                }
            }
        }
        int[] firstColumn = {
            0xff0000,
            0x00ff00,
            0x0000ff,
            0xff00ff,
            0x00ffff,
            0xffff00,};
        for (int b = 0x0, i = 0; b <= 0xff; b += 0x33, i++) {
            int rgb = (b << 16) | (b << 8) | b;
            m.add(new ColorIcon(firstColumn[i]));
            for (int r = 0x99; r <= 0xff; r += 0x33) {
                for (int g = 0; g <= 0xff; g += 0x33) {
                    rgb = 0xff000000 | (r << 16) | (g << 8) | b;
                    m.add(new ColorIcon(rgb));
                }
            }
        }
        WEBSAVE_COLORS = Collections.unmodifiableList(m);
    }
    public final static int WEBSAVE_COLORS_COLUMN_COUNT = 19;
    /**
     * HSV color palette. This is a 'human friendly' color palette which
     * arranges the color in a way that makes it (hopefully) easy for humans to
     * select the desired color.
     * <p>
     * This palette has 12 columns. The topmost row contains a null-color and
     * gray scales.
     */
    public final static java.util.List<ColorIcon> HSV_COLORS;

    static {
        LinkedList<ColorIcon> m = new LinkedList<ColorIcon>();
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
        m.add(new ColorIcon(null, labels.getToolTipTextProperty("attribute.color.noColor")));
        for (int b = 10; b >= 0; b--) {
            m.add(new ColorIcon(Color.HSBtoRGB(0f, 0f, b * 0.1f)));
        }
        for (int b = 2; b <= 10; b += 2) {
            for (int h = 0; h < 12; h++) {
                m.add(new ColorIcon(Color.HSBtoRGB((h + 6) / 12f, 1f, b * 0.1f)));
            }
        }
        for (int s = 8; s > 0; s -= 2) {
            for (int h = 0; h < 12; h++) {
                m.add(new ColorIcon(Color.HSBtoRGB((h + 6) / 12f, s * 0.1f, 1f)));
            }
        }
        HSV_COLORS = Collections.unmodifiableList(m);
    }
    public final static int HSV_COLORS_COLUMN_COUNT = 12;

    private static class ToolButtonListener implements ItemListener {

        private Tool tool;
        private DrawingEditor editor;

        public ToolButtonListener(Tool t, DrawingEditor editor) {
            this.tool = t;
            this.editor = editor;
        }

        public void itemStateChanged(ItemEvent evt) {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                editor.setTool(tool);
            }
        }
    }

    /**
     * Prevent instance creation.
     */
    private ButtonFactory() {
    }

    public static Collection<Action> createDrawingActions(DrawingEditor editor) {
        LinkedList<Action> a = new LinkedList<>();
        a.add(new CutAction());
        a.add(new CopyAction());
        a.add(new PasteAction());
        a.add(new SelectSameAction(editor));

        return a;
    }

    public static Collection<Action> createSelectionActions(DrawingEditor editor) {
        LinkedList<Action> a = new LinkedList<>();
        a.add(new DuplicateAction());
        a.add(new SplitSegmentAction(editor));

        a.add(null); // separator

        a.add(new GroupAction(editor));
        a.add(new UngroupAction(editor));

        a.add(null); // separator

        a.add(new BringToFrontAction(editor));
        a.add(new SendToBackAction(editor));

        a.add(null); // separator

        a.add(new EdgeDetectionAction(editor));
        return a;
    }

    public static JToggleButton addSelectionToolTo(JToolBar tb, final DrawingEditor editor) {
        return addSelectionToolTo(tb, editor, createDrawingActions(editor), createSelectionActions(editor));
    }

    public static JToggleButton addSelectionToolTo(JToolBar tb, final DrawingEditor editor,
            Collection<Action> drawingActions, Collection<Action> selectionActions) {

        Tool selectionTool = new DelegationSelectionTool(
                drawingActions, selectionActions);

        return addSelectionToolTo(tb, editor, selectionTool);
    }

    /**
     * Adds a button to a toolbar and associates that button with a specific
     * tool.
     *
     * FIXME: Code smell "Long Method"
     *
     * @param toolbar
     * @param editor
     * @param selectionTool
     * @return
     */
    public static JToggleButton addSelectionToolTo(JToolBar toolbar, final DrawingEditor editor, Tool selectionTool) {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");

        JToggleButton button;
        Tool tool;
        HashMap<String, Object> attributes;

        ButtonGroup group;
        if (toolbar.getClientProperty("toolButtonGroup") instanceof ButtonGroup) {
            group = (ButtonGroup) toolbar.getClientProperty("toolButtonGroup");
        } else {
            group = new ButtonGroup();
            toolbar.putClientProperty("toolButtonGroup", group);
        }

        // Selection tool
        editor.setTool(selectionTool);

        button = new JToggleButton();
        button.setName("select");
        final JToggleButton defaultToolButton = button;

        ToolListener toolHandler;
        if (toolbar.getClientProperty("toolHandler") instanceof ToolListener) {
            toolHandler = (ToolListener) toolbar.getClientProperty("toolHandler");
        } else {
            toolHandler = new ToolListener() {

                public void toolStarted(ToolEvent event) {
                }

                public void toolDone(ToolEvent event) {
                    defaultToolButton.setSelected(true);
                }

                public void areaInvalidated(ToolEvent e) {
                }
            };
            toolbar.putClientProperty("toolHandler", toolHandler);
        }

        labels.configureToolBarButton(button, "selectionTool");
        button.setSelected(true);
        button.addItemListener(
                new ToolButtonListener(selectionTool, editor));
        button.setFocusable(false);
        group.add(button);
        toolbar.add(button);

        return button;
    }

    /**
     * Method addSelectionToolTo must have been invoked prior to this on the
     * JToolBar.
     *
     */
    public static JToggleButton addToolTo(JToolBar toolbar, DrawingEditor editor,
            Tool tool, String labelKey,
            ResourceBundleUtil labels) {

        ButtonGroup group;
        if (toolbar.getClientProperty("toolButtonGroup") instanceof ButtonGroup) {
            group = (ButtonGroup) toolbar.getClientProperty("toolButtonGroup");
        } else {
            group = new ButtonGroup();
            toolbar.putClientProperty("toolButtonGroup", group);
        }
        ToolListener toolHandler = (ToolListener) toolbar.getClientProperty("toolHandler");

        JToggleButton button = new JToggleButton();
        labels.configureToolBarButton(button, labelKey);
        button.addItemListener(new ToolButtonListener(tool, editor));
        button.setFocusable(false);
        tool.addToolListener(toolHandler);
        group.add(button);
        toolbar.add(button);

        return button;
    }

    public static void addZoomButtonsTo(JToolBar toolbar, final DrawingEditor editor) {
        toolbar.add(createZoomButton(editor));
    }

    public static AbstractButton createZoomButton(final DrawingEditor editor) {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");

        final JPopupButton zoomPopupButton = new JPopupButton();

        labels.configureToolBarButton(zoomPopupButton, "view.zoomFactor");
        zoomPopupButton.setFocusable(false);
        if (editor.getDrawingViews().size() == 0) {
            zoomPopupButton.setText("100 %");
        } else {
            zoomPopupButton.setText((int) (editor.getDrawingViews().iterator().next().getScaleFactor() * 100) + " %");
        }
        editor.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                // String constants are interned
                if (evt.getPropertyName() == DrawingEditor.ACTIVE_VIEW_PROPERTY) {
                    if (evt.getNewValue() == null) {
                        zoomPopupButton.setText("100 %");
                    } else {
                        zoomPopupButton.setText((int) (editor.getActiveView().getScaleFactor() * 100) + " %");
                    }
                }
            }
        });

        double[] factors = {16, 8, 5, 4, 3, 2, 1.5, 1.25, 1, 0.75, 0.5, 0.25, 0.10};
        for (int i = 0; i < factors.length; i++) {
            zoomPopupButton.add(
                    new ZoomEditorAction(editor, factors[i], zoomPopupButton) {

                @Override
                @FeatureEntryPoint(JHotDrawFeatures.VIEW_PALETTE)
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    super.actionPerformed(e);
                    zoomPopupButton.setText((int) (editor.getActiveView().getScaleFactor() * 100) + " %");
                }
            });
        }
        //zoomPopupButton.setPreferredSize(new Dimension(16,16));
        zoomPopupButton.setFocusable(false);
        return zoomPopupButton;
    }

    public static AbstractButton createZoomButton(DrawingView view) {
        return createZoomButton(view, new double[]{
            5, 4, 3, 2, 1.5, 1.25, 1, 0.75, 0.5, 0.25, 0.10
        });
    }

    public static AbstractButton createZoomButton(final DrawingView view, double[] factors) {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");

        final JPopupButton zoomPopupButton = new JPopupButton();

        labels.configureToolBarButton(zoomPopupButton, "view.zoomFactor");
        zoomPopupButton.setFocusable(false);
        zoomPopupButton.setText((int) (view.getScaleFactor() * 100) + " %");

        view.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                // String constants are interned
                if (evt.getPropertyName() == "scaleFactor") {
                    zoomPopupButton.setText((int) (view.getScaleFactor() * 100) + " %");
                }
            }
        });

        for (int i = 0; i < factors.length; i++) {
            zoomPopupButton.add(
                    new ZoomAction(view, factors[i], zoomPopupButton) {

                @Override
                @FeatureEntryPoint(JHotDrawFeatures.VIEW_PALETTE)
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    super.actionPerformed(e);
                    zoomPopupButton.setText((int) (view.getScaleFactor() * 100) + " %");
                }
            });
        }
        //zoomPopupButton.setPreferredSize(new Dimension(16,16));
        zoomPopupButton.setFocusable(false);
        return zoomPopupButton;
    }
    
    public static AbstractButton createWorkspaceBGButton(DrawingView view) {
        return createWorkspaceBGButton(view, new String[]{
                "Black", "White", "Gray"
        });
    }

    public static AbstractButton createWorkspaceBGButton(final DrawingView view, String[] colors) {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");

        final JPopupButton BGPopupButton = new JPopupButton();

        labels.configureToolBarButton(BGPopupButton, "view.outerBGColor");
        BGPopupButton.setFocusable(false);
        BGPopupButton.setText(view.getWorkspaceBG().toString());

        view.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                // String constants are interned
                if (evt.getPropertyName() == "workspaceBG") {
                    BGPopupButton.setText(view.getWorkspaceBG().toString());
                }
            }
        });
        for (int i = 0; i < colors.length; i++) {
            BGPopupButton.add(new WorkspaceBGAction(view, colors[i], BGPopupButton));
        }
        BGPopupButton.setFocusable(false);
        return BGPopupButton;
    }
    
    /**
     * Creates toolbar buttons and adds them to the specified JToolBar
     */
    public static void addAttributesButtonsTo(JToolBar bar, DrawingEditor editor) {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
        JButton b;

        b = bar.add(new PickAttributesAction(editor));
        b.setFocusable(false);
        b = bar.add(new ApplyAttributesAction(editor));
        b.setFocusable(false);
        bar.addSeparator();

        addColorButtonsTo(bar, editor);
        bar.addSeparator();
        addStrokeButtonsTo(bar, editor);
        bar.addSeparator();
        addFontButtonsTo(bar, editor);
    }

    public static void addColorButtonsTo(JToolBar bar, DrawingEditor editor) {
        addColorButtonsTo(bar, editor, DEFAULT_COLORS, DEFAULT_COLORS_COLUMN_COUNT);
    }

    public static void addColorButtonsTo(JToolBar toolbar, DrawingEditor editor,
            java.util.List<ColorIcon> colors, int columnCount) {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");

        toolbar.add(createEditorColorButton(editor, STROKE_COLOR, colors, columnCount, "attribute.strokeColor", labels, new HashMap<AttributeKey, Object>()));
        toolbar.add(createEditorColorButton(editor, FILL_COLOR, colors, columnCount, "attribute.fillColor", labels, new HashMap<AttributeKey, Object>()));
        toolbar.add(createEditorColorButton(editor, TEXT_COLOR, colors, columnCount, "attribute.textColor", labels, new HashMap<AttributeKey, Object>()));
    }

    /**
     * Creates a color button, with an action region and a popup menu. The
     * button works like the color button in Microsoft Office:
     * <ul>
     * <li>When the user clicks on the action region, the default color of the
     * DrawingEditor is applied to the selected figures.</li>
     * <li>When the user opens the popup menu, a color palette is displayed.
     * Choosing a color from the palette changes the default color of the editor
     * and also changes the color of the selected figures.</li>
     * <li>A rectangle on the color button displays the current default color of
     * the DrawingEditor. The rectangle has the dimensions 1, 17, 20, 4 (x, y,
     * width, height).</li>
     * </ul>
     *
     * @param editor The DrawingEditor.
     * @param attributeKey The AttributeKey of the color.
     * @param swatches A list with labeled colors containing the color palette
     * of the popup menu. The actual labels are retrieved from the supplied
     * resource bundle. This is usually a LinkedMap, so that the colors have a
     * predictable order.
     * @param columnCount The number of columns of the color palette.
     * @param labelKey The resource bundle key used for retrieving the icon and
     * the tooltip of the button.
     * @param labels The resource bundle.
     */
    public static JPopupButton createEditorColorButton(
            DrawingEditor editor, AttributeKey<Color> attributeKey,
            java.util.List<ColorIcon> swatches, int columnCount,
            String labelKey, ResourceBundleUtil labels) {
        return createEditorColorButton(
                editor, attributeKey,
                swatches, columnCount,
                labelKey, labels,
                null);
    }

    /**
     * Creates a color button, with an action region and a popup menu. The
     * button works like the color button in Microsoft Office:
     * <ul>
     * <li>When the user clicks on the action region, the default color of the
     * DrawingEditor is applied to the selected figures.</li>
     * <li>When the user opens the popup menu, a color palette is displayed.
     * Choosing a color from the palette changes the default color of the editor
     * and also changes the color of the selected figures.</li>
     * <li>A rectangle on the color button displays the current default color of
     * the DrawingEditor. The rectangle has the dimensions 1, 17, 20, 4 (x, y,
     * width, height).</li>
     * </ul>
     *
     * @param editor The DrawingEditor.
     * @param attributeKey The AttributeKey of the color.
     * @param swatches A list with labeled colors containing the color palette
     * of the popup menu. The actual labels are retrieved from the supplied
     * resource bundle. This is usually a LinkedMap, so that the colors have a
     * predictable order.
     * @param columnCount The number of columns of the color palette.
     * @param labelKey The resource bundle key used for retrieving the icon and
     * the tooltip of the button.
     * @param labels The resource bundle.
     * @param defaultAttributes A set of attributes which are also applied to
     * the selected figures, when a color is selected. This can be used, to set
     * attributes that otherwise prevent the color from being shown. For
     * example, when the color attribute is set, we wan't the gradient attribute
     * of the Figure to be cleared.
     */
    public static JPopupButton createEditorColorButton(
            DrawingEditor editor, AttributeKey<Color> attributeKey,
            java.util.List<ColorIcon> swatches, int columnCount,
            String labelKey, ResourceBundleUtil labels,
            Map<AttributeKey, Object> defaultAttributes) {
        return createEditorColorButton(editor, attributeKey,
                swatches, columnCount, labelKey, labels, defaultAttributes,
                new Rectangle(1, 17, 20, 4));
    }

    /**
     * Creates a color button, with an action region and a popup menu. The
     * button works like the color button in Microsoft Office:
     * <ul>
     * <li>When the user clicks on the action region, the default color of the
     * DrawingEditor is applied to the selected figures.</li>
     * <li>When the user opens the popup menu, a color palette is displayed.
     * Choosing a color from the palette changes the default color of the editor
     * and also changes the color of the selected figures.</li>
     * <li>A shape on the color button displays the current default color of the
     * DrawingEditor.</li>
     * </ul>
     *
     * @param editor The DrawingEditor.
     * @param attributeKey The AttributeKey of the color.
     * @param swatches A list with labeled colors containing the color palette
     * of the popup menu. The actual labels are retrieved from the supplied
     * resource bundle. This is usually a LinkedHashMap, so that the colors have
     * a predictable order.
     * @param columnCount The number of columns of the color palette.
     * @param labelKey The resource bundle key used for retrieving the icon and
     * the tooltip of the button.
     * @param labels The resource bundle.
     * @param defaultAttributes A set of attributes which are also applied to
     * the selected figures, when a color is selected. This can be used, to set
     * attributes that otherwise prevent the color from being shown. For
     * example, when the color attribute is set, we wan't the gradient attribute
     * of the Figure to be cleared.
     * @param colorShape This shape is superimposed on the icon of the button.
     * The shape is drawn with the default color of the DrawingEditor.
     */
    public static JPopupButton createEditorColorButton(
            DrawingEditor editor, AttributeKey<Color> attributeKey,
            java.util.List<ColorIcon> swatches, int columnCount,
            String labelKey, ResourceBundleUtil labels,
            Map<AttributeKey, Object> defaultAttributes,
            Shape colorShape) {
        final JPopupButton popupButton = new JPopupButton();
        popupButton.setPopupAlpha(1f);
        if (defaultAttributes == null) {
            defaultAttributes = new HashMap<AttributeKey, Object>();
        }

        popupButton.setAction(
                new DefaultAttributeAction(editor, attributeKey, defaultAttributes),
                new Rectangle(0, 0, 22, 22));
        popupButton.setColumnCount(columnCount, false);
        boolean hasNullColor = false;
        for (ColorIcon swatch : swatches) {
            AttributeAction a;
            HashMap<AttributeKey, Object> attributes = new HashMap<AttributeKey, Object>(defaultAttributes);
            attributes.put(attributeKey, swatch.getColor());
            if (swatch.getColor() == null) {
                hasNullColor = true;
            }
            popupButton.add(a
                    = new AttributeAction(
                            editor,
                            attributes,
                            labels.getToolTipTextProperty(labelKey),
                            swatch));
            a.putValue(Action.SHORT_DESCRIPTION, swatch.getName());
        }

        // No color
        if (!hasNullColor) {
            AttributeAction a;
            HashMap<AttributeKey, Object> attributes = new HashMap<AttributeKey, Object>(defaultAttributes);
            attributes.put(attributeKey, null);
            popupButton.add(a
                    = new AttributeAction(
                            editor,
                            attributes,
                            labels.getToolTipTextProperty("attribute.color.noColor"),
                            new ColorIcon(null, labels.getToolTipTextProperty("attribute.color.noColor"), swatches.get(0).getIconWidth(), swatches.get(0).getIconHeight())));
            a.putValue(Action.SHORT_DESCRIPTION, labels.getToolTipTextProperty("attribute.color.noColor"));
        }

        // Color chooser
        ImageIcon chooserIcon = new ImageIcon(
                ButtonFactory.class.getResource("/org/jhotdraw/draw/action/images/attribute.color.colorChooser.png"));
        Action a;
        popupButton.add(
                a = new EditorColorChooserAction(
                        editor,
                        attributeKey,
                        "color",
                        chooserIcon,
                        defaultAttributes));
        labels.configureToolBarButton(popupButton, labelKey);
        a.putValue(Action.SHORT_DESCRIPTION, labels.getToolTipTextProperty("attribute.color.colorChooser"));
        Icon icon = new EditorColorIcon(editor,
                attributeKey,
                labels.getIconProperty(labelKey, ButtonFactory.class).getImage(),
                colorShape);
        popupButton.setIcon(icon);
        popupButton.setDisabledIcon(icon);
        popupButton.setFocusable(false);

        editor.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                popupButton.repaint();
            }
        });

        return popupButton;
    }

    /**
     * Creates a color button, with an action region and a popup menu. The
     * button works like the color button in Adobe Fireworks:
     * <ul>
     * <li>When the user clicks at the button a popup menu with a color palette
     * is displayed. Choosing a color from the palette changes the default color
     * of the editor and also changes the color of the selected figures.</li>
     * <li>A shape on the color button displays the color of the selected
     * figures. If no figures are selected, the default color of the
     * DrawingEditor is displayed.</li>
     * <li>A rectangle on the color button displays the current default color of
     * the DrawingEditor. The rectangle has the dimensions 1, 17, 20, 4 (x, y,
     * width, height).</li>
     * </ul>
     *
     * @param editor The DrawingEditor.
     * @param attributeKey The AttributeKey of the color.
     * @param swatches A list with labeled colors containing the color palette
     * of the popup menu. The actual labels are retrieved from the supplied
     * resource bundle. This is usually a LinkedHashMap, so that the colors have
     * a predictable order.
     * @param columnCount The number of columns of the color palette.
     * @param labelKey The resource bundle key used for retrieving the icon and
     * the tooltip of the button.
     * @param labels The resource bundle.
     */
    public static JPopupButton createSelectionColorButton(
            DrawingEditor editor, AttributeKey<Color> attributeKey,
            java.util.List<ColorIcon> swatches, int columnCount,
            String labelKey, ResourceBundleUtil labels) {
        return createSelectionColorButton(
                editor, attributeKey,
                swatches, columnCount,
                labelKey, labels,
                null);
    }

    /**
     * Creates a color button, with an action region and a popup menu. The
     * button works like the color button in Adobe Fireworks:
     * <ul>
     * <li>When the user clicks at the button a popup menu with a color palette
     * is displayed. Choosing a color from the palette changes the default color
     * of the editor and also changes the color of the selected figures.</li>
     * <li>A rectangle on the color button displays the current default color of
     * the DrawingEditor. The rectangle has the dimensions 1, 17, 20, 4 (x, y,
     * width, height).</li>
     * </ul>
     *
     * @param editor The DrawingEditor.
     * @param attributeKey The AttributeKey of the color.
     * @param swatches A list with labeled colors containing the color palette
     * of the popup menu. The actual labels are retrieved from the supplied
     * resource bundle. This is usually a LinkedHashMap, so that the colors have
     * a predictable order.
     * @param columnCount The number of columns of the color palette.
     * @param labelKey The resource bundle key used for retrieving the icon and
     * the tooltip of the button.
     * @param labels The resource bundle.
     * @param defaultAttributes A set of attributes which are also applied to
     * the selected figures, when a color is selected. This can be used, to set
     * attributes that otherwise prevent the color from being shown. For
     * example, when the color attribute is set, we wan't the gradient attribute
     * of the Figure to be cleared.
     */
    public static JPopupButton createSelectionColorButton(
            DrawingEditor editor, AttributeKey<Color> attributeKey,
            java.util.List<ColorIcon> swatches, int columnCount,
            String labelKey, ResourceBundleUtil labels,
            Map<AttributeKey, Object> defaultAttributes) {
        return createSelectionColorButton(editor, attributeKey,
                swatches, columnCount, labelKey, labels, defaultAttributes,
                new Rectangle(1, 17, 20, 4));
    }

    public static JButton createEyedropperButton(DrawingEditor editor, AbstractButton popup) {
         JButton btn;
         btn = new JButton(new EyedropperAction(editor, popup));
         if (btn.getIcon() != null) {
             btn.putClientProperty("hideActionText", Boolean.FALSE);
         }
         btn.setHorizontalTextPosition(JButton.CENTER);
         btn.setVerticalTextPosition(JButton.BOTTOM);
         btn.setText(null);
         btn.setFocusable(false);
         btn.setEnabled(true);
         
         return btn;
     }

    public static JPopupButton createCustomizedColorButton(
             DrawingEditor editor, AttributeKey<Color> attributeKey,
             java.util.List<ColorIcon> swatches, int columnCount,
             String labelKey, ResourceBundleUtil labels,
             Map<AttributeKey, Object> defaultAttributes,
             Shape colorShape) {
         final JPopupButton popupButton = new JPopupButton();
         popupButton.setPopupAlpha(1f);
         if (defaultAttributes == null) {
             defaultAttributes = new HashMap<AttributeKey, Object>();
         }
 
         popupButton.setColumnCount(columnCount, false);
         boolean hasNullColor = false;
         for (ColorIcon swatch : swatches) {
             AttributeAction a;
             HashMap<AttributeKey, Object> attributes = new HashMap<AttributeKey, Object>(defaultAttributes);
             attributes.put(attributeKey, swatch.getColor());
             if (swatch.getColor() == null) {
                 hasNullColor = true;
             }
             popupButton.add(a =
                     new AttributeAction(
                     editor,
                     attributes,
                     labels.getToolTipTextProperty(labelKey),
                     swatch));
             a.putValue(Action.SHORT_DESCRIPTION, swatch.getName());
         }
         
         labels.configureToolBarButton(popupButton, labelKey);
         Icon icon = new SelectionColorIcon(editor,
                 attributeKey,
                 labels.getIconProperty(labelKey, ButtonFactory.class).getImage(),
                 colorShape);
         popupButton.setIcon(icon);
         popupButton.setDisabledIcon(icon);
         popupButton.setFocusable(false);
 
         new SelectionComponentRepainter(editor, popupButton);
         return popupButton;
     }

    
    /**
     * Creates a color button, with an action region and a popup menu. The
     * button works like the color button in Adobe Fireworks:
     * <ul>
     * <li>When the user clicks at the button a popup menu with a color palette
     * is displayed. Choosing a color from the palette changes the default color
     * of the editor and also changes the color of the selected figures.</li>
     * <li>A shape on the color button displays the color of the selected
     * figures. If no figures are selected, the default color of the
     * DrawingEditor is displayed.</li>
     * </ul>
     *
     * @param editor The DrawingEditor.
     * @param attributeKey The AttributeKey of the color.
     * @param swatches A list with labeled colors containing the color palette
     * of the popup menu. The actual labels are retrieved from the supplied
     * resource bundle. This is usually a LinkedHashMap, so that the colors have
     * a predictable order.
     * @param columnCount The number of columns of the color palette.
     * @param labelKey The resource bundle key used for retrieving the icon and
     * the tooltip of the button.
     * @param labels The resource bundle.
     * @param defaultAttributes A set of attributes which are also applied to
     * the selected figures, when a color is selected. This can be used, to set
     * attributes that otherwise prevent the color from being shown. For
     * example, when the color attribute is set, we wan't the gradient attribute
     * of the Figure to be cleared.
     * @param colorShape This shape is superimposed on the icon of the button.
     * The shape is drawn with the default color of the DrawingEditor.
     */
    
    private static LinkedList<ColorIcon> myColorList;
    
    public static JPopupButton createSelectionColorButton(
            DrawingEditor editor, AttributeKey<Color> attributeKey,
            java.util.List<ColorIcon> swatches, int columnCount,
            String labelKey, ResourceBundleUtil labels,
            Map<AttributeKey, Object> defaultAttributes,
            Shape colorShape) {
        final JPopupButton popupButton = new JPopupButton();
        popupButton.setPopupAlpha(1f);
        if (defaultAttributes == null) {
            defaultAttributes = new HashMap<AttributeKey, Object>();
        }

        popupButton.setColumnCount(columnCount, false);
        boolean hasNullColor = false;
        for (ColorIcon swatch : swatches) {
            AttributeAction a;
            HashMap<AttributeKey, Object> attributes = new HashMap<AttributeKey, Object>(defaultAttributes);
            attributes.put(attributeKey, swatch.getColor());
            if (swatch.getColor() == null) {
                hasNullColor = true;
            }
            popupButton.add(a
                    = new AttributeAction(
                            editor,
                            attributes,
                            labels.getToolTipTextProperty(labelKey),
                            swatch));
            a.putValue(Action.SHORT_DESCRIPTION, swatch.getName());
        }

        // No color
        if (!hasNullColor) {
            AttributeAction a;
            HashMap<AttributeKey, Object> attributes = new HashMap<AttributeKey, Object>(defaultAttributes);
            attributes.put(attributeKey, null);
            popupButton.add(a
                    = new AttributeAction(
                            editor,
                            attributes,
                            labels.getToolTipTextProperty("attribute.color.noColor"),
                            new ColorIcon(null, labels.getToolTipTextProperty("attribute.color.noColor"))));
            a.putValue(Action.SHORT_DESCRIPTION, labels.getToolTipTextProperty("attribute.color.noColor"));
        }
        // Color chooser
        ImageIcon chooserIcon = new ImageIcon(
                ButtonFactory.class.getResource("/org/jhotdraw/draw/action/images/attribute.color.colorChooser.png"));
        Action a;
        popupButton.add(
                a = new SelectionColorChooserAction(
                        editor,
                        attributeKey,
                        labels.getToolTipTextProperty("attribute.color.colorChooser"),
                        chooserIcon,
                        defaultAttributes));
        a.putValue(Action.SHORT_DESCRIPTION, labels.getToolTipTextProperty("attribute.color.colorChooser"));
        labels.configureToolBarButton(popupButton, labelKey);
        Icon icon = new SelectionColorIcon(editor,
                attributeKey,
                labels.getIconProperty(labelKey, ButtonFactory.class).getImage(),
                colorShape);
        
        //My Colors Begins 
        for (int i = 0; i < 7; i++) {
            popupButton.addSeparator();
        }
        myColorList = new LinkedList<>();
        popupButton.add(myColorsAddButton(myColorList,attributeKey,editor,popupButton));
        popupButton.add(myColorsLoadButton(attributeKey,editor,popupButton));
        popupButton.add(myColorsSaveButton(myColorList));
        popupButton.add(myColorsClearButton(myColorList));
        //My Colors ends
        
        
        popupButton.setIcon(icon);
        popupButton.setDisabledIcon(icon);
        popupButton.setFocusable(false);

        new SelectionComponentRepainter(editor, popupButton);
        return popupButton;
    }

    
    private static JPopupButton myColorsAddButton(LinkedList<ColorIcon> list,AttributeKey<Color> attributeKey,DrawingEditor editor, JPopupButton parent){
     ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
     JPopupButton addColors = new JPopupButton();
     
                addColors.setUI((PaletteButtonUI) PaletteButtonUI.createUI(addColors));
                addColors.setItemFont(UIManager.getFont("MenuItem.font"));                
                labels.configureToolBarButton(addColors, "add.myColors");
                addColors.addActionListener((ActionEvent e) -> { 
                    Color color = editor.getDefaultAttribute(attributeKey);            
                    MyColorsAddAction mc = new MyColorsAddAction();
                    mc.add(attributeKey, editor,color, parent);
                    list.add(new ColorIcon(color, color.toString()));
                });
                return addColors;              
    }
    
     private static JPopupButton myColorsLoadButton(AttributeKey<Color> attributeKey,DrawingEditor editor, JPopupButton parent){
     ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
     JPopupButton load = new JPopupButton();
                load.setUI((PaletteButtonUI) PaletteButtonUI.createUI(load));
                load.setItemFont(UIManager.getFont("MenuItem.font"));
                labels.configureToolBarButton(load, "load.myColors");                
                load.addActionListener((ActionEvent e) -> {            
                MyColorsLoadAction myLoader = new MyColorsLoadAction();
                myLoader.load(attributeKey,editor, parent,null);
                
                });
      return load;          
     }
     
     private static JPopupButton myColorsSaveButton(LinkedList<ColorIcon> list){
     ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
     JPopupButton save = new JPopupButton();
                save.setUI((PaletteButtonUI) PaletteButtonUI.createUI(save));
                save.setItemFont(UIManager.getFont("MenuItem.font"));
                labels.configureToolBarButton(save, "save.myColors");
                save.addActionListener((ActionEvent e) -> { 
                    MyColorsSaveAction mySave = new MyColorsSaveAction();
                    mySave.save(list,null);
                });
                return save;
     }
     
     private static JPopupButton myColorsClearButton(LinkedList<ColorIcon> list){
     ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
     JPopupButton clear = new JPopupButton();
                clear.setUI((PaletteButtonUI) PaletteButtonUI.createUI(clear));
                clear.setItemFont(UIManager.getFont("MenuItem.font"));
                labels.configureToolBarButton(clear, "clear.myColors");
                clear.addActionListener((ActionEvent e) -> { 
                    list.clear();
                    //Clears only list to be saved not UI
                });
                return clear;
     }


    /**
     * Creates a color button, with an action region and a popup menu. The
     * button acts on attributes of the Drawing object in the current
     * DrawingView of the DrawingEditor.
     *
     * @param editor The DrawingEditor.
     * @param attributeKey The AttributeKey of the color.
     * @param swatches A list with labeled colors containing the color palette
     * of the popup menu. The actual labels are retrieved from the supplied
     * resource bundle. This is usually a LinkedHashMap, so that the colors have
     * a predictable order.
     * @param columnCount The number of columns of the color palette.
     * @param labelKey The resource bundle key used for retrieving the icon and
     * the tooltip of the button.
     * @param labels The resource bundle.
     */
    public static JPopupButton createDrawingColorButton(
            DrawingEditor editor, AttributeKey<Color> attributeKey,
            java.util.List<ColorIcon> swatches, int columnCount,
            String labelKey, ResourceBundleUtil labels) {
        return createDrawingColorButton(
                editor, attributeKey,
                swatches, columnCount,
                labelKey, labels,
                null);
    }

    /**
     * Creates a color button, with an action region and a popup menu. The
     * button acts on attributes of the Drawing object in the current
     * DrawingView of the DrawingEditor.
     *
     * @param editor The DrawingEditor.
     * @param attributeKey The AttributeKey of the color.
     * @param swatches A list with labeled colors containing the color palette
     * of the popup menu. The actual labels are retrieved from the supplied
     * resource bundle. This is usually a LinkedHashMap, so that the colors have
     * a predictable order.
     * @param columnCount The number of columns of the color palette.
     * @param labelKey The resource bundle key used for retrieving the icon and
     * the tooltip of the button.
     * @param labels The resource bundle.
     * @param defaultAttributes A set of attributes which are also applied to
     * the selected figures, when a color is selected. This can be used, to set
     * attributes that otherwise prevent the color from being shown. For
     * example, when the color attribute is set, we wan't the gradient attribute
     * of the Figure to be cleared.
     */
    public static JPopupButton createDrawingColorButton(
            DrawingEditor editor, AttributeKey<Color> attributeKey,
            java.util.List<ColorIcon> swatches, int columnCount,
            String labelKey, ResourceBundleUtil labels,
            Map<AttributeKey, Object> defaultAttributes) {
        return createDrawingColorButton(editor, attributeKey,
                swatches, columnCount, labelKey, labels, defaultAttributes,
                new Rectangle(1, 17, 20, 4));
    }

    /**
     * Creates a color button, with an action region and a popup menu. The
     * button acts on attributes of the Drawing object in the current
     * DrawingView of the DrawingEditor.
     *
     * @param editor The DrawingEditor.
     * @param attributeKey The AttributeKey of the color.
     * @param swatches A list with labeled colors containing the color palette
     * of the popup menu. The actual labels are retrieved from the supplied
     * resource bundle. This is usually a LinkedHashMap, so that the colors have
     * a predictable order.
     * @param columnCount The number of columns of the color palette.
     * @param labelKey The resource bundle key used for retrieving the icon and
     * the tooltip of the button.
     * @param labels The resource bundle.
     * @param defaultAttributes A set of attributes which are also applied to
     * the selected figures, when a color is selected. This can be used, to set
     * attributes that otherwise prevent the color from being shown. For
     * example, when the color attribute is set, we wan't the gradient attribute
     * of the Figure to be cleared.
     * @param colorShape This shape is superimposed on the icon of the button.
     * The shape is drawn with the default color of the DrawingEditor.
     */
    public static JPopupButton createDrawingColorButton(
            DrawingEditor editor, AttributeKey<Color> attributeKey,
            java.util.List<ColorIcon> swatches, int columnCount,
            String labelKey, ResourceBundleUtil labels,
            Map<AttributeKey, Object> defaultAttributes,
            Shape colorShape) {
        final JPopupButton popupButton = new JPopupButton();
        popupButton.setPopupAlpha(1f);
        if (defaultAttributes == null) {
            defaultAttributes = new HashMap<AttributeKey, Object>();
        }

        popupButton.setColumnCount(columnCount, false);
        boolean hasNullColor = false;
        for (ColorIcon swatch : swatches) {
            DrawingAttributeAction a;
            HashMap<AttributeKey, Object> attributes = new HashMap<AttributeKey, Object>(defaultAttributes);
            attributes.put(attributeKey, swatch.getColor());
            if (swatch.getColor() == null) {
                hasNullColor = true;
            }
            popupButton.add(a
                    = new DrawingAttributeAction(
                            editor,
                            attributes,
                            labels.getToolTipTextProperty(labelKey),
                            swatch));
            a.putValue(Action.SHORT_DESCRIPTION, swatch.getName());
        }

        // No color
        if (!hasNullColor) {
            DrawingAttributeAction a;
            HashMap<AttributeKey, Object> attributes = new HashMap<AttributeKey, Object>(defaultAttributes);
            attributes.put(attributeKey, null);
            popupButton.add(a
                    = new DrawingAttributeAction(
                            editor,
                            attributes,
                            labels.getToolTipTextProperty("attribute.color.noColor"),
                            new ColorIcon(null, labels.getToolTipTextProperty("attribute.color.noColor"))));
            a.putValue(Action.SHORT_DESCRIPTION, labels.getToolTipTextProperty("attribute.color.noColor"));
        }

        // Color chooser
        ImageIcon chooserIcon = new ImageIcon(
                ButtonFactory.class.getResource("/org/jhotdraw/draw/action/images/attribute.color.colorChooser.png"));
        Action a;
        popupButton.add(
                a = new DrawingColorChooserAction(
                        editor,
                        attributeKey,
                        "color",
                        chooserIcon,
                        defaultAttributes));
        labels.configureToolBarButton(popupButton, labelKey);
        a.putValue(Action.SHORT_DESCRIPTION, labels.getToolTipTextProperty("attribute.color.colorChooser"));
        Icon icon = new DrawingColorIcon(editor,
                attributeKey,
                labels.getIconProperty(labelKey, ButtonFactory.class).getImage(),
                colorShape);
        popupButton.setIcon(icon);
        popupButton.setDisabledIcon(icon);
        popupButton.setFocusable(false);

        if (editor != null) {
            editor.addPropertyChangeListener(new SelectionComponentRepainter(editor, popupButton));
        }

        return popupButton;
    }
    
    public static void addStrokeButtonsTo(JToolBar bar, DrawingEditor editor) {
        bar.add(createStrokeDecorationButton(editor));
        bar.add(createStrokeWidthButton(editor));
        bar.add(createStrokeDashesButton(editor));
        bar.add(createStrokeTypeButton(editor));
        bar.add(createStrokePlacementButton(editor));
        bar.add(createStrokeCapButton(editor));
        bar.add(createStrokeJoinButton(editor));
    }

    public static JPopupButton createStrokeWidthButton(DrawingEditor editor) {
        return createStrokeWidthButton(
                editor,
                new double[]{0.5d, 1d, 2d, 3d, 5d, 9d, 13d},
                ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels"));
    }

    public static JPopupButton createStrokeWidthButton(DrawingEditor editor,
            ResourceBundleUtil labels) {
        return createStrokeWidthButton(
                editor,
                new double[]{0.5d, 1d, 2d, 3d, 5d, 9d, 13d},
                labels);
    }

    public static JPopupButton createStrokeWidthButton(DrawingEditor editor,
            double[] widths) {
        return createStrokeWidthButton(
                editor, new double[]{0.5d, 1d, 2d, 3d, 5d, 9d, 13d},
                ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels"));
    }

    public static JPopupButton createStrokeWidthButton(
            DrawingEditor editor, double[] widths, ResourceBundleUtil labels) {
        JPopupButton strokeWidthPopupButton = new JPopupButton();

        labels.configureToolBarButton(strokeWidthPopupButton, "attribute.strokeWidth.text");
        strokeWidthPopupButton.setFocusable(false);

        NumberFormat formatter = NumberFormat.getInstance();
        if (formatter instanceof DecimalFormat) {
            ((DecimalFormat) formatter).setMaximumFractionDigits(1);
            ((DecimalFormat) formatter).setMinimumFractionDigits(0);
        }
        for (int i = 0; i < widths.length; i++) {
            String label = Double.toString(widths[i]);
            Icon icon = new StrokeIcon(new BasicStroke((float) widths[i], BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            AttributeAction a = new AttributeAction(
                    editor,
                    STROKE_WIDTH,
                    new Double(widths[i]),
                    label,
                    icon);
            a.putValue(Actions.UNDO_PRESENTATION_NAME_KEY, labels.getString("attribute.strokeWidth.text"));
            AbstractButton btn = strokeWidthPopupButton.add(a);
            btn.setDisabledIcon(icon);
        }
        return strokeWidthPopupButton;
    }

    public static JPopupButton createStrokeDecorationButton(DrawingEditor editor) {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");

        JPopupButton strokeDecorationPopupButton = new JPopupButton();
        labels.configureToolBarButton(strokeDecorationPopupButton, "attribute.strokeDecoration");
        strokeDecorationPopupButton.setFocusable(false);
        strokeDecorationPopupButton.setColumnCount(2, false);
        LineDecoration[] decorations = {
            // Arrow
            new ArrowTip(0.35, 12, 11.3),
            // Arrow
            new ArrowTip(0.35, 13, 7),
            // Generalization triangle
            new ArrowTip(Math.PI / 5, 12, 9.8, true, true, false),
            // Dependency arrow
            new ArrowTip(Math.PI / 6, 12, 0, false, true, false),
            // Link arrow
            new ArrowTip(Math.PI / 11, 13, 0, false, true, true),
            // Aggregation diamond
            new ArrowTip(Math.PI / 6, 10, 18, false, true, false),
            // Composition diamond
            new ArrowTip(Math.PI / 6, 10, 18, true, true, true),
            null
        };
        for (int i = 0; i < decorations.length; i++) {
            strokeDecorationPopupButton.add(
                    new AttributeAction(
                            editor,
                            START_DECORATION,
                            decorations[i],
                            null,
                            new LineDecorationIcon(decorations[i], true)));
            strokeDecorationPopupButton.add(
                    new AttributeAction(
                            editor,
                            END_DECORATION,
                            decorations[i],
                            null,
                            new LineDecorationIcon(decorations[i], false)));
        }

        return strokeDecorationPopupButton;
    }

    public static JPopupButton createStrokeDashesButton(DrawingEditor editor) {
        return createStrokeDashesButton(editor,
                ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels"));
    }

    public static JPopupButton createStrokeDashesButton(DrawingEditor editor,
            ResourceBundleUtil labels) {
        return createStrokeDashesButton(editor, new double[][]{
            null,
            {4d, 4d},
            {2d, 2d},
            {4d, 2d},
            {2d, 4d},
            {8d, 2d},
            {6d, 2d, 2d, 2d},},
                labels);
    }

    public static JPopupButton createStrokeDashesButton(DrawingEditor editor,
            double[][] dashes) {
        return createStrokeDashesButton(editor, dashes,
                ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels"));
    }

    public static JPopupButton createStrokeDashesButton(DrawingEditor editor,
            double[][] dashes,
            ResourceBundleUtil labels) {
        JPopupButton strokeDashesPopupButton = new JPopupButton();
        labels.configureToolBarButton(strokeDashesPopupButton, "attribute.strokeDashes");
        strokeDashesPopupButton.setFocusable(false);
        //strokeDashesPopupButton.setColumnCount(2, false);
        for (int i = 0; i < dashes.length; i++) {

            float[] fdashes;
            if (dashes[i] == null) {
                fdashes = null;
            } else {
                fdashes = new float[dashes[i].length];
                for (int j = 0; j < dashes[i].length; j++) {
                    fdashes[j] = (float) dashes[i][j];
                }
            }

            Icon icon = new StrokeIcon(
                    new BasicStroke(2f, BasicStroke.CAP_BUTT,
                            BasicStroke.JOIN_BEVEL, 10f, fdashes, 0));

            AbstractButton btn = strokeDashesPopupButton.add(
                    new AttributeAction(
                            editor,
                            STROKE_DASHES,
                            dashes[i],
                            null,
                            icon));
            btn.setDisabledIcon(icon);
        }
        return strokeDashesPopupButton;
    }

    public static JPopupButton createStrokeTypeButton(DrawingEditor editor) {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");

        JPopupButton strokeTypePopupButton = new JPopupButton();
        labels.configureToolBarButton(strokeTypePopupButton, "attribute.strokeType");
        strokeTypePopupButton.setFocusable(false);

        strokeTypePopupButton.add(
                new AttributeAction(
                        editor,
                        STROKE_TYPE,
                        AttributeKeys.StrokeType.BASIC,
                        labels.getString("attribute.strokeType.basic"),
                        new StrokeIcon(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL))));
        HashMap<AttributeKey, Object> attr = new HashMap<AttributeKey, Object>();
        attr.put(STROKE_TYPE, AttributeKeys.StrokeType.DOUBLE);
        attr.put(STROKE_INNER_WIDTH_FACTOR, 2d);
        strokeTypePopupButton.add(
                new AttributeAction(
                        editor,
                        attr,
                        labels.getString("attribute.strokeType.double"),
                        new StrokeIcon(new DoubleStroke(2, 1))));
        attr = new HashMap<AttributeKey, Object>();
        attr.put(STROKE_TYPE, AttributeKeys.StrokeType.DOUBLE);
        attr.put(STROKE_INNER_WIDTH_FACTOR, 3d);
        strokeTypePopupButton.add(
                new AttributeAction(
                        editor,
                        attr,
                        labels.getString("attribute.strokeType.double"),
                        new StrokeIcon(new DoubleStroke(3, 1))));
        attr = new HashMap<AttributeKey, Object>();
        attr.put(STROKE_TYPE, AttributeKeys.StrokeType.DOUBLE);
        attr.put(STROKE_INNER_WIDTH_FACTOR, 4d);
        strokeTypePopupButton.add(
                new AttributeAction(
                        editor,
                        attr,
                        labels.getString("attribute.strokeType.double"),
                        new StrokeIcon(new DoubleStroke(4, 1))));

        return strokeTypePopupButton;
    }

    public static JPopupButton createStrokePlacementButton(DrawingEditor editor) {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");

        JPopupButton strokePlacementPopupButton = new JPopupButton();
        labels.configureToolBarButton(strokePlacementPopupButton, "attribute.strokePlacement");
        strokePlacementPopupButton.setFocusable(false);

        HashMap<AttributeKey, Object> attributes;
        attributes = new HashMap<AttributeKey, Object>();
        attributes.put(STROKE_PLACEMENT, AttributeKeys.StrokePlacement.CENTER);
        attributes.put(FILL_UNDER_STROKE, AttributeKeys.Underfill.CENTER);
        strokePlacementPopupButton.add(
                new AttributeAction(
                        editor,
                        attributes,
                        labels.getString("attribute.strokePlacement.center"),
                        null));
        attributes = new HashMap<AttributeKey, Object>();
        attributes.put(STROKE_PLACEMENT, AttributeKeys.StrokePlacement.INSIDE);
        attributes.put(FILL_UNDER_STROKE, AttributeKeys.Underfill.CENTER);
        strokePlacementPopupButton.add(
                new AttributeAction(
                        editor,
                        attributes,
                        labels.getString("attribute.strokePlacement.inside"),
                        null));
        attributes = new HashMap<AttributeKey, Object>();
        attributes.put(STROKE_PLACEMENT, AttributeKeys.StrokePlacement.OUTSIDE);
        attributes.put(FILL_UNDER_STROKE, AttributeKeys.Underfill.CENTER);
        strokePlacementPopupButton.add(
                new AttributeAction(
                        editor,
                        attributes,
                        labels.getString("attribute.strokePlacement.outside"),
                        null));
        attributes = new HashMap<AttributeKey, Object>();
        attributes.put(STROKE_PLACEMENT, AttributeKeys.StrokePlacement.CENTER);
        attributes.put(FILL_UNDER_STROKE, AttributeKeys.Underfill.FULL);
        strokePlacementPopupButton.add(
                new AttributeAction(
                        editor,
                        attributes,
                        labels.getString("attribute.strokePlacement.centerFilled"),
                        null));
        attributes = new HashMap<AttributeKey, Object>();
        attributes.put(STROKE_PLACEMENT, AttributeKeys.StrokePlacement.INSIDE);
        attributes.put(FILL_UNDER_STROKE, AttributeKeys.Underfill.FULL);
        strokePlacementPopupButton.add(
                new AttributeAction(
                        editor,
                        attributes,
                        labels.getString("attribute.strokePlacement.insideFilled"),
                        null));
        attributes = new HashMap<AttributeKey, Object>();
        attributes.put(STROKE_PLACEMENT, AttributeKeys.StrokePlacement.OUTSIDE);
        attributes.put(FILL_UNDER_STROKE, AttributeKeys.Underfill.FULL);
        strokePlacementPopupButton.add(
                new AttributeAction(
                        editor,
                        attributes,
                        labels.getString("attribute.strokePlacement.outsideFilled"),
                        null));
        attributes = new HashMap<AttributeKey, Object>();
        attributes.put(STROKE_PLACEMENT, AttributeKeys.StrokePlacement.CENTER);
        attributes.put(FILL_UNDER_STROKE, AttributeKeys.Underfill.NONE);
        strokePlacementPopupButton.add(
                new AttributeAction(
                        editor,
                        attributes,
                        labels.getString("attribute.strokePlacement.centerUnfilled"),
                        null));
        attributes = new HashMap<AttributeKey, Object>();
        attributes.put(STROKE_PLACEMENT, AttributeKeys.StrokePlacement.INSIDE);
        attributes.put(FILL_UNDER_STROKE, AttributeKeys.Underfill.NONE);
        strokePlacementPopupButton.add(
                new AttributeAction(
                        editor,
                        attributes,
                        labels.getString("attribute.strokePlacement.insideUnfilled"),
                        null));
        attributes = new HashMap<AttributeKey, Object>();
        attributes.put(STROKE_PLACEMENT, AttributeKeys.StrokePlacement.OUTSIDE);
        attributes.put(FILL_UNDER_STROKE, AttributeKeys.Underfill.NONE);
        strokePlacementPopupButton.add(
                new AttributeAction(
                        editor,
                        attributes,
                        labels.getString("attribute.strokePlacement.outsideUnfilled"),
                        null));

        return strokePlacementPopupButton;
    }
    
    public static void addFontButtonsTo(JToolBar bar, DrawingEditor editor) {
        bar.add(createFontButton(editor));
        bar.add(createFontStyleBoldButton(editor, ""));
        bar.add(createFontStyleItalicButton(editor, ""));
        bar.add(createFontStyleUnderlineButton(editor, ""));
    }

    public static JPopupButton createFontButton(DrawingEditor editor) {
        AttributeKey<Font> key = FONT_FACE;
        JPopupButton fontPopupButton;

        fontPopupButton = new JPopupButton();

        SVGlabels.configureToolBarButton(fontPopupButton, "attribute.font");
        fontPopupButton.setFocusable(false);

        JPopupMenu popupMenu = new JPopupMenu();
        JFontChooser fontChooser = new JFontChooser();
        new FontChooserHandler(editor, key, fontChooser, popupMenu);

        popupMenu.add(fontChooser);
        fontPopupButton.setPopupMenu(popupMenu);
        fontPopupButton.setFocusable(false);

        return fontPopupButton;
    }

    public static JButton createButton(String configuration) {
        JButton button;
        button = new JButton();
        SVGlabels.configureToolBarButton(button, configuration);
        button.setFocusable(false);
        return button;
    }

    public static JButton createFontStyleBoldButton(DrawingEditor editor, String configuration) {
        JButton button = createButton(configuration);
        AbstractAction a = new AttributeToggler<Boolean>(editor,
                FONT_BOLD, Boolean.TRUE, Boolean.FALSE,
                new StyledEditorKit.BoldAction());
        a.putValue(Actions.UNDO_PRESENTATION_NAME_KEY, SVGlabels.getString("attribute.fontStyle.bold.text"));
        button.addActionListener(a);
        return button;
    }

    public static JButton createFontStyleItalicButton(DrawingEditor editor, String configuration) {
        JButton button = createButton(configuration);
        AbstractAction action = new AttributeToggler<Boolean>(editor,
                FONT_ITALIC, Boolean.TRUE, Boolean.FALSE,
                new StyledEditorKit.BoldAction());
        action.putValue(Actions.UNDO_PRESENTATION_NAME_KEY, SVGlabels.getString("attribute.fontStyle.italic.text"));
        button.addActionListener(action);
        return button;
    }

    public static JButton createFontStyleUnderlineButton(DrawingEditor editor, String configuration) {
        JButton button = createButton(configuration);
        AbstractAction a = new AttributeToggler<Boolean>(editor,
                FONT_UNDERLINE, Boolean.TRUE, Boolean.FALSE,
                new StyledEditorKit.BoldAction());
        a.putValue(Actions.UNDO_PRESENTATION_NAME_KEY, SVGlabels.getString("attribute.fontStyle.underline.text"));
        button.addActionListener(a);
        return button;
    }

    /**
     * Creates a button which toggles between two GridConstrainer for a
     * DrawingView.
     */
    public static AbstractButton createToggleGridButton(final DrawingView view) {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
        final JToggleButton toggleButton;

        toggleButton = new JToggleButton();
        labels.configureToolBarButton(toggleButton, "view.toggleGrid");
        toggleButton.setFocusable(false);
        toggleButton.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent event) {
                view.setConstrainerVisible(toggleButton.isSelected());
                //view.getComponent().repaint();
            }
        });
        view.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                // String constants are interned
                if (evt.getPropertyName() == DrawingView.CONSTRAINER_VISIBLE_PROPERTY) {
                    toggleButton.setSelected(view.isConstrainerVisible());
                }
            }
        });

        return toggleButton;
    }

    public static JPopupButton createStrokeCapButton(DrawingEditor editor) {
        String[] labels = {"attribute.strokeCap.butt", "attribute.strokeCap.round", "attribute.strokeCap.square"};
        int[] strokes = {BasicStroke.CAP_BUTT, BasicStroke.CAP_ROUND, BasicStroke.CAP_SQUARE};
        
        JPopupButton popupButton = createPopupWithOptions(editor, labels, strokes, STROKE_CAP);
        SVGlabels.configureToolBarButton(popupButton, "attribute.strokeCap");
        popupButton.setFocusable(false);
        
        return popupButton;
    }

    public static JPopupButton createStrokeJoinButton(DrawingEditor editor) {
        String[] labels = {"attribute.strokeJoin.bevel", 
            "attribute.strokeJoin.round", "attribute.strokeJoin.miter"};
        int[] strokeTypes = {BasicStroke.JOIN_BEVEL, BasicStroke.JOIN_ROUND, 
            BasicStroke.JOIN_MITER};
        
        JPopupButton popupButton = createPopupWithOptions(editor, labels, strokeTypes, STROKE_JOIN);
        SVGlabels.configureToolBarButton(popupButton, "attribute.strokeJoin");
        popupButton.setFocusable(false); 
        
        return popupButton;
    }

    private static JPopupButton createPopupWithOptions(DrawingEditor editor, String[] labels, 
            int[] strokeTypes, AttributeKey<Integer> attributeKey) {
        JPopupButton button = new JPopupButton();
        Map<AttributeKey, Object> attributes;
        
        for(int listIterator = 0; listIterator < labels.length; listIterator++) {
            attributes = new HashMap<>();
            attributes.put(attributeKey, strokeTypes[listIterator]);
            button.add(new AttributeAction(editor, attributes,
                    SVGlabels.getString(labels[listIterator]),
                    null));
        }
        return button;
    }

    public static JButton createPickAttributesButton(DrawingEditor editor) {
        JButton button = new JButton(new PickAttributesAction(editor));
        button = setButtonProperties(button, editor);
        return button;
    }

    /**
     * Creates a button that applies the default attributes of the editor to the
     * current selection.
     */
    public static JButton createApplyAttributesButton(DrawingEditor editor) {
        JButton button = new JButton(new ApplyAttributesAction(editor));
        button = setButtonProperties(button, editor);
        return button;
    }
    
    private static JButton setButtonProperties(JButton btn, DrawingEditor editor) {
        if (btn.getIcon() != null) {
            btn.putClientProperty("hideActionText", Boolean.TRUE);
        }
        btn.setHorizontalTextPosition(JButton.CENTER);
        btn.setVerticalTextPosition(JButton.BOTTOM);
        btn.setText(null);
        btn.setFocusable(false);
        return btn;
    }

    public static AbstractButton createMagnifyButton(final DrawingView view) {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
        final JToggleButton magnifyButton;
        magnifyButton = new JToggleButton();

        labels.configureToolBarButton(magnifyButton, "view.magnifyGlass");
        magnifyButton.setFocusable(false);
        magnifyButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (view.getScaleFactor() == 1 && magnifyButton.isSelected()) {
                    view.setScaleFactor(2);
                }else{
                    view.setScaleFactor(1);  
                }
            }

        });

        return magnifyButton;
    }
}
