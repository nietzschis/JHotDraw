/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.recording;

import java.util.ArrayList;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.FigureEvent;
import org.jhotdraw.draw.FigureListener;
import org.jhotdraw.draw.QuadTreeDrawing;

/**
 *
 * @author Mikkel
 */
public class RecordingFigureListener implements FigureListener {

    protected long lastFrameCapturedAt = 0;
    protected final long interval;
    protected ArrayList<FigureUpdate> updates;
    
    public RecordingFigureListener(int FPS, ArrayList<FigureUpdate> updates) {
        this.interval = 1000 / FPS;
        this.updates = updates;
    }

    RecordingFigureListener() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void storeChange(FigureEvent e) {
        if (e.getFigure() instanceof QuadTreeDrawing) {
            return;
        }

        if (System.currentTimeMillis() - lastFrameCapturedAt > interval) {
            lastFrameCapturedAt = System.currentTimeMillis();

            updates.add(
                    new FigureUpdate((Figure) e.getFigure().clone(),
                            e.getFigure().hashCode()
                    ));
        }
    }

    @Override
    public void areaInvalidated(FigureEvent e) {
        storeChange(e);
    }

    @Override
    public void attributeChanged(FigureEvent e) {
        storeChange(e);
    }

    @Override
    public void figureHandlesChanged(FigureEvent e) {
        storeChange(e);
    }

    @Override
    public void figureChanged(FigureEvent e) {
        storeChange(e);
    }

    @Override
    public void figureAdded(FigureEvent e) {
        storeChange(e);
    }

    @Override
    public void figureRemoved(FigureEvent e) {
        storeChange(e);
    }

    @Override
    public void figureRequestRemove(FigureEvent e) {
        storeChange(e);
    }
}