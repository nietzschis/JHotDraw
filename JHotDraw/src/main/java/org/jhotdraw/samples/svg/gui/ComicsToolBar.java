/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhotdraw.samples.svg.gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.NumberFormat;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;
import static org.jhotdraw.draw.AttributeKeys.CANVAS_HEIGHT;
import static org.jhotdraw.draw.AttributeKeys.CANVAS_WIDTH;
import static org.jhotdraw.draw.AttributeKeys.FILL_COLOR;
import static org.jhotdraw.draw.AttributeKeys.STROKE_COLOR;
import static org.jhotdraw.draw.AttributeKeys.STROKE_WIDTH;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.gui.plaf.palette.PaletteButtonUI;
import org.jhotdraw.samples.svg.figures.SVGFigure;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.jhotdraw.util.ResourceBundleUtil;
/**
 *
 * @author Karol Zdunek
 */
public class ComicsToolBar extends AbstractToolBar{
    public ComicsToolBar() {
    ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
    setName(labels.getString(getID() + ".toolbar"));
    setDisclosureStateCount(3);
    }
    @Override
    protected JComponent createDisclosedComponent(int state) {
    JPanel p = null;
    ActionListener buttonListener = new ActionListener(){
         public void actionPerformed(ActionEvent e){
             if (setCanvas()){
                clearPanel();
              newrectangle(0, 0, CANVAS_WIDTH.get(getDrawing()),CANVAS_HEIGHT.get(getDrawing()));
                 switch (e.getActionCommand()) {
                     case "Template1":
                         {
                             int pause = 10;
                             int height = (int) ((CANVAS_HEIGHT.get(getDrawing())/2)-((pause*3)/2));
                             int width = (int) ((CANVAS_WIDTH.get(getDrawing())/2)-((pause*3)/2));
                             int point_a =pause*2 + height;
                             int point_b =pause*2 + width;
                             newrectangle(pause,pause,width,height);
                             newrectangle(point_b,pause,width,height);
                             newrectangle(pause,point_a,width,height);
                             newrectangle(point_b,point_a,width,height);
                             break;
                         }
                     case "Template2":
                         {
                             int pause = 10;
                             int height = (int) (CANVAS_HEIGHT.get(getDrawing())-(pause*2));
                             int width = (int) (CANVAS_WIDTH.get(getDrawing())-(pause*2));
                             newrectangle(pause,pause,width,height);
                             break;
                         }
                     case "Template3":
                         {
                             int pause = 10;
                             int height = (int) ((CANVAS_HEIGHT.get(getDrawing())/3)-((pause*4)/3));
                             int width = (int) (CANVAS_WIDTH.get(getDrawing())-(pause*2));
                             int point_a =pause*2 + height;
                             int point_b =pause*3 + height*2;
                             newrectangle(pause,pause,width,height);
                             newrectangle(pause,point_a,width,height);
                             newrectangle(pause,point_b,width,height);
                             break;
                         }
                     case "Template4":
                         {
                             int pause = 10;
                             int height = (int) (CANVAS_HEIGHT.get(getDrawing())-(pause*2));
                             int width = (int) ((CANVAS_WIDTH.get(getDrawing())/3)-((pause*4)/3));
                             int point_a =pause*2 + width;
                             int point_b =pause*3 + width*2;
                             newrectangle(pause,pause,width,height);
                             newrectangle(point_a,pause,width,height);
                             newrectangle(point_b,pause,width,height);
                             break;
                         }
                     case "Template5":
                         {
                             int pause = 10;
                             int height = (int) ((CANVAS_HEIGHT.get(getDrawing())/3)-((pause*4)/3));
                             int width = (int) (CANVAS_WIDTH.get(getDrawing())-(pause*2));
                             int width_1 = (int) ((CANVAS_WIDTH.get(getDrawing())/2)-(pause*3)/2);
                             int point_a =pause*2 + height;
                             int point_b = pause*2 + width_1;
                             int point_c = pause*2 + height;
                             int point_d =pause*3 + height*2;
                             newrectangle(pause,pause,width,height);
                             newrectangle(pause,point_a,width_1,height);
                             newrectangle(point_b,point_c,width_1,height);
                             newrectangle(pause,point_d,width,height);
                             break;
                         }
                     case "Template6":
                         {
                             int pause = 10;
                             int height = (int) ((CANVAS_HEIGHT.get(getDrawing())/3)-((pause*4)/3));
                             int height_1 = (int) ((CANVAS_HEIGHT.get(getDrawing())-height)-((pause*3)));
                             int width = (int) (CANVAS_WIDTH.get(getDrawing())-(pause*2));
                             int width_1 = (int) ((CANVAS_WIDTH.get(getDrawing())/3)-((pause*4)/3));
                             int point_a = pause*2 + height;
                             int point_b =pause *2 + width_1;
                             int point_c = pause*3 + width_1*2;
                             newrectangle(pause,pause,width,height);
                             newrectangle(pause,point_a,width_1,height_1);
                             newrectangle(point_b,point_a,width_1,height_1);
                             newrectangle(point_c,point_a,width_1,height_1);
                             break;
                         }
                     default:
                         break;
                 }
              }
        }
    };
        switch (state) {
            case 1:
                 {
                    p = new JPanel();
                    p.setOpaque(false);
                    p.removeAll();
                    p.setBorder(new EmptyBorder(5, 5, 5, 8));
                    ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
                    GridBagLayout layout = new GridBagLayout();
                    p.setLayout(layout);
                    GridBagConstraints gbc;
                    AbstractButton btn;

                    btn = new JButton();
                    btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                    btn.setText(null);
                    labels.configureToolBarButton(btn, "comics.Template1");
                    gbc = new GridBagConstraints();
                    btn.setPreferredSize(new Dimension(32, 32));
                    gbc.insets = new Insets(0, 0, 3, 0);
                    gbc.gridy = 0;
                    gbc.gridwidth=1;
                    p.add(btn, gbc);
                    btn.setActionCommand("Template1");
                    btn.addActionListener(buttonListener);
                    
                    btn = new JButton();
                    btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                    btn.setText(null);
                    labels.configureToolBarButton(btn, "comics.Template2");
                    gbc = new GridBagConstraints();
                    btn.setPreferredSize(new Dimension(32, 32));
                    gbc.gridy = 1;
                    gbc.gridwidth=1;
                    p.add(btn, gbc);
                    btn.setActionCommand("Template2");
                    btn.addActionListener(buttonListener);
                }
                break;
            case 2:
                 {
                    p = new JPanel();
                    p.setOpaque(false);
                    p.removeAll();
                    p.setBorder(new EmptyBorder(5, 5, 5, 8));
                    ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
                    GridBagLayout layout = new GridBagLayout();
                    p.setLayout(layout);
                    GridBagConstraints gbc;
                    AbstractButton btn;
                    
                    JPanel p1 = new JPanel(new GridBagLayout());
                    JPanel p2 = new JPanel(new GridBagLayout());
                    p1.setOpaque(false);
                    p2.setOpaque(false);

                    btn = new JButton();
                    btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                    btn.setText(null);
                    labels.configureToolBarButton(btn, "comics.Template1");
                    gbc = new GridBagConstraints();
                    btn.setPreferredSize(new Dimension(32, 32));
                    gbc.insets = new Insets(0, 0, 3, 3);
                    gbc.gridwidth=1;
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.fill = GridBagConstraints.HORIZONTAL;
                    
                    p1.add(btn, gbc);
                    btn.setActionCommand("Template1");
                    btn.addActionListener(buttonListener);
                    
                    btn = new JButton();
                    btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                    btn.setText(null);
                    labels.configureToolBarButton(btn, "comics.Template3");
                    gbc = new GridBagConstraints();
                    btn.setPreferredSize(new Dimension(32, 32));
                    gbc.insets = new Insets(0, 0, 3, 3);
                    gbc.gridwidth=1;
                    gbc.gridx = 1;
                    gbc.gridy = 0;
                    gbc.fill = GridBagConstraints.HORIZONTAL;

                    p1.add(btn, gbc);
                    btn.setActionCommand("Template3");
                    btn.addActionListener(buttonListener);
                    
                    btn = new JButton();
                    btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                    btn.setText(null);
                    labels.configureToolBarButton(btn, "comics.Template5");
                    gbc = new GridBagConstraints();
                    btn.setPreferredSize(new Dimension(32, 32));
                    gbc.insets = new Insets(0, 0, 3, 0);
                    gbc.gridwidth=1;
                    gbc.gridx = 2;
                    gbc.gridy = 0;
                    gbc.fill = GridBagConstraints.HORIZONTAL;
                    p1.add(btn, gbc);
                    btn.setActionCommand("Template5");
                    btn.addActionListener(buttonListener);
                    
                    btn = new JButton();
                    btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                    btn.setText(null);
                    labels.configureToolBarButton(btn, "comics.Template2");
                    gbc = new GridBagConstraints();
                    btn.setPreferredSize(new Dimension(32, 32));
                    gbc.insets = new Insets(0, 0, 0, 3);
                    gbc.gridwidth=1;
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.fill = GridBagConstraints.HORIZONTAL;
                    
                    p2.add(btn, gbc);
                    btn.setActionCommand("Template2");
                    btn.addActionListener(buttonListener);
                    
                    btn = new JButton();
                    btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                    btn.setText(null);
                    labels.configureToolBarButton(btn, "comics.Template4");
                    gbc = new GridBagConstraints();
                    btn.setPreferredSize(new Dimension(32, 32));
                    gbc.insets = new Insets(0, 0, 0, 3);
                    gbc.gridwidth=1;
                    gbc.gridx = 1;
                    gbc.gridy = 0;
                    gbc.fill = GridBagConstraints.HORIZONTAL;
                    
                    p2.add(btn, gbc);
                    btn.setActionCommand("Template4");
                    btn.addActionListener(buttonListener);
                    
                    btn = new JButton();
                    btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                    btn.setText(null);
                    labels.configureToolBarButton(btn, "comics.Template6");
                    gbc = new GridBagConstraints();
                    btn.setPreferredSize(new Dimension(32, 32));
                    gbc.gridwidth=1;
                    gbc.gridx = 2;
                    gbc.gridy = 0;
                    gbc.fill = GridBagConstraints.HORIZONTAL;

                    p2.add(btn, gbc);
                    btn.setActionCommand("Template6");
                    btn.addActionListener(buttonListener);
                    gbc = new GridBagConstraints();                    
                    gbc.gridy = 0;
                    gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                    p.add(p1, gbc);
                    gbc.gridy = 1;
                    gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                    p.add(p2, gbc);
                }
                break;
        }
        return p;
    }
        public boolean setCanvas(){
        Boolean result = true;
        String[] options = { "New Canvas", "Use old Canvas",
                "Cancel" };
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        JLabel W = new JLabel("W:");
        JLabel H = new JLabel("H:");
        panel.add(new JLabel("Enter new size of canvas: "));
        NumberFormat format = NumberFormat.getInstance();
        format.setGroupingUsed(false);
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
     
        JFormattedTextField width = new JFormattedTextField(formatter);
        JFormattedTextField height = new JFormattedTextField(formatter); 
        width.setPreferredSize(new Dimension(80,24));
        height.setPreferredSize(new Dimension(80,24));
        width.addFocusListener(new FocusListener() {
       
        @Override
        public void focusGained(FocusEvent e) {
                width.setText("");
                JFormattedTextField width = new JFormattedTextField(formatter); 
                
        }
        
        @Override
        public void focusLost(java.awt.event.FocusEvent e) {
           JFormattedTextField width = new JFormattedTextField(formatter); 
           }
        });
       
        height.addFocusListener(new FocusListener() {
        
        @Override
        public void focusGained(FocusEvent e) {
            height.setText("");
            JFormattedTextField height = new JFormattedTextField(formatter); 
       
        }
        @Override
        public void focusLost(FocusEvent e) {
            JFormattedTextField width = new JFormattedTextField(formatter); 
        }
        }); 
        
        panel.add(W);
        panel.add(width);
        panel.add(H);
        panel.add(height);
        
        int option =JOptionPane.showOptionDialog(frame,panel,"Size of Canvas",JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE,null,options,options[0]);

        switch (option){
            case 0:
                if (width.getText().isEmpty() == false && height.getText().isEmpty() == false){
                    double x = Double.parseDouble(width.getText());
                    double y = Double.parseDouble(height.getText());
                    CANVAS_WIDTH.set(getDrawing(),x);
                    CANVAS_HEIGHT.set(getDrawing(),y);
                    result = true;
                }
                 else if (width.getText().isEmpty() == true && height.getText().isEmpty() == true){
                    result = false;
                    setCanvas();
                     
                 }
                 
                else if (width.getText().isEmpty() == true || height.getText().isEmpty() == true){
                    if (width.getText().isEmpty() == false){
                        double x = Double.parseDouble(width.getText());
                        double y = x;
                        CANVAS_WIDTH.set(getDrawing(),x);
                        CANVAS_HEIGHT.set(getDrawing(),y);
                    }
                    else if (height.getText().isEmpty() == false){
                        double y = Double.parseDouble(height.getText());
                        double x = y;
                        CANVAS_WIDTH.set(getDrawing(),x);
                        CANVAS_HEIGHT.set(getDrawing(),y);
                }
                result = true;
                }
                break;
            case 1:
                if((CANVAS_WIDTH.get(getDrawing()) == null) || (CANVAS_HEIGHT.get(getDrawing()) == null)){
                    result = false;
                    setCanvas();
                   }
                break;
           default:
                result = false;
                break;
        }
       return result;
    }
        
     private SVGFigure newrectangle(int x, int y, double w, double h){
        SVGFigure r = new SVGRectFigure(x, y, w, h);
        getDrawing().add(r);
        FILL_COLOR.set(r, Color.white);
        STROKE_COLOR.set(r, Color.black);
        STROKE_WIDTH.set(r, 2.0);
        return r;
    }
     
    private Drawing getDrawing(){
        return getEditor().getActiveView().getDrawing();
    }   
    
    public void clearPanel(){
        getDrawing().removeAllChildren();
    }   
    @Override
     protected String getID(){
        return "comics";
    }
    
}

