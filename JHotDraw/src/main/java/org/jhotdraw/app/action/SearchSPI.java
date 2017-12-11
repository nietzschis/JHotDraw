/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import java.util.ArrayList;
import javax.swing.Action;

/**
 *
 * @author Daniel
 */
public interface SearchSPI {
    public void setComboBox(ArrayList<Action> actions);
}
