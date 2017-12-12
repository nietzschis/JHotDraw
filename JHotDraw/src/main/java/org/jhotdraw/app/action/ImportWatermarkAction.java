/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import org.apache.commons.io.FilenameUtils;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.ExportableView;
import org.jhotdraw.app.JHotDrawFeatures;
import org.jhotdraw.app.View;
import org.jhotdraw.gui.JSheet;
import org.jhotdraw.gui.event.SheetEvent;
import org.jhotdraw.gui.event.SheetListener;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 *
 * @author Mads
 */
public class ImportWatermarkAction extends AbstractViewAction {

    public final static String ID = "file.importWatermark";
    private Component oldFocusOwner;

    /**
     * Creates a new instance.
     */
    public ImportWatermarkAction(Application app) {
        super(app);
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        labels.configureAction(this, ID);
    }

    @FeatureEntryPoint(JHotDrawFeatures.IMPORT_WATERMARK)
    public void actionPerformed(ActionEvent e) {
        ExportableView view = (ExportableView) getActiveView();
        if (view.isEnabled()) {
            ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
            oldFocusOwner = SwingUtilities.getWindowAncestor(view.getComponent()).getFocusOwner();

            JFileChooser fileChooser = view.getOpenChooser();
            JSheet.showSheet(fileChooser, view.getComponent(), labels.getString("filechooser.importWatermark"), new SheetListener() {
                @Override
                public void optionSelected(SheetEvent evt) {
                    if (evt.getOption() == JFileChooser.APPROVE_OPTION) {
                        try {
                            File watermarkFile = evt.getFileChooser().getSelectedFile();
                            String fileUtils = FilenameUtils.getExtension(watermarkFile.getPath());
                            if(fileUtils.equals("png") || fileUtils.equals("bmp") || fileUtils.equals("jpg") || fileUtils.equals("gif")){
                               BufferedImage watermarkImage = ImageIO.read(watermarkFile);
                               view.setWatermark(watermarkImage); 
                            }                       
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        view.setEnabled(true);
                        if (oldFocusOwner != null) {
                            oldFocusOwner.requestFocus();
                        }
                    }
                }
            });
        }
    }

}
