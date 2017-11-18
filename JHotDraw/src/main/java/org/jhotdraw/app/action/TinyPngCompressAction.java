package org.jhotdraw.app.action;

import com.tinify.*;
import java.io.File;

/**
 *
 * @author Matic-ProBook
 */
public class TinyPngCompressAction {
    
    public static boolean compressImage(File f) {
        try {
            // Use the Tinify API client.
            Tinify.setKey("nNBJKdX3DsD-XO181v1ws1dOptFy2cZ3");
            Source source = Tinify.fromFile(f.getPath());
            String fileName = f.getPath().substring(0, f.getPath().lastIndexOf('.'));
            source.toFile(fileName + "_compressed.png");
            int compressionsThisMonth = Tinify.compressionCount();
            System.out.println("Compressions this month: " + compressionsThisMonth);
            return true;
        } catch (AccountException e) {
            System.out.println("Verify your API key and account limit. The error message is: " + e.getMessage());
        } catch (ClientException e) {
            System.out.println("Check your source image and request options. The error message is: " + e.getMessage());
        } catch (ServerException e) {
            System.out.println("Temporary issue with the Tinify API. The error message is: " + e.getMessage());
        } catch (ConnectionException e) {
            System.out.println("A network connection error occurred. The error message is: " + e.getMessage());
        } catch (java.lang.Exception e) {
            System.out.println("Something else went wrong, unrelated to the Tinify API. The error message is: " + e.getMessage());
        }
        return false;
    }
}
