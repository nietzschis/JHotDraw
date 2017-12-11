/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.opencontaingfolder;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.jhotdraw.opencontainingfolder.OpenListener;
import org.jhotdraw.opencontainingfolder.OpenListenerImpl;

/**
 *
 * @author kevan14
 */
public class CorrectStrategyTest {
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void checkCorrectImplementationStrategy() {
        
        //A strategy is always null if the openlistener is not setting one on creation.
        //Hereby the strategy cannot be null, if the construction and detection of OS went well...
        OpenListener openFolder = new OpenListenerImpl();
        
        assertNotNull(openFolder.getStrategy());
    }
}
