/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.junit.Test;
import com.tngtech.jgiven.junit.SimpleScenarioTest;
import java.io.File;
import static org.jhotdraw.app.action.TinyPngCompressActionTest.getCompressedFileName;
import static org.jhotdraw.app.action.TinyPngCompressActionTest.getTestImageUrl;
import static org.jhotdraw.app.action.TinyPngCompressActionTest.isCompressedSmaller;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Matic-ProBook
 */
public class TinyPngCompressActionJGivenTest extends SimpleScenarioTest<TinyPngCompressActionJGivenTest.Steps> {
    
    @Test
    public void example_scenario() {
        given().image();
        when().applying_compression();
        then().having_smaller_image();
    }

    public static class Steps {

        @ProvidedScenarioState
        File f;
        
        @ProvidedScenarioState
        String compressedFileName;
        
        @ProvidedScenarioState
        File fCompressed;

        public void image() {
            f = new File(getTestImageUrl(getClass()).getPath());
            compressedFileName = getCompressedFileName(f);
        }

        public void applying_compression() {
            fCompressed = new File(compressedFileName);
            boolean compressed = TinyPngCompressAction.compressImage(f);
        }

        public void having_smaller_image() {
            assertTrue(isCompressedSmaller(f, fCompressed));
        }
    }
    
}
