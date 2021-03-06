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
    
    private static Animation instance;
    private List<JFrame> frames;
    private JFrame currentFrame;
    private int timesPlayed;

    public Animation() {
        frames = new ArrayList<>();
        timesPlayed = 0;
    }
    
    public static Animation getInstance() {
        if(instance == null){
            instance = new Animation();
        }
        return instance;
    }

    public List<JFrame> getFrames() {
        return frames;
    }

    public JFrame getCurrentFrame() {
        return currentFrame;
    }

    public int getTimesPlayed() {
        return timesPlayed;
    }

    public void incrementTimesPlayed() {
        timesPlayed++;
    }

    public void setTimesPlayed(int timesPlayed) {
        this.timesPlayed = timesPlayed;
    }
    
    public void setCurrentFrame(JFrame currentFrame) {
        this.currentFrame = currentFrame;
    }
    
    public JFrame setFrame(int frame) {
        return frames.get(frame);
    }

    public void addFrame(JFrame frame) {
        if(frame == null){
            System.out.println("Can not add null");
        }else{
            frames.add(frame);
        }
    }

    public void removeFrame(JFrame frameToRemove) {
        frames.remove(getFrames().indexOf(frameToRemove));
    }
}