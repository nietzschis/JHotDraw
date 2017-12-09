package org.jhotdraw.samples.svg.gui;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.gui.plaf.palette.PaletteButtonUI;
import org.jhotdraw.util.ResourceBundleUtil;
/**
 *
 * @author Karol Zdunek
 */

public class ComicsToolBar extends AbstractToolBar {
    private final ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");

    public ComicsToolBar() {
        setName(labels.getString(getID() + ".toolbar"));
        setDisclosureStateCount(3);
    }
    
    /**
    * Creating the panel with JButtons which appears on bottom of screen.
    */
    @Override
    protected JComponent createDisclosedComponent(int state) {
        JPanel p = null;
        //initialize new panel on bottom of screen
        switch (state) {
            //case 1: showing two buttons
            case 1:{   
                JButton btn;
                GridBagConstraints gbc;
                GridBagLayout layout = new GridBagLayout();

                p = new JPanel();
                p.setOpaque(false);
                p.removeAll();
                p.setBorder(new EmptyBorder(5, 5, 5, 5));
                p.setLayout(layout);

                //1st button
                gbc = new GridBagConstraints();
                gbc.insets = new Insets(0, 0, 3, 0);
                btn = createTemplateButton(0, 0, 1, gbc, getDrawing());
                p.add(btn,gbc);

                // 2nd button
                gbc = new GridBagConstraints();
                btn = createTemplateButton(0, 1, 2, gbc, getDrawing());
                p.add(btn, gbc);
            }
            break;
                //case 2: show 6 buttons
            case 2:{
                JButton btn;
                GridBagConstraints gbc;
                GridBagLayout layout = new GridBagLayout();
                gbc = new GridBagConstraints();
               
                p = new JPanel();
                p.setOpaque(false);
                p.removeAll();
                p.setBorder(new EmptyBorder(5, 5, 5, 5));
                p.setLayout(layout);

                JPanel p1 = new JPanel(new GridBagLayout());
                p1.setOpaque(false);

                //1st button upper panel
                gbc.insets = new Insets(0, 0, 3, 3);
                btn = createTemplateButton(0, 0, 1, gbc, getDrawing());
                p1.add(btn, gbc);

                //2nd button upper panel
                gbc.insets = new Insets(0, 0, 3, 3);
                btn = createTemplateButton(1, 0, 3, gbc, getDrawing());
                p1.add(btn, gbc);

                //3th button upper panel
                gbc.insets = new Insets(0, 0, 3, 3);
                btn = createTemplateButton(2, 0, 5, gbc, getDrawing());
                p1.add(btn, gbc);

                //1st button lower panel
                gbc.insets = new Insets(0, 0, 0, 3);
                btn = createTemplateButton(0, 1, 2, gbc, getDrawing());
                p1.add(btn, gbc);

                //2nd button lower panel
                gbc.insets = new Insets(0, 0, 0, 3);
                btn = createTemplateButton(1, 1, 4, gbc, getDrawing());
                p1.add(btn, gbc);

                //3th button lower panel
                 gbc.insets = new Insets(0, 0, 0, 3);
                btn = createTemplateButton(2, 1, 6, gbc, getDrawing());
                p1.add(btn, gbc);

                p.add(p1, gbc);
            }
            break;
        }
        return p;
    }
    
    private Drawing getDrawing() {
        return getEditor().getActiveView().getDrawing();
    } 
   
    private JButton createTemplateButton (int gridx, int gridy, int templateNumber, GridBagConstraints gbc, Drawing d) {
        JButton btn = new JButton();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        labels.configureToolBarButton(btn, "comics.Template"+templateNumber);
        btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
        btn.setPreferredSize(new Dimension(32, 32));
        btn.addActionListener(e ->{
            //ComicsCanvasCheck.getInstance().setCanvas();
            ComicsCanvasCheck.getInstance().getOption(d);
                if (ComicsCanvasCheck.getInstance().getResult()){
                   TemplateFactory.createTemplate("temp"+templateNumber).apply(d);
                }
            } 
        );
        return btn;
    }
    
    @Override
    protected String getID() {
        return "comics";
    }
}