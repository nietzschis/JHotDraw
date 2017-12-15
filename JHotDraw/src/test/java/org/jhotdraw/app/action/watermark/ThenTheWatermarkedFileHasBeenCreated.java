/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action.watermark;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import static org.assertj.core.api.Assertions.*;

/**
 *
 * @author Mads
 */
public class ThenTheWatermarkedFileHasBeenCreated extends Stage<ThenTheWatermarkedFileHasBeenCreated>{
    
    @ExpectedScenarioState
    boolean sourceFileExists;
    
    public ThenTheWatermarkedFileHasBeenCreated The_Watermarked_File_Has_Been_Created(){
        assertThat(sourceFileExists).isTrue();
        return self();
    }   
}
