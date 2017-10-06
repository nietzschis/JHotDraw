package org.jhotdraw.app.action;

import com.tinify.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Matic-ProBook
 */
public class TinyPngCompressAction {
    
    public static byte[] getByteArrayFromFile(File f) throws FileNotFoundException, IOException {
        //org.apache.commons.io.FileUtils.readFileToByteArray(f);
        // guava
        
        FileInputStream fileInputStream = new FileInputStream(f);
        int byteLength = (int) f.length(); //bytecount of the file-content
        byte[] filecontent = new byte[byteLength];
        fileInputStream.read(filecontent, 0, byteLength);
        return filecontent;
    }
    
    public static void compressImage(File f) {
        try {
            // Use the Tinify API client.
            Tinify.setKey("nNBJKdX3DsD-XO181v1ws1dOptFy2cZ3");
            
            Source source = Tinify.fromFile(f.getPath());
            source.toFile(f.getPath() + "compressed.png");
            
            int compressionsThisMonth = Tinify.compressionCount();

            /* Example
            Source source = Tinify.fromFile("unoptimized.jpg");
            source.toFile("optimized.jpg");

            byte[] sourceData = Files.readAllBytes(Paths.get("unoptimized.jpg"));
            byte[] resultData = Tinify.fromBuffer(sourceData).toBuffer();
            */
            
            /* My code
            byte[] sourceData = getByteArrayFromFile(f);
            //byte[] sourceData = Files.readAllBytes(Paths.get(f.getPath()));
            byte[] resultData = Tinify.fromBuffer(sourceData).toBuffer();
            Path path = Paths.get(f.getPath());
            Files.write(path, resultData);
            */
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
    }
}
