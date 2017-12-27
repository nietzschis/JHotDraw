/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhotdraw.util;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

/**
 *
 * @author JOnes
 */
public class GuiSizes {
    
    private static int widthOfScreen;
    private static int heightOfScreen;
    
    public static void getResolution() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        GuiSizes.setWidthOfScreen(width);
        GuiSizes.setHeightOfScreen(height);
    }

    private static void setWidthOfScreen(int widthOfScreen) {
        GuiSizes.widthOfScreen = widthOfScreen;
    }

    private static void setHeightOfScreen(int heightOfScreen) {
        GuiSizes.heightOfScreen = heightOfScreen;
    }

    public static Dimension getPreferredButtonSizes() {
        return new Dimension(widthOfScreen / 87, heightOfScreen / 49);
    }
}