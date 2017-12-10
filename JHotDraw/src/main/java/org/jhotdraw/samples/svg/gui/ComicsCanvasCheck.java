package org.jhotdraw.samples.svg.gui;

import java.awt.Dimension;
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
    private JLabel widthLabel,heightLabel;
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
    * @return 
    */
    public boolean setCanvas(Drawing d) {
        NumberFormat format;
   
        format = NumberFormat.getInstance();
        format.setGroupingUsed(false);
        formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        
        heightField = createField(heightField,height);
        widthField = createField(widthField,width);
        
        widthLabel = createLabel(widthLabel,width);
        heightLabel = createLabel(heightLabel,height);

        JPanel panel = new JPanel();
        
        panel.add(new JLabel(labels.getString("comics.canvasSize.text")));
        panel.add(widthLabel);
        panel.add(widthField);
        panel.add(heightLabel);
        panel.add(heightField);
        
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /**
        *Option pane with given options as buttons.
        */
        String[] options = {"Cancel", "Use old Canvas", "New Canvas"};
        int option = JOptionPane.showOptionDialog(frame,panel,labels.getString("comics.optionDialog.text"),JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE,null,options,options[2]);
        
        switch (option){
           case 2:
                if (!widthField.getText().isEmpty() && !heightField.getText().isEmpty()) {
                    double x = Double.parseDouble(widthField.getText());
                    double y = Double.parseDouble(heightField.getText());
                    CANVAS_WIDTH.set(d,x);
                    CANVAS_HEIGHT.set(d,y);
                    result = true;
                } else if (widthField.getText().isEmpty() && heightField.getText().isEmpty()) {
                    result = false;
                    setCanvas(d);
                } else if (widthField.getText().isEmpty() || heightField.getText().isEmpty()) {
                    if (widthField.getText().isEmpty()){
                        double y = Double.parseDouble(heightField.getText());
                        double x = y;
                        CANVAS_WIDTH.set(d,x);
                        CANVAS_HEIGHT.set(d,y);
                    } else if (heightField.getText().isEmpty()) {
                        double x = Double.parseDouble(widthField.getText());
                        double y = x;
                        CANVAS_WIDTH.set(d,x);
                        CANVAS_HEIGHT.set(d,y);
                    }
                    result = true;
                    }
                result = true;
                break;
            case 1:
                if((CANVAS_WIDTH.get(d) == null) || (CANVAS_HEIGHT.get(d) == null)) {
                    result = false;
                    setCanvas(d);
                    
                }else result = true;
                break;
            default:
                result = false;
                break;
        }
        return result;
    }
    
    public boolean getResult() {
        return result;
    }
    
    private JLabel createLabel (JLabel newLabel,String s) {
        newLabel = new JLabel();
        newLabel.setUI((LabelUI) PaletteLabelUI.createUI(newLabel));
        newLabel.setToolTipText(labels.getString("comics."+s+".toolTipText"));
        newLabel.setText(labels.getString("comics."+s+".text"));

        return newLabel;
    }
     
    private JFormattedTextField createField(JFormattedTextField newField,String s) {
        newField = new JFormattedTextField(formatter);
        newField.setText("600");
        newField.setToolTipText(labels.getString("comics."+s+".toolTipText"));
        newField.setPreferredSize(new Dimension(80, 24));
        newField.addFocusListener(new ClearFields());
        
        return newField;
    }
     
    private class ClearFields implements FocusListener {
    @Override
        public void focusGained(FocusEvent e) {
            if(e.getSource().equals(widthField)){
                widthField.setText("");
                JFormattedTextField widthField = null;
                widthField = createField(widthField, width);
            } else if (e.getSource().equals(heightField)){
                heightField.setText("");
                JFormattedTextField heightField = null;
                heightField = createField(heightField,height);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if(e.getSource().equals(widthField)){
                JFormattedTextField widthField = null;
                widthField = createField(widthField, width);
            } else if (e.getSource().equals(heightField)){
                JFormattedTextField heightField = null;
                heightField = createField(heightField,height);
            }
        }
    }
}