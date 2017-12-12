<<<<<<< HEAD
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg;


import javax.swing.undo.*;
import org.jhotdraw.samples.svg.figures.*;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.BezierTool;
import org.jhotdraw.util.*;
import org.jhotdraw.undo.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.*;
import org.jhotdraw.geom.*;
import org.jhotdraw.samples.svg.gui.AbstractToolBar;
import org.jhotdraw.beans.AbstractBean;
import javax.swing.event.*;
import javax.swing.ImageIcon;
import org.jhotdraw.samples.svg.Erasing;

/**
 *
 * @author Frank Frederiksen-Moeller
 * This tool will completely remove a figure or a drawing from the active Drawing canvas
 * This class handles the configuration of the Eraser Tool in JHotDraw.  * 
 */


public class EraserTool extends AbstractTool
{
    protected DrawingEditor drawEdit;
    protected Point hotspot = new Point(0,0);
    public Figure fig;
    public AbstractToolBar atb;
    public Erasing erase;
    public String name;
    public String presentationName;
    
    public EraserTool(AbstractToolBar atb)
    {
        this.atb = atb;
        erase = new Erasing(this);             
                
    }
    //This method configures the right cursor icon according the OS of the user
    //It also prepares a label in the Edit menu for undo and redo an erased figure. 
    @Override
    public void activate(DrawingEditor editor) 
    {
        this.drawEdit = editor;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        String osName = System.getProperty("os.name").toLowerCase();
        
        if (osName.startsWith("mac os x"))
            {
                Image image = new ImageIcon("src/main/resources/org/jhotdraw/samples/svg/action/images/createEraserToolCursorMac.png").getImage();
                Cursor c = toolkit.createCustomCursor(image, hotspot, "eraser");
                atb.getEditor().getActiveView().setCursor(c);
            }
            else if (osName.startsWith("windows"))
                {
                    Image image = new ImageIcon("src\\main\\resources\\org\\jhotdraw\\samples\\svg\\action\\images\\createEraserToolCursorWin.png").getImage();
                    Cursor c = toolkit.createCustomCursor(image, hotspot, "eraser");
                    atb.getEditor().getActiveView().setCursor(c);
            
                }
            else 
            {
                Image image = new ImageIcon("src/main/resources/org/jhotdraw/samples/svg/action/images/createEraserToolCursorMac.png").getImage();
                Cursor c = toolkit.createCustomCursor(image, hotspot, "eraser");
                atb.getEditor().getActiveView().setCursor(c);                
            }       
   
        if (name == null) {
            ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
            name = labels.getString("edit.erasedFigure.text");
            
        }
        this.presentationName = name;     

    }   
    
    @Override
    public void mouseDragged(MouseEvent evt) {
       Point.Double p = new Point.Double(evt.getX(), evt.getY());
       fig = atb.getEditor().getActiveView().getDrawing().findFigure(p);

       if (fig == null)
        {
            atb.getEditor().getActiveView().getDrawing().findFigure(p);
            
        }
        else if (fig.contains(p)) 
        { 
           erase.Erasing(fig);
           
        }
 
    }
    
}
=======
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg;


import javax.swing.undo.*;
import org.jhotdraw.samples.svg.figures.*;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.BezierTool;
import org.jhotdraw.util.*;
import org.jhotdraw.undo.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.*;
import org.jhotdraw.geom.*;
import org.jhotdraw.samples.svg.gui.AbstractToolBar;
import org.jhotdraw.beans.AbstractBean;
import javax.swing.event.*;
import javax.swing.ImageIcon;
import org.jhotdraw.samples.svg.Erasing;

/**
 *
 * @author Frank Frederiksen-Moeller
 * This tool will completely remove a figure or a drawing from the active Drawing canvas
 * This class handles the configuration of the Eraser Tool in JHotDraw.  * 
 */


public class EraserTool extends AbstractTool
{
    protected Point hotspot = new Point(0,0);
    public Figure fig;
    public AbstractToolBar atb;
    public Erasing erase;
    public String name;
    public String presentationName;
    
    public EraserTool(AbstractToolBar atb)
    {
        this.atb = atb;
        erase = new Erasing(this);             
                
    }
    //This method configures the right cursor icon according the OS of the user
    //It also prepares a label in the Edit menu for undo and redo an erased figure. 
    @Override
    public void activate(DrawingEditor editor) 
    {
        super.activate(editor);
        this.editor = editor;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        String osName = System.getProperty("os.name").toLowerCase();
        
        if (osName.startsWith("mac os x"))
            {
                Image image = new ImageIcon("src/main/resources/org/jhotdraw/samples/svg/action/images/createEraserToolCursorMac.png").getImage();
                Cursor c = toolkit.createCustomCursor(image, hotspot, "eraser");
                atb.getEditor().getActiveView().setCursor(c);
            }
            else if (osName.startsWith("windows"))
                {
                    Image image = new ImageIcon("src\\main\\resources\\org\\jhotdraw\\samples\\svg\\action\\images\\createEraserToolCursorWin.png").getImage();
                    Cursor c = toolkit.createCustomCursor(image, hotspot, "eraser");
                    atb.getEditor().getActiveView().setCursor(c);
            
                }
            else 
            {
                Image image = new ImageIcon("src/main/resources/org/jhotdraw/samples/svg/action/images/createEraserToolCursorMac.png").getImage();
                Cursor c = toolkit.createCustomCursor(image, hotspot, "eraser");
                atb.getEditor().getActiveView().setCursor(c);                
            }       
   
        if (name == null) {
            ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
            name = labels.getString("edit.erasedFigure.text");
            
        }
        this.presentationName = name;     

    }   
    
    @Override
    public void mouseDragged(MouseEvent evt) {
       Point.Double p = new Point.Double(evt.getX(), evt.getY());
       fig = atb.getEditor().getActiveView().getDrawing().findFigure(p);

       if (fig == null)
        {
            atb.getEditor().getActiveView().getDrawing().findFigure(p);
            
        }
        else if (fig.contains(p)) 
        { 
           erase.Erasing(fig);
           
        }
 
    }
    
}
>>>>>>> refs/remotes/origin/master
