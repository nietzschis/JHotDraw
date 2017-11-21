/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhotdraw.samples.svg.gui;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.jhotdraw.gui.plaf.palette.PaletteButtonUI;
import org.jhotdraw.util.ResourceBundleUtil;
/**
 *
 * @author Karol
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
                    
                    btn = new JButton();
                    btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
                    btn.setText(null);
                    labels.configureToolBarButton(btn, "comics.Template2");
                    gbc = new GridBagConstraints();
                    btn.setPreferredSize(new Dimension(32, 32));
                    gbc.gridy = 1;
                    gbc.gridwidth=1;
                    p.add(btn, gbc);
                }
                break;
            case 2:
                 {
                    p = new JPanel();
                    p.setOpaque(false);

                    JPanel p1 = new JPanel(new GridBagLayout());
                    JPanel p2 = new JPanel(new GridBagLayout());
                    p1.setOpaque(false);
                    p2.setOpaque(false);

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
                    gbc.insets = new Insets(0, 0, 3, 3);
                    gbc.gridwidth=1;
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.fill = GridBagConstraints.HORIZONTAL;
                    p1.add(btn, gbc);
                    
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
    
    protected String getID(){
        return "comics";
    }
}

