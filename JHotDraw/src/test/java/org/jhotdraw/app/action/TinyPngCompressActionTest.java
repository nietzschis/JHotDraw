package org.jhotdraw.app.action;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Matic-ProBook
 */
public class TinyPngCompressActionTest {
    static boolean isCompressedSmaller(File original, File compressed) {
        return compressed.length() < original.length();
    }

    static URI getTestImageUrl(Class c) throws URISyntaxException {
        return c.getClassLoader().getResource("org/jhotdraw/draw/action/images/tree.png").toURI();
    }

    static String getCompressedFileName(File f) {
        return f.getPath().substring(0, f.getPath().lastIndexOf('.')) + "_compressed.png";
    }

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

    @Test
    public void testCompressImage() throws URISyntaxException {
        URI uri = getTestImageUrl(getClass());
        File f = new File(uri.getPath());
        String compressedFileName = getCompressedFileName(f);
        File fCompressed = new File(compressedFileName);
        boolean compressed = TinyPngCompressAction.compressImage(f);
        assertTrue(isCompressedSmaller(f, fCompressed));
    }
}
