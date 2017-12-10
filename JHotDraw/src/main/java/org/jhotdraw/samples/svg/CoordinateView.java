package org.jhotdraw.samples.svg;

import org.jhotdraw.samples.svg.figures.*;
import org.jhotdraw.draw.*;
import java.awt.*;
import java.text.NumberFormat;
import javax.swing.*;
import javax.swing.text.NumberFormatter;
import static org.jhotdraw.draw.AttributeKeys.*;

/**
 * Class for representing the presentation of a coordinate system
 * Used by CoordinateTool.java
 * @author Henrik Bastholm
 */
public class CoordinateView {
    private JFrame outerFrame;
    private JPanel outerPanel;
    private JFormattedTextField heightTextField, widthTextField, intervalsTextField;
    private JLabel heightLabel, widthLabel, intervalsLabel;
    private int wMod, hMod;
    private double strokeSize,lineSize;
    
    public CoordinateView() {
    }
    
    /**
     * Creates a checkbox with the swing library.
     * Initiates the frame, panel, label and textfields.
     * Will return an integer depending on which button the user presses.
     * Is used by CoordinateTool.java
     */
    protected int checkBox() {
        outerFrame = new JFrame();
        outerPanel = new JPanel();
        widthLabel = new JLabel("Choose canvas size - W: ");
        heightLabel = new JLabel(" H: ");
        intervalsLabel = new JLabel("Number of intervals: ");
        widthTextField = createTextField(widthTextField, 10);
        heightTextField = createTextField(heightTextField, 10);
        intervalsTextField = createTextField(intervalsTextField, 5);
        intervalsTextField.setValue(6);
        
        outerPanel.add(widthLabel);
        outerPanel.add(widthTextField);
        outerPanel.add(heightLabel);
        outerPanel.add(heightTextField);
        outerPanel.add(intervalsLabel);
        outerPanel.add(intervalsTextField);
        
        String[] options = {"Insert","Cancel"};
        String introMessage = "Pick parameters for the coordinate system";
        // Creates the layout for the frame.
        int buttonOptions = JOptionPane.showOptionDialog(outerFrame,outerPanel,introMessage,JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
        return buttonOptions;
    }
    
    /**
     * Returns a JFormattedTextField which only accepts integers.
     * Can be used as input from user as an example to adjust the canvas size.
     */
    private JFormattedTextField createTextField(JFormattedTextField toBeCreated, int size) {
        // Numberformat will make sure that the textfield only accepts integers as input.
        NumberFormat noFormat = NumberFormat.getInstance();
        NumberFormatter noFormatter = new NumberFormatter(noFormat);
        noFormatter.setValueClass(Integer.class);
        noFormatter.setMinimum(0);
        noFormatter.setMaximum(Integer.MAX_VALUE);
        noFormatter.setAllowsInvalid(false);
        // Creates a new textfield of the given size.
        toBeCreated = new JFormattedTextField(noFormatter);
        toBeCreated.setColumns(size);
        return toBeCreated;
    }
    
    /**
     * Inserts figures to simulate a coordinate system to the drawing d.
     * Will adjust the height and width of canvas first.
     */
    protected void insertACoordinateSystem(Drawing d) {
        adjustCanvas(d);
        int halfWidth = (int) (CANVAS_WIDTH.get(d) / 2);
        int halfHeight = (int) (CANVAS_HEIGHT.get(d) / 2);
        wMod = (int) (CANVAS_WIDTH.get(d) / 500);
        hMod = (int) (CANVAS_HEIGHT.get(d) / 500);
        strokeSize = Math.min(wMod, hMod)*0.1 + 0.1;
        
        d.add(createQuadrant(0, 0, halfWidth, halfHeight));
        d.add(createQuadrant(halfWidth, 0, halfWidth, halfHeight));
        d.add(createQuadrant(0, halfHeight, halfWidth, halfHeight));
        d.add(createQuadrant(halfWidth, halfHeight, halfWidth, halfHeight));
        insertIntervals(d, halfWidth*2, halfHeight*2);
    }
    
    /**
     * Will adjust the canvas size according to what is entered to the checkbox.
     * If nothing is entered, the canvas size will adjust to the computers screen resolution.
     */
    private void adjustCanvas(Drawing d) {
        boolean widthIsEmpty = widthTextField.getText().isEmpty();
        boolean heightIsEmpty = heightTextField.getText().isEmpty();
        Double widthValue, heightValue;
        
        if (!widthIsEmpty && !heightIsEmpty) {
            widthValue = (double) ((int) widthTextField.getValue());
            heightValue = (double) ((int) heightTextField.getValue());
        }
        else if (widthIsEmpty && !heightIsEmpty) {
            heightValue = (double) ((int) heightTextField.getValue());
            widthValue = heightValue;
        }
        else if (!widthIsEmpty && heightIsEmpty) {
            widthValue = (double) ((int) widthTextField.getValue());
            heightValue = widthValue;
        }
        else {
            Dimension screenResolution = Toolkit.getDefaultToolkit().getScreenSize();
            widthValue = screenResolution.getWidth();
            heightValue = screenResolution.getHeight();
        }
        CANVAS_WIDTH.set(d, widthValue);
        CANVAS_HEIGHT.set(d, heightValue);
    }
    
    /**
     * Will create a square with the given parameters.
     * The square is transparent (has color null) and will only be visible by the stroke.
     * It returns this square figure.
     */
    private SVGFigure createQuadrant(int x, int y, double w, double h) {
        SVGFigure quadrant = new SVGRectFigure(x, y, w, h);
        FILL_COLOR.set(quadrant, null);
        STROKE_WIDTH.set(quadrant, strokeSize);
        STROKE_COLOR.set(quadrant, Color.black);
        return quadrant;
    }
    
    /**
     * Will insert interval lines according to the amount that is typed in intervalsTextField.
     * As a default the amount of intervals is 6.
     */
    private void insertIntervals(Drawing d, int width, int height) {
        int amountOfIntervals = (int) intervalsTextField.getValue();
        lineSize = Math.max(CANVAS_WIDTH.get(d), CANVAS_HEIGHT.get(d)) / 50;
        
        double spaceBetween = (width / 2) / (amountOfIntervals + 1);
        int middle = (int) ((height / 2) - (lineSize / 2));
        createIntervalLines(d, amountOfIntervals, 0, spaceBetween, middle, true);
        createIntervalLines(d, amountOfIntervals, width/2, spaceBetween, middle, true);
        
        spaceBetween = (height / 2) / (amountOfIntervals + 1);
        middle = (int) ((width / 2) - (lineSize / 2));
        createIntervalLines(d, amountOfIntervals, 0, spaceBetween, middle, false);
        createIntervalLines(d, amountOfIntervals, height/2, spaceBetween, middle, false);
    }
    
    /**
     * Will create the interval lines for insertIntervals(d, w, h) to use.
     */
    private void createIntervalLines(Drawing d, int intervals, double start, double spaceBetween, int middle, boolean isHorisontal) {
        start += spaceBetween;
        while (intervals > 0) {
            SVGFigure line;
            if (isHorisontal) {
                line = new SVGRectFigure(start, middle, 0, lineSize);
            }
            else {
                line = new SVGRectFigure(middle, start, lineSize, 0);
            }
            d.add(line);
            FILL_COLOR.set(line, null);
            STROKE_WIDTH.set(line, strokeSize);
            STROKE_COLOR.set(line, Color.black);
            start += spaceBetween;
            intervals--;
        }
    }
}
