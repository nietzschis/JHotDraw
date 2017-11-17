/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevan14.feature.openfolder;

import java.io.File;

/**
 *
 * @author Bruger
 */
public class OpenListenerImpl implements OpenListener{

    private Strategy strategy;
    private static String OS = System.getProperty("os.name").toLowerCase();
    
    public OpenListenerImpl() {
    
        if(isWindows()) {
            this.strategy = new WindowsStrategy();
        }
        
        if(isMac()) {
            this.strategy = new MacStrategy();
        }
        
        if(isUnix()) {
            this.strategy = new UnixStrategy();
        }
        
        if(isSolaris()) {
            this.strategy = new SolarisStrategy();
        }
        
    }

    @Override
    public void openBrowser(File file) {
        strategy.action(file);
    }
    
    	public static boolean isWindows() {

		return (OS.indexOf("win") >= 0);

	}

	public static boolean isMac() {

		return (OS.indexOf("mac") >= 0);

	}

	public static boolean isUnix() {

		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );

	}

	public static boolean isSolaris() {

		return (OS.indexOf("sunos") >= 0);

	}
    
}
