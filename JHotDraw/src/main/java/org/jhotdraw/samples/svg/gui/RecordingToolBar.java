package org.jhotdraw.samples.svg.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.FigureEvent;
import org.jhotdraw.draw.FigureListener;
import org.jhotdraw.draw.QuadTreeDrawing;
import org.jhotdraw.gui.plaf.palette.PaletteButtonUI;
import org.jhotdraw.util.ResourceBundleUtil;

public class RecordingToolBar extends AbstractToolBar {
    public RecordingToolBar() {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        setName(labels.getString("recording.toolbar"));
    }
    
    RecordingManager recordingManager = null;
    ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
    
    protected AbstractButton recordButton = null;
    protected ArrayList<Integer> listOfActiveRecordings = new ArrayList<>();
    
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

                    GridBagLayout layout = new GridBagLayout();
                    p.setLayout(layout);

                    GridBagConstraints gbc;
                    AbstractButton btn;
                    
                    // Play Recording Btn
                    btn = new JButton(recordingManager.getPlayFramesAction()); 
                    btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                    labels.configureToolBarButton(btn, "recordingTool.play");
                    gbc = new GridBagConstraints();
                    gbc.gridy = 0;
                    gbc.gridx = 0;
                    //gbc.insets = new Insets(-24, 0, 0, 0);
                    p.add(btn, gbc);
                    
                    // Start Recording Btn
                    btn = new JButton(recordingManager.getStartRecordingAction()); 
                    recordButton = btn;
                    btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                    labels.configureToolBarButton(btn, "recordingTool.start");
                    
                    btn.addActionListener((ActionEvent e) -> {
                        listOfActiveRecordings.add(editor.getActiveView().getDrawing().hashCode());
                        updateRecordButton();
                    });
                    
                    gbc = new GridBagConstraints();
                    gbc.gridy = 1;
                    gbc.gridx = 0;
                    //gbc.insets = new Insets(-24, 0, 0, 0);
                    p.add(btn, gbc);
                    
                    // Stop Recording Btn
                    btn = new JButton(recordingManager.getStopRecordingAction()); 
                    btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                    labels.configureToolBarButton(btn, "recordingTool.stop");
                    
                    btn.addActionListener((ActionEvent e) -> {
                        if(listOfActiveRecordings.contains(editor.getActiveView().getDrawing().hashCode())) {
                            listOfActiveRecordings.remove(
                                    listOfActiveRecordings.indexOf(editor.getActiveView().getDrawing().hashCode())
                            );
                        }
                        updateRecordButton();
                    });
                    
