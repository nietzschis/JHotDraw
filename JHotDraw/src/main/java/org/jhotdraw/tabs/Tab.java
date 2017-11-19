/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.tabs;

import java.util.UUID;
import org.jhotdraw.draw.Drawing;

/**
 *
 * @author pc4
 */
public class Tab
{
    private Drawing drawing;
    private String name;
    private String id;

    public Tab(Drawing drawing, String name)
    {
        this.drawing = drawing;
        this.name = name;
        this.id = UUID.randomUUID().toString();
    }
    
    public Drawing getDrawing()
    {
        return drawing;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getId()
    {
        return id;
    }
    
    
}
