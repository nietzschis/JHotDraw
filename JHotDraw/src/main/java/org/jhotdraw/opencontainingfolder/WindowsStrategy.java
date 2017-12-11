/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.opencontainingfolder;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kevan14
 */
public class WindowsStrategy implements Strategy {

    @Override
    public void action(File file) {
        try {
            Runtime.getRuntime().exec("explorer.exe /select," + file.getPath());
        } catch (IOException ex) {
            Logger.getLogger(WindowsStrategy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
