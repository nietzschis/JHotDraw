package org.jhotdraw.samples.svg.gui;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.NumberFormat;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.plaf.LabelUI;
import javax.swing.text.NumberFormatter;
import static org.jhotdraw.draw.AttributeKeys.CANVAS_HEIGHT;
import static org.jhotdraw.draw.AttributeKeys.CANVAS_WIDTH;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.gui.plaf.palette.PaletteLabelUI;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author Karol Zdunek
 */
public class ComicsCanvasCheck {
    
    private static final ComicsCanvasCheck instance = new ComicsCanvasCheck();
    private static final String width = "width", height = "height";
    
    private boolean result;
    private NumberFormatter formatter;
    private JFormattedTextField heightField,widthField;
   
    private final ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");

    private ComicsCanvasCheck() {}
    
    public static ComicsCanvasCheck getInstance() {
       return instance;
    }
    
   /**
    * Creating new option pane with given fields and labes, that user
    * can choose to adjust new size of canvas or use the old. 
    */
    
    public void setCanvas(Drawing d) {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        String[] options = {"Cancel", "Use old Canvas", "New Canvas"};
        /**
        *Option pane with given options as buttons.
        */
        while (true) {
            int option = JOptionPane.showOptionDialog(frame,adjustPanel(),labels.getString("comics.optionDialog.text"),JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE,null,options,options[2]);
            
            boolean emptyWidth = widthField.getText().isEmpty();
            boolean emptyHeight = heightField.getText().isEmpty();
            
            if (option == 0) {
                result = false;
                break;
            } else if (option == 1) {
                if ((CANVAS_WIDTH.get(d) != null) && (CANVAS_HEIGHT.get(d) != null)) {
                    result = true;
                    break;
                }
                result = false;
            } else if (option == 2) {
                double x = getWidth();
                double y = getHeight();
                if (emptyWidth && emptyHeight) {
                    x = gd.getDisplayMode().getWidth();
                    y = gd.getDisplayMode().getHeight();
                }
                else if (emptyWidth) {
                    x = y;
                }
                else if (emptyHeight) {
                    y = x;
                }
                 
                CANVAS_WIDTH.set(d,x);
                CANVAS_HEIGHT.set(d,y);
                result = true;
                break;
            } else {
                result = false;
                break;
            }
        }
    }
    public boolean getResult() {
        return  result;
    }
    private double getHeight() {
        return heightField.getText().isEmpty()?-1:Double.parseDouble(heightField.getText());
    }
   
    private double getWidth() {
        return widthField.getText().isEmpty()?-1:Double.parseDouble(widthField.getText());
    }
    
    private JPanel adjustPanel() {
        NumberFormat format =  NumberFormat.getInstance();
        format.setGroupingUsed(false);
        formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        
        heightField = createField(height);
        widthField = createField(width);
        
        JLabel widthLabel = createLabel(width);
        JLabel heightLabel = createLabel(height);

        JPanel panel = new JPanel();
        
        panel.add(new JLabel(labels.getString("comics.canvasSize.text")));
        panel.add(widthLabel);
        panel.add(widthField);
        panel.add(heightLabel);
        panel.add(heightField);
        
        return panel;
    }
    
    private JLabel createLabel (String s) {
        JLabel newLabel = new JLabel();
        newLabel.setUI((LabelUI) PaletteLabelUI.createUI(newLabel));
        newLabel.setToolTipText(labels.getString("comics."+s+".toolTipText"));
        newLabel.setText(labels.getString("comics."+s+".text"));

        return newLabel;
    }
     
    private JFormattedTextField createField(String s) {
        JFormattedTextField newField = new JFormattedTextField(formatter);
        newField.setText("600");
        newField.setToolTipText(labels.getString("comics."+s+".toolTipText"));
        newField.setPreferredSize(new Dimension(80, 24));
        newField.addFocusListener(new ClearFields());
        
        return newField;
    }
     
    private class ClearFields implements FocusListener {
        @Override
        public void focusGained(FocusEvent e) {
            ((JFormattedTextField)e.getSource()).setText("");
            if (e.getSource().equals(widthField)) {
                createField(width);
            } else if (e.getSource().equals(heightField)) {
                createField(height);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (e.getSource().equals(widthField)) {
                createField(width);
            } else if (e.getSource().equals(heightField)) {
                createField(height);
            }
        }
    }
}