                    gbc = new GridBagConstraints();
                    gbc.gridy = 2;
                    gbc.gridx = 0;
                    //gbc.insets = new Insets(3, 0, 0, 0);
                    p.add(btn, gbc);
                }
                break;
        }
        
        editor.getActiveView().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if(evt.getPropertyName() == "drawing") {
                    updateRecordButton();
                }
            }
        });
        
        return p;
    }
    
    protected void updateRecordButton() {
        if(!listOfActiveRecordings.contains(editor.getActiveView().getDrawing().hashCode())) {
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
    
    /**
     * setEditor has been overridden so that the RecordingManager instance is constructed with a editor
     * rather than just null. This is necessary because the toolbar doesn't get the editor when it's created by SVGDrawingPanel.
     */
    @Override
    public void setEditor(DrawingEditor editor) {
        super.setEditor(editor);
        recordingManager = new RecordingManager(editor);
    }
    
    private class RecordingManager {
        /**
         * Action that initiates playing of the recorded frames.
         */
        public class PlayRecordingAction extends AbstractAction {
            @Override
            public void actionPerformed(ActionEvent e) {
                startPlaybackActiveDrawing();
            }
        }
        
        /** 
         * Action that initiates the recording implemented by extending AbstractAction to not stray from what other toolbars do.
         */
        public class StartRecordingAction extends AbstractAction {
            @Override
            public void actionPerformed(ActionEvent e) {
                startRecordingActiveDrawing();
            }
        }
        
        /** 
         * Action that stops the recording implemented by extending AbstractAction to not stray from what other toolbars do.
         */
        public class StopRecordingAction extends AbstractAction {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopRecordingActiveDrawing();
            }
        }
        
        /**
         * Class used to store data relevant for later replaying and editing.
         * The drawing variable should be used for storing the Drawing received from the DrawingView,
         * and the klass variable should be used for storing the class of the source that triggered the creation of a new frame instance.
         */
        public class Frame {
            private final Object drawing;
            private final Class klass;
            
            public Frame(Object drawing, Class klass) {
                this.drawing = drawing;
                this.klass = klass;
            }
            
            public Object getDrawing() {
                return drawing;
            }
            
            public Class getKlass() {
                return klass;
            }
        }
        
        public RecordingManager(DrawingEditor editor) {
            this.editor = editor;
        }
        
        private DrawingEditor editor = null;
        private PlayRecordingAction playFramesAction = null;
        private StartRecordingAction startRecordingAction = null;
        private StopRecordingAction stopRecordingAction = null;
        
        // Used for storing Frame instances associated with a specific Drawing based on that Drawing's hashcode.
        private HashMap<Integer, ArrayList<Frame>> mapOfDrawingFrames = new HashMap<>();
        
        // Used for storing FigureListeners created for the purpose of recording. Stored for the purpose of later removing them again.
        private HashMap<Integer, FigureListener> mapOfFigureListeners = new HashMap<>();
        
        public HashMap<Integer, FigureListener> getMapOfFigureListeners() {
            return mapOfFigureListeners;
        }
        
        /**
         * Can be used for setting the editor if it for some reason changes after the initial setup of the application.
         */
        public void setEditor(DrawingEditor editor) {
            this.editor = editor;
        }
        
        /**
         * 
         */
        public PlayRecordingAction getPlayFramesAction() {
            if(playFramesAction == null) {
                playFramesAction = new PlayRecordingAction();
            }
            
            return playFramesAction;
        }
        
        /**
         * Lazy initializes the StartRecordingAction used to initiate the recording of a Drawing.
         */
        public StartRecordingAction getStartRecordingAction() {
            if(startRecordingAction == null) {
                startRecordingAction = new StartRecordingAction();
            }
            
            return startRecordingAction;
        }
        
        /**
         * Lazy initializes the StopRecordingAction used to stop the recording of a Drawing.
         */
        public StopRecordingAction getStopRecordingAction() {
            if(stopRecordingAction == null) {
                stopRecordingAction = new StopRecordingAction();
            }
            
            return stopRecordingAction;
        }
        
        /**
         * Creates a FigureListener and attaches it to the active Drawing.
         */
        private void startPlaybackActiveDrawing() {

            stopRecordingActiveDrawing();

            int hashcode = editor.getActiveView().getDrawing().hashCode();

            if (!mapOfDrawingFrames.containsKey(hashcode)) {
                JOptionPane.showMessageDialog(null, "No recording found for this tab, press the record button to begin recording.");
                return;
            }

            JFrame window = new JFrame("Recording Playback");
            window.setVisible(true);
            window.setSize(1100, 600);
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            window.setLocation(dim.width / 2 - window.getSize().width / 2, dim.height / 2 - window.getSize().height / 2);

            ArrayList<Frame> frames = mapOfDrawingFrames.get(hashcode);

            DefaultDrawingView drawingView = new DefaultDrawingView();

            AtomicBoolean looping = new AtomicBoolean(true);
            AtomicInteger frameIndex = new AtomicInteger();

            JPanel controls = new JPanel();
            controls.setMaximumSize(new Dimension(999, 32));

            JToggleButton btnPlaying = new JToggleButton("Pause");
            controls.add(btnPlaying);

            JSlider timeline = new JSlider(0, frames.size());
            controls.add(timeline);

            JToggleButton btnLooping = new JToggleButton("Loop");
            controls.add(btnLooping);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(drawingView);
            panel.add(controls);
            window.setContentPane(panel);

            Timer timer;
            timer = new Timer(0, (ae) -> {
                timeline.setValue(frameIndex.get());
                if (frameIndex.get() < frames.size()) {
                    Frame current = frames.get(frameIndex.getAndIncrement());
                    QuadTreeDrawing drawing = (QuadTreeDrawing) current.drawing;

                    System.out.println(frameIndex + " / " + frames.size() + " - figures: " + drawing.getChildren().size());
                    drawingView.setDrawing(drawing);

                    for (int i = 0; i < drawing.getChildCount(); i++) {
                        Figure figure = drawing.getChild(i);
                        QuadTreeDrawing qtd = ((QuadTreeDrawing) drawing);
                        qtd.repaintFigure(figure);
                    }

                } else {
                    if (looping.get()) {
                        frameIndex.set(0);
                    }
                    System.out.println("Animation done");
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

        }
        
        /**
         * Creates a FigureListener and attaches it to the active Drawing.
         */
        private void startRecordingActiveDrawing() {
            if(editor == null) {
                System.err.println("Cannot record when there is no active editor!");
                return;
            }
            
            int hashcode = editor.getActiveView().getDrawing().hashCode();
            mapOfDrawingFrames.putIfAbsent(hashcode, new ArrayList<Frame>());
            mapOfFigureListeners.putIfAbsent(hashcode, createFigureListener(hashcode));
            editor.getActiveView().getDrawing().addFigureListener(mapOfFigureListeners.get(hashcode));
        }
        
        /**
         * Removes the FigureListener from the active Drawing as well as the map where in listeners are stored, 
         * it does however not delete the frames that have been recorded though.
         */
        private void stopRecordingActiveDrawing() {
            int hashcode = editor.getActiveView().getDrawing().hashCode();
            editor.getActiveView().getDrawing().removeFigureListener(mapOfFigureListeners.get(hashcode));
            mapOfFigureListeners.remove(hashcode);
        }
        
        /**
         * Not currently used, but could be used for clearing out frames that were previously recorded.
         */
        private void clearFramesOfActiveDrawing() {
            int hashcode = editor.getActiveView().getDrawing().hashCode();
            mapOfDrawingFrames.get(hashcode).clear();
        }
        
        /**
         * Creates a FigureListener associated with a specific Drawing and stores 
         * clones of that same drawing based on a hash obtained from that Drawing's hashcode function.
         */
        private FigureListener createFigureListener(int hashcode) {
            return new FigureListener() {
                /**
                 * Because we wanna store all changes this is cleaner than pasting the same code to all handlers.
                 * What this does is it creates a new frame which is fed a clone of the active Drawing as well as the
                 * class of the figure that triggered the event.
                 */
                private void storeChange(FigureEvent e) {
                    mapOfDrawingFrames.get(hashcode).add(new Frame(
                            editor.getActiveView().getDrawing().clone(),
                            e.getSource().getClass()
                    ));
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
            };
        }
    }
}
