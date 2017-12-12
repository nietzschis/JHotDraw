/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action.watermark;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.junit.Test;

/**
 *
 * @author Mads
 */
public class WatermarkJGivenTest extends ScenarioTest<GivenIHaveAFinishedDrawing, WhenIHaveImportedWatermarkAndExported, ThenTheWatermarkedFileHasBeenCreated>{
    
    @Test
    public void watermarkScenarioTest(){
        given().I_Have_A_Finished_Drawing();
        when().I_Have_Imported_Watermark().
                and().I_Have_Exported_The_Drawing();
        then().The_Watermarked_File_Has_Been_Created();
    }
        
}
