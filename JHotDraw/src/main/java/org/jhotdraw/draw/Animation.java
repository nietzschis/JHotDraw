/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

/**
 *
 * @author lasca
 */
public class Animation {
    
    private List<JFrame> frames;
    private JFrame currentFrame;

    public Animation() {
        frames = new ArrayList<>();
    }

    public List<JFrame> getFrames() {
        return frames;
    }

    public JFrame getCurrentFrame() {
        return currentFrame;
    }

    public void setFrames(List<JFrame> frames) {
        this.frames = frames;
    }

    public void setCurrentFrame(JFrame currentFrame) {
        this.currentFrame = currentFrame;
    }
}
