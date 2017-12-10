/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.recording;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.FigureListener;
import org.jhotdraw.gui.recording.PlaybackPopup;
import org.jhotdraw.recording.actions.PlayRecordingAction;
import org.jhotdraw.recording.actions.StartRecordingAction;
import org.jhotdraw.recording.actions.StopRecordingAction;

public class RecordingManager {

    private DrawingEditor editor = null;
    private PlayRecordingAction playFramesAction = null;
    private StartRecordingAction startRecordingAction = null;
    private StopRecordingAction stopRecordingAction = null;
    private int FPS = 60;

    private HashMap<Integer, ArrayList<FigureUpdate>> mapOfFigureUpdates = new HashMap<>();

    private HashMap<Integer, FigureListener> mapOfFigureListeners = new HashMap<>();

    public RecordingManager(DrawingEditor editor) {
        this.editor = editor;
    }

    public HashMap<Integer, FigureListener> getMapOfFigureListeners() {
        return mapOfFigureListeners;
    }
    
    public HashMap<Integer, ArrayList<FigureUpdate>> getMapOfFigureUpdates() {
        return mapOfFigureUpdates;
    }

    public void setEditor(DrawingEditor editor) {
        this.editor = editor;
    }

    public PlayRecordingAction getPlayFramesAction() {
        if (playFramesAction == null) {
            playFramesAction = new PlayRecordingAction((Callable) () -> {
                getStopRecordingAction().fire();

                int hashcode = editor.getActiveView().getDrawing().hashCode();

                PlaybackPopup.Show(FPS, mapOfFigureUpdates.get(hashcode));

                return null;
            });
        }

        return playFramesAction;
    }

    public StartRecordingAction getStartRecordingAction() {
        if (startRecordingAction == null) {
            startRecordingAction = new StartRecordingAction((Callable) () -> {
                if (editor == null) {
                    System.err.println("Cannot record when there is no active editor!");
                    return null;
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

                return null;
            });
        }

        return startRecordingAction;
    }

    public StopRecordingAction getStopRecordingAction() {
        if (stopRecordingAction == null) {
            stopRecordingAction = new StopRecordingAction((Callable) () -> {
                int hashcode = editor.getActiveView().getDrawing().hashCode();
                editor.getActiveView().getDrawing().removeFigureListener(mapOfFigureListeners.get(hashcode));
                mapOfFigureListeners.remove(hashcode);
                return null;
            });
        }

        return stopRecordingAction;
    }
}
