/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.tabs;

/**
 *
 * @author pc4
 */
public interface Tabs
{
    Tab currentTab();
    void setCurrentTab(Tab tab);
    void add(Tab tab);
    void remove(Tab tab);
}
