package org.jhotdraw.samples.svg;

import java.util.List;
import javax.swing.JMenu;
import org.jhotdraw.app.DefaultApplicationModel;
import org.jhotdraw.app.DefaultSDIApplication;
import org.jhotdraw.util.ResourceBundleUtil;
import org.junit.AfterClass;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Niels
 */
public class SVGApplicationModelTest {

    private static DefaultApplicationModel model;
    private static ResourceBundleUtil appLabels;
    private static List<JMenu> menus;

    @BeforeClass
    public static void setUpClass() {
        model = new SVGApplicationModel();
        appLabels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        menus = model.createMenus(new DefaultSDIApplication(), new SVGView());
    }
    
    @AfterClass
    public static void tearDownClass() {
        model = null;
        appLabels = null;
        menus = null;
    }
    
    @Test
    public void testFileMenuCreation() {
        for (JMenu menu : menus) {
            if (menu.getText().equals(appLabels.getTextProperty("file"))) {
                return;
            }
        }
        fail("File menu not created");
    }
    
    @Test
    public void testEditMenuCreation() {
        for (JMenu menu : menus) {
            if (menu.getText().equals(appLabels.getTextProperty("edit"))) {
                return;
            }
        }
        fail("Edit menu not created");
    }
    
    @Test
    public void testCollaborationMenuCreation() {
        for (JMenu menu : menus) {
            if (menu.getText().equals(appLabels.getTextProperty("collaboration"))) {
                return;
            }
        }
        fail("Collaboration menu not created");
    }

    @Test
    public void testViewMenuCreation() {
        for (JMenu menu : menus) {
            if (menu.getText().equals(appLabels.getTextProperty("view"))) {
                return;
            }
        }
        fail("View menu not created");
    }
    
    @Test
    public void testHelpMenuCreation() {
        for (JMenu menu : menus) {
            if (menu.getText().equals(appLabels.getTextProperty("help"))) {
                return;
            }
        }
        fail("Help menu not created");
    }

}
