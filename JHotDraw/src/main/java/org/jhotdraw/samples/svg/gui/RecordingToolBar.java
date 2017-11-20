package org.jhotdraw.samples.svg.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.prefs.Preferences;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.gui.plaf.palette.PaletteButtonUI;
import org.jhotdraw.util.ResourceBundleUtil;

public class RecordingToolBar extends AbstractToolBar {
    public RecordingToolBar() {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        setName(labels.getString("recording.toolbar"));
        recordingManager = new RecordingManager(this.editor);
    }
    
    RecordingManager recordingManager = null;

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
                    btn = new JButton(recordingManager.getStartRecordingAction()); 
                    btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                    labels.configureToolBarButton(btn, "recordingTool.start");
                    gbc = new GridBagConstraints();
                    gbc.gridy = 0;
                    gbc.gridx = 0;
                    gbc.insets = new Insets(-24, 0, 0, 0);
                    p.add(btn, gbc);
                    
                    // Stop Recording Btn
                    btn = new JButton(recordingManager.getStopRecordingAction()); 
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
    
    private class RecordingManager {
        public class StartRecordingAction extends AbstractAction {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Start");
            }
        }
        
        public class StopRecordingAction extends AbstractAction {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Stop");
            }
        }
        
        public RecordingManager(DrawingEditor editor) {
            this.editor = editor;
        }
        
        private DrawingEditor editor = null;
        private StartRecordingAction startRecordingAction = null;
        private StopRecordingAction stopRecordingAction = null;
        
        public void setEditor(DrawingEditor editor) {
            this.editor = editor;
        }
        
        public StartRecordingAction getStartRecordingAction() {
            if(startRecordingAction == null) {
                startRecordingAction = new StartRecordingAction();
            }
            
            return startRecordingAction;
        }
        
        public StopRecordingAction getStopRecordingAction() {
            if(stopRecordingAction == null) {
                stopRecordingAction = new StopRecordingAction();
            }
            
            return stopRecordingAction;
        }
    }
}
