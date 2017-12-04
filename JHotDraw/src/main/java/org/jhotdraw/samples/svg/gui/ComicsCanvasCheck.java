/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.gui;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
 * @author Karol
 */
public class ComicsCanvasCheck {
    public static boolean result;
    public boolean setCanvas(Drawing d){
        JLabel widthLabel,heightLabel;
        JFrame frame = new JFrame();  
        JPanel panel = new JPanel();
        FocusListener clearFields;
        KeyListener fastCreate;
        JFormattedTextField width,height;
        
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        NumberFormat format = NumberFormat.getInstance();
        format.setGroupingUsed(false);
        //formatater to receive only numbers to textFields
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        //width JFormattedTextField
        width = new JFormattedTextField(formatter);
        width.setText("600");
        width.setToolTipText(labels.getString("attribute.canvasWidth.toolTipText"));
        width.setPreferredSize(new Dimension(80,24));

        //height JFormattedTextField
        height = new JFormattedTextField(formatter);
        height.setText("600");
        height.setToolTipText(labels.getString("attribute.canvasHeight.toolTipText"));
        height.setPreferredSize(new Dimension(80,24));

        //Labels with width and height
        widthLabel = new javax.swing.JLabel();
        heightLabel = new javax.swing.JLabel();
        widthLabel.setUI((LabelUI) PaletteLabelUI.createUI(widthLabel));
        widthLabel.setToolTipText(labels.getString("attribute.canvasWidth.toolTipText"));
        widthLabel.setText(labels.getString("attribute.canvasWidth.text"));

        heightLabel.setUI((LabelUI) PaletteLabelUI.createUI(heightLabel));
        heightLabel.setToolTipText(labels.getString("attribute.canvasHeight.toolTipText"));
        heightLabel.setText(labels.getString("attribute.canvasHeight.text")); // NOI18N

        //button options in JOptionPane
        String[] options = { "New Canvas", "Use old Canvas",
                "Cancel" };
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        //FocusListener for JFormattedTextFields
        clearFields = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(e.getSource().equals(width)){
                    width.setText("");
                    JFormattedTextField width = new JFormattedTextField(formatter); 
                }
                else if (e.getSource().equals(height)){
                    height.setText("");
                    JFormattedTextField height = new JFormattedTextField(formatter); 
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(e.getSource().equals(width)){
                    JFormattedTextField width = new JFormattedTextField(formatter); 
                }
                else if (e.getSource().equals(height)){
                    JFormattedTextField height = new JFormattedTextField(formatter); 
                }
            }
            
        };
        
        width.addFocusListener(clearFields);
        height.addFocusListener(clearFields);
        
       
        //add components to panel
        //JLabel with "Enter size of Canvas"
        panel.add(new JLabel(labels.getString("comics.canvasSize.text")));
        panel.add(widthLabel);
        panel.add(width);
        panel.add(heightLabel);
        panel.add(height);

        //optionPane
        int option = JOptionPane.showOptionDialog(frame,panel,labels.getString("comics.optionDialog.text"),JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
        //swtich for optionPane
        switch (option){
            case 0:
                if (width.getText().isEmpty() == false && height.getText().isEmpty() == false){
                    double x = Double.parseDouble(width.getText());
                    double y = Double.parseDouble(height.getText());
                    CANVAS_WIDTH.set(d,x);
                    CANVAS_HEIGHT.set(d,y);
                    result = true;
                }
                else if (width.getText().isEmpty() == true && height.getText().isEmpty() == true){
                    result = false;
                    setCanvas(d);
                }

                else if (width.getText().isEmpty() == true || height.getText().isEmpty() == true){
                    if (width.getText().isEmpty() == false){
                        double x = Double.parseDouble(width.getText());
                        double y = x;
                        CANVAS_WIDTH.set(d,x);
                        CANVAS_HEIGHT.set(d,y);
                    }
                    
                    else if (height.getText().isEmpty() == false){
                        double y = Double.parseDouble(height.getText());
                        double x = y;
                        CANVAS_WIDTH.set(d,x);
                        CANVAS_HEIGHT.set(d,y);
                    }
                    
                    result = true;
                }
                break;
            case 1:
                if((CANVAS_WIDTH.get(d) == null) || (CANVAS_HEIGHT.get(d) == null)){
                    result = false;
                    setCanvas(d);
                }
                break;
           default:
                result = false;
                break;
        }
    return result;
    }
    
}
