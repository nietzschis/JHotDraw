/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action.watermark;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;


/**
 *
 * @author Mads
 */
public class GivenIHaveAFinishedDrawing extends Stage<GivenIHaveAFinishedDrawing>{
    
    @ProvidedScenarioState
    boolean drawingNotEmpty;
    
    public GivenIHaveAFinishedDrawing I_Have_A_Finished_Drawing(){
        drawingNotEmpty = true;
        return self();
    }
    
}
