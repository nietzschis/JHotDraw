
package org.jhotdraw.app.action;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jhotdraw.draw.action.ColorIcon;


/**
 *
 * @author lefoz
 */
public class MyColorsSaveAction {
    //private LinkedList<ColorIcon> myColorList = new LinkedList<>();
    FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Text", "txt", "text");
    JFileChooser jfc;
    JFrame frame; 
  
    public MyColorsSaveAction(){
    
    }
    
    public void save(LinkedList<ColorIcon> myColorList, String filePath){
    
    int result = saveMyColors(myColorList,filePath);
    switch(result){
                    case 1:
                        System.out.println("No Colors to save");
                        //JOptionPane.showMessageDialog(frame, "No Colors to save");
                        break;
                    case 2:
                        System.out.println("Error trying to save colors");
                        //JOptionPane.showMessageDialog(frame, "Error trying to save colors");
                        break;
                    case 3:
                        System.out.println("Colors saved");
                        //JOptionPane.showMessageDialog(frame, "Colors saved");
                }
    }
    private int saveMyColors(LinkedList<ColorIcon> myColorList, String filePath){
        if (myColorList.isEmpty()) return 1;
        if(filePath==null|| filePath.isEmpty()){
        jfc = new JFileChooser();
        jfc.setDialogTitle("Save My Colors");
        jfc.addChoosableFileFilter(txtFilter);
        int returnValue = jfc.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
		System.out.println("You selected the directory: " + jfc.getSelectedFile());
		boolean compltede = writeFile(myColorList, jfc.getSelectedFile().getPath()+".txt");
                    if (compltede) return 3; 
		}}
        else if(!filePath.isEmpty()){boolean compltede = writeFile(myColorList, filePath+".txt");
                    if (compltede) return 3;}
    return 2;
    }
    private final static String toHexString(Color colour) throws NullPointerException {
    String hexColour = Integer.toHexString(colour.getRGB() & 0xffffff);
    if (hexColour.length() < 6) {
    hexColour = "000000".substring(0, 6 - hexColour.length()) + hexColour;
    }
    return "#" + hexColour;
    }
    // Saves My Colors to a .txt File
    private Boolean writeFile( LinkedList<ColorIcon> data, String fileName){		
    PrintWriter out = null;                
    try {
        out = new PrintWriter(new FileWriter(new File(fileName)));
        for (ColorIcon color : data) {
        String hexColor = toHexString(color.getColor());
        out.write(hexColor+"\r\n");
	} 
	   return true;
		} 
	catch (IOException e) {			
	    System.err.println("Caught IOException: " +  e.getMessage());
	    return false;
	}		
	finally {
	    if (out != null) {
	        out.close();
	    }
		   
	}
}
}	
