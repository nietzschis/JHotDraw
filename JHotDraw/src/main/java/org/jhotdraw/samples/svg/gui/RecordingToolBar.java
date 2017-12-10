package org.jhotdraw.samples.svg.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.prefs.Preferences;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.gui.plaf.palette.PaletteButtonUI;
import org.jhotdraw.recording.RecordingManager;
import org.jhotdraw.util.ResourceBundleUtil;

public class RecordingToolBar extends AbstractToolBar {    
    public RecordingToolBar() {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        setName(labels.getString("recording.toolbar"));
    }

    RecordingManager recordingManager = null;
    ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");

    protected AbstractButton recordButton = null;
    protected ArrayList<Integer> listOfActiveRecordings = new ArrayList<>();

    @Override
    protected JComponent createDisclosedComponent(int state) {
        JPanel p = null;

        switch (state) {
            case 1: {
                p = new JPanel();
                p.setOpaque(false);
                p.setBorder(new EmptyBorder(5, 5, 5, 8));

                Preferences prefs = Preferences.userNodeForPackage(getClass());

                GridBagLayout layout = new GridBagLayout();
                p.setLayout(layout);

                GridBagConstraints gbc;
                AbstractButton btn;

                
                // Start Recording Btn
                btn = new JButton(recordingManager.getStartRecordingAction());
                recordButton = btn;
                btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                labels.configureToolBarButton(btn, "recordingTool.start");

                btn.addActionListener((ActionEvent e) -> {
                    listOfActiveRecordings.add(editor.getActiveView().getDrawing().hashCode());
                    updateRecordButton();
                });

                gbc = new GridBagConstraints();
                gbc.gridy = 0;
                gbc.gridx = 0;
                gbc.insets = new Insets(0, 0, 0, 0);
                p.add(btn, gbc);

                // Stop Recording Btn
                btn = new JButton(recordingManager.getStopRecordingAction());
                btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                labels.configureToolBarButton(btn, "recordingTool.stop");

                btn.addActionListener((ActionEvent e) -> {
                    if (listOfActiveRecordings.contains(editor.getActiveView().getDrawing().hashCode())) {
                        listOfActiveRecordings.remove(
                                listOfActiveRecordings.indexOf(editor.getActiveView().getDrawing().hashCode())
                        );
                    }
                    updateRecordButton();
                });

                gbc = new GridBagConstraints();
                gbc.gridy = 1;
                gbc.gridx = 0;
                gbc.insets = new Insets(5, 0, 0, 0);
                p.add(btn, gbc);
                
                // Play Recording Btn
                btn = new JButton(recordingManager.getPlayFramesAction());
                btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                labels.configureToolBarButton(btn, "recordingTool.play");
                gbc = new GridBagConstraints();
                gbc.gridy = 2;
                gbc.gridx = 0;
                gbc.insets = new Insets(5, 0, 0, 0);
                p.add(btn, gbc);
            }
            break;
        }

        editor.getActiveView().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName() == "drawing") {
                    updateRecordButton();
                }
            }
        });

        return p;
    }

    protected void updateRecordButton() {
        if (!listOfActiveRecordings.contains(editor.getActiveView().getDrawing().hashCode())) {
            labels.configureToolBarButton(recordButton, "recordingTool.start");
        } else {
            labels.configureToolBarButton(recordButton, "recordingTool.recording");
        }
    }

    @Override
    protected String getID() {
        return "recording";
    }

    @Override
    protected int getDefaultDisclosureState() {
        return 1;
    }

    @Override
    public void setEditor(DrawingEditor editor) {
        super.setEditor(editor);
        recordingManager = new RecordingManager(editor);
    }
}
