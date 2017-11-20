package org.jhotdraw.samples.svg.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collection;
import java.util.LinkedList;
import java.util.prefs.Preferences;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.jhotdraw.app.action.DuplicateAction;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.action.BringToFrontAction;
import org.jhotdraw.draw.action.ButtonFactory;
import org.jhotdraw.draw.action.EdgeDetectionAction;
import org.jhotdraw.draw.action.GroupAction;
import org.jhotdraw.draw.action.SendToBackAction;
import org.jhotdraw.draw.action.UngroupAction;
import org.jhotdraw.gui.plaf.palette.PaletteButtonUI;
import org.jhotdraw.samples.svg.action.CombineAction;
import org.jhotdraw.samples.svg.action.SplitAction;
import org.jhotdraw.samples.svg.figures.SVGGroupFigure;
import org.jhotdraw.util.ResourceBundleUtil;

public class RecordingToolBar extends AbstractToolBar {
    public RecordingToolBar() {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        setName(labels.getString("recording.toolbar"));
    }

    @Override
    protected JComponent createDisclosedComponent(int state) {
        JPanel p = null;

        switch (state) {
            case 1:
               {
                    p = new JPanel();
                    p.setOpaque(false);
                    p.setBorder(new EmptyBorder(5, 5, 5, 8));

                    Preferences prefs = Preferences.userNodeForPackage(getClass());

                    ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");

                    GridBagLayout layout = new GridBagLayout();
                    p.setLayout(layout);

                    GridBagConstraints gbc;
                    AbstractButton btn;

                    // Start Recording Btn
                    btn = new JButton(); // undoManager.getUndoAction()
                    btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                    labels.configureToolBarButton(btn, "recordingTool.start");
                    gbc = new GridBagConstraints();
                    gbc.gridy = 0;
                    gbc.gridx = 0;
                    gbc.insets = new Insets(-24, 0, 0, 0);
                    p.add(btn, gbc);
                    
                    // Stop Recording Btn
                    btn = new JButton(); // undoManager.getUndoAction()
                    btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                    labels.configureToolBarButton(btn, "recordingTool.stop");
                    gbc = new GridBagConstraints();
                    gbc.gridy = 1;
                    gbc.gridx = 0;
                    gbc.insets = new Insets(3, 0, 0, 0);
                    p.add(btn, gbc);
                }
                break;
        }
        
        return p;
    }

    @Override
    protected String getID() {
        return "recording";
    }
    
    @Override
    protected int getDefaultDisclosureState() {
        return 1;
    }
}
