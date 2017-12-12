package org.jhotdraw.app.action;

import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.junit.Test;
import com.tngtech.jgiven.junit.SimpleScenarioTest;

import java.io.File;
import java.net.URISyntaxException;

import static org.jhotdraw.app.action.TinyPngCompressActionTest.getCompressedFileName;
import static org.jhotdraw.app.action.TinyPngCompressActionTest.getTestImageUrl;
import static org.jhotdraw.app.action.TinyPngCompressActionTest.isCompressedSmaller;
import static org.junit.Assert.assertTrue;

/**
 * @author Matic-ProBook
 */
public class TinyPngCompressActionJGivenTest extends SimpleScenarioTest<TinyPngCompressActionJGivenTest.Steps> {
    static class Steps {
        @ProvidedScenarioState
        File f;

        @ProvidedScenarioState
        String compressedFileName;

        @ProvidedScenarioState
        File fCompressed;

        void image() throws URISyntaxException {
            f = new File(getTestImageUrl(getClass()).getPath());
            compressedFileName = getCompressedFileName(f);
        }

        void applying_compression() {
            fCompressed = new File(compressedFileName);
            boolean compressed = TinyPngCompressAction.compressImage(f);
        }

        void having_smaller_image() {
            assertTrue(isCompressedSmaller(f, fCompressed));
        }
    }

    @Test
    public void example_scenario() throws URISyntaxException {
        given().image();
        when().applying_compression();
        then().having_smaller_image();
    }
}
