/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw;

import org.jhotdraw.draw.AbstractFigure;
import org.jhotdraw.draw.DefaultDrawingEditor;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.FigureListener;
import org.jhotdraw.draw.SimpleDrawing;
import org.jhotdraw.recording.RecordingManager;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RecordingToolTest {

    private static final DefaultDrawingView drawingView = new DefaultDrawingView();
    private static final DefaultDrawingEditor drawingEditor = new DefaultDrawingEditor();
    private static final RecordingManager recordingManager = new RecordingManager(drawingEditor);
    
    @BeforeClass
    public static void setUpClass() {
        drawingView.setDrawing(new SimpleDrawing());
        drawingEditor.setActiveView(drawingView);
    }

    @Test
    public void firstTestStartRecording() {
        assertEquals(0, recordingManager.getMapOfFigureListeners().size());

        recordingManager.getStartRecordingAction().fire();
        FigureListener listener = recordingManager.getMapOfFigureListeners().get(drawingView.getDrawing().hashCode());

        boolean containsListener = false;
        for (Object o : ((AbstractFigure) drawingView.getDrawing()).getFigureListenerList().getListenerList()) {
            if (o == listener) {
                containsListener = true;
            }
        }
        
        assertEquals(true, containsListener);

        assertEquals(1, recordingManager.getMapOfFigureListeners().size());
    }

    @Test
    public void secondTestRecordChange() {
        assertEquals(0, recordingManager.getMapOfFigureUpdates().get(drawingView.getDrawing().hashCode()).size());
        
        ((SimpleDrawing) drawingEditor.getActiveView().getDrawing()).fireFigureChanged();
        
        assertEquals(1, recordingManager.getMapOfFigureUpdates().get(drawingView.getDrawing().hashCode()).size());
    }

    @Test
    public void thirdTestStopRecording() {
        assertEquals(1, recordingManager.getMapOfFigureListeners().size());

        FigureListener listener = recordingManager.getMapOfFigureListeners().get(drawingView.getDrawing().hashCode());
        recordingManager.getStopRecordingAction().fire();

        boolean containsListener = false;
        for (Object o : ((AbstractFigure) drawingView.getDrawing()).getFigureListenerList().getListenerList()) {
            if (o == listener) {
                containsListener = true;
            }
        }

        assertEquals(false, containsListener);

        assertEquals(0, recordingManager.getMapOfFigureListeners().size());
    }
}