package org.jhotdraw.app.action;

import java.io.File;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Matic-ProBook
 */
public class TinyPngCompressTest {
    
    @Test
    public void tinyPngCompressTest() {
        String fileName = "tree.png";
        File f = new File("D:\\" + fileName);
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
