/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.collaboration.common;

import java.io.Serializable;
import java.util.Map;
import org.jhotdraw.draw.AttributeKey;

/**
 *
 * @author niclasmolby
 */
public class SVGRectDTO implements Serializable {
    public double x;
    public double y;
    public double width;
    public double height;
    public double rx;
    public double ry;
    public Map<AttributeKey, Object> attributes;
}
