package org.jhotdraw.draw.action;

import java.io.File;
import java.net.URL;
import org.jhotdraw.app.action.TinyPngCompressAction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Matic-ProBook
 */
public class TinyPngCompressActionTest {
    
    public TinyPngCompressActionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of compressImage method, of class TinyPngCompressAction.
     */
    @Ignore
    @Test
    public void testCompressImage() {
        URL url = TinyPngCompressActionTest.class.getResource("images/tree.png");
        File f = new File(url.getPath());
        String compressedFileName = f.getPath().substring(0, f.getPath().lastIndexOf('.')) + "_compressed.png";
        File fCompressed = new File(compressedFileName);
        boolean compressed = TinyPngCompressAction.compressImage(f);
        assertTrue(isCompressedSmaller(f, fCompressed));
    }
    
    public boolean isCompressedSmaller(File original, File compressed) {
        if(compressed.length() < original.length())
            return true;
        return false;
    }

}
