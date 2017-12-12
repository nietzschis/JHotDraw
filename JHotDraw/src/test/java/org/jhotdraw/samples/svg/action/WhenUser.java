/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.action;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jhotdraw.samples.svg.SVGView;

/**
 *
 * @author Marc
 */
public class WhenUser extends Stage<WhenUser> {
    @ExpectedScenarioState
    SVGView view;
    
    @ProvidedScenarioState
    ViewSourceWindow viewSourceWindow;
    
    public WhenUser the_user_opens_the_view_source_window() {
        try {
            viewSourceWindow = new ViewSourceWindow(view);
        } catch (IOException ex) {
            
        }
        return self();
    }
    
    public WhenUser the_user_inserts_xml_in_the_source_window_to_add_a_rectangle() {
        viewSourceWindow.getTextArea().setText("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                                "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" version=\"1.2\" baseProfile=\"tiny\">\n" +
                                                "    <defs/>\n" +
                                                "    <rect x=\"708\" y=\"408\" width=\"303\" height=\"230\" fill=\"#fff\" stroke=\"#000\"/>\n" +
                                                "</svg>");
        return self();
    }
    
    public WhenUser the_user_closes_the_view_source_window() {
        viewSourceWindow.getDialog().dispatchEvent(new WindowEvent(viewSourceWindow.getDialog(), WindowEvent.WINDOW_CLOSING));
        return self();
    }
}
