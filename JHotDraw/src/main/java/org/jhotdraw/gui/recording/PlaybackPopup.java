/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.gui.recording;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.Timer;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.QuadTreeDrawing;
import org.jhotdraw.recording.FigureUpdate;

/**
 *
 * @author Mikkel, Holst & Harald
 */
public class PlaybackPopup {
    public PlaybackPopup() {
        
    }
    
    public static void Show(int FPS, ArrayList<FigureUpdate> updates) {
        if (updates == null || updates.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No recording found for this tab, press the record button to begin recording.");
            return;
        }
        
        JFrame window = new JFrame("Recording Playback");
        window.setVisible(true);
        window.setSize(1100, 600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width / 2 - window.getSize().width / 2, dim.height / 2 - window.getSize().height / 2);

        DefaultDrawingView drawingView = new DefaultDrawingView();
        drawingView.setDrawing(new QuadTreeDrawing());

        AtomicBoolean looping = new AtomicBoolean(true);
        AtomicInteger frameIndex = new AtomicInteger();

        JPanel controls = new JPanel();
        controls.setMaximumSize(new Dimension(999, 32));

        JToggleButton btnPlaying = new JToggleButton("Pause");
        controls.add(btnPlaying);

        JSlider timeline = new JSlider(0, updates.size());
        controls.add(timeline);

        JToggleButton btnLooping = new JToggleButton("Loop");
        controls.add(btnLooping);

        JPanel toolPanel = new JPanel();
        toolPanel.setLayout(new BoxLayout(toolPanel, BoxLayout.X_AXIS));
        JPanel toolList = new JPanel();
        toolList.setLayout(new BoxLayout(toolList, BoxLayout.Y_AXIS));
        toolPanel.add(toolList);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(drawingView);
        panel.add(controls);
        toolPanel.add(panel);
        window.setContentPane(toolPanel);

        HashMap<Class, Boolean> mapOfClassesRecorded = new HashMap<>();
        updates.forEach((update) -> {
            mapOfClassesRecorded.putIfAbsent(update.getFigure().getClass(), true);
        });

        HashMap<Integer, Figure> mapOfDrawnFigures = new HashMap<>();

        Timer timer;
        timer = new Timer(1000 / FPS, (ae) -> {
            timeline.setValue(frameIndex.get());
            if (frameIndex.get() < updates.size()) {
                FigureUpdate current = updates.get(frameIndex.getAndIncrement());

                if (mapOfClassesRecorded.get(current.getFigure().getClass())) {
                    if (mapOfDrawnFigures.containsKey(current.getHash())) {
                        drawingView.getDrawing().remove(mapOfDrawnFigures.get(current.getHash()));
                        mapOfDrawnFigures.remove(current.getHash());
                    }

                    drawingView.getDrawing().add(current.getFigure());
                    mapOfDrawnFigures.put(current.getHash(), current.getFigure());
                }

                ((QuadTreeDrawing) drawingView.getDrawing()).repaintFigure(current.getFigure());

            } else {
                mapOfDrawnFigures.clear();
                drawingView.getDrawing().removeAllChildren();

                if (looping.get()) {
                    frameIndex.set(0);
                }
            }

        });
        timer.start();

        btnPlaying.addActionListener((ActionEvent e) -> {
            if (timer.isRunning()) {
                timer.stop();
            } else {
                timer.start();
            }
        });

        btnLooping.addActionListener((ActionEvent e) -> {
            looping.set(!looping.get());
        });

        timeline.addChangeListener((ce) -> {
            frameIndex.set(timeline.getValue());
        });

        window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                timer.stop();
            }
        });

        mapOfClassesRecorded.forEach((klass, bool) -> {
            JCheckBox checkBox = new JCheckBox(klass.getSimpleName(), bool);

            checkBox.addActionListener((ae) -> {
                mapOfClassesRecorded.put(klass, checkBox.isSelected());
            });

            toolList.add(checkBox);
        });
    }
}
