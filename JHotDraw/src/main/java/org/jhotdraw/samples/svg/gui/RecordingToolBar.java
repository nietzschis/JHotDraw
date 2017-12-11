package org.jhotdraw.samples.svg.gui;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.AbstractButton;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.recording.RecordingManager;
import org.jhotdraw.util.ResourceBundleUtil;

public class RecordingToolBar extends AbstractToolBar {    

    RecordingManager recordingManager = null;
    ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");

    protected AbstractButton recordButton = null;
    protected ArrayList<Integer> listOfActiveRecordings = new ArrayList<>();
    
    public RecordingToolBar() {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        setName(labels.getString("recording.toolbar"));
    }
        
    @Override
    public void setEditor(DrawingEditor editor) {
        super.setEditor(editor);
        recordingManager = new RecordingManager(editor);
        
        recordButton = AddToolButton(recordingManager.getStartRecordingAction(), "btnStartRecording", "recordingTool.start");
        recordButton.addActionListener((ActionEvent e) -> {
            listOfActiveRecordings.add(editor.getActiveView().getDrawing().hashCode());
            updateRecordButton();
        });
        
        AddToolButton(recordingManager.getStopRecordingAction(), "btnStopRecording", "recordingTool.stop").
                addActionListener((ActionEvent e) -> {
                    if (listOfActiveRecordings.contains(editor.getActiveView().getDrawing().hashCode())) {
                        listOfActiveRecordings.remove(
                                listOfActiveRecordings.indexOf(editor.getActiveView().getDrawing().hashCode())
                        );
                    }
                    updateRecordButton();
                });

        AddToolButton(recordingManager.getPlayFramesAction(), "btnPlayRecording", "recordingTool.play");
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

    
}
