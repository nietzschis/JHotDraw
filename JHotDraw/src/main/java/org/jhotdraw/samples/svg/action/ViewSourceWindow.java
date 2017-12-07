/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.action;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import org.jhotdraw.samples.svg.SVGView;
import org.jhotdraw.samples.svg.io.SVGOutputFormat;

/**
 * Window for viewing source - Refactored for easier testing
 *
 * @author Marc
 */
public class ViewSourceWindow {

    private final static String DIALOG_CLIENT_PROPERTY = "view.viewSource.dialog";

    private final SVGView p;
    private final JDialog dialog;
    private final JTextArea ta;
    private String source;

    public ViewSourceWindow(SVGView p) throws IOException {
        this.p = p;

        this.source = generateSource(p);

        if (p.getClientProperty(DIALOG_CLIENT_PROPERTY) == null) {
            this.ta = new JTextArea(source);
            this.dialog = createNewDialog(p, ta);
        } else {
            this.dialog = (JDialog) p.getClientProperty(DIALOG_CLIENT_PROPERTY);
            this.ta = (JTextArea) ((JScrollPane) dialog.getContentPane().getComponent(0)).getViewport().getView();
            this.ta.setText(source);
        }

        this.dialog.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent evt) {
                try {
                    saveChanges();
                } catch(IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    
    private void saveChanges() throws IOException{
        
    }

    private String generateSource(SVGView p) throws IOException {
        SVGOutputFormat format = new SVGOutputFormat();
        format.setPrettyPrint(true);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();

        format.write(buf, p.getDrawing());
        String source = buf.toString("UTF-8");

        return source;
    }

    private JDialog createNewDialog(SVGView p, JTextArea ta) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(p.getComponent()));
        p.putClientProperty(DIALOG_CLIENT_PROPERTY, dialog);
        dialog.setTitle(p.getTitle());
        dialog.setResizable(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        ta.setWrapStyleWord(true);
        ta.setLineWrap(true);
        JScrollPane sp = new JScrollPane(ta);
        //sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        dialog.getContentPane().add(sp);
        dialog.setSize(400, 400);
        dialog.setLocationByPlatform(true);

        return dialog;
    }

    private JDialog getExistingDialog(SVGView p) {
        return (JDialog) p.getClientProperty(DIALOG_CLIENT_PROPERTY);
    }

    public JDialog getDialog() {
        return dialog;
    }

    public JTextArea getTextArea() {
        return ta;
    }

    public String getSource() {
        return source;
    }
}
