/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.recording;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.AbstractAction;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.FigureListener;
import org.jhotdraw.gui.recording.PlaybackPopup;

public class RecordingManager {
    public class PlayRecordingAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            startPlaybackActiveDrawing();
        }
    }

    public class StartRecordingAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            startRecordingActiveDrawing();
        }
    }

    public class StopRecordingAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            stopRecordingActiveDrawing();
        }
    }

    public RecordingManager(DrawingEditor editor) {
        this.editor = editor;
    }

    private DrawingEditor editor = null;
    private PlayRecordingAction playFramesAction = null;
    private StartRecordingAction startRecordingAction = null;
    private StopRecordingAction stopRecordingAction = null;
    private int FPS = 60;

    private HashMap<Integer, ArrayList<FigureUpdate>> mapOfFigureUpdates = new HashMap<>();

    private HashMap<Integer, FigureListener> mapOfFigureListeners = new HashMap<>();

    public HashMap<Integer, FigureListener> getMapOfFigureListeners() {
        return mapOfFigureListeners;
    }

    public void setEditor(DrawingEditor editor) {
        this.editor = editor;
    }

    public PlayRecordingAction getPlayFramesAction() {
        if (playFramesAction == null) {
            playFramesAction = new PlayRecordingAction();
        }

        return playFramesAction;
    }

    public StartRecordingAction getStartRecordingAction() {
        if (startRecordingAction == null) {
            startRecordingAction = new StartRecordingAction();
        }

        return startRecordingAction;
    }

    public StopRecordingAction getStopRecordingAction() {
        if (stopRecordingAction == null) {
            stopRecordingAction = new StopRecordingAction();
        }

        return stopRecordingAction;
    }

    private void startPlaybackActiveDrawing() {
        stopRecordingActiveDrawing();

        int hashcode = editor.getActiveView().getDrawing().hashCode();

        PlaybackPopup.Show(FPS, mapOfFigureUpdates.get(hashcode));
    }

    private void startRecordingActiveDrawing() {
        if (editor == null) {
            System.err.println("Cannot record when there is no active editor!");
            return;
        }

        int hashcode = editor.getActiveView().getDrawing().hashCode();
        mapOfFigureUpdates.putIfAbsent(hashcode, new ArrayList<FigureUpdate>());

        for (Figure figure : editor.getActiveView().getDrawing().getChildren()) {
            mapOfFigureUpdates.get(hashcode).add(new FigureUpdate(
                    (Figure) figure.clone(),
                    figure.hashCode()
            ));
        }

        mapOfFigureListeners.putIfAbsent(hashcode, new RecordingFigureListener(FPS, mapOfFigureUpdates.get(hashcode)));
        editor.getActiveView().getDrawing().addFigureListener(mapOfFigureListeners.get(hashcode));
    }

    private void stopRecordingActiveDrawing() {
        int hashcode = editor.getActiveView().getDrawing().hashCode();
        editor.getActiveView().getDrawing().removeFigureListener(mapOfFigureListeners.get(hashcode));
        mapOfFigureListeners.remove(hashcode);
    }

    private void clearFramesOfActiveDrawing() {
        int hashcode = editor.getActiveView().getDrawing().hashCode();
        //mapOfDrawingFrames.get(hashcode).clear();
        mapOfFigureUpdates.get(hashcode).clear();
    }
}
