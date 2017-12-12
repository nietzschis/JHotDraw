
package org.jhotdraw.app.action;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.action.AttributeAction;
import org.jhotdraw.draw.action.ColorIcon;
import org.jhotdraw.gui.JPopupButton;

/**
 *
 * @author lefoz
 */
public class MyColorsLoadAction {
    
    private java.util.List<ColorIcon> MY_COLORS;
    
    FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Text", "txt");
    JFileChooser jfc;
    
    public MyColorsLoadAction(){
        
    }
    public List<ColorIcon> loadTester(String filePath){
    MY_COLORS = new LinkedList<ColorIcon>();  
    MY_COLORS = loadMyColors(filePath);
    return  MY_COLORS;   
    }
    public void load(AttributeKey<Color> attributeKey,DrawingEditor editor,JPopupButton parent,String filePath){
    MY_COLORS = new LinkedList<ColorIcon>();  
    MY_COLORS = loadMyColors(filePath);
    if(MY_COLORS!=null)
    addToUI(attributeKey, editor, parent);
    
    }
    
    private List<ColorIcon> loadMyColors(String filePath){
     String[] hexColorList;
     List<ColorIcon> loadedList;
     if(filePath==null||filePath.isEmpty()){
     jfc = new JFileChooser();
        jfc.setDialogTitle("Load My Colors");
        jfc.addChoosableFileFilter(txtFilter);
        int returnValue = jfc.showOpenDialog(null);
    if (returnValue == JFileChooser.APPROVE_OPTION) {
         try {  
             hexColorList = readFile(jfc.getSelectedFile());
             loadedList = createList(hexColorList);
             return loadedList;
         } catch (IOException ex) {
             Logger.getLogger(MyColorsLoadAction.class.getName()).log(Level.SEVERE, null, ex);
         }
    }}
     else if(filePath!=null|!filePath.isEmpty()){
       try {    
             hexColorList = readFile(new File (filePath));
             loadedList = createList(hexColorList);
             return loadedList;
         } catch (IOException ex) {
             Logger.getLogger(MyColorsLoadAction.class.getName()).log(Level.SEVERE, null, ex);
         }  
     }
        loadedList = null;
        return loadedList;
    }
  
    private List<ColorIcon> createList(String[] colorArray){
        if (colorArray!=null && colorArray.length>0) {
        LinkedList<ColorIcon> m = new LinkedList<>();
        for (String hexString : colorArray ) {
            
           m.add(new ColorIcon(Color.decode(hexString), hexString)); 
        }        
        MY_COLORS = Collections.unmodifiableList(m);
        return MY_COLORS;
        }
        return null;
    }
    //Loads a My Colors .txt file to myColors List
    private  String[] readFile(File filepath) throws IOException {
        FileReader fileReader;
        BufferedReader bufferedReader;
        List<String> lines =new ArrayList<String>();
            try {                       
            fileReader = new FileReader(filepath);
            bufferedReader = new BufferedReader(fileReader);           
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();
            } 
        catch (FileNotFoundException ex) {
        Logger.getLogger(MyColorsLoadAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lines.toArray(new String[lines.size()]);
        }
        
        private void addToUI(AttributeKey<Color> attributeKey,DrawingEditor editor,JPopupButton parent){
        boolean hasNullColor = false;
        for (ColorIcon swatch : MY_COLORS) {
            AttributeAction a;
            String colorCode = ""+swatch.getName();
            HashMap<AttributeKey, Object> attributes = new HashMap<AttributeKey, Object>();
            attributes.put(attributeKey, swatch.getColor());
            if (swatch.getColor() == null) {
                hasNullColor = true;
            }
            parent.add(a =
                    new AttributeAction(
                    editor,
                    attributes,
                    colorCode,
                    swatch));
            a.putValue(Action.SHORT_DESCRIPTION, swatch.getName());
        }

        // No color
        if (!hasNullColor) { //Do nothing
        }; 
        }  
}
