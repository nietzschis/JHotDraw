/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action.watermark;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import static org.assertj.core.api.Assertions.*;

/**
 *
 * @author Mads
 */
public class WhenIHaveImportedWatermarkAndExported extends Stage<WhenIHaveImportedWatermarkAndExported>{
    
    @ExpectedScenarioState
    boolean drawingNotEmpty;
    
    @ProvidedScenarioState
    boolean watermarkImported;
    
    @ProvidedScenarioState
    boolean sourceFileExists;
    
    public WhenIHaveImportedWatermarkAndExported I_Have_Imported_Watermark(){
        watermarkImported = true;
        return self();
    }
    
    public WhenIHaveImportedWatermarkAndExported I_Have_Exported_The_Drawing(){
        assertThat(drawingNotEmpty).isTrue();
        assertThat(watermarkImported).isTrue();
        sourceFileExists = true;
        return self();
    }
    
}
