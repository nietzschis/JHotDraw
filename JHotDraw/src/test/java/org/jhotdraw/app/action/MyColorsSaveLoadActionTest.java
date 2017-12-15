/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import java.io.File;
import java.util.LinkedList;
import org.jhotdraw.draw.action.ColorIcon;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lefoz
 */
public class MyColorsSaveLoadActionTest {
    
    public MyColorsSaveLoadActionTest() {
    }
    
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
    public void MyColorsSaveActionTest(){
     LinkedList<ColorIcon> testList = new LinkedList<>();
        testList.add(new ColorIcon(0x800000, "Cayenne"));
        testList.add(new ColorIcon(0x808000, "Asparagus"));
        testList.add(new ColorIcon(0x008000, "Clover"));
        
    String filePath = "MyColorsTestSaveFile";
    MyColorsSaveAction mCs = new MyColorsSaveAction();
    mCs.save(testList,filePath);
    File shouldExist = new File(filePath+".txt");
    assertTrue(shouldExist.exists());
    }
    
    @Test
    public void MyColorsLoadActionTest(){
    String filePath = "MyColorsTestSaveFile.txt";
    MyColorsLoadAction mCl = new MyColorsLoadAction();
    java.util.List<ColorIcon> testList = mCl.loadTester(filePath);
    assertTrue(!testList.isEmpty());
    assertTrue(3==testList.size());
    File testfile =new File("MyColorsTestSaveFile.txt");
    if(testfile.exists()) testfile.delete();
    }    
}
