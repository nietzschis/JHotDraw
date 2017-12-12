/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.recording.actions;

import java.awt.event.ActionEvent;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import org.jhotdraw.recording.RecordingManager;

/**
 *
 * @author Mikkel
 */
public class StartRecordingAction extends AbstractAction {

    private final Callable callable;

    public StartRecordingAction(Callable callable) {
        this.callable = callable;
    }

    public void fire() {
        try {
            callable.call();
        } catch (Exception ex) {
            Logger.getLogger(PlayRecordingAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            callable.call();
        } catch (Exception ex) {
            Logger.getLogger(RecordingManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